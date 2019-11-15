//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;




//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente!
//
public class ProcesadorAdivina {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	//private InputStream inputStream;
	private BufferedReader inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	//private OutputStream outputStream;
	private PrintWriter outputStream;

	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;

	private int num_aciertos = 0;

	private String OK = "Ok";
	private String ERROR = "ERROR";
	private String Adivinanza = "¿quieres una adivinanza? (si/no)";
	private String Adios = "Chao pescao";
	private ArrayList<Pair<String, String> > adivinanzas;
	private ArrayList<Pair<String, String> > pistas;
	private String Fallo = "Te has equivocado, ¿Quieres otra pista? (si/no)";

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorAdivina(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();

		adivinanzas = new ArrayList<Pair<String,String>>();
		adivinanzas.add(new Pair<>("Todos me quieren para descansar. Si ya te te lo he dicho! No pienses mas", "silla"));
		adivinanzas.add(new Pair<>("Oro parece, plata no es", "platano"));
		adivinanzas.add(new Pair<>("Te la digo y no me entiendes, te la repito y no me entiendes", "tela"));
		adivinanzas.add(new Pair<>("Soy ave y soy llana, pero no tengo pico ni alas", "avellanas"));
		adivinanzas.add(new Pair<>("Son de color chocolate, se ablandan con el calor y si se meten al horno explotan con gran furor.", "castañas"));
		adivinanzas.add(new Pair<>("Iba una vaca de lado, luego resultó pescado.", "bacalao"));
		adivinanzas.add(new Pair<>("Lleva años en el mar y aún no sabe nadar.", "arena"));
		adivinanzas.add(new Pair<>("No son flores, pero tienen plantas y olores.", "pies"));


		pistas = new ArrayList<Pair<String,String>>();
		pistas.add(new Pair<>("Te sientas en ella", "Tiene cuatro patas"));
		pistas.add(new Pair<>("Es una fruta","Tiene mucho potasio"));
		pistas.add(new Pair<>("Con ella se hace ropa","Se fabrica con hilos"));
		pistas.add(new Pair<>("Es un fruto seco", "Con ella se fabrica la Nutella TM"));
		pistas.add(new Pair<>("Es un fruto seco","Se come en otoño"));
		pistas.add(new Pair<>("Es un pescado","Mucha gente lo suele escribir mal al acabarlo en \"-ado\""));
		pistas.add(new Pair<>("Molesta mucho en la playa","Puedes hacer castillos con ella"));
		pistas.add(new Pair<>("Es una parte del cuerpo","Los zapatos les quedan muy bien"));



	}


	// Aquí es donde se realiza el procesamiento realmente:
	synchronized void procesa(){

		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		//byte [] datosRecibidos=new byte[1024];
		//int bytesRecibidos=0;
		String datosRecibidos = new String();

		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		//byte [] datosEnviar;
		String datosEnviar = new String();


		try {
			// Obtiene los flujos de escritura/lectura
			inputStream=new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
			outputStream=new PrintWriter(socketServicio.getOutputStream(), true);

			// Lee la frase a Yodaficar:
			////////////////////////////////////////////////////////
			// read ... datosRecibidos.. (Completar)
			////////////////////////////////////////////////////////

			datosRecibidos = inputStream.readLine();


			if (datosRecibidos.equals("conexion") ){

				System.out.println("Recibida conexión de un cliente, mandamos si quiere adivinanza");

				outputStream.println(Adivinanza);
				outputStream.flush();





				datosRecibidos = inputStream.readLine();

				System.out.println("Recibida respuesta: " + datosRecibidos);

				while (datosRecibidos.equals("si")){

			 		// datosRecibidos == si
					// seleccionamos la adivinanza
					int azar = random.nextInt(adivinanzas.size() );

					System.out.println("Mandamos una nueva adivinanza");
					// mandamos la adivinanza
					outputStream.println(adivinanzas.get(azar).getKey() + " (escribe \"otra\" para cambiar de adivinanza)");
					outputStream.flush();


					datosRecibidos = inputStream.readLine();

					System.out.println("Recibida respuesta de adivinanza: " + datosRecibidos);
					while (!datosRecibidos.equals(adivinanzas.get(azar).getValue()) && !datosRecibidos.equals("otra") ) {
						// el servidor recibe la respuesta a la adivinanza

						outputStream.println(Fallo);
						outputStream.flush();

						datosRecibidos = inputStream.readLine();
						System.out.println("Recibido información sobre si quiere pista: " + datosRecibidos);

						if (datosRecibidos.equals("si")){
							int p = random.nextInt(2);

							String pista_a_mandar;

							if (p == 0){
								pista_a_mandar = pistas.get(azar).getKey();
							} else {
								pista_a_mandar = pistas.get(azar).getValue();
							}

							System.out.println("Mandamos la pista");
							outputStream.println(pista_a_mandar);
							outputStream.flush();
						} else {
							outputStream.println(adivinanzas.get(azar).getKey() + " (escribe \"otra\" para cambiar de adivinanza)");
							outputStream.flush();
						}

						System.out.println("Leemos nueva respuesta a la adivinanza");
						datosRecibidos = inputStream.readLine();



					}

					if ( !datosRecibidos.equals("otra") ){

						System.out.println("Recibido que quiere acierta la adivinanza, mandamos si quiere una nueva");

						num_aciertos++;
						outputStream.println("Has acertado, " + Adivinanza);
						outputStream.flush();
					} else {
						System.out.println("Recibido que quiere otra adivinanza, madamos si quiere nueva adivinanza");
						outputStream.println(Adivinanza);
						outputStream.flush();

					}


					datosRecibidos = inputStream.readLine();



				}

				System.out.println("Recibido que no quiere más adivinanza, mandamos despedida y cerramos la conexión");

				outputStream.println(Adios + ", has adivinado " + num_aciertos + " adivinanzas.");

				outputStream.flush();

				socketServicio.close();

			}







			/*

			// Yoda hace su magia:
			// Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
			String peticion= datosRecibidos;
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);
			// Convertimos el String de respuesta en una array de bytes:
			datosEnviar=respuesta;

			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
			// ... write ... datosEnviar... datosEnviar.length ... (Completar)
			////////////////////////////////////////////////////////
			outputStream.println(datosEnviar);

			outputStream.flush();

			socketServicio.close();
			*/


		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";

		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];

			s[j]=s[k];
			s[k]=tmp;
		}

		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}

		return resultado;
	}




	public class Pair<L,R> {

	  private final L left;
	  private final R right;

	  public Pair(L left, R right) {
	    this.left = left;
	    this.right = right;
	  }

	  public L getKey() { return left; }
	  public R getValue() { return right; }

	  @Override
	  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof Pair)) return false;
	    Pair pairo = (Pair) o;
	    return this.left.equals(pairo.getKey()) &&
	           this.right.equals(pairo.getValue());
	  }

	}

}

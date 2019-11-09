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


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente!
//
public class ProcesadorYodafy {
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

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}


	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){

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
}

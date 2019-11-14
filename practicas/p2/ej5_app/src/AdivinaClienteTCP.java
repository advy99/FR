//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class AdivinaClienteTCP {



	public static void main(String[] args) {

		//byte []buferEnvio;
		String buferEnvio = new String();

		//byte []buferRecepcion=new byte[256];
		String buferRecepcion = new String();

		//int bytesLeidos=0;
		//para bytes leidos usaremos metodo String
		String caracteresLeidos = new String();

		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;

		Scanner in = new Scanner(System.in);

		// Socket para la conexión TCP
		Socket socketServicio=null;

		try {


			String conexion = "conexion";
			String no = "no";
			String si = "si";
			String otra_adivinanza; // se lle por entrada de teclado = "otra_adivinanza";
			String acierta; // se lle por enrtada de teclado (respuesta dada a la adivinanza)



			boolean salir = false;

			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			// socketServicio= ... (Completar)
			//////////////////////////////////////////////////////
			socketServicio = new Socket (host, port);

			//InputStream inputStream = socketServicio.getInputStream();
			//OutputStream outputStream = socketServicio.getOutputStream();
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(), true);

			BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:

			// primera conexion
			outPrinter.println(conexion);
			outPrinter.flush();



			// recibimos la respuesta del servidor, que sera si queremos una adivinanza
			caracteresLeidos = inReader.readLine();


			do{


				// leemos de teclado si contestar
				do{
					System.out.println(caracteresLeidos);

					buferEnvio = in.nextLine();
					buferEnvio = buferEnvio.toLowerCase();
				} while ( !buferEnvio.equals("no") && !buferEnvio.equals("si"));

				// mandamos la respuesta
				outPrinter.println(buferEnvio);
				outPrinter.flush();


				// recibimos la respuesta del servidor
				caracteresLeidos = inReader.readLine();


				if (!caracteresLeidos.substring(0, 4).equals("Chao") ){

					boolean correcto = false;
					boolean quiere_otra = false;

					do {

						// escribimos la adivinanza
						System.out.println(caracteresLeidos);


						// leemos la respuesta a la adivinanza
						buferEnvio = in.nextLine();
						buferEnvio = buferEnvio.toLowerCase();

						// mandamos la respuesta del cliente
						outPrinter.println(buferEnvio);
						outPrinter.flush();


						if (!buferEnvio.equals("otra")){

							caracteresLeidos = inReader.readLine();

							if ( !caracteresLeidos.substring(0, 3).equals("Has") ){
								// si no has acertado, pide pista

								do{
									System.out.println(caracteresLeidos);
									buferEnvio = in.nextLine();
									buferEnvio = buferEnvio.toLowerCase();
								} while ( !buferEnvio.equals("no") &&  !buferEnvio.equals("si") );

								// mandamos la respuesta
								outPrinter.println(buferEnvio);
								outPrinter.flush();

								caracteresLeidos = inReader.readLine();


							} else {
								correcto = true;
							}
						} else {
							quiere_otra = true;
							caracteresLeidos = inReader.readLine();
						}

					} while (!correcto && !quiere_otra);


				} else {
					salir = true;
				}



			} while(!salir);

			System.out.println(caracteresLeidos);


			socketServicio.close();




			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class AdivinaServidorConcurrente {

	public static void main(String[] args) {

		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		//byte []buffer=new byte[256];
		String buffer = new String ();

		// Número de bytes leídos
		//int bytesLeidos=0;

		ServerSocket serverSocket = null;

		Socket socketServicio = null;

		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			// ...serverSocket=... (completar)
			//////////////////////////////////////////////////
			serverSocket = new ServerSocket(port);
			// Mientras ... siempre!
			do {

				// Aceptamos una nueva conexión con accept()
				/////////////////////////////////////////////////
				// socketServicio=... (completar)
				//////////////////////////////////////////////////
				try {
					socketServicio = serverSocket.accept();
				} catch (IOException e){
					System.out.println("Error: no se pudo aceptar la conexión solicitada");
				}
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				Hebrita hebra = new Hebrita(socketServicio);
				hebra.start();


			} while (true);

		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}

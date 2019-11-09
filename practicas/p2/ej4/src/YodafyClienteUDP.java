//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.*;

public class YodafyClienteUDP {

	public static void main(String[] args) {

		byte []buferEnvio=new byte[256];
		//int bytesLeidos=0;

		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;

		InetAddress direccion;
		DatagramPacket paquete;

		DatagramSocket socket = null;

		try {

			socket = new DatagramSocket();

			direccion = InetAddress.getByName(host);




			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:
			buferEnvio="Al monte del volcán debes ir sin demora".getBytes();

			paquete = new DatagramPacket(buferEnvio, buferEnvio.length, direccion, port);

			socket.send(paquete);

			paquete = new DatagramPacket(buferEnvio, buferEnvio.length);
			socket.receive(paquete);

			String recibido = new String(paquete.getData(), 0, paquete.getData().length);




			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			System.out.println(recibido);

			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////
			// ... close(); (Completar)
			//////////////////////////////////////////////////////
			socket.close();

			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}

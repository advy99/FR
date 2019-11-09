import java.lang.Thread;
import java.net.Socket;


public class Hebrita extends Thread{

   ProcesadorYodafy procesador;

   Hebrita(Socket socketServicio){
      procesador = new ProcesadorYodafy(socketServicio);
   }

   public void run(){
      procesador.procesa();
   }

}

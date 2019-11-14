import java.lang.Thread;
import java.net.Socket;


public class Hebrita extends Thread{

   ProcesadorAdivina procesador;

   Hebrita(Socket socketServicio){
      procesador = new ProcesadorAdivina(socketServicio);
   }

   public void run(){
      procesador.procesa();
   }

}

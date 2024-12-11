import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server_Spam implements Runnable {
    private final static int MAX_BYTES = 1400;
    private final static String COD_TEXTO = "UTF-8";
    private final static int PUERTO = 50000;
    private InetAddress IPCliente;
    private int puertoCliente;
    private DatagramSocket serverSocket;
    public Server_Spam(InetAddress IPCliente, int puertoCliente,DatagramSocket serverSocket){
        this.IPCliente = IPCliente;
        this.puertoCliente = puertoCliente;
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {
        try(DatagramSocket serverSocket = new DatagramSocket(PUERTO)){
            while (true){
                System.out.println("Esperando usuario...");
                byte[] datosRecibidos = new byte[MAX_BYTES];
                DatagramPacket packetRecibido = new DatagramPacket(datosRecibidos,datosRecibidos.length);
                serverSocket.receive(packetRecibido);
                InetAddress IPCliente = packetRecibido.getAddress();
                int puertoCliente = packetRecibido.getPort();
                Server_Spam ss = new Server_Spam(IPCliente,puertoCliente,serverSocket);
                ss.run();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try{
                byte[] b = "Spam".getBytes(COD_TEXTO);
                DatagramPacket packetSpam = new DatagramPacket(b,b.length,IPCliente,puertoCliente);
                serverSocket.send(packetSpam);
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

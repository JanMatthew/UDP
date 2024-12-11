import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente_Victima {
    private final static int PUERTO = 50000;
    private final static String COD_TEXTO = "UTF-8";
    private final static int MAX_BYTES = 1400;

    public static void main(String[] args) {
        try(DatagramSocket clientSocket = new DatagramSocket();
            InputStreamReader isrStdin = new InputStreamReader(System.in,COD_TEXTO);
            BufferedReader brStdin = new BufferedReader(isrStdin)){
            InetAddress IPServidor = InetAddress.getByName("localhost");
            byte[] b = "h".getBytes(COD_TEXTO);
            DatagramPacket paqueteEnviado = new DatagramPacket(b,b.length,IPServidor,PUERTO);
            clientSocket.connect(IPServidor,PUERTO);
            clientSocket.send(paqueteEnviado);

            while (true){
                byte[] datosRecibidos = new byte[MAX_BYTES];
                DatagramPacket packetRecibido = new DatagramPacket(datosRecibidos,datosRecibidos.length);
                clientSocket.receive(packetRecibido);
                String respuesta = new String(packetRecibido.getData(),0,packetRecibido.getLength(),COD_TEXTO);
                System.out.println(respuesta);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

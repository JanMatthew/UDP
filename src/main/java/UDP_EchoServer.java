import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;
public class UDP_EchoServer {
    private final static int MAX_BYTES = 1400;
    private final static String COD_TEXTO = "UTF-8";
    private final static int PUERTO = 50000;

    public static void main(String[] args) {
        try(DatagramSocket serverSocket = new DatagramSocket(PUERTO)){
            System.out.println("Creado socket de datagramas");
            while (true){
                System.out.println("Esperando datos...");

                byte[] datosRecibidos = new byte[MAX_BYTES];
                DatagramPacket packetRecibido = new DatagramPacket(datosRecibidos,datosRecibidos.length);
                serverSocket.receive(packetRecibido);
                String lineaRecibida = new String(packetRecibido.getData(),0,packetRecibido.getLength(),COD_TEXTO);
                InetAddress IPCliente = packetRecibido.getAddress();
                int puertoCliente = packetRecibido.getPort();
                System.out.printf("Recibido datagrama de %s:%d (%s)\n", IPCliente.getHostAddress(), puertoCliente, lineaRecibida);
                String respuesta = "#" + lineaRecibida + "#";
                byte[] b = respuesta.getBytes(COD_TEXTO);
                DatagramPacket packetEnviado = new DatagramPacket(b,b.length,IPCliente,puertoCliente);
                serverSocket.send(packetEnviado);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

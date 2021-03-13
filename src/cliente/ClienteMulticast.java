package cliente;

import estres.Globals;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteMulticast extends Thread{
    private static int MULTICAST_PORT = 6868;
    private static String IP_MULTICAST = "239.192.0.1";
    private MulticastSocket socket;
    private InetAddress direccion;
    private Cliente cliente;

    public ClienteMulticast(Cliente cliente){
        this.cliente=cliente;
        try {
            direccion = InetAddress.getByName(IP_MULTICAST);
            socket = new MulticastSocket(MULTICAST_PORT);
            socket.joinGroup(direccion);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteMulticast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteMulticast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
            boolean ganador = false;
            while (!ganador) {
                try {
                    byte[] buffer = new byte[1000];
                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                    socket.receive(messageIn);

                    String message = new String(messageIn.getData()).trim();
                    if(!Globals.debug) System.out.println(message);
                    String arr[] = message.split(":");
                    if (arr[0].equals("W")) { //Se registra winner
                        ganador = true;
                        cliente.reportWinner(arr[1]);
                    } else if (arr[0].equals("P")) {
                        cliente.cambiaTopo((int) Integer.parseInt(arr[1]));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClienteMulticast.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            socket.close();

    }
}

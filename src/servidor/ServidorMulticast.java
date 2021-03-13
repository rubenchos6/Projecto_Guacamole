package servidor;

import estres.Globals;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorMulticast extends Thread{

    private JuegoGuacamole juego;
    private static int MULTICAST_PORT=6868;
    private MulticastSocket socket;
    private InetAddress direccion;
    private static String IP_MULTICAST= "239.192.0.1";
    private static int CHANGE_POS_TIME=5000;

    public ServidorMulticast (JuegoGuacamole juego) {
        this.juego=juego;
    }

    public void despliegue(){
        try {
            this.socket = new MulticastSocket(MULTICAST_PORT);
            this.direccion=InetAddress.getByName(IP_MULTICAST);
            socket.joinGroup(direccion);
            System.out.println("Servicio multicast desplegado");
            this.start();
        } catch (IOException ex) {
            System.out.println("ServidorMulticast: Problemas para unirse al grupo.");
            Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while(true) {
            while (!juego.getFinalizado()) {
                juego.mueveTopo();
                String message = "Pos:"+juego.obtenPosicion();
                byte[] m = message.getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, direccion, MULTICAST_PORT);
                try {
                    socket.send(messageOut);
                    Thread.sleep(CHANGE_POS_TIME);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String message = "W:" + juego.getWinner();
            System.out.println("JUEGO TERMINADO");
            byte[] m = message.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, direccion, MULTICAST_PORT);
            juego.restart();
            try {
                socket.send(messageOut);
                Thread.sleep(CHANGE_POS_TIME);
            } catch (IOException ex) {
                Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

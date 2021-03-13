package servidor;

import estres.Globals;

public class Starter {

    public static void main (String args[]){
        //Inicia servidores RMI, Multicast y TCP
        // Tiene que iniciar el juego
        System.setProperty("sun.net.maxDatagramSockets", "500");
        System.setProperty("java.net.preferIPv4Stack", "true"); // Para el multicast

        int n= Globals.topos;
        JuegoGuacamole juego= new JuegoGuacamole(n);
        ServidorRMI rmi= new ServidorRMI(juego);
        ServidorMulticast multi= new ServidorMulticast(juego);
        ServidorTCP tcp = new ServidorTCP(juego);

        rmi.despliegue();
        multi.despliegue();
        tcp.despliegue();

    }
}

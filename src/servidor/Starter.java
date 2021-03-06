package servidor;

public class Starter {

    public static void main (String args[]){
        //Inicia servidores RMI, Multicast y TCP
        // Tiene que iniciar el juego

        int n=3, m=3;
        JuegoGuacamole juego= new JuegoGuacamole(n,m);
        ServidorRMI rmi= new ServidorRMI(juego);
        ServidorMulticast multi= new ServidorMulticast(juego);


    }
}

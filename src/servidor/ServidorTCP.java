package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

    private static int TCP_PORT= 7896;
    JuegoGuacamole juego;

    private class Connection extends Thread{
        DataInputStream in;
        DataOutputStream out;
        Socket clientSocket;
        JuegoGuacamole juego;

        public Connection (Socket aClientSocket, JuegoGuacamole juego) {
            this.juego = juego;
            try {
                clientSocket = aClientSocket;
                in = new DataInputStream(clientSocket.getInputStream());
                out =new DataOutputStream(clientSocket.getOutputStream());
            } catch(IOException e)  {
                System.out.println("Connection:"+e.getMessage());
            }
        }

        @Override
        public void run() {
            try {// an echo server
                boolean kill=false;
                while (!kill) {
                    String mensaje = in.readUTF();
                    String[] arr = mensaje.split(":");
                    if(arr[1].equals("kill")) {
                        kill = true;
                        out.writeUTF("Kill");
                    }else{
                        String name = arr[0];
                        int pos = Integer.parseInt(arr[1]);
                        int puntuacion = juego.golpeJugador(name, pos); //actualizar resultados
                        out.writeInt(puntuacion);
                    }
                }
            }
            catch(EOFException e) {
                System.out.println("EOF:"+e.getMessage());
            }
            catch(IOException e) {
                System.out.println("IO:"+e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e){
                    System.out.println(e);
                }
            }
        }
    }

    public ServidorTCP(JuegoGuacamole juego){
        this.juego=juego;
    }

    public void despliegue(){
        try{
            ServerSocket listenSocket = new ServerSocket(TCP_PORT);
            while(true) {
                System.out.println("TCP esperando...");
                Socket clientSocket = listenSocket.accept();  // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
                Connection c = new Connection(clientSocket, this.juego);
                c.start();
            }
        } catch(IOException e) {
            System.out.println("Listen :"+ e.getMessage());
        }
    }
}

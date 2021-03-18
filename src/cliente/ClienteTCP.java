package cliente;

import estres.Globals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTCP {
//    private static int TCP_PORT=7896;
    private int TCP_PORT;
    private String IP_TCP;
    private Socket socket;
    private DataInputStream in;
    private int erroresTCP;
    private DataOutputStream out;
    private Cliente cliente;
    private List<Long> responseTime=new ArrayList<>();
    public ClienteTCP(Cliente cliente,String IP_TCP, int TCP_PORT){
        this.cliente=cliente;
        this.IP_TCP = IP_TCP;
        this.TCP_PORT = TCP_PORT;
        this.erroresTCP=0;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public int getErroresTCP() {
        return erroresTCP;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Long> getResponseTime() {
        return responseTime;
    }

    public int golpeaTopo(int pos){
        try{
            long initTime;
            initTime=System.currentTimeMillis();
            out.writeUTF(cliente.getName() +":"+pos);

            int puntuacion = in.readInt();

            if(Globals.debug) {
                responseTime.add(System.currentTimeMillis() - initTime);
            }
            return puntuacion;
        }catch (IOException ex) {
            this.erroresTCP++;
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public void conecta(){
        try{
            //this.socket=new Socket("localhost", TCP_PORT);
            this.socket=new Socket(IP_TCP, TCP_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconecta(){
        try{
            out.writeUTF(cliente.getName()+":kill");
            in.readUTF();
        }catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

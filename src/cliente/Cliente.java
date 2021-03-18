package cliente;

import estres.Globals;

public class Cliente {
    private String name;
    private GameFrame frame;
    private boolean inGame;

    private ClienteRMI rmi;
    private ClienteMulticast multicast;
    private ClienteTCP tcp;

    public Cliente(GameFrame frame, String name) {
        this.frame = frame;
        this.name = name;
        this.inGame = false;
    }
    public Cliente(String name){
        this.frame=null;
        this.name=name;
        this.inGame=false;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameFrame getFrame() {
        return frame;
    }

    public void setFrame(GameFrame frame) {
        this.frame = frame;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public ClienteRMI getRmi() {
        return rmi;
    }

    public ClienteMulticast getMulticast() {
        return multicast;
    }

    public ClienteTCP getTcp() {
        return tcp;
    }

    public int golpeaTopo(int i) {
        return tcp.golpeaTopo(i);
    }

    public void reportWinner(String winner) {
        if(frame != null) {
            frame.reportWinner(winner);
        }
        this.setInGame(false);
        tcp.desconecta();
        if(!Globals.debug) {
           this.start();
        }
    }

    public void cambiaTopo(int id) {
        if(frame != null) {
            frame.cambiaTopo(id);
        }
    }

    public void start() {
        System.setProperty("java.net.preferIPv4Stack", "true"); // Para el multicast

        rmi = new ClienteRMI(this);
        multicast = new ClienteMulticast(this, rmi.getIPMulticast(), rmi.getMulticastSocket());
        tcp = new ClienteTCP(this, rmi.getIPTCP(), rmi.getTCPSocket());

        rmi.registra();
        if(!inGame) return;
        multicast.start();
        tcp.conecta();
    }
}

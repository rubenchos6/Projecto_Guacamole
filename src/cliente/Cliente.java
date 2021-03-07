package cliente;

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

    public int golpeaTopo(int i) {
        return tcp.golpeaTopo(i);
    }

    public void reportWinner(String winner) {
        frame.reportWinner(winner);
        rmi.registra();
    }

    public void cambiaTopo(int id) {
        frame.cambiaTopo(id);
    }

    public void start() {
        System.setProperty("java.net.preferIPv4Stack", "true"); // Para el multicast

        rmi = new ClienteRMI(this);
        multicast = new ClienteMulticast(this);
        tcp = new ClienteTCP(this);

        rmi.registra();
        if(!inGame) return;
        multicast.start();
        tcp.conecta();
    }
}

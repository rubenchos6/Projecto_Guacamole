package cliente;

import interfaces.Registro;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteRMI {
    private Cliente cliente;
    private Registro reg;

    public ClienteRMI(Cliente cliente) {
        this.cliente = cliente;
        System.setProperty("java.security.policy","file:src/client/client.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String name = "RegistroGuacamole";
        Registry registry = null; // server's ip address

        try {
            registry = LocateRegistry.getRegistry("localhost");
            reg = (Registro) registry.lookup(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void registra(){
        try{
            cliente.setInGame(reg.registrarJugador(cliente.getName()));
        } catch (RemoteException ex){
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Registro getReg() {
        return reg;
    }

    public void setReg(Registro reg) {
        this.reg = reg;
    }
}

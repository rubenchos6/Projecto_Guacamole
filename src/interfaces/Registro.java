package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Registro extends Remote {
    public boolean registrarJugador(String name) throws RemoteException;


}

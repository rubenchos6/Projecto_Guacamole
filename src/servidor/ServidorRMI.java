package servidor;

import interfaces.Registro;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMI implements Registro {


    JuegoGuacamole juego;

    public ServidorRMI(JuegoGuacamole juego){
        this.juego=juego;
    }

    void despliega(){
        System.setProperty("java.security.policy","file:src/server/server.policy");
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            LocateRegistry.createRegistry(1099);
            String name= "RegistroGuacamole";
            Registro stub = (Registro) UnicastRemoteObject.exportObject(this,0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name,stub);
            System.out.println("Servicios desplegados");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registrarJugador(String name) throws RemoteException {
        return juego.agregaJugador(name);
    }
}

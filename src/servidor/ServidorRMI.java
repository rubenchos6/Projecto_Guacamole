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

    void despliegue(){
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
        boolean regis = juego.agregaJugador(name);
        if (regis){
            System.out.println(name + " se registró correctamente en el juego");
        }else{
            System.out.println("En la sala ya se encuentra "+ name);
        }
        return regis;
    }
}

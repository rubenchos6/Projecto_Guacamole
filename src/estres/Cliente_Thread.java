package estres;

import cliente.Cliente;

import java.util.List;
import java.util.Random;

public class Cliente_Thread extends Thread{
    Cliente cliente;

    public static int CLIENT_SLEEP_TIME=100;
    public Cliente_Thread(String name){
        this.cliente=new Cliente(name);
    }

    @Override
    public void run() {
        cliente.start();
        int x;
        Random rand=new Random();
        while(cliente.isInGame()){
            //tiros aleatorios
            x=rand.nextInt(Globals.topos);
            cliente.golpeaTopo(x);
            try {
                Thread.sleep(CLIENT_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("RMI,"+cliente.getName()+","+cliente.getRmi().getRegisterTime());
        System.out.println("TCP-mean,"+cliente.getName()+","+Estresador.mean(cliente.getTcp().getResponseTime()));
        System.out.println("TCP-stdDev,"+cliente.getName()+","+Estresador.stdDev(cliente.getTcp().getResponseTime()));
        System.out.println("Errores-TCP,"+cliente.getName()+","+cliente.getTcp().getErroresTCP()/cliente.getTcp().getResponseTime().size());
        if(cliente.getRmi().isError()){
            System.out.println("Errores-RMI,"+cliente.getName()+","+1);
        }else{
            System.out.println("Errores-RMI,"+cliente.getName()+","+0);
        }

    }
}

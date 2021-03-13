package estres;

import java.util.List;

public class Estresador {

    public static double mean(List<Long> arr){
        long sum=0;
        for (Long v: arr) sum+=v;
        return (double) sum/(double) arr.size();
    }
    public static double stdDev(List<Long> arr){
        double num =0.0;
        double prom=mean(arr);
        for(Long v:arr) num+=Math.pow(v-prom,2);
        return Math.sqrt(num/arr.size());
    }

    public static void main(String[] args){
        System.setProperty("sun.net.maxDatagramSockets", "500");
        int n=500;

        Cliente_Thread[] clientes = new Cliente_Thread[n];
        for(int i=0;i<n;i++){
            clientes[i]=new Cliente_Thread("Cliente_"+i);
            clientes[i].start();
        }
    }
}

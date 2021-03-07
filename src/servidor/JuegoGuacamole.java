package servidor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class JuegoGuacamole {


    private Set<String> usuarios;
    private HashMap<String, Integer> puntuacion;
    private int POINTS_TO_WIN = 3;

    private int pos; //Posicion del topo
    private int n; //Dimensiones del tablero

    private boolean finalizado=false;
    private String winner;
    private boolean topoGolpeado;

    public JuegoGuacamole(int n){
        this.n=n;
        this.pos=0;
        this.topoGolpeado=true;
        this.usuarios= new HashSet<>();
        this.puntuacion= new HashMap<>();
    }

    public boolean agregaJugador(String name){
        if(usuarios.contains(name)) return false;
        usuarios.add(name);
        puntuacion.put(name,0);
        return true;
    }

    public void eliminaJugador(String name){
        usuarios.remove(name);
        puntuacion.remove(name);
    }

    public void mueveTopo(){
        Random rand = new Random();
        pos = rand.nextInt(n);
        topoGolpeado=false;
    }

    public int golpeJugador(String name, int i){
        int curr = puntuacion.get(name);
        if (finalizado || topoGolpeado) return curr;
        if (pos != i) return curr;
        topoGolpeado=true;
        curr++;
        puntuacion.put(name,curr);
        if(curr>=POINTS_TO_WIN){
            finalizado=true;
            winner=name;
        }
        return curr;
    }

    public int obtenPosicion() {return pos;}

    public boolean getFinalizado(){
        return finalizado;
    }

    public String getWinner(){
        return winner;
    }
    public int obtenPuntuacion(String name){
        return puntuacion.get(name);
    }

    public Set<String> getUsuarios(){
        return usuarios;
    }

    public void restart(){
        usuarios.clear();
        puntuacion.clear();
        winner = null;
        finalizado = false;

    }
}

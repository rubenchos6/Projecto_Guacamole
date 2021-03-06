package servidor;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class JuegoGuacamole {


    private Set<String> usuarios;
    private HashMap<String, Integer> puntuacion;
    private int POINTS_TO_WIN = 3;

    private int xPos, yPos; //Posicion del topo
    private int n,m; //Dimensiones del tablero

    private boolean finalizado=false;
    private String winner;

    public JuegoGuacamole(int n, int m){
        this.n=n;
        this.m=m;
        this.mueveTopo();
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
        xPos = rand.nextInt(n);
        yPos = rand.nextInt(m);
    }

    public int golpeJugador(String name, int i, int j){
        int curr = puntuacion.get(name);
        if (xPos!=i || yPos!=j){
            return curr;
        }
        curr++;
        puntuacion.put(name,curr);
        if(curr>=POINTS_TO_WIN){
            finalizado=true;
            winner=name;
        }
        return curr;
    }

    public boolean getFinalizado(){
        return finalizado;
    }

    public String obtenPosicion(){
        return xPos+","+yPos;
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

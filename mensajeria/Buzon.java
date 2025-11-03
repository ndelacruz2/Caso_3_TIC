package mensajeria;
import java.util.Queue;
import java.util.LinkedList;

public abstract class Buzon {
    
    protected int capacidad;
    protected Queue<Mensaje> cola;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new LinkedList<>();
    }

    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }

    public synchronized boolean estaLleno() {
        return cola.size() >= capacidad;
    }
    public abstract void depositar(Mensaje mensaje);
    public abstract Mensaje retirar();
}

    

package mensajeria;

public class BuzonEntrega extends Buzon {

    public BuzonEntrega(int capacidad) {
        super(capacidad);
    }

    @Override
    public synchronized void depositar(Mensaje mensaje) {
        while (estaLleno()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        cola.add(mensaje);
        System.out.println("[Buzón Entrega] Nuevo mensaje agregado: " + mensaje);
        notifyAll();
    }

    @Override
    public synchronized Mensaje retirar() {
        while (estaVacio()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Mensaje m = cola.poll();
        notifyAll();
        return m;
    }
}


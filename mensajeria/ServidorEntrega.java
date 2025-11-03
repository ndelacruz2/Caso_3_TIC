package mensajeria;

import java.util.Random;

public class ServidorEntrega extends Thread {

    private BuzonEntrega buzonEntrega;
    private Random rand = new Random();
    private int id;

    public ServidorEntrega(int id, BuzonEntrega entrega) {
        this.id = id;
        this.buzonEntrega = entrega;
    }

    @Override
    public void run() {
        while (true) {
            Mensaje m = buzonEntrega.retirar();

            if (m.getTipo() == TipoMensaje.FIN) {
                System.out.println("ServidorEntrega " + id + ": fin recibido, terminando...");
                break;
            }

            System.out.println("ServidorEntrega " + id + " procesando: " + m);
            try {
                Thread.sleep(rand.nextInt(800) + 400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

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
        System.out.println("ServidorEntrega " + id + " iniciado.");

        boolean activo = true;

        while (activo) {
            Mensaje m = buzonEntrega.retirar();

            if (m.getTipo() == TipoMensaje.FIN) {
                System.out.println("ServidorEntrega " + id + ": mensaje FIN recibido. Cerrando servidor.\n");
                activo = false;
            } else {
                System.out.println("ServidorEntrega " + id + " procesando mensaje: " + m);
                try {
                    Thread.sleep(rand.nextInt(800) + 400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

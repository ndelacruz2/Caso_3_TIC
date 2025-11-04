package mensajeria;

import java.util.Iterator;
import java.util.Random;

public class ManejadorCuarentena extends Thread {

    private BuzonCuarentena buzonCuarentena;
    private BuzonEntrega buzonEntrega;
    private Random random = new Random();

    public ManejadorCuarentena(BuzonCuarentena cuarentena, BuzonEntrega entrega) {
        this.buzonCuarentena = cuarentena;
        this.buzonEntrega = entrega;
    }

    @Override
    public void run() {
        System.out.println("ManejadorCuarentena iniciado.");

        boolean activo = true;

        while (activo) {
            synchronized (buzonCuarentena) {
                Iterator<Mensaje> it = buzonCuarentena.cola.iterator();

                while (it.hasNext()) {
                    Mensaje m = it.next();

                    if (m.getTipo() == TipoMensaje.FIN) {
                        buzonEntrega.depositar(m);
                        System.out.println("ManejadorCuarentena: mensaje FIN recibido. Terminando.\n");
                        activo = false;
                        return;
                    }

                    m.setTiempoCuarentena(m.getTiempoCuarentena() - 1);

                    if (m.getTiempoCuarentena() <= 0) {
                        int n = random.nextInt(21) + 1;
                        if (n % 7 == 0) {
                            System.out.println("ManejadorCuarentena: mensaje eliminado (malicioso) -> " + m);
                        } else {
                            buzonEntrega.depositar(m);
                            System.out.println("ManejadorCuarentena: mensaje liberado -> " + m);
                        }
                        it.remove();
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

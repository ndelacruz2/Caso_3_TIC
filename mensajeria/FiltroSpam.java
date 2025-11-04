package mensajeria;

import java.util.Random;

public class FiltroSpam extends Thread {

    private BuzonEntrada buzonEntrada;
    private BuzonCuarentena buzonCuarentena;
    private BuzonEntrega buzonEntrega;

    private Random random = new Random();

    public FiltroSpam(BuzonEntrada entrada, BuzonCuarentena cuarentena, BuzonEntrega entrega) {
        this.buzonEntrada = entrada;
        this.buzonCuarentena = cuarentena;
        this.buzonEntrega = entrega;
    }

    @Override
    public void run() {
        boolean activo = true;
        System.out.println("FiltroSpam iniciado.");

        while (activo) {
            Mensaje m = buzonEntrada.retirar();

            if (m.getTipo() == TipoMensaje.FIN) {
                buzonEntrega.depositar(new Mensaje("FIN_ENTREGA", TipoMensaje.FIN, "Fin de entrega", false));
                buzonCuarentena.depositar(new Mensaje("FIN_CUARENTENA", TipoMensaje.FIN, "Fin de cuarentena", false));
                System.out.println("FiltroSpam: mensaje FIN recibido. Cerrando filtro.\n");
                activo = false;
            } 
            else if (m.getTipo() == TipoMensaje.NORMAL) {
                if (m.isSpam()) {
                    int tiempo = random.nextInt(11) + 10;
                    m.setTiempoCuarentena(tiempo);
                    buzonCuarentena.depositar(m);
                    System.out.println("FiltroSpam: mensaje SPAM enviado a cuarentena -> " + m);
                } else {
                    buzonEntrega.depositar(m);
                    System.out.println("FiltroSpam: mensaje válido enviado a entrega -> " + m);
                }
            }
        }
    }
}

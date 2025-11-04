package mensajeria;

public class ClienteEmisor extends Thread {

    private int idCliente;
    private int cantMensajes;
    private BuzonEntrada buzon;

    public ClienteEmisor(int idCliente, int cantMensajes, BuzonEntrada buzon) {
        this.idCliente = idCliente;
        this.cantMensajes = cantMensajes;
        this.buzon = buzon;
    }

    @Override
    public void run() {
        System.out.println("Cliente " + idCliente + " iniciando envío de mensajes.");

        Mensaje inicio = new Mensaje("C" + idCliente + "-INICIO", TipoMensaje.INICIO, 
                                     "Inicio cliente " + idCliente, false);
        buzon.depositar(inicio);

        for (int i = 1; i <= cantMensajes; i++) {
            boolean esSpam = Math.random() < 0.5;
            Mensaje m = new Mensaje("C" + idCliente + "-M" + i, TipoMensaje.NORMAL, 
                                    "Mensaje " + i + " del cliente " + idCliente, esSpam);
            buzon.depositar(m);
            System.out.println("Cliente " + idCliente + " envió: " + m);

            try {
                Thread.sleep((int) (Math.random() * 500 + 200));
            } catch (InterruptedException e) {}
        }

        Mensaje fin = new Mensaje("C" + idCliente + "-FIN", TipoMensaje.FIN, 
                                  "Fin cliente " + idCliente, false);
        buzon.depositar(fin);

        System.out.println("Cliente " + idCliente + " finalizó el envío de mensajes.\n");
    }
}

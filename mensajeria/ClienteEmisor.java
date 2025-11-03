package mensajeria;

public class ClienteEmisor extends Thread {
    private int id;
    private int numMensajes;
    private BuzonEntrada buzonEntrada;

    public ClienteEmisor(int id, int numMensajes, BuzonEntrada buzonEntrada) {
        this.id = id;
        this.numMensajes = numMensajes;
        this.buzonEntrada = buzonEntrada;
    }

    @Override
    public void run() {
        Mensaje inicio = new Mensaje("C" + id + "-INICIO", TipoMensaje.INICIO, "Inicio de cliente " + id, false);
        buzonEntrada.depositar(inicio);

        for (int i = 1; i <= numMensajes; i++) {
            boolean esSpam = Math.random() < 0.3; 
            Mensaje correo = new Mensaje("C" + id + "-M" + i, TipoMensaje.NORMAL, "Mensaje " + i + " del cliente " + id, esSpam);
            buzonEntrada.depositar(correo);

            try {
                Thread.sleep((int)(Math.random() * 300 + 200)); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Mensaje fin = new Mensaje("C" + id + "-FIN", TipoMensaje.FIN, "Fin de cliente " + id, false);
        buzonEntrada.depositar(fin);

        System.out.println("Cliente " + id + " terminó de enviar sus mensajes.");
    }
}

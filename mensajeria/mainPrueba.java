package mensajeria;

public class mainPrueba {
    public static void main(String[] args) {
        int numClientes = 3;
        int mensajesPorCliente = 5;
        int capacidadBuzon = 10;

        BuzonEntrada buzonEntrada = new BuzonEntrada(capacidadBuzon);

        for (int i = 1; i <= numClientes; i++) {
            ClienteEmisor cliente = new ClienteEmisor(i, mensajesPorCliente, buzonEntrada);
            cliente.start();
        }
    }
}

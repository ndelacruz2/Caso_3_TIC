package mensajeria;

public class MainPrueba2 {
    public static void main(String[] args) {
        // Parámetros configurables
        int numClientes = 3;
        int mensajesPorCliente = 5;
        int numFiltros = 2;
        int numServidores = 2;
        int capacidadEntrada = 10;
        int capacidadEntrega = 10;

        // Crear buzones compartidos
        BuzonEntrada buzonEntrada = new BuzonEntrada(capacidadEntrada);
        BuzonCuarentena buzonCuarentena = new BuzonCuarentena();
        BuzonEntrega buzonEntrega = new BuzonEntrega(capacidadEntrega);

        // Crear hilos
        for (int i = 1; i <= numClientes; i++) {
            new ClienteEmisor(i, mensajesPorCliente, buzonEntrada).start();
        }

        for (int i = 1; i <= numFiltros; i++) {
            new FiltroSpam(buzonEntrada, buzonCuarentena, buzonEntrega).start();
        }

        new ManejadorCuarentena(buzonCuarentena, buzonEntrega).start();

        for (int i = 1; i <= numServidores; i++) {
            new ServidorEntrega(i, buzonEntrega).start();
        }

        System.out.println("\n=== Sistema de mensajería iniciado ===\n");
    }
}

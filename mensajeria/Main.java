package mensajeria;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("=== Sistema de Mensajería Concurrente ===");
        System.out.println("==========================================");

        String archivoConfig = "config.txt";

        int numClientes = 0;
        int mensajesPorCliente = 0;
        int numFiltros = 0;
        int numServidores = 0;
        int capacidadEntrada = 0;
        int capacidadEntrega = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoConfig))) {
            numClientes = Integer.parseInt(br.readLine().trim());
            mensajesPorCliente = Integer.parseInt(br.readLine().trim());
            numFiltros = Integer.parseInt(br.readLine().trim());
            numServidores = Integer.parseInt(br.readLine().trim());
            capacidadEntrada = Integer.parseInt(br.readLine().trim());
            capacidadEntrega = Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el archivo de configuración: " + e.getMessage());
            return;
        }

        System.out.println("\nConfiguración cargada:");
        System.out.println("  - Clientes: " + numClientes);
        System.out.println("  - Mensajes por cliente: " + mensajesPorCliente);
        System.out.println("  - Filtros de spam: " + numFiltros);
        System.out.println("  - Servidores: " + numServidores);
        System.out.println("  - Capacidad buzón entrada: " + capacidadEntrada);
        System.out.println("  - Capacidad buzón entrega: " + capacidadEntrega);
        System.out.println("==========================================\n");

        BuzonEntrada entrada = new BuzonEntrada(capacidadEntrada);
        BuzonCuarentena cuarentena = new BuzonCuarentena();
        BuzonEntrega entrega = new BuzonEntrega(capacidadEntrega);

        List<Thread> hilos = new ArrayList<>();

        for (int i = 1; i <= numClientes; i++) {
            ClienteEmisor cliente = new ClienteEmisor(i, mensajesPorCliente, entrada);
            hilos.add(cliente);
            cliente.start();
        }

        for (int i = 1; i <= numFiltros; i++) {
            FiltroSpam filtro = new FiltroSpam(entrada, cuarentena, entrega);
            hilos.add(filtro);
            filtro.start();
        }

        ManejadorCuarentena manejador = new ManejadorCuarentena(cuarentena, entrega);
        hilos.add(manejador);
        manejador.start();

        for (int i = 1; i <= numServidores; i++) {
            ServidorEntrega servidor = new ServidorEntrega(i, entrega);
            hilos.add(servidor);
            servidor.start();
        }

        System.out.println(">>> Simulación iniciada...\n");

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n>>> Simulación finalizada correctamente.");
    }
}

package mensajeria;

public class BuzonEntrada extends Buzon{

    public BuzonEntrada(int capacidad)
    {
        super(capacidad);
    }

    @Override
    public synchronized void depositar(Mensaje m)
    {
        while(estaLleno())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
        cola.add(m);
        System.out.println("Deposito en Buzón de entrada" + m);
        notifyAll();
    }

    @Override
    public synchronized Mensaje retirar()
    {
        while (estaVacio()) 
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        Mensaje m = cola.poll();
        notifyAll();
        return m;
    }
    
}

package mensajeria;
public class BuzonCuarentena extends Buzon {

    public BuzonCuarentena(){

        super(Integer.MAX_VALUE);
    }

    @Override
    public synchronized void depositar(Mensaje m) {
        cola.add(m); //no es neecsario esperar, porque hay espacio ilimitado
        notifyAll();
    } 
    
    @Override
    public synchronized Mensaje retirar() {
        while (estaVacio()) {
            try {wait();
            }
            catch ( InterruptedException e) {
                Thread.currentThread().interrupt();
            }    
        }
        Mensaje m = cola.poll(); //metodo definido para FIFO, da el primer valor en entrar
        notifyAll();
        return m;
    }


    }

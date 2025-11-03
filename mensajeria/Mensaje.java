package mensajeria;
public class Mensaje {
    private String id;
    private TipoMensaje tipo;
    private String contenido;
    private boolean esSpam;
    private int tiempoCuarentena;

    public Mensaje(String id, TipoMensaje tipo, String contenido, boolean esSpam)
    {
        this.id = id;
        this.tipo = tipo;
        this.contenido = contenido;
        this.esSpam = esSpam;
        this.tiempoCuarentena = 0;
    }
    public String getId() 
    { return id; }
    
    public TipoMensaje getTipo() 
    { return tipo; }

    public String getContenido() 
    { return contenido; }

    public boolean isSpam() 
    { return esSpam; }

    public int getTiempoCuarentena() 
    { return tiempoCuarentena; }

    public void setTiempoCuarentena(int tiempo) 
    { this.tiempoCuarentena = tiempo; }

    @Override
    public String toString() {
        return "[Mensaje " + tipo + " - ID: " + id + " - Spam: " + esSpam + "]";
    }
}
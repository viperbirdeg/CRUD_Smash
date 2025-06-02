package PersonajeCRUD;


public abstract class Personaje {
    private String nombre;
    private String tipo;
    private int vida;
    private int resistencia;
    private int alcance;

    public Personaje() {
    }

    public Personaje(String nombre, String tipo, int vida, int resistencia, int alcance) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vida = vida;
        this.resistencia = resistencia;
        this.alcance = alcance;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    //Metodos abstractos
    public abstract String getHabilidad();

    public abstract void setHabilidad(String nombre_hab);

    public abstract void setHabilidad(int can_mana);

    @Override
    public String toString() {
        return "nombre:" + nombre + "\n" +
                "tipo:" + tipo + "\n" +
                "vida:" + vida + "\n" +
                "resistencia:" + resistencia + "\n" +
                "alcance:" + alcance;
    }
}

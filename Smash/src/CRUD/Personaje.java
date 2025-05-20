package CRUD;



public abstract class  Personaje {
    private String nombre ;
    private String Tipo ;
    private int vida ;
    private int resistencia ;
    private int alcanze ;

    public Personaje(){}

    public Personaje (String nombre, String tipo, int vida, int resistencia, int alcanze) {
        this.nombre = nombre;
        Tipo = tipo;
        this.vida = vida;
        this.resistencia = resistencia;
        this.alcanze = alcanze;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
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

    public int getAlcanze() {
        return alcanze;
    }

    public void setAlcanze(int alcanze) {
        this.alcanze = alcanze;
    }

    public abstract String getha();

    @Override
    public String toString() {
        return  "nombre:" + nombre + "\n" +
                "tipo:" + Tipo + "\n" +
                "vida:" + vida +"\n"+
                "resistencia:" + resistencia +"\n"+
                "alcanze:" + alcanze ;
    }
}

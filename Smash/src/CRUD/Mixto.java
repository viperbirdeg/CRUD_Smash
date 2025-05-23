package CRUD;

public class Mixto extends Personaje {
    private String haki ;
    public Mixto (){}

    public Mixto(String nombre, String tipo, int vida, int resistencia, int alcanze, String haki) {
        super(nombre, tipo, vida, resistencia, alcanze);
        this.haki = haki;
    }

    public String getHaki() {
        return haki;
    }

    public void setHaki(String haki) {
        this.haki = haki;
    }
    @Override
    public String getha(){
        return haki;
    }
    @Override
    public void setha(String nombre_hab) {
        this.haki = nombre_hab;
    }
    @Override
    public void setha(int can_mana) {
        throw new UnsupportedOperationException("Mixto no usa maná.");
    }

    @Override
    public String toString() {
        return  "Nombre:"+getNombre()+"\n"+
                "tipo:"+getTipo()+"\n"+
                "vida:"+getVida()+"\n"+
                "Resistencia:"+getResistencia()+"\n"+
                "alcanze:"+getAlcanze()+"\n"+
                "haki:" + haki ;
    }
}

package CRUD;

public class Mago extends Personaje {
    private String  mana ;
    public Mago(){}

    public Mago(String nombre, String tipo, int vida, int resistencia, int alcanze, String  mana) {
        super(nombre, tipo, vida, resistencia, alcanze);
        this.mana = mana;
    }

    public String getMana() {
        return mana;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }
    @Override
    public String getha(){
        return mana;
    }
    @Override
    public String toString() {
        return "Nombre:"+getNombre()+"\n"+
                "tipo:"+getTipo()+"\n"+
                "vida:"+getVida()+"\n"+
                "Resistencia:"+getResistencia()+"\n"+
                "alcanze:"+getAlcanze()+"\n"+
                "mana:" + mana ;
    }
}

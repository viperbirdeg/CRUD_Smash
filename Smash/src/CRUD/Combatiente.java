package CRUD;

public class Combatiente extends Personaje{
    private String golpe;
    public Combatiente(){}

    public Combatiente(String nombre, String tipo, int vida, int resistencia, int alcanze,String golpe) {
        super(nombre, tipo, vida, resistencia, alcanze);
        this.golpe = golpe;
    }

    public String getGolpe_bajo() {
        return golpe;
    }

    public void setGolpe_bajo(String golpe_bajo) {
        this.golpe = golpe_bajo;
    }


    @Override
    public String getHabilidad(){
        return golpe;
    }
    @Override
    public void setHabilidad(String nombre_hab) {
        this.golpe = nombre_hab;
    }

    @Override
    public void setHabilidad(int can_mana) {
        throw new UnsupportedOperationException("Combatiente no usa maná.");
    }
    @Override
    public String toString() {
        return "Nombre:"+getNombre()+"\n"+
                "tipo:"+getTipo()+"\n"+
                "vida:"+getVida()+"\n"+
                "Resistencia:"+getResistencia()+"\n"+
                "alcance:"+getAlcance()+"\n"+
                "golpe:" + golpe;
    }
}

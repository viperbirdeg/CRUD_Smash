package PersonajeCRUD;

public class Mago extends Personaje {
    private String mana;

    public Mago() {
    }

    public Mago(String nombre, String tipo, int vida, int resistencia, int alcanze, String mana) {
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
    public String getHabilidad() {
        return mana;
    }

    @Override
    public void setHabilidad(int can_mana) {
        this.mana = String.valueOf(can_mana);
    }

    @Override
    public void setHabilidad(String nombre_hab) {
        throw new UnsupportedOperationException("Mago no usa habilidad con nombre.");
    }

    @Override
    public String toString() {
        return "Nombre:" + getNombre() + "\n" +
                "tipo:" + getTipo() + "\n" +
                "vida:" + getVida() + "\n" +
                "Resistencia:" + getResistencia() + "\n" +
                "alcance:" + getAlcance() + "\n" +
                "mana:" + mana;
    }
}

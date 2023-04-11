package caso2;

public class Pagina {
    private int id;
    private boolean enMemoriaReal;
    private int bits;

    public Pagina(int id) {
        this.id = id;
        this.enMemoriaReal = false;
        this.bits = 0;
    }

    public int getId() {
        return id;
    }

    public boolean estaEnMemoriaReal() {
        return enMemoriaReal;
    }

    public void setEnMemoriaReal(boolean enMemoriaReal) {
        this.enMemoriaReal = enMemoriaReal;
    }

    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }
}
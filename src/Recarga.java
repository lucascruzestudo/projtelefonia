import java.util.GregorianCalendar;

public class Recarga {
    private GregorianCalendar data;
    private float valor;

    public Recarga(GregorianCalendar data, float valor) {
        this.data = data;
        this.valor = valor;
    }
    public GregorianCalendar getData() {
        return this.data;
    }
    public float getValor() {
        return this.valor;
    }

    @Override
    public String toString() {
        return "A recarga foi feita em "+this.data.getTime()+" no valor de "+this.valor;
    }
}

import java.util.GregorianCalendar;

public class Chamada {
    private GregorianCalendar data;
    private int duracao;

    public Chamada(GregorianCalendar data, int duracao) {
        this.data = data;
        this.duracao = duracao;
    }

    public GregorianCalendar getData() {
        return this.data;
    }
    public int getDuracao() {
        return this.duracao;
    }

    @Override
    public String toString() {
        return "A chamada foi feita em "+this.data.getTime()+"\ncom duração de "+this.duracao+" Minutes";
    }

}
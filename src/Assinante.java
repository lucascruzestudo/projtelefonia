import java.util.GregorianCalendar;
import java.util.ArrayList;

public abstract class Assinante {
    private long cpf;
    private String nome;
    private int nummero;

    protected int numChamadas;
    protected ArrayList<Chamada> chamadas;


    public Assinante (long cpf, String nome, int numero)
    {
        this.cpf = cpf;
            this.nome = nome;
            this.nummero = numero;
            this.numChamadas = 0;
            this.chamadas = new ArrayList<>();
    }

    public long getCpf() {
        return this.cpf;
    }

    public abstract void imprimirFaturas(int mes, int ano);
    public abstract void fazerChamada(GregorianCalendar data, int duracao);
    
    @Override
    public String toString() 
    {
        return
                "  Cpf: "+this.cpf+"\n" +
                "  Nome: " +this.nome+"\n" +
                "  Numero: "+this.nummero;
    }
}


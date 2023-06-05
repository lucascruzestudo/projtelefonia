import java.util.GregorianCalendar;

public abstract class Assinante {
    private long cpf;
    private String nome;
    private int nummero;

    protected int numChamadas;
    protected Chamada[] chamadas;

    public Assinante (long cpf, String nome, int numero)
    {
        this.cpf = cpf;
            this.nome = nome;
            this.nummero = numero;
            this.numChamadas = 0;
            this.chamadas = new Chamada[5];
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


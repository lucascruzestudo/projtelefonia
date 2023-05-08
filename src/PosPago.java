import java.util.Calendar;
import java.util.GregorianCalendar;


public class PosPago extends Assinante 
{
    private float assinatura;

    public PosPago(long cpf, String nome, int numero, float assinatura) 
    {
        super(cpf, nome, numero);
        this.assinatura = assinatura;
    }
    public void fazerChamada(GregorianCalendar data, int duracao) 
    {
        if (this.numChamadas >= this.chamadas.length) 
        {
            System.out.println("Assinante com limite de chamadas.");
            return;
        }

        this.chamadas[this.numChamadas] = new Chamada(data, duracao);
        this.assinatura += (1.04) * duracao;
        this.numChamadas++;
    }
}
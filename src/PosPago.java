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

    public void fazerChamada(GregorianCalendar data, int duracao) {
        if (this.numChamadas >= this.chamadas.length) {
            System.out.println("Assinante com limite de chamadas.");
            return;
        }

        this.chamadas[this.numChamadas] = new Chamada(data, duracao);
        this.numChamadas++;
    }

    public void imprimirFaturas(int mes) {
        float valorTotalChamdas = 0f;
        boolean achouChamada = false;

        System.out.print("\nChamadas...");
        for (int i = 0; i < this.numChamadas; i++) {
            if (mes == this.chamadas[i].getData().get(Calendar.MONTH)) {
                if (!achouChamada) {
                    System.out.println("===============================");
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                }

                float custo = (float) (this.chamadas[i].getDuracao() * 1.04);
                System.out.println("\n Chamada["+i+"]: " + this.chamadas[i].toString());
                System.out.println(" valorChamada: " + custo);
                valorTotalChamdas += custo;
                achouChamada = true;
            }
        }

        if (achouChamada) {
            System.out.println(" Total gasto em chamadas: " + valorTotalChamdas);
            System.out.println(" Valor da assinatura: " + this.assinatura);
            System.out.print("\n Valor final da assinatura: " + (this.assinatura + valorTotalChamdas));
        }
        System.out.println("\n");
    }
}
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
    @Override
    public void fazerChamada(GregorianCalendar data, int duracao) {
        if (this.numChamadas >= this.chamadas.length) {
            System.out.println("Assinante com limite de chamadas.");
            return;
        }

        this.chamadas[this.numChamadas] = new Chamada(data, duracao);
        this.numChamadas++;
    }

    @Override
    public void imprimirFaturas(int mes, int ano) {
        float valorTotalChamdas = 0f;
        boolean achouChamada = false;

        System.out.printf("\nChamadas... de %d/%d", mes, ano);

        for (int i = 0; i < this.numChamadas; i++) {
            GregorianCalendar dataCorrente = this.chamadas[i].getData();
            if (mes == dataCorrente.get(Calendar.MONTH) && dataCorrente.get(Calendar.YEAR) == ano) {
                if (!achouChamada) {
                    System.out.println("===============================");
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    achouChamada = true;
                }

                float custo = (float) (this.chamadas[i].getDuracao() * 1.04);
                System.out.println("\n Chamada["+i+"]: " + this.chamadas[i].toString());
                System.out.println(" valorChamada: " + custo);
                valorTotalChamdas += custo;
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
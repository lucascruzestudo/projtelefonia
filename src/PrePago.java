import java.util.Calendar;
import java.util.GregorianCalendar;


public class PrePago extends Assinante {
    private float creditos = 0f;
    private int numRecargas = 0;
    private Recarga[] recargas;

      public PrePago(long cpf, String nome, int numero) {
        super(cpf, nome, numero);
        this.recargas = new Recarga[5];
    }
    
    public void recarregar(GregorianCalendar data, float valor) {
        if (this.numRecargas >= this.recargas.length) {
            System.out.println("Assinante com limite de recarga.");
            return;
        }
        this.recargas[this.numRecargas] = new Recarga(data, valor);
        this.creditos += valor;
        this.numRecargas++;
    }

    public Float fazerChamada(GregorianCalendar data, int duracao) {

        if (this.numChamadas >= this.chamadas.length) {
            System.out.println("Assinante com limite de chamadas.\n");
            return null;
        }

        if (this.creditos < (1.45 * duracao)) {
            System.out.println("Assinante nao possui creditos para completar a chamada.");
            System.out.printf("Credito restante do assinante: %.2f\n", this.creditos);
            System.out.printf("Credito necessario para completar a chamada: %.2f\n\n", (1.45 * duracao));
            return null;
        }

        this.chamadas[this.numChamadas] = new Chamada(data, duracao);
        this.creditos -= (1.45 * duracao);
        this.numChamadas++;

        return creditos;
    }
    
    public void imprimirFaturas(int mes) {
        float valorTotalChamadas = 0f;
        float valorTotalRecargas = 0f;
        boolean achouChamadaOrRecarga = false;

        for (int i = 0; i < this.numChamadas; i++) {
            if (mes == this.chamadas[i].getData().get(Calendar.MONTH)) {
                if (!achouChamadaOrRecarga) {
                    System.out.print("\nChamadas...");
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    System.out.println("===============================");
                }
                System.out.println("\n Chamada["+i+"]: " + this.chamadas[i].toString());
                float _valorTotalChamadas = (float)(this.chamadas[i].getDuracao() * 1.45);
                System.out.println(" Valor da chamada: " + _valorTotalChamadas);
                valorTotalChamadas += _valorTotalChamadas;
                achouChamadaOrRecarga = true;
            }
        }

        if (achouChamadaOrRecarga) System.out.println("\n Total gasto em chamadas: " + valorTotalChamadas);

        for (int i = 0; i < this.numRecargas; i++) {
            if (mes == this.recargas[i].getData().get(Calendar.MONTH)) {
                if (!achouChamadaOrRecarga) {
                    System.out.print("\nRecarrega...");
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    System.out.println("===============================");
                }

                System.out.println("\n Recarga["+i+"]: " + this.recargas[i].toString());
                valorTotalRecargas += this.recargas[i].getValor();
                achouChamadaOrRecarga = true;
            }
        }

        if (achouChamadaOrRecarga) System.out.println("\n Total gasto em recarga: " + valorTotalRecargas+" \n");

    }
}

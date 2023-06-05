import java.util.Calendar;
import java.util.GregorianCalendar;


public class PrePago extends Assinante {
    private float creditos;
    private int numRecargas;
    private Recarga[] recargas;

      public PrePago(long cpf, String nome, int numero) {
        super(cpf, nome, numero);
        this.recargas = new Recarga[5];
        this.creditos = 0f;
        this.numRecargas = 0;
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

    @Override
    public void fazerChamada(GregorianCalendar data, int duracao) {

        if (this.numChamadas >= this.chamadas.length) {
            System.out.println("Assinante com limite de chamadas.\n");
            return;
        }

        if (this.creditos < (1.45 * duracao)) {
            System.out.println("Assinante nao possui creditos para completar a chamada.");
            System.out.printf("Credito restante do assinante: %.2f\n", this.creditos);
            System.out.printf("Credito necessario para completar a chamada: %.2f\n\n", (1.45 * duracao));
            return;
        }

        this.chamadas[this.numChamadas] = new Chamada(data, duracao);
        this.creditos -= (1.45 * duracao);
        this.numChamadas++;
    }

    @Override
    public void imprimirFaturas(int mes, int ano) {
        float valorTotalChamadas = 0f;
        float valorTotalRecargas = 0f;
        boolean achouChamadaOrRecarga = false;

        System.out.printf("\nChamadas... de %d/%d", mes, ano);
        
        for (int i = 0; i < this.numChamadas; i++) {
            GregorianCalendar dataCorrente = this.chamadas[i].getData();
            if (mes == dataCorrente.get(Calendar.MONTH) && dataCorrente.get(Calendar.YEAR) == ano) {
                if (!achouChamadaOrRecarga) {
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    System.out.println("===============================");
                    achouChamadaOrRecarga = true;
                }
                System.out.println("\n Chamada["+i+"]: " + this.chamadas[i].toString());
                float _valorTotalChamadas = (float)(this.chamadas[i].getDuracao() * 1.45);
                System.out.println(" Valor da chamada: " + _valorTotalChamadas);
                valorTotalChamadas += _valorTotalChamadas;
            }
        }

        if (achouChamadaOrRecarga) System.out.println("\n Total gasto em chamadas: " + valorTotalChamadas);

        for (int i = 0; i < this.numRecargas; i++) {
            GregorianCalendar dataCorrente = this.chamadas[i].getData();
            if (mes == dataCorrente.get(Calendar.MONTH) && dataCorrente.get(Calendar.YEAR) == ano) {
                if (!achouChamadaOrRecarga) {
                    System.out.println("===============================");
                    System.out.print("\nRecarrega...");
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    achouChamadaOrRecarga = true;
                }

                System.out.println("\n Recarga["+i+"]: " + this.recargas[i].toString());
                valorTotalRecargas += this.recargas[i].getValor();
            }
        }

        if (achouChamadaOrRecarga) System.out.println("\n Total gasto em recarga: " + valorTotalRecargas+" \n");

    }
}

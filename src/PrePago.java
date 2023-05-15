import java.util.Calendar;
import java.util.GregorianCalendar;


// fazer construtor

public class PrePago extends Assinante {
    private float creditos = 0f;
    private int numRecargas = 0;
    private Recarga[] recargas;

    
    
    public void fazerRecarga(GregorianCalendar data, float valor) {
        if (this.numRecargas >= this.recargas.length) {
            System.out.println("Assinannte com limite de recarga.");
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
            System.out.println("Assinante nao possui creditos para completar a chamadas.");
            System.out.printf("Credito restate do assinante: %.2f\n", this.creditos);
            System.out.printf("Credito necessarrio para completa a chamada: %.2f\n\n", (1.45 * duracao));
            return null;
        }
        this.chamadas[this.numChamadas] = new Chamada(data, duracao);
        this.creditos -= (1.45) * duracao;
        this.numChamadas++;

        return creditos;
    }
    
    public void imprimirFaturas(int mes) {
        float valorTotalChamdas = 0f;
        float valorTotalRecargas = 0f;
        System.out.println("===============================");

        System.out.println("Dados Pessoais...");
        System.out.println(this.toString());

        System.out.print("\nChamadas...");
        for (int i = 0; i < this.numChamadas; i++) {
            if (mes == this.chamadas[i].getData().get(Calendar.MONTH)) {
                System.out.println("\n Chamada["+i+"]: " + this.chamadas[i].toString());
                float _valorTotalChamdas = (float)(this.chamadas[i].getDuracao() * 1.45);
                System.out.println("  valorChamada:" + _valorTotalChamdas);
                valorTotalChamdas += _valorTotalChamdas;
            } else {
                System.out.print("  Nenhuma...");
            }
        }

        System.out.println(" Total gasto em chamadas: "+valorTotalChamdas+" \n");

        System.out.print("\nRecarrega...");
        for (int i = 0; i < this.numRecargas; i++) {
            if (mes == this.recargas[i].getData().get(Calendar.MONTH)) {
                System.out.println("\n Recarga["+i+"]: " + this.recargas[i].toString());
                float _valorTotalRecargas = this.recargas[i].getValor();
                valorTotalRecargas += _valorTotalRecargas;
            } else {
                System.out.print("  Nenhuma...");
            }
        }

        System.out.println("\n Total gasto em recarga: "+valorTotalRecargas+" \n");

    }
}

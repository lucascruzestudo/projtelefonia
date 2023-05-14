import java.util.Calendar;
import java.util.GregorianCalendar;

public class PrePago extends Assinante {
    
    
    
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

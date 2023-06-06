import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class PrePago extends Assinante {
    private float creditos;
    private int numRecargas;
    private ArrayList<Recarga> recargas;

      public PrePago(long cpf, String nome, int numero) {
        super(cpf, nome, numero);
        this.recargas = new ArrayList<>();
        this.creditos = 0f;
        this.numRecargas = 0;
    }
    
    public void recarregar(GregorianCalendar data, float valor) {
        
        this.recargas.add(new Recarga(data, valor));
        this.creditos += valor;
        this.numRecargas++;
    }

    @Override
    public void fazerChamada(GregorianCalendar data, int duracao) {

        if (this.creditos < (1.45 * duracao)) {
            System.out.println("Assinante nao possui creditos para completar a chamada.");
            System.out.printf("Credito restante do assinante: %.2f\n", this.creditos);
            System.out.printf("Credito necessario para completar a chamada: %.2f\n\n", (1.45 * duracao));
            return;
        }

        this.chamadas.add(new Chamada(data, duracao));
        this.creditos -= (1.45 * duracao);
        this.numChamadas++;
    }

    @Override
    public void imprimirFaturas(int mes, int ano) {
        float valorTotalChamadas = 0f;
        float valorTotalRecargas = 0f;
        boolean achouChamadaOrRecarga = false;

        System.out.printf("\nChamadas... de %d/%d\n", mes, ano);

        for (int i = 0; i < this.chamadas.size(); i++) {
            GregorianCalendar dataCorrente = this.chamadas.get(i).getData();
            if (mes == dataCorrente.get(Calendar.MONTH) && dataCorrente.get(Calendar.YEAR) == ano) {
                if (!achouChamadaOrRecarga) {
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    System.out.println("===============================");
                    achouChamadaOrRecarga = true;
                }
                System.out.println("\n Chamada[" + i + "]: " + this.chamadas.get(i).toString());
                float _valorTotalChamadas = (float)(this.chamadas.get(i).getDuracao() * 1.45);
                System.out.println(" Valor da chamada: " + _valorTotalChamadas);
                valorTotalChamadas += _valorTotalChamadas;
            }
        }

        if (achouChamadaOrRecarga) {
            System.out.println("\n Total gasto em chamadas: " + valorTotalChamadas);
        }

        for (int i = 0; i < this.numRecargas; i++) {
            GregorianCalendar dataCorrente = this.recargas.get(i).getData();
            if (mes == dataCorrente.get(Calendar.MONTH) && dataCorrente.get(Calendar.YEAR) == ano) {
                if (!achouChamadaOrRecarga) {
                    System.out.println("===============================");
                    System.out.print("\nRecargas...");
                    System.out.println("Dados Pessoais...");
                    System.out.println(this.toString());
                    achouChamadaOrRecarga = true;
                }

                System.out.println("\n Recarga[" + i + "]: " + this.recargas.get(i).toString());
                valorTotalRecargas += this.recargas.get(i).getValor();
            }
        }

        if (achouChamadaOrRecarga) {
            System.out.println("\n Total gasto em recargas: " + valorTotalRecargas + "\n");
        }
    }
}
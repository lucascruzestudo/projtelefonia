import java.util.Scanner;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Telefonia {
    private int numPrePagos;
    private int numPosPagos;

    private PrePago[] prePago;
    private PosPago[] posPago;

    private Scanner scanner;

    public Telefonia() {
        this.posPago = new PosPago[5];
        this.prePago = new PrePago[5];
        this.scanner = new Scanner(System.in)
    }

    // Finalizar
    public void cadastrarAssinante() {
        GerenciadorEntrada gerenciadorEntrada = GerenciadorEntrada.getInstancia();
        EnumClassificacaoAssinantes tipoAssinante = gerenciadorEntrada.solicitarTipoAssinante();

        boolean is_pospago = tipoAssinante.equals(EnumClassificacaoAssinantes.POSPAGO);
        boolean is_prepago = tipoAssinante.equals(EnumClassificacaoAssinantes.PREPAGO);

        // verificando o tipo de  assinante para posteriormente solicitar os dados de \n
        //assinante específico 
    }

    public void fazerChamada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        GregorianCalendar dataHora = new GregorianCalendar();
        PosPago assinante_pos = null;
        PrePago assinante_pre = null;
        Long cpfAssinante = null;
        int duracao = 0;
        
        System.out.println("Digite o CPF do assinante: ");
        cpfAssinante = scanner.nextLong();

        assinante_pos = localizarPosPago(cpfAssinante);
        assinante_pre = localizarPrePago(cpfAssinante);

        if (assinante_pre == null && assinante_pos == null) {
            System.out.println("Assinante Não localizado...\n");
            return;
        }

        do {
            System.out.println("Digite a data (formato dd/MM/yyyy HH:mm:ss");
            String dataHoraStr = scanner.next();

            System.out.println("Digite a duracao da chamada em minutos: ");
            duracao = scanner.nextInt();

            try {
                Date dataHoraDate = sdf.parse(dataHoraStr);
                dataHora.setTime(dataHoraDate);
            } catch (ParseException e) {
                System.out.println("Data inválida");
                dataHora = null;
            }
        } while(dataHora == null || duracao < 1);

        if (assinante_pre) {
            assinante_pre.fazerChamada(dataHora, duracao);
        }

        if (assinante_pos) {
            assinante_pos.fazerChamada(dataHora, duracao);
        }

    }

    public void fazerRecarga() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        GregorianCalendar dataHora = new GregorianCalendar();
        PrePago assinante_pre = null;
        Long cpfAssinante = null;
        float valor = 0f;

        System.out.println("Digite o CPF do assinante: ");
        cpfAssinante = scanner.nextLong();

        assinante_pre = localizarPrePago(cpfAssinante);

        if (assinante_pre != null) {
            do {
                System.out.println("Digite a data (formato dd/MM/yyyy HH:mm:ss");
                String dataHoraStr = scanner.next();
                try {
                    Date dataHoraDate = sdf.parse(dataHoraStr);
                    dataHora.setTime(dataHoraDate);

                    System.out.println("Digite o valor da recarga: ");
                    valor = scanner.nextFloat();
                } catch (ParseException e) {
                    System.out.println("Data ou valor de recarga inválida");
                    dataHora = null;
                }
            } while(dataHora == null && valor <= 0);

            assinante_pre.recarregar(dataHora, valor);

        } else {
            System.out.println("Assinante prepago Não localizado...\n");
        }

    }

    public void imprimirFaturas() {
        int mesSelecionado = null;
        boolean entradaValida = false;
        EnumMeses mes = null;

        do {
            System.out.println("Digite o nome do mes: ");
            String input = scanner.nextLine().toUpperCase();

            try {
                mes = EnumMeses.valueOf(input);
                entradaValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Valor inválido.\n");
            }
        } while (!entradaValida || mes == null);

        mesSelecionado = mes.ordinal();

        System.out.println("\nFATURAS DE TODOS OS POSPAGOS...");
        for (PosPago pos: this.posPago) {
            if (pos == null) break;
            pos.imprimirFaturas(mesSelecionado);
        }

        System.out.println("\nFATURAS DE TODOS OS PREPAGOS...");
        for (PrePago pre: this.prePago) {
            if (pre == null) break;
            pre.imprimirFaturas(mesSelecionado);
        }

    }

    public void listarAssinante() {
        System.out.println("\nTODOS OS POSPAGOS...\n");
        for (PosPago pos: this.posPago) {
            if (pos == null) break;
            System.out.println(pos.toString());
        }
        System.out.println("\nTODOS OS PREPAGOS..\n");
        for (PrePago pre: this.prePago) {
            if (pre == null) break;
            System.out.println(pre.toString());
        }
        }

    public void sairDoPrograma() {
        scanner.close();
        System.exit(1);
    }

    private PrePago localizarPrePago(long cpf) {
        PrePago pre = null;

        for (int i = 0; i < this.numPrePagos; i++) {
            if (this.prePago[i].getCpf() == cpf) {
                pre = this.prePago[i];
                break;
            }
        }

        return pre;
    }

    private PosPago localizarPosPago(long cpf) {
        PosPago pos = null;

        for (int i = 0; i < this.numPosPagos; i++) {
            if (this.posPago[i].getCpf() == cpf) {
                pos = this.posPago[i];
                break;
            }
        }

        return pos;
    }

    public static void main(String[] args) {
        System.out.println("Seja bem-vindo a Telefonia");
        Menu menu = new Menu();
        menu.exibirMenu();
    }

}
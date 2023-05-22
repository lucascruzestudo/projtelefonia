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

    private static Scanner scanner = new Scanner(System.in);

    public Telefonia() {
        this.posPago = new PosPago[5];
        this.prePago = new PrePago[5];
    }

    public void cadastrarAssinante() {
        EnumClassificacaoAssinantes tipoAssinante = null;
        boolean se_pospago = false;
        boolean se_prepago = false;
        Long cpfAssinante = null;
        int telefone;
        String nome;
        float assinatura;
        boolean se_tipoAssinanteValido = false;

        while (!se_tipoAssinanteValido) {
            try {
                System.out.println("Digite o tipo de assinante (PREPAGO ou POSPAGO): ");
                String input = scanner.nextLine().toUpperCase();

                tipoAssinante = EnumClassificacaoAssinantes.valueOf(input);
                se_tipoAssinanteValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Valor inválido.\nDigite PREPAGO ou POSPAGO: ");
                se_tipoAssinanteValido = false;
            }
        }

        se_pospago = tipoAssinante.equals(EnumClassificacaoAssinantes.POSPAGO);
        se_prepago = tipoAssinante.equals(EnumClassificacaoAssinantes.PREPAGO);

        if (se_pospago && this.numPosPagos >= this.posPago.length) {
            System.out.println("Limite de Assinanntes Pospagos");
            return;
        }

        if (se_prepago && this.numPrePagos >= this.prePago.length) {
            System.out.println("Limite de Assinanntes Pospagos");
            return;
        }

        while (cpfAssinante == null) {
            System.out.println("Digite o CPF do assinante: ");
            cpfAssinante = scanner.nextLong();
            scanner.nextLine();
            PosPago assinante_pos = localizarPosPago(cpfAssinante);
            PrePago assinante_pre = localizarPrePago(cpfAssinante);

            if (assinante_pos == null && assinante_pre == null) {
                break;
            } else {
                System.out.println("CPF já cadastrado!");
                cpfAssinante = null;
            }
        }

        System.out.println("Digite o nome do assinante: ");
        nome = scanner.nextLine();
        System.out.println("Digite o telefone do assinante: ");
        telefone = scanner.nextInt();

        if (se_pospago) {
            System.out.println("Digite o valor da assinatura do Pospago: ");
            assinatura = scanner.nextFloat();

            this.posPago[this.numPosPagos] = new PosPago(cpfAssinante, nome, telefone, assinatura);
            this.numPosPagos++;
        }

        if (se_prepago) {
            this.prePago[this.numPrePagos] = new PrePago(cpfAssinante, nome, telefone);
            this.numPrePagos++;
        }

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
        scanner.nextLine();

        assinante_pos = localizarPosPago(cpfAssinante);
        assinante_pre = localizarPrePago(cpfAssinante);

        if (assinante_pre == null && assinante_pos == null) {
            System.out.println("Assinante Não localizado...\n");
            return;
        }

        do {
            System.out.println("Digite a data (formato dd/MM/yyyy HH:mm:ss): ");
            String dataHoraStr = scanner.nextLine();

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

        if (assinante_pre != null) {
            assinante_pre.fazerChamada(dataHora, duracao);
        }

        if (assinante_pos != null) {
            assinante_pos.fazerChamada(dataHora, duracao);
        }

    }

    public void fazerRecarga() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        GregorianCalendar dataHora = new GregorianCalendar();
        PrePago assinante_pre;
        Long cpfAssinante;
        float valor = 0f;

        System.out.println("Digite o CPF do assinante: ");
        cpfAssinante = scanner.nextLong();
        scanner.nextLine();

        assinante_pre = localizarPrePago(cpfAssinante);

        if (assinante_pre != null) {
            do {
                System.out.println("Digite a data (formato dd/MM/yyyy HH:mm:ss): ");
                String dataHoraStr = scanner.nextLine();

                System.out.println("Digite o valor da recarga: ");
                valor = scanner.nextFloat();

                try {
                    Date dataHoraDate = sdf.parse(dataHoraStr);
                    dataHora.setTime(dataHoraDate);
                } catch (ParseException e) {
                    System.out.println("\nData ou valor da recarga inválida\n");
                    scanner.nextLine();
                    dataHora = null;
                }
            } while(dataHora == null || valor <= 0);

           assinante_pre.recarregar(dataHora, valor);

        } else {
            System.out.println("Assinante prepago Não localizado...\n");
        }

    }

    public void imprimirFaturas() {
        int mesSelecionado;
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
            System.out.println(pos.toString() + "\n");
        }
        System.out.println("\nTODOS OS PREPAGOS..\n");
        for (PrePago pre: this.prePago) {
            if (pre == null) break;
            System.out.println(pre.toString() + "\n");
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

        Telefonia telefonia = new Telefonia();
        int opcao = 0;
        boolean exibirOpcoes = true;

        do {
            if (exibirOpcoes) {
                System.out.println("\n\n(1) Cadastrar assinante.");
                System.out.println("(2) Listar assinantes.");
                System.out.println("(3) Fazer chamada.");
                System.out.println("(4) Fazer recarga.");
                System.out.println("(5) Imprimir faturas.");
                System.out.println("(6) Sair do programa.");
            }
            exibirOpcoes = true;
            System.out.print("\nDigite sua opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Cadastrar assinante...");
                    telefonia.cadastrarAssinante();
                    break;
                case 2:
                    System.out.println("Listar assinantes...");
                    telefonia.listarAssinante();
                    break;
                case 3:
                    System.out.println("Fazer chamada...");
                    telefonia.fazerChamada();
                    break;
                case 4:
                    System.out.println("Fazer recarga...");
                    telefonia.fazerRecarga();
                    break;
                case 5:
                    System.out.println("Imprimir faturas...");
                    telefonia.imprimirFaturas();
                    break;
                case 6:
                    System.out.println("Sair do programa...");
                    telefonia.sairDoPrograma();
                    break;
                default:
                    exibirOpcoes = false;
                    System.out.println("Opção inválida...");
            }

        } while(opcao != 6);
    }

}
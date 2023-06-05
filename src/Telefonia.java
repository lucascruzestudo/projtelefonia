import com.sun.source.tree.InstanceOfTree;

import java.util.Scanner;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Telefonia {
    private int numAssinantes;
    private Assinante[] assinantes;
    private static Scanner scanner = new Scanner(System.in);

    public Telefonia() {
        this.assinantes = new Assinante[15];
    }

    public void cadastrarAssinante(Assinante assinante) {
        if (this.numAssinantes >= this.assinantes.length) {
            System.out.println("Chegou no limite de Assinanntes");
            return;
        }
        this.assinantes[this.numAssinantes] = assinante;
        this.numAssinantes++;
    }

    public void fazerChamada(Assinante assinante) {
        if (assinante == null) {
            System.out.println("Assinante Não localizado...\n");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        GregorianCalendar dataHora = new GregorianCalendar();
        int duracao = 0;

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

        assinante.fazerChamada(dataHora, duracao);
    }

    public void fazerRecarga(PrePago assinante_pre) {
        if (assinante_pre == null || !(assinante_pre instanceof PrePago)) {
            System.out.println("Assinante Não localizado...\n");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        GregorianCalendar dataHora = new GregorianCalendar();
        float valor = 0f;

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
    }

    public void imprimirFaturas() {
        int mesSelecionado, inputAno;
        boolean entradaValida = false;
        EnumMeses mes = null;

        do {
            System.out.println("Digite o nome do mes: ");
            String input = scanner.nextLine().toUpperCase();

            System.out.println("Digite o ano: ");
            inputAno = scanner.nextInt();
            scanner.nextLine();

            try {
                mes = EnumMeses.valueOf(input);
                entradaValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Valor inválido.\n");
            }
        } while (!entradaValida || mes == null);

        mesSelecionado = mes.ordinal();

        System.out.println("\nFATURAS DE TODOS OS Assinantes...");
        for (int i = 0; i < this.numAssinantes; i++) {
            assinantes[i].imprimirFaturas(mesSelecionado, inputAno);
        }

    }

    public void listarAssinante() {
        System.out.println("\nLista de Assinantes...\n");
        for (int i = 0; i < this.numAssinantes; i++) {
            System.out.println(assinantes[i].toString() + "\n");
        }
    }

    public void sairDoPrograma() {
        scanner.close();
        System.exit(1);
    }

    private Assinante localizarAssinante(Long cpf) {
        Assinante assinante = null;

        for (int i = 0; i < this.numAssinantes; i++) {
            if (this.assinantes[i].getCpf() == cpf) {
                assinante = this.assinantes[i];
                break;
            }
        }

        return assinante;

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
                    {
                        System.out.println("Cadastrar assinante...");

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

                        while (cpfAssinante == null) {
                            System.out.println("Digite o CPF do assinante: ");
                            cpfAssinante = scanner.nextLong();
                            scanner.nextLine();
                            Assinante assinante = telefonia.localizarAssinante(cpfAssinante);

                            if (assinante == null) {
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

                            telefonia.cadastrarAssinante(new PosPago(cpfAssinante, nome, telefone, assinatura));
                        }

                        if (se_prepago) {
                            telefonia.cadastrarAssinante(new PrePago(cpfAssinante, nome, telefone));
                        }
                    }
                    break;
                case 2:
                    System.out.println("Listar assinantes...");
                    telefonia.listarAssinante();
                    break;
                case 3:
                    {
                        System.out.println("Fazer chamada...");

                        System.out.println("Digite o CPF do assinante: ");
                        Long cpfAssinante = scanner.nextLong();
                        scanner.nextLine();
                        Assinante assinante = telefonia.localizarAssinante(cpfAssinante);

                        telefonia.fazerChamada(assinante);
                    }
                    break;
                case 4:
                    {
                        System.out.println("Fazer recarga...");

                        System.out.println("Digite o CPF do assinante: ");
                        Long cpfAssinante = scanner.nextLong();
                        scanner.nextLine();

                        Assinante assinante = telefonia.localizarAssinante(cpfAssinante);

                        if (assinante != null && assinante instanceof PrePago) {
                            telefonia.fazerRecarga((PrePago) assinante);
                        } else {
                            System.out.println("Assinante Não localizado...\n");
                        }
                    }
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
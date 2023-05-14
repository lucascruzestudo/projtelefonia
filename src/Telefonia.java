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

    public Telefonia() {
        this.posPago = new PosPago[5];
        this.prePago = new PrePago[5];
    }

    public void cadastrarAssinante() {
        GerenciadorEntrada gerenciadorEntrada = GerenciadorEntrada.getInstancia();
        EnumClassificacaoAssinantes tipoAssinante = gerenciadorEntrada.solicitarTipoAssinante();

        boolean is_pospago = tipoAssinante.equals(EnumClassificacaoAssinantes.POSPAGO);
        boolean is_prepago = tipoAssinante.equals(EnumClassificacaoAssinantes.PREPAGO);

        // verificando o tipo de  assinante para posteriormente solicitar os dados de \n
        //assinante específico 
    }

    public void fazerChamada() {
        Scanner scanner = new Scanner(System.in);
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
            scanner.close()
            return;
        }

        do {
            System.out.println("Digite a data (formato dd/MM/yyyy HH:mm:ss");
            String dataHoraStr = scanner.next();
            try {
                Date dataHoraDate = sdf.parse(dataHoraStr);
                dataHora.setTime(dataHoraDate);
            } catch (ParseException e) {
                System.out.println("Data inválida");
                dataHora = null;
            }
        } while(dataHora == null);

 
        System.out.println("Digite a duracao da chamada em minutos: ");
        duracao = scanner.nextInt();

        if (assinante_pre != null) {
            assinante_pre.fazerChamada(dataHora, duracao);
        }

        if (assinante_pos != null) {
            assinante_pos.fazerChamada(dataHora, duracao);
        }

        scanner.close()

    }

    public void fazerRecarga() {
        Scanner scanner = new Scanner(System.in);
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

            assinante_pre.fazerRecarga(dataHora, valor);

        } else {
            System.out.println("Assinante prepago Não localizado...\n");
        }

        scanner.close()
    }


}
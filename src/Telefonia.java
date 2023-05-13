import java.sql.Array;
import java.util.GregorianCalendar;

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
 // verificando o tipo de  assinante para posteriormente solicitar os dados de assinante específico 
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
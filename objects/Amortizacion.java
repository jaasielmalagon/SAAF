package objects;
/**
 *
 * @author JMalagon
 * Esta clase se ha incluido para manejar la información acerca de los pagos 
 * dentro de la base de datos. Contiene 15 métodos que nos ayudarán a manejar 
 * toda la información relacionada con los pagos de los clientes.
 */
public class Amortizacion {

    private int MONTO = 0, INTERES = 0, TOTAL = 0, PAGO = 0;
    public Amortizacion() {
    }

    /**
     * Tabla de amortización al 14.5% x 20 semanas (5 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla145_20(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 87;
                break;
            case 1500:
                pago = 129;
                break;
            case 2000:
                pago = 173;
                break;
            case 2500:
                pago = 216;
                break;
            case 3000:
                pago = 259;
                break;
            case 3500:
                pago = 302;
                break;
            case 4000:
                pago = 345;
                break;
            case 4500:
                pago = 388;
                break;
            case 5000:
                pago = 431;
                break;
            case 5500:
                pago = 474;
                break;
            case 6000:
                pago = 518;
                break;
            case 6500:
                pago = 561;
                break;
            case 7000:
                pago = 604;
                break;
            case 7500:
                pago = 647;
                break;
            case 8000:
                pago = 690;
                break;
            case 8500:
                pago = 733;
                break;
            case 9000:
                pago = 776;
                break;
            case 9500:
                pago = 819;
                break;
            case 10000:
                pago = 863;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    /**
     * Tabla de amortización al 14.0% x 20 semanas (5 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla140_20(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 85;
                break;
            case 1500:
                pago = 128;
                break;
            case 2000:
                pago = 170;
                break;
            case 2500:
                pago = 213;
                break;
            case 3000:
                pago = 255;
                break;
            case 3500:
                pago = 298;
                break;
            case 4000:
                pago = 340;
                break;
            case 4500:
                pago = 383;
                break;
            case 5000:
                pago = 425;
                break;
            case 5500:
                pago = 468;
                break;
            case 6000:
                pago = 510;
                break;
            case 6500:
                pago = 553;
                break;
            case 7000:
                pago = 595;
                break;
            case 7500:
                pago = 638;
                break;
            case 8000:
                pago = 680;
                break;
            case 8500:
                pago = 723;
                break;
            case 9000:
                pago = 765;
                break;
            case 9500:
                pago = 808;
                break;
            case 10000:
                pago = 850;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    /**
     * Tabla de amortización al 13.5% x 20 semanas (5 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla135_20(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 84;
                break;
            case 1500:
                pago = 126;
                break;
            case 2000:
                pago = 168;
                break;
            case 2500:
                pago = 209;
                break;
            case 3000:
                pago = 251;
                break;
            case 3500:
                pago = 293;
                break;
            case 4000:
                pago = 335;
                break;
            case 4500:
                pago = 377;
                break;
            case 5000:
                pago = 419;
                break;
            case 5500:
                pago = 461;
                break;
            case 6000:
                pago = 503;
                break;
            case 6500:
                pago = 544;
                break;
            case 7000:
                pago = 586;
                break;
            case 7500:
                pago = 628;
                break;
            case 8000:
                pago = 670;
                break;
            case 8500:
                pago = 712;
                break;
            case 9000:
                pago = 754;
                break;
            case 9500:
                pago = 796;
                break;
            case 10000:
                pago = 838;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    /**
     * Tabla de amortización al 13.0% x 20 semanas (5 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla130_20(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 83;
                break;
            case 1500:
                pago = 124;
                break;
            case 2000:
                pago = 165;
                break;
            case 2500:
                pago = 206;
                break;
            case 3000:
                pago = 248;
                break;
            case 3500:
                pago = 289;
                break;
            case 4000:
                pago = 330;
                break;
            case 4500:
                pago = 371;
                break;
            case 5000:
                pago = 413;
                break;
            case 5500:
                pago = 454;
                break;
            case 6000:
                pago = 495;
                break;
            case 6500:
                pago = 536;
                break;
            case 7000:
                pago = 578;
                break;
            case 7500:
                pago = 619;
                break;
            case 8000:
                pago = 660;
                break;
            case 8500:
                pago = 701;
                break;
            case 9000:
                pago = 743;
                break;
            case 9500:
                pago = 784;
                break;
            case 10000:
                pago = 825;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    //PRESTAMOS A 24 SEMANAS
    /**
     * Tabla de amortización al 14.5% x 24 semanas (6 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla145_24(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 78;
                break;
            case 1500:
                pago = 117;
                break;
            case 2000:
                pago = 156;
                break;
            case 2500:
                pago = 195;
                break;
            case 3000:
                pago = 234;
                break;
            case 3500:
                pago = 273;
                break;
            case 4000:
                pago = 312;
                break;
            case 4500:
                pago = 351;
                break;
            case 5000:
                pago = 390;
                break;
            case 5500:
                pago = 429;
                break;
            case 6000:
                pago = 468;
                break;
            case 6500:
                pago = 507;
                break;
            case 7000:
                pago = 546;
                break;
            case 7500:
                pago = 585;
                break;
            case 8000:
                pago = 624;
                break;
            case 8500:
                pago = 663;
                break;
            case 9000:
                pago = 702;
                break;
            case 9500:
                pago = 741;
                break;
            case 10000:
                pago = 780;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    /**
     * Tabla de amortización al 14.0% x 24 semanas (6 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla140_24(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 77;
                break;
            case 1500:
                pago = 115;
                break;
            case 2000:
                pago = 154;
                break;
            case 2500:
                pago = 192;
                break;
            case 3000:
                pago = 230;
                break;
            case 3500:
                pago = 268;
                break;
            case 4000:
                pago = 307;
                break;
            case 4500:
                pago = 345;
                break;
            case 5000:
                pago = 383;
                break;
            case 5500:
                pago = 422;
                break;
            case 6000:
                pago = 460;
                break;
            case 6500:
                pago = 498;
                break;
            case 7000:
                pago = 537;
                break;
            case 7500:
                pago = 575;
                break;
            case 8000:
                pago = 613;
                break;
            case 8500:
                pago = 652;
                break;
            case 9000:
                pago = 690;
                break;
            case 9500:
                pago = 728;
                break;
            case 10000:
                pago = 767;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    /**
     * Tabla de amortización al 13.5% x 24 semanas (6 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla135_24(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 75;
                break;
            case 1500:
                pago = 113;
                break;
            case 2000:
                pago = 151;
                break;
            case 2500:
                pago = 188;
                break;
            case 3000:
                pago = 226;
                break;
            case 3500:
                pago = 264;
                break;
            case 4000:
                pago = 302;
                break;
            case 4500:
                pago = 339;
                break;
            case 5000:
                pago = 377;
                break;
            case 5500:
                pago = 415;
                break;
            case 6000:
                pago = 452;
                break;
            case 6500:
                pago = 490;
                break;
            case 7000:
                pago = 528;
                break;
            case 7500:
                pago = 566;
                break;
            case 8000:
                pago = 603;
                break;
            case 8500:
                pago = 541;
                break;
            case 9000:
                pago = 679;
                break;
            case 9500:
                pago = 717;
                break;
            case 10000:
                pago = 754;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    /**
     * Tabla de amortización al 13.0% x 24 semanas (6 meses)
     *
     * @param monto : (int) monto de préstamo solicitado
     * @param semanas : (int) plazo en semanas para pagar el préstamo
     */
    public void tabla130_24(int monto, int semanas) {
        int pago;
        switch (monto) {
            case 1000:
                pago = 74;
                break;
            case 1500:
                pago = 111;
                break;
            case 2000:
                pago = 148;
                break;
            case 2500:
                pago = 185;
                break;
            case 3000:
                pago = 222;
                break;
            case 3500:
                pago = 260;
                break;
            case 4000:
                pago = 297;
                break;
            case 4500:
                pago = 334;
                break;
            case 5000:
                pago = 371;
                break;
            case 5500:
                pago = 408;
                break;
            case 6000:
                pago = 445;
                break;
            case 6500:
                pago = 482;
                break;
            case 7000:
                pago = 519;
                break;
            case 7500:
                pago = 556;
                break;
            case 8000:
                pago = 593;
                break;
            case 8500:
                pago = 630;
                break;
            case 9000:
                pago = 667;
                break;
            case 9500:
                pago = 705;
                break;
            case 10000:
                pago = 742;
                break;
            default:
                pago = 0;
                break;
        }
        this.MONTO = monto;
        this.TOTAL = pago * semanas;
        this.INTERES = this.TOTAL - monto;
        this.PAGO = pago;
    }

    public String[] montos() {
        String[] montos = {"1000", "1500", "2000", "2500", "3000", "3500", "4000", "4500", "5000", "6000", "6500", "7000", "8000", "8500", "9000", "9500", "10000"};
        return montos;
    }

    public int getMONTO() {
        return MONTO;
    }

    public int getINTERES() {
        return INTERES;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public int getPAGO() {
        return PAGO;
    }

    public double getTasa(int nPrestamos) {
//        if (nPrestamos > 15) {
//            return 10.0;
//        } else 
        if (nPrestamos >= 10) {
            return 13.0;
        } else if (nPrestamos <= 9 && nPrestamos >= 7) {
            return 13.5;
        } else if (nPrestamos <= 6 && nPrestamos >= 4) {
            return 14.0;
        } else {
            return 14.5;
        }
    }

    public void setAmortizacionFromSolicitud(int plazo, int montoSolicitado, String tasa) {
        if (plazo == 20) {//TABLAS A 20 SEMANAS                                    
            switch (tasa) {
                case "14.5":
                    this.tabla145_20(montoSolicitado, plazo);//14.5% A 20 SEMANAS
                    break;
                case "14.0":
                    this.tabla140_20(montoSolicitado, plazo);//14.0% A 20 SEMANAS
                    break;
                case "13.5":
                    this.tabla135_20(montoSolicitado, plazo);//13.5% A 20 SEMANAS
                    break;
                case "13.0":
                    this.tabla130_20(montoSolicitado, plazo);//13.0% A 20 SEMANAS
                    break;
                default:
                    System.out.println("No se encuentra la tabla de amortización para los parámetros ingresados.\nLa operación ha sido cancelada");
                    break;
            }
        } else if (plazo == 24) {//TABLAS A 24 SEMANAS
            switch (tasa) {
                case "14.5":
                    this.tabla145_24(montoSolicitado, plazo);//14.5% A 24 SEMANAS
                    break;
                case "14.0":
                    this.tabla140_24(montoSolicitado, plazo);//14.0% A 24 SEMANAS
                    break;
                case "13.5":
                    this.tabla135_24(montoSolicitado, plazo);//13.5% A 24 SEMANAS
                    break;
                case "13.0":
                    this.tabla130_24(montoSolicitado, plazo);//13.0% A 24 SEMANAS
                    break;
                default:
                    System.out.println("No se encuentra la tabla de amortización para los parámetros ingresados.\nLa operación ha sido cancelada");
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "|" + this.MONTO + "|" + this.INTERES + "|" + this.TOTAL + "|" + this.PAGO + "|";
    }

}

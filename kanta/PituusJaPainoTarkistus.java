package kanta;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class PituusJaPainoTarkistus {

//    /** 
//     * Pituuteen ja painoon kelpaavat tarkistusmerkit järjestyksessä
//     * Numeroita vastaavat merkit
//     */
//    public static final String NUMEROT = "0123456789";
//
//    /** Desimaalilukuun käyvät merkit */
//    public static final String DESIMAALINUMEROT = "0123456789.,";
//    
//    private String sallitut;
//
//    public static boolean merkkienTarkistus(String jono, String sallitut) {
//        for (int i = 0; i < jono.length(); i++)
//            if (sallitut.indexOf(jono.charAt(i)) < 0) return false;
//        return true;
//    }
//
//    public String tarkista(String jono) {
//        if (merkkienTarkistus(jono, sallitut)) return null;
//        return "Saa olla vain merkkejä: " + sallitut;
//    }
//
//    public void sisaltaaTarkistaja(String sallitut) {
//        this.sallitut = sallitut;
//    }
//
    /**
     * 
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala, yla]
     */
    public static double rand(double ala, double yla) {
        double n = (yla - ala) * Math.random() + ala;
        return Math.round(n);
    }

    /**
     * @return satunnainen luku annetulta väliltä
     */
    public static double arvoPituus() {
        return rand(210, 160);
    }

    /**
     * @return satunnainen luku annetulta väliltä
     */
    public static double arvoPaino() {
        return rand(70, 120);
    }
}

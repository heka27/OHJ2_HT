package Treeniseuranta;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class Profiili {

    private int tunnusNro = 0;
    private String nimi;
    private double paino;
    private double pituus;
    
    private static int seuraavaNro = 1;

    /**
     * Antaa profiilille seuraavan id-numeron
     * @return jasenen uusi tunnusNro
     * @example
     * <pre name="test">
     * Profiili Henrik = new Profiili();
     * Profiili Antti = new Profiili();
     * Henrik.rekisteroi();
     * Henrik.rekisteroi();
     * Antti.rekisteroi();
     * int n1 = Henrik.getTunnusNro(); 
     * int n2 = Antti.getTunnusNro();
     * n1 === n2 - 1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Antaa profiilin tunnusNron
     * @return profiilin tunnusNro
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /**
     * Hakee profiilin nimen
     * @return profiilin nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * tulostetaan profiilin tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " Paino: " + String.format("%.1f", paino) + "kg" + " Pituus: " + pituus + "cm");
    }
    
    /**
     * tulostetaan profiilin tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Täytetään profiilin tiedot satunnaisilla arvoilla
     */
    public void taytaProfiilinTiedot() {
        nimi = "Profiili";
        pituus = kanta.PituusJaPainoTarkistus.arvoPituus();
        paino = kanta.PituusJaPainoTarkistus.arvoPaino();
        
    }
    
    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Profiili Henrik = new Profiili();
        Profiili Antti = new Profiili();
        Henrik.rekisteroi();
        Antti.rekisteroi();
        Henrik.tulosta(System.out);
        Antti.tulosta(System.out);  
        Henrik.taytaProfiilinTiedot();
        Antti.taytaProfiilinTiedot();
        Henrik.tulosta(System.out);
        Antti.tulosta(System.out);
    }
}
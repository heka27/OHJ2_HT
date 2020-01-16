package Treeniseuranta;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class Liike {

    private int tunnusNro = 0;
    private int treeniNro = 1;
    private String liikeTeksti = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * Parametriton alustaja
     */
    public Liike() {
        //
    }
    
    /**
     * Alustetaan liike
     * @param treeniNro mille treenille liike kuuluu
     */
    public Liike(int treeniNro) {
        this.treeniNro = treeniNro;
    }

    /**
     * Antaa liikkeelle seuraavan id-numeron
     * @return liikkeen uusi tunnusNro
     * @example
     * <pre name="test">
     * Liike liike1 = new Liike();
     * Liike liike2 = new Liike();
     * liike1.rekisteroi();
     * liike1.rekisteroi();
     * liike2.rekisteroi();
     * int n1 = liike1.getTunnusNro(); 
     * int n2 = liike2.getTunnusNro();
     * n1 === n2 - 1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * Palauttaa liikkeen tekstin
     * @return liikkeen teksti
     */
    public String getLiikeTeksti() {
        return liikeTeksti;
    }
    
    /**
     * Asetetaan liikkeen teksti
     * @param liikeTeksti haluttu teksti
     */
    public void setLiikeTeksti(String liikeTeksti) {
        this.liikeTeksti = liikeTeksti;
    }

    /**
     * Antaa liikkeen tunnusNron
     * @return liikkeen tunnusNro
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /**
     * Palautetaan mille treenille liike kuuluu
     * @return profiilin id
     */
    public int getTreeniNro() {
        return treeniNro;
    }
    
    /**
     * tulostetaan liikkeen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " " + liikeTeksti);
    }
    
    /**
     * tulostetaan profiilin tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Täytetään liikkeen tiedot arvoilla
     * @param nro viite treenin jonka liikkeestä on kyse
     */
    public void taytaLiikkeenTiedot(int nro) {
        treeniNro = nro;
        liikeTeksti = "Penkki 3x6 60kg";
    }
    
    private void setTunnusNro(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    @Override
    public String toString() {
        return treeniNro + "|" + getTunnusNro() + "|" + getLiikeTeksti();
    }
    
    /**
     * Hakee liikkeen tiedot tiedostosta
     * @param rivi liikeen tiedot tiedostossa yhdellä rivillä
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        treeniNro = Mjonot.erota(sb,  '|', treeniNro);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        liikeTeksti = Mjonot.erota(sb, '|', liikeTeksti);
    }
    
    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Liike liike1 = new Liike();
        Liike liike2 = new Liike();
        liike1.rekisteroi();
        liike2.rekisteroi();
        liike1.tulosta(System.out);
        liike2.tulosta(System.out);  
        liike1.taytaLiikkeenTiedot(1);
        liike2.taytaLiikkeenTiedot(2);
        liike1.tulosta(System.out);
        liike2.tulosta(System.out);
    }
}
package Treeniseuranta;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class Treeni {

    private int tunnusNro = 0;
    private int profiiliNro = 1;
    private String tNimi = "";
    private String muistiinpano = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * Alustetaan Treeni. Vielä ei tarvitse tehdä mitään.
     */
    public Treeni() {
        //
    }
    
    /**
     * Alustetaan tietyn profiilin treeni
     * @param profiiliNro profiilin viitenumero
     */
    public Treeni(int profiiliNro) {
        this.profiiliNro = profiiliNro;
    }

    /**
     * Antaa treenille seuraavan id-numeron
     * @return treenin uusi tunnusNro
     * @example
     * <pre name="test">
     * Treeni Treeni1 = new Treeni();
     * Treeni Treeni2 = new Treeni();
     * Treeni1.rekisteroi();
     * Treeni2.rekisteroi();
     * int n1 = Treeni1.getTunnusNro(); 
     * int n2 = Treeni2.getTunnusNro();
     * n1 === n2 - 1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Antaa treenin tunnusNron
     * @return treenin tunnusNro
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    private void setTunnusNro(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * Palautetaan mille profiilille treeni kuuluu
     * @return profiilin id
     */
    public int getProfiiliNro() {
        return profiiliNro;
    }
    
    /**
     * Asetetaan treenille kuuluva profiilinumero
     * @param nro haluttu profiilinumero
     */
    public void setProfiilNro(int nro) {
        profiiliNro = nro;
    }
    
    /**
     * Palauttaa treenin nimen
     * @return treenin nimi
     */
    public String getTNimi() {
        return tNimi;
    }
    
    /**
     * Asettaa treenille käyttäjän antaman nimen
     * @param tNimi käyttäjän antama nimi
     */
    public void setTNimi(String tNimi) {
        this.tNimi = tNimi;
    }
    
    /**
     * Palauttaa treenin muistiinpanot
     * @return muistiinpanot
     */
    public String getMuistiinpano() {
        return muistiinpano;
    }
    
    /**
     * Asettaa muistiinpanoon käyttäjän antaman tekstin
     * @param teksti käyttäjän antama teksti
     */
    public void setMuistiinpanot(String teksti) {
        this.muistiinpano = teksti;
    }

    /**
     * tulostetaan treenin tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " " + tNimi + " " + muistiinpano);
    }

    /**
     * tulostetaan profiilin tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Täytetään treenin tiedot arvoilla
     */
    public void taytaTreeninTiedot() {
        double treeniNum = Math.random()*10;
        treeniNum++;
        tNimi = "Treeni" + Math.round(treeniNum);
        muistiinpano = "Meni hyvin";
    }
    
    @Override
    public String toString() {
        return getProfiiliNro() + "|" + getTunnusNro() + "|" + tNimi + "|" + getMuistiinpano();
    }
    
    /**
     * Hakee liikkeen tiedot tiedostosta
     * @param rivi liikeen tiedot tiedostossa yhdellä rivillä
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setProfiilNro(Mjonot.erota(sb, '|', getProfiiliNro()));
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        tNimi = Mjonot.erota(sb, '|', tNimi);
        muistiinpano = Mjonot.erota(sb, '|', getMuistiinpano()); 
    }
  
    
    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Treeni Treeni1 = new Treeni();
        Treeni Treeni2 = new Treeni();
        Treeni1.rekisteroi();
        Treeni2.rekisteroi();
        Treeni1.tulosta(System.out);
        Treeni2.tulosta(System.out);
        Treeni1.taytaTreeninTiedot();
        Treeni1.tulosta(System.out);
        Treeni2.taytaTreeninTiedot();
        Treeni2.tulosta(System.out);
    }
}

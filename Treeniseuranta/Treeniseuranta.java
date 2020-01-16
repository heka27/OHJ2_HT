package Treeniseuranta;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Treeniseuranta-luokka, joka huolehtii profiileista. Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" profiileihin.
 * @author Henrik Seppänen
 * @version 2.5.2019
 */
public class Treeniseuranta {

    private Profiilit profiilit = new Profiilit();
    private Treenit treenit = new Treenit();
    private Liikkeet liikkeet = new Liikkeet();

    /**
     * @param profiili lisättävä profiili
     * @throws SailoException jos lisäystä ei voitu tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treeniseuranta treeniseuranta = new Treeniseuranta();
     * Profiili Henrik = new Profiili(), Antti = new Profiili();
     * Henrik.rekisteroi(); Antti.rekisteroi();
     * treeniseuranta.getProfiileja() === 0;
     * treeniseuranta.lisaa(Henrik); treeniseuranta.getProfiileja() === 1;
     * treeniseuranta.lisaa(Antti); treeniseuranta.getProfiileja() === 2;
     * treeniseuranta.annaProfiili(0) === Henrik;
     * treeniseuranta.annaProfiili(1) === Antti;
     * </pre>
     */
    public void lisaa(Profiili profiili) throws SailoException {
        this.profiilit.lisaa(profiili);
    }

    /**
     * Lisätään uusi treeni treeniseurantaan
     * @param treeni lisättävä treeni
     * @throws SailoException Jos lisäämisessä ongelmia
     */
    public void lisaa(Treeni treeni) throws SailoException {
        treenit.lisaa(treeni);
    }

    /**
     * Poistetaan haluttu treeni ja treeniin liittyvät liikkeet
     * @param treeni poistettava treeni
     * @return 1 jos poisto onnistui, 0 jos ei
     */
    public int poista(Treeni treeni) {
        if (treeni == null) return 0;
        int ret = treenit.poista(treeni.getTunnusNro());
        liikkeet.poistaTreeninLiikkeet(treeni.getTunnusNro());
        return ret;
    }
    
    /**
     * Muokataan haluttua treeniä
     * @param treeni treeni jota muokataan
     */
    public void muokkaa(Treeni treeni) {
        treenit.muokkaa(treeni); 
    }

    /**
     * Lisätään uusi liike
     * @param liike liike joka halutaan lisätä
     */
    public void lisaa(Liike liike) {
        liikkeet.lisaa(liike);
    }
    
    /**
     * Poistetaan haluttu liike
     * @param liike liike joka poistetaan
     */
    public void poista(Liike liike) {
        if (liike == null) return;
        liikkeet.poista(liike);
    }
    
    /**
     * Muokataan haluttua liikettä
     * @param liike liike jota muokataan
     */
    public void muokkaa(Liike liike) {
        liikkeet.muokkaa(liike);
    }

    /**
     * Palauttaa i:n profiilin
     * @param i monesko profiili palautetaan
     * @return viite i:teen profiiliin
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Profiili annaProfiili(int i) throws IndexOutOfBoundsException {
        return profiilit.anna(i);
    }

    /**
     * Antaa paikassa i olevan treenin
     * @param i indeksi i
     * @return treeni paikassa i
     */
    public Treeni annaTreeni(int i) {
        return treenit.anna(i);
    }

    /**
     * Palauttaa profiilien lukumäärän
     * @return profiilien lukumäärä
     */
    public int getProfiileja() {
        return profiilit.getLkm();
    }

    /**
     * Palauttaa treenien lukumäärän
     * @return treenien lukumäärä
     */
    public int getTreeneja() {
        return treenit.getLkm();
    }


    /**
     * Haetaan profiilin kaikki treenit
     * @param profiili profiili jonka treenejä haetaan
     * @return tietorakenne jossa viitteet löydettyihin treeneihin
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     * Treeniseuranta treeniseuranta = new Treeniseuranta();
     * Profiili Henrik = new Profiili(), Antti = new Profiili(), Roope = new Profiili();
     * Henrik.rekisteroi(); Antti.rekisteroi(); Roope.rekisteroi();
     * int id1 = Henrik.getTunnusNro();
     * int id2 = Antti.getTunnusNro();
     * Treeni treeni11 = new Treeni(id1); treeniseuranta.lisaa(treeni11);
     * Treeni treeni12 = new Treeni(id1); treeniseuranta.lisaa(treeni12);
     * Treeni treeni21 = new Treeni(id2); treeniseuranta.lisaa(treeni21);
     * Treeni treeni22 = new Treeni(id2); treeniseuranta.lisaa(treeni22);
     * Treeni treeni23 = new Treeni(id2); treeniseuranta.lisaa(treeni23);
     * 
     * List<Treeni>loytyneet;
     * loytyneet = treeniseuranta.annaTreenit(Roope);
     * loytyneet.size() === 0;
     * loytyneet = treeniseuranta.annaTreenit(Henrik);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == treeni11 === true;
     * loytyneet.get(1) == treeni12 === true;
     * loytyneet = treeniseuranta.annaTreenit(Antti);
     * loytyneet.size() === 3;
     * loytyneet.get(0) == treeni21 === true;
     * </pre>
     */
    public List<Treeni> annaTreenit(Profiili profiili) {
        return treenit.annaTreenit(profiili.getTunnusNro());
    }

    /**
     * Antaa treenin kaikki liikkeet
     * @param treeniKohdalla minkä treenin liikkeet halutaan
     * @return lista liikkeistä
     */
    public List<Liike> annaLiikkeet(Treeni treeniKohdalla) {
        return liikkeet.annaLiikkeet(treeniKohdalla.getTunnusNro());
    }

    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi tiedoston uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        treenit.setTiedostonPerusNimi(hakemistonNimi + "treenit");
        liikkeet.setTiedostonPerusNimi(hakemistonNimi + "liikkeet");
    }

    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien treenien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä treeneistä
     */
    public Collection<Treeni> etsi(String hakuehto, int k) {
        return treenit.etsi(hakuehto, k);
    }

    /**
     * Lukee treeniseurannan tiedot tiedostosta
     * @param nimi nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.*;
     * #import java.util.*;
     * 
     * Treeniseuranta treeniseuranta = new Treeniseuranta();
     * 
     * Treeni treeni11 = new Treeni(1); treeni11.taytaTreeninTiedot(); treeni11.rekisteroi();
     * Treeni treeni12 = new Treeni(1); treeni12.taytaTreeninTiedot(); treeni12.rekisteroi();
     * Liike liike21 = new Liike(); liike21.taytaLiikkeenTiedot(treeni12.getTunnusNro()); liike21.rekisteroi();
     * Liike liike11 = new Liike(); liike11.taytaLiikkeenTiedot(treeni11.getTunnusNro()); liike11.rekisteroi();
     * Liike liike22 = new Liike(); liike22.taytaLiikkeenTiedot(treeni12.getTunnusNro()); liike22.rekisteroi();
     * Liike liike12 = new Liike(); liike12.taytaLiikkeenTiedot(treeni11.getTunnusNro()); liike12.rekisteroi();
     * Liike liike23 = new Liike(); liike23.taytaLiikkeenTiedot(treeni12.getTunnusNro()); liike23.rekisteroi();
     * 
     * String hakemisto = "testiTreeniseuranta";
     * File dir = new File(hakemisto);
     * File ftied = new File(hakemisto + "/treenit.dat");
     * File fhtied = new File(hakemisto + "/liikkeet.dat");
     * dir.mkdir();
     * ftied.delete();
     * fhtied.delete();
     * treeniseuranta.lueTiedostosta(hakemisto); #THROWS SailoException
     * treeniseuranta.lisaa(treeni11);
     * treeniseuranta.lisaa(treeni12);
     * treeniseuranta.lisaa(liike21);
     * treeniseuranta.lisaa(liike11);
     * treeniseuranta.lisaa(liike22);
     * treeniseuranta.lisaa(liike12);
     * treeniseuranta.lisaa(liike23);
     * treeniseuranta.tallenna();
     * treeniseuranta = new Treeniseuranta();
     * treeniseuranta.lueTiedostosta(hakemisto);
     * Collection<Treeni> kaikki = treeniseuranta.etsi("", -1);
     * Iterator<Treeni> it = kaikki.iterator();
     * it.next().toString() === treeni11.toString();
     * it.next().toString() === treeni12.toString();
     * it.hasNext() === false;
     * List<Liike> loytyneet = treeniseuranta.annaLiikkeet(treeni11);
     * Iterator<Liike> il = loytyneet.iterator();
     * il.next().toString() === liike11.toString();
     * il.next().toString() === liike12.toString();
     * il.hasNext() === false;
     * loytyneet = treeniseuranta.annaLiikkeet(treeni12);
     * il = loytyneet.iterator();
     * il.next().toString() === liike21.toString();
     * il.next().toString() === liike22.toString();
     * il.next().toString() === liike23.toString();
     * il.hasNext() === false;
     * treeniseuranta.lisaa(treeni12);
     * treeniseuranta.lisaa(liike23);
     * treeniseuranta.tallenna();
     * ftied.delete() === true;
     * fhtied.delete() === true;
     * File fbak = new File(hakemisto + "/treenit.bak");
     * File fhbak = new File(hakemisto + "/liikkeet.bak");
     * fbak.delete() === true;
     * fhbak.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        treenit = new Treenit();
        liikkeet = new Liikkeet();

        setTiedosto(nimi);
        treenit.lueTiedostosta();
        liikkeet.lueTiedostosta();
    }
    
    /**
     * Kysytään luokilta onko tietoja muutettu
     * @return true jos on, false jos ei
     */
    public boolean tallennetaanko() {
        if (treenit.tallennetaanko() == true || 
            liikkeet.tallennetaanko() == true) return true;
        return false;
    }

    /**
     * Tallentaa treeniseurannan tiedot tiedostoon
     * Vaikka treenien tallentaminen epäonnistuisi, niin yritetään silti tallentaa
     * liikkeet ennen poikkeuksen heittämistä
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            treenit.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }

        try {
            liikkeet.tallenna();       
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }

    /**
     * Testiohjelma treeniseurannasta
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Treeniseuranta treeniseuranta = new Treeniseuranta();

        try {
            Profiili Henrik = new Profiili(), Antti = new Profiili();
            Henrik.rekisteroi();
            Henrik.taytaProfiilinTiedot();
            Antti.rekisteroi();
            Antti.taytaProfiilinTiedot();

            treeniseuranta.lisaa(Henrik);
            treeniseuranta.lisaa(Antti);

            int id1 = Henrik.getTunnusNro();
            int id2 = Antti.getTunnusNro();
            Treeni treeni11 = new Treeni(id1); treeniseuranta.lisaa(treeni11); treeni11.taytaTreeninTiedot();
            Treeni treeni12 = new Treeni(id1); treeniseuranta.lisaa(treeni12); treeni12.taytaTreeninTiedot();
            Treeni treeni21 = new Treeni(id2); treeniseuranta.lisaa(treeni21); treeni21.taytaTreeninTiedot();
            Treeni treeni22 = new Treeni(id2); treeniseuranta.lisaa(treeni22); treeni22.taytaTreeninTiedot();
            Treeni treeni23 = new Treeni(id2); treeniseuranta.lisaa(treeni23); treeni23.taytaTreeninTiedot();

            System.out.println("============= Treeniseurannan testi =============");

            for (int i = 0; i < treeniseuranta.getProfiileja(); i++) {
                Profiili profiili = treeniseuranta.annaProfiili(i);
                System.out.println("Profiili paikassa: " + i);
                profiili.tulosta(System.out);
                List<Treeni> loytyneet = treeniseuranta.annaTreenit(profiili);
                for (Treeni treeni : loytyneet)
                    treeni.tulosta(System.out);
            }
        } catch (SailoException e) {
            System.err.println("Virhe: " + e.getMessage());
        }
    }

    /**
     * Asetetaan treenin muistiinpanoon haluttu teksti
     * @param treeni treeni jonka muistiinpanoa muokataan
     * @param teksti teksti joka lisätään
     */
    public void setMuistiinpanot(Treeni treeni, String teksti) {
        treenit.setMuistiinpanot(treeni.getTunnusNro(), teksti);
    }
}

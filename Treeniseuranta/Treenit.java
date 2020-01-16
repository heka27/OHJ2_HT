package Treeniseuranta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 */

public class Treenit implements Iterable<Treeni> {

    private static final int MAX_TREENEJA = 10;
    private boolean muutettu = false;
    private int lkm = 0;
    private String tiedostonPerusNimi = "Henkka/treenit";
    private Treeni alkiot[] = new Treeni[MAX_TREENEJA];

    /**
     * Oletusmuodostaja
     */
    public Treenit() {
        //
    }

    /**
     * Palauttaa profiilin treenien lukumäärän
     * @return treenien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }

    /**
     * Antaa halutun indeksin treenin
     * @param i indeksi
     * @return treeni
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    protected Treeni anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    /**
     * Iteraattori kaikkien treenien läpikäymiseen
     * @return treeni-iteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Treenit treenaukset = new Treenit();
     * Treeni treeni11 = new Treeni(1); treenaukset.lisaa(treeni11);
     * Treeni treeni12 = new Treeni(1); treenaukset.lisaa(treeni12);
     * Treeni treeni21 = new Treeni(2); treenaukset.lisaa(treeni21);
     * Treeni treeni13 = new Treeni(1); treenaukset.lisaa(treeni13);
     * Treeni treeni22 = new Treeni(2); treenaukset.lisaa(treeni22);
     * 
     * Iterator<Treeni> i2 = treenaukset.iterator();
     * i2.next() === treeni11;
     * i2.next() === treeni12;
     * i2.next() === treeni21;
     * i2.next() === treeni13;
     * i2.next() === treeni22;
     * i2.next() === treeni11; #THROWS NoSuchElementException
     * 
     * int n = 0;
     * int jnrot[] = { 1, 1, 2, 1, 2};
     * 
     * for ( Treeni treeni:treenaukset ) {
     * treeni.getProfiiliNro() === jnrot[n]; n++;
     * }
     * 
     * n === 5;
     * 
     * </pre>
     */
    @Override
    public Iterator<Treeni> iterator() {
        return new TreenitIterator();
    }

    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä jäsenistä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treenit treenit = new Treenit();
     * Treeni treeni11 = new Treeni(1); treeni11.parse("1|1|Treeni 1|Treeni meni hyvin");
     * Treeni treeni12 = new Treeni(1); treeni12.parse("1|2|Treeni 2");
     * Treeni treeni13 = new Treeni(1); treeni13.parse("1|1");
     * treenit.lisaa(treeni11); treenit.lisaa(treeni12); treenit.lisaa(treeni13);
     * </pre>
     */
    @SuppressWarnings("unused")
    public Collection<Treeni> etsi(String hakuehto, int k) {
        Collection<Treeni> loytyneet = new ArrayList<Treeni>();
        for (Treeni treeni : this) {
            loytyneet.add(treeni);
        }
        return loytyneet;
    }

    /**
     * Haetaan kaikki profiilin treenit
     * @param tunnusnro profiilin tunnusnumero jolle treenejä haetaan
     * @return tietorakenne jossa viitteet löydettyihin harrastuksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * #THROWS SailoException
     * 
     * Treenit treenaukset = new Treenit();
     * Treeni treeni11 = new Treeni(1); treenaukset.lisaa(treeni11);
     * Treeni treeni12 = new Treeni(1); treenaukset.lisaa(treeni12);
     * Treeni treeni21 = new Treeni(2); treenaukset.lisaa(treeni21);
     * Treeni treeni13 = new Treeni(1); treenaukset.lisaa(treeni13);
     * Treeni treeni22 = new Treeni(2); treenaukset.lisaa(treeni22);
     * Treeni treeni31 = new Treeni(3); treenaukset.lisaa(treeni31);
     * 
     * List<Treeni> loytyneet;
     * loytyneet = treenaukset.annaTreenit(4);
     * loytyneet.size() === 0;
     * loytyneet = treenaukset.annaTreenit(1);
     * loytyneet.size() === 3
     * loytyneet.get(0) == treeni11 === true;
     * loytyneet.get(1) == treeni12 === true;
     * loytyneet = treenaukset.annaTreenit(3);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == treeni31 === true;
     * </pre>
     */
    public List<Treeni> annaTreenit (int tunnusnro) {
        List<Treeni> loydetyt = new ArrayList<Treeni>();
        Treeni treeni = new Treeni();
        for (int i = 0; i < alkiot.length; i++) {
            treeni = alkiot[i];
            if (treeni.getProfiiliNro() == tunnusnro) loydetyt.add(treeni);
            if (alkiot [i+1] == null) break;
        }
        return loydetyt;
    }

    /**
     * Lukee treenit tiedostosta
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni(), treeni2 = new Treeni();
     * treeni1.taytaTreeninTiedot();
     * treeni2.taytaTreeninTiedot();
     * String hakemisto = "testiTreeniseuranta";
     * String tiedNimi = "treenit";
     * File ftied = new File(tiedNimi + ".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * treenit.lueTiedostosta(tiedNimi); #THROWS SailoException
     * treenit.lisaa(treeni1);
     * treenit.lisaa(treeni2);
     * treenit.tallenna();
     * treenit = new Treenit();
     * treenit.lueTiedostosta(tiedNimi);
     * Iterator<Treeni> i = treenit.iterator();
     * i.next().toString() === treeni1.toString();
     * i.next().toString() === treeni2.toString();
     * i.hasNext() === false;
     * treenit.lisaa(treeni2);
     * treenit.tallenna();
     * ftied.delete() === true;
     * File fbak = new File(tiedNimi + ".bak");
     * fbak.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {

        setTiedostonPerusNimi(tied);
        try (BufferedReader br = new BufferedReader(new FileReader(getTiedostonNimi()))) {

            String rivi;
            while ((rivi = br.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == '#') continue;
                Treeni treeni = new Treeni();
                treeni.parse(rivi);
                lisaa(treeni);
            }
            muutettu = false;

        } catch (FileNotFoundException e) {

            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea!");
        } 
        catch (IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
     * luetaan jo tehdystä tiedostosta
     * @throws SailoException jos lukemisessa ongelmia
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }

    /**
     * Kysytään onko tietoja muutettu
     * @return true jos on, false jos ei
     */
    public boolean tallennetaanko() {
        return muutettu;
    }

    /**
     * Tallentaa treenit tiedostoon
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;

        File fbak = new File (getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            for (Treeni treeni : this) {
                fo.println(treeni.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    /**
     * Asetetaan valitun treenin muistiinpanolle haluttu teksti
     * @param tnro treenin tunnusnumero
     * @param teksti haluttu teksti
     */
    public void setMuistiinpanot(int tnro, String teksti) {
        for (int i = 0; i < alkiot.length; i++) {
            if (alkiot[i].getTunnusNro() == tnro) {
                alkiot [i].setMuistiinpanot(teksti);
                muutettu = true;
                break;
            }
        }

    }

    /**
     * Lisää uuden treenin tietorakenteeseen. Ottaa treenin omistukseensa.
     * @param treeni lisättävä treeni.
     */
    public void lisaa(Treeni treeni) {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm*2);
        alkiot[lkm] = treeni;
        lkm++;
        muutettu = true;
    }

    /**
     * Muokataan treeniä
     * @param treeni treeni jota muokataan
     */
    public void muokkaa(Treeni treeni) {
        int id = treeni.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
                alkiot[i] = treeni;
                muutettu = true;
                return;
            }
        }
    }

    /**
     * Poistaa treenin jolla on valittu tunnusnumero
     * @param tnro poistettavan treenin tunnusnumero
     * @return 1 jos poisto onnistui, 0 jos ei
     * @example
     * <pre name="test">
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni(), treeni2 = new Treeni(), treeni3 = new Treeni();
     * treeni1.rekisteroi(); treeni2.rekisteroi(); treeni3.rekisteroi();
     * treenit.lisaa(treeni1); treenit.lisaa(treeni2); treenit.lisaa(treeni3);
     * treenit.poista(treeni1.getTunnusNro() + 1) === 1;
     * treenit.poista(treeni1.getTunnusNro()) === 1; treenit.getLkm() === 1;
     * treenit.poista(treeni1.getTunnusNro() + 3) === 0; treenit.getLkm() === 1;
     * </pre>
     */
    public int poista(int tnro) {
        int ind = 0;
        for (int i = 0; i < alkiot.length; i++) {
            if (tnro == alkiot[i].getTunnusNro()) {
                ind = i;
                break;
            }
            if (alkiot[i+1] == null) {
                ind = -1;
                break;
            }
        }
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot [i +1];
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    /** Asettaa tiedoston perusnimen ilman tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }

    /**
     * Palauttaa tiedoston nime, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Luokka treenien iteroimiseksi
     *@example
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Treenit treenit = new Treenit();
     * Treeni treeni1 = new Treeni(), treeni2 = new Treeni();
     * treeni1.rekisteroi(); treeni2.rekisteroi();
     * 
     * treenit.lisaa(treeni1);
     * treenit.lisaa(treeni2);
     * treenit.lisaa(treeni1);
     * 
     * StringBuilder ids = new StringBuilder(30);
     * for (Treeni treeni : treenit)
     *  ids.append(" " + treeni.getTunnusNro());
     *  
     *  String tulos = " " + treeni1.getTunnusNro() + " " + treeni2.getTunnusNro() + " " + treeni1.getTunnusNro();
     *  
     *  ids.toString() === tulos;
     *  
     *  ids = new StringBuilder(30);
     *  for (Iterator<Treeni> i = treenit.iterator(); i.hasNext();) {
     *      Treeni treeni = i.next();
     *      ids.append(" " + treeni.getTunnusNro());
     *  }
     *  
     *  ids.toString() === tulos;
     *  
     *  Iterator<Treeni>  i = treenit.iterator();
     *  i.next() == treeni1 === true;
     *  i.next() == treeni2 === true;
     *  i.next() == treeni1 === true;
     *  
     *  i.next(); #THROWS NoSuchElementException
     * </pre>
     */
    public class TreenitIterator implements Iterator<Treeni> {
        private int kohdalla = 0;

        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        @Override
        public Treeni next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }
    }

    /**
     * Testipääohjelma
     * @param args ei käytössä
     * @throws SailoException jos ongelmia
     */
    public static void main(String[] args) throws SailoException {

        Treenit treenaukset = new Treenit();
        Treeni Treeni1 = new Treeni();
        Treeni1.taytaTreeninTiedot();
        Treeni Treeni2 = new Treeni();
        Treeni2.taytaTreeninTiedot();
        Treeni Treeni3 = new Treeni();
        Treeni3.taytaTreeninTiedot();
        Treeni Treeni4 = new Treeni();
        Treeni4.taytaTreeninTiedot();

        treenaukset.lisaa(Treeni1);
        treenaukset.lisaa(Treeni2);
        treenaukset.lisaa(Treeni3);
        treenaukset.lisaa(Treeni2);
        treenaukset.lisaa(Treeni4);

        treenaukset.tallenna();

        System.out.println("============= Treenit testi =================");

        List<Treeni> treenit2 = treenaukset.annaTreenit(2);

        for (Treeni treeni : treenit2) {
            System.out.print(treeni.getProfiiliNro() + " ");
            treeni.tulosta(System.out);
        }
    }
}


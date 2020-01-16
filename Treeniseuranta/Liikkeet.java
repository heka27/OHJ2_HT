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
public class Liikkeet implements Iterable<Liike> {
    
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "Henkka/liikkeet";
    
    private final ArrayList<Liike> alkiot = new ArrayList<Liike>();

    /**
     * Vielä ei tarvitse tehdä mitään.
     */
    public Liikkeet() {
        //
    }
    
    /**
     * Lisää uuden liikkeen tietorakenteeseen. Ottaa liikkeen omistukseensa.
     * @param liike lisättävä liike
     */
    public void lisaa(Liike liike) {
        alkiot.add(liike);
        muutettu = true;
    }
    
    /**
     * Muokataan liikettä
     * @param liike liike jota muokataan
     */
    public void muokkaa(Liike liike) {
        int id = liike.getTunnusNro();
        for (int i = 0; i < alkiot.size(); i++) {
            if (alkiot.get(i).getTunnusNro() == id) {
                alkiot.set(i, liike);
                muutettu = true;
                return;
            }
        }
    }
    
    /**
     * Poistaa valitun liikkeen
     * @param liike poistettava liike
     * @return tosi, jos löytyi poistettava liike
     * @example
     * <pre name="test">
     * #import java.io.File;
     * Liikkeet liikkeet = new Liikkeet();
     * Liike liike21 = new Liike();
     * liike21.taytaLiikkeenTiedot(2);
     * Liike liike11 = new Liike();
     * liike11.taytaLiikkeenTiedot(1);
     * Liike liike22 = new Liike();
     * liike22.taytaLiikkeenTiedot(2);
     * Liike liike12 = new Liike();
     * liike12.taytaLiikkeenTiedot(1);
     * Liike liike23 = new Liike();
     * liike23.taytaLiikkeenTiedot(2);
     * liikkeet.lisaa(liike21);
     * liikkeet.lisaa(liike11);
     * liikkeet.lisaa(liike22);
     * liikkeet.lisaa(liike12);
     * liikkeet.poista(liike23) === false; liikkeet.getLkm() === 4;
     * liikkeet.poista(liike11) === true; liikkeet.getLkm() === 3;
     * List<Liike> l = liikkeet.annaLiikkeet(1);
     *  l.size() === 1;
     *  l.get(0) === liike12;
     * </pre>
     */
    public boolean poista(Liike liike) {
        boolean ret = alkiot.remove(liike);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * Poistaa kaikki tietyn treenin harrastukset
     * @param tunnusNro viite siihen mihin treeniin liittyvät harrastukset poistetaan
     * @return montako poistettiin
     * @example
     * <pre name="test">
     * Liikkeet liikkeet = new Liikkeet();
     * Liike liike21 = new Liike();
     * liike21.taytaLiikkeenTiedot(2);
     * Liike liike11 = new Liike();
     * liike11.taytaLiikkeenTiedot(1);
     * Liike liike22 = new Liike();
     * liike22.taytaLiikkeenTiedot(2);
     * Liike liike12 = new Liike();
     * liike12.taytaLiikkeenTiedot(1);
     * Liike liike23 = new Liike();
     * liike23.taytaLiikkeenTiedot(2);
     * liikkeet.lisaa(liike21);
     * liikkeet.lisaa(liike11);
     * liikkeet.lisaa(liike22);
     * liikkeet.lisaa(liike12);
     * liikkeet.lisaa(liike23);
     * liikkeet.poistaTreeninLiikkeet(2) === 3; liikkeet.getLkm() === 2;
     * liikkeet.poistaTreeninLiikkeet(3) === 0; liikkeet.getLkm() === 2;
     * List<Liike> l = liikkeet.annaLiikkeet(2);
     * l.size() === 0;
     * l = liikkeet.annaLiikkeet(1);
     * l.get(0) === liike11;
     * l.get(1) === liike12;
     * </pre>
     */
    public int poistaTreeninLiikkeet(int tunnusNro) {
        int n = 0;
        for (Iterator<Liike> it = alkiot.iterator(); it.hasNext();) {
            Liike liike = it.next();
            if (liike.getTreeniNro() == tunnusNro) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * Lukee liikeet tiedostosta
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * Liikkeet liikkeet = new Liikkeet();
     * Liike liike21 = new Liike(); liike21.taytaLiikkeenTiedot(2);
     * Liike liike11 = new Liike(); liike11.taytaLiikkeenTiedot(1);
     * Liike liike22 = new Liike(); liike22.taytaLiikkeenTiedot(2);
     * Liike liike12 = new Liike(); liike12.taytaLiikkeenTiedot(1);
     * Liike liike23 = new Liike(); liike23.taytaLiikkeenTiedot(2);
     * String tiedNimi = "testiLiikkeet";
     * File ftied = new File(tiedNimi + ".dat");
     * liikkeet.lueTiedostosta(tiedNimi); #THROWS SailoException
     * liikkeet.lisaa(liike21);
     * liikkeet.lisaa(liike11);
     * liikkeet.lisaa(liike22);
     * liikkeet.lisaa(liike12);
     * liikkeet.lisaa(liike23);
     * liikkeet.tallenna();
     * liikkeet = new Liikkeet();
     * liikkeet.lueTiedostosta(tiedNimi);
     * Iterator<Liike> i = liikkeet.iterator();
     * i.next().toString() === liike21.toString();
     * i.next().toString() === liike11.toString();
     * i.next().toString() === liike22.toString();
     * i.next().toString() === liike12.toString();
     * i.next().toString() === liike23.toString();
     * i.hasNext() === false;
     * liikkeet.lisaa(liike23);
     * liikkeet.tallenna();
     * ftied.delete() === true;
     * File fbak = new File(tiedNimi + ".bak");
     * fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        
        setTiedostonPerusNimi(tied);
        try (BufferedReader br = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            
            String rivi;
            while ((rivi = br.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == '#') continue;
                Liike liike = new Liike();
                liike.parse(rivi);
                lisaa(liike);
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
     * Luetaan aikaisemin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee ongelmia lukemisessa
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
     * Tallentaa liikkeet tiedostoon
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            for (Liike liike : this) {
                fo.println(liike.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        
        muutettu = false;
    }
    
    /**
     * Palauttaa treenin liikkeiden lukumäärän
     * @return liikkeiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
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
     * Iteraattori kaikkien liikkeiden läpikäymiseen
     * @return liike-iteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Liikkeet liikkeet = new Liikkeet();
     * Liike liike11 = new Liike(1); liikkeet.lisaa(liike11);
     * Liike liike12 = new Liike(1); liikkeet.lisaa(liike12);
     * Liike liike21 = new Liike(2); liikkeet.lisaa(liike21);
     * Liike liike13 = new Liike(1); liikkeet.lisaa(liike13);
     * Liike liike22 = new Liike(2); liikkeet.lisaa(liike22);
     * 
     * Iterator<Liike> i2 = liikkeet.iterator();
     * i2.next() === liike11;
     * i2.next() === liike12;
     * i2.next() === liike21;
     * i2.next() === liike13;
     * i2.next() === liike22;
     * i2.next() === liike11; #THROWS NoSuchElementException
     * 
     * int n = 0;
     * int jnrot[] = { 1, 1, 2, 1, 2};
     * 
     * for ( Liike liike : liikkeet ) {
     * liike.getTreeniNro() === jnrot[n]; n++;
     * }
     * 
     * n === 5;
     * 
     * </pre>
     */
    @Override
    public Iterator<Liike> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Haetaan kaikki treenin liikkeet
     * @param tunnusnro treenin tunnusnumero jolle liikkeitä haetaan
     * @return tietorakenne jossa viitteet löydettyihin liikkeisiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Liikkeet liikkeet = new Liikkeet();
     * Liike liike11 = new Liike(1); liikkeet.lisaa(liike11); liike11.rekisteroi();
     * Liike liike12 = new Liike(1); liikkeet.lisaa(liike12); liike12.rekisteroi();
     * Liike liike21 = new Liike(2); liikkeet.lisaa(liike21); liike21.rekisteroi();
     * Liike liike13 = new Liike(1); liikkeet.lisaa(liike13); liike13.rekisteroi();
     * Liike liike22 = new Liike(2); liikkeet.lisaa(liike22); liike22.rekisteroi();
     * Liike liike31 = new Liike(3); liikkeet.lisaa(liike31); liike31.rekisteroi();
     * 
     * List<Liike> loytyneet;
     * loytyneet = liikkeet.annaLiikkeet(4);
     * loytyneet.size() === 0;
     * loytyneet = liikkeet.annaLiikkeet(1);
     * loytyneet.size() === 3
     * loytyneet.get(0) == liike11 === true;
     * loytyneet.get(1) == liike12 === true;
     * loytyneet = liikkeet.annaLiikkeet(3);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == liike31 === true;
     * </pre>
     */
    public List<Liike> annaLiikkeet (int tunnusnro) {
        List<Liike> loydetyt = new ArrayList<Liike>();
        for ( Liike liike : alkiot )
            if ( liike.getTreeniNro() == tunnusnro ) loydetyt.add(liike);
        return loydetyt;
    }
    
    /**
     * Testipääohjelma
     * @param args ei käytössä
     * @throws SailoException jos ongelmia
     */
    public static void main(String[] args) throws SailoException {
        
        Liikkeet liikkeet = new Liikkeet();
        Liike Liike1 = new Liike();
        Liike1.taytaLiikkeenTiedot(1);
        Liike Liike2 = new Liike();
        Liike2.taytaLiikkeenTiedot(2);
        Liike Liike3 = new Liike();
        Liike3.taytaLiikkeenTiedot(3);
        Liike Liike4 = new Liike();
        Liike4.taytaLiikkeenTiedot(1);
        
        liikkeet.lisaa(Liike1);
        liikkeet.lisaa(Liike2);
        liikkeet.lisaa(Liike3);
        liikkeet.lisaa(Liike2);
        liikkeet.lisaa(Liike4);
        
        liikkeet.tallenna();
        
        System.out.println("============= Liikkeet testi =================");

        List<Liike> liikkeet2 = liikkeet.annaLiikkeet(2);

        for (Liike liike : liikkeet2) {
            System.out.print(liike.getTreeniNro() + " ");
            liike.tulosta(System.out);
        }
    }
}


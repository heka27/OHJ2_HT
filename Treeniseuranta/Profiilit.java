package Treeniseuranta;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class Profiilit {
    private static final int MAX_PROFIILIT = 10;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Profiili[]       alkiot        = new Profiili[MAX_PROFIILIT];
    private boolean          muutettu      = false;
    
    /**
     * Oletusmuodostaja
     */
    public Profiilit() {
        //
    }
    
    /**
     * Lisää uuden jäsenen tietorakenteeseen. Ottaa jäsenen omistukseensa.
     * @param profiili lisättävän profiilin viite. Huom! Tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Profiilit profiilit = new Profiilit();
     * Profiili Henrik = new Profiili(), Antti = new Profiili();
     * profiilit.getLkm() === 0;
     * profiilit.lisaa(Henrik); profiilit.getLkm() === 1;
     * profiilit.lisaa(Antti); profiilit.getLkm() === 2;
     * profiilit.anna(0) === Henrik;
     * profiilit.anna(1) === Antti;
     * profiilit.anna(1) == Henrik === false;
     * profiilit.anna(1) == Antti === true;
     * profiilit.anna(2) === Henrik; #THROWS IndexOutOfBoundsException
     * profiilit.lisaa(Henrik); profiilit.getLkm() === 3;
     * profiilit.lisaa(Henrik);
     * profiilit.lisaa(Henrik); profiilit.getLkm() === 5;
     * profiilit.lisaa(Henrik);
     * profiilit.lisaa(Henrik);
     * profiilit.lisaa(Henrik);
     * profiilit.lisaa(Henrik);
     * profiilit.lisaa(Henrik);
     * profiilit.lisaa(Henrik); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Profiili profiili) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        this.alkiot[lkm] = profiili;
        lkm++;
    }
    
    /**
     * Palauttaa viitteen i:een jäseneen
     * @param i monennenko jäsenen viite halutaan.
     * @return viite jäseneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Profiili anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * Lukee treenit tiedostosta. Kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/treenit.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    /**
     * Kysytään onko tietoja muutettu
     * @return true jos on, false jos ei
     */
    public boolean tallennetaanko() {
        return muutettu;
    }
    
    /**
     * Tallentaa profiilit tiedostoon. Kesken.
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata vielä tallentaa tiedostoa " + tiedostonNimi);
    }
    
    /**
     * Palauttaa ohjelman profiilien lukumäärän
     * @return profiilien lukumäärä
     */
    public int getLkm() {
        return this.lkm;
    }
    
    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
    Profiilit profiilit = new Profiilit();
    
    Profiili Henrik = new Profiili(), Antti = new Profiili();
    Antti.rekisteroi();
    Henrik.tulosta(System.out);
    Antti.tulosta(System.out);  
    Henrik.taytaProfiilinTiedot();
    Antti.taytaProfiilinTiedot();
    
    try {
        profiilit.lisaa(Henrik);
        profiilit.lisaa(Antti);
    
    System.out.println("============== Jäsenet testi ==============");
   
    for (int i = 0; i < profiilit.getLkm(); i++) {
        Profiili profiili = profiilit.anna(i);
        System.out.println("Jäsen paikassa: " + i);
        profiili.tulosta(System.out);
    }
    } catch (SailoException e) {
        System.err.println("Virhe: " + e.getMessage());
    }
    }

}

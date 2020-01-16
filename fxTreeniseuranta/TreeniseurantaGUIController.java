package fxTreeniseuranta;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Treeniseuranta.Liike;
import Treeniseuranta.Profiili;
import Treeniseuranta.SailoException;
import Treeniseuranta.Treeni;
import Treeniseuranta.Treeniseuranta;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author Henrik Seppänen
 * @version 6.5.2019
 *
 */
public class TreeniseurantaGUIController implements Initializable {

    @FXML private ListChooser<Treeni> chooserTreenit;
    @FXML private ListChooser<Liike> chooserLiikkeet;
    @FXML private TextField muistiinpanot;
    @FXML private TextField hakuehto;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    /**
     * Käsitellään uuden treenin lisääminen
     */
    @FXML private Button avaaUusiTreeni;

    @FXML private void handleUusiTreeni() throws SailoException {
        uusiTreeni();
    }

    /**
     * Käsitellään treenin muokkaaminen
     */
    @FXML private void handleMuokkaaTreeni() {
        muokkaaTreeni();
    }

    /**
     * Käsitellään treenin poistaminen
     */
    @FXML private void handlePoistaTreeni() {
        if (voikoPoistaa() == true)
            poistaTreeni();
    }

    /**
     * Käsitellään treenien hakeminen listaan
     */
    @FXML private void handleHaeTreeni() {
        haeTreenit(0);
    }

    /**
     * Käsitellään uuden liikkeen lisääminen
     */
    @FXML private Button avaaUusiLiike;

    @FXML private void handleUusiLiike() {
        uusiLiike();
    }

    /**
     * Käsitellään liikkeen muokkaaminen
     */
    @FXML private void handleMuokkaaLiike() {
        muokkaaLiike();
    }

    @FXML private Button poistaLiike;

    /**
     * Käsitellään yleinen poistaminen
     */
    @FXML private void handlePoistaLiike() {
        if (voikoPoistaa() == true)
            poistaLiike();
    }

    /**
     * Käsitellään painoseuranta-ikkuna
     */
    @FXML private Button avaaPainoseuranta;

    /**
     * Käsitellään painoseurannan avaaminen
     */
    @FXML private void handlePainoseuranta() {
        ModalController.showModal(TreeniseurantaGUIController.class.getResource("painoseuranta.fxml"), "Painoseuranta", null, "" );
    }

    @FXML private TextField pituuskentta;
    @FXML private TextField painokentta;
    @FXML private TextField bmikentta;

    /**
     * Käsitellään BMI:n laskeminen
     */
    @FXML private void handleBMI() {
        laskeBMI();
    }

    /**
     * Käsitellään uuden profiilin lisääminen
     */
    @FXML private void handleUusiProfiili() {
        ModalController.showModal(TreeniseurantaGUIController.class.getResource("uusiProfiili.fxml"), "Tietoja", null, "" );
    }

    /**
     * Kysytään käyttäjältä poiston varmistus
     */
    private boolean voikoPoistaa() {
        return Dialogs.showQuestionDialog("Poisto", "Haluatko varmasti poistaa?", "Kyllä", "Ei");
    }

    /**
     * Käsitellään muistiinpanojen tallennus
     */
    @FXML private void handleMuistiinpanot() {
        setMuistiinpano();
    }

    /**
     * Käsitellään tallennuskäsky
     * @throws SailoException jos tallennuksessa ongelmia
     */
    @FXML private void handleTallenna() throws SailoException {
        tallenna();
    }

    /**
     * Käsitellään lopetuskäsky
     * @throws SailoException jos tallennuksessa ongelmia
     */
    @FXML private void handleLopeta() throws SailoException {
        if (voikoLopettaa() == true) {
            
            tallenna();
            Platform.exit();
        }
    }

    /**
     * Käsitellään apua-ikkuna
     */
    @FXML private void handleApua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/heansase");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Käsitellään tietoja-ikkuna
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(TreeniseurantaGUIController.class.getResource("tietoja.fxml"), "Tietoja", null, "" );
    }


    //===========================================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia

    @SuppressWarnings("unused")
    private String treeniseurantanimi = "treeniseuranta";
    private Treeniseuranta treeniseuranta;
    private Treeni treeniKohdalla;
    private Liike liikeKohdalla;
    private Profiili perusprofiili;

    /**
     * Tekee tarvittavat muut alustukset ja alustetaan treenilistan kuuntelija
     */
    protected void alusta() {
        chooserTreenit.addSelectionListener(e -> naytaTreeni());
        chooserLiikkeet.addSelectionListener(e -> naytaLiike());
    }

    /**
     * Asetetaan viite käytettävään treeniseurantaan
     * @param treeniseuranta käytettävä treeniseuranta
     * @throws SailoException jos virheitä
     */
    public void setTreeniseuranta(Treeniseuranta treeniseuranta) throws SailoException {
        this.treeniseuranta = treeniseuranta;

        perusprofiili = new Profiili();
        perusprofiili.rekisteroi();
        perusprofiili.taytaProfiilinTiedot();
        treeniseuranta.lisaa(perusprofiili);

        naytaTreeni();
        naytaLiike();
    }


    /**
     * Luodaan uusi treeni
     * @throws SailoException Jos lisäämisessä ongelmia
     */
    protected void uusiTreeni() throws SailoException {
        Treeni treeni = new Treeni();
        treeni = TreeniController.kysyTreeniTiedot(null, treeni, "Uusi Treeni");
        if (treeni == null) return;
        treeni.rekisteroi();
        treeniseuranta.lisaa(treeni);
        haeTreenit(treeni.getTunnusNro());
    }

    /**
     * Poistetaan listalta valittu treeni
     */
    private void poistaTreeni() {
        Treeni treeni = treeniKohdalla;
        if ( treeni == null ) return;
        treeniseuranta.poista(treeni);
        int index = chooserTreenit.getSelectedIndex();
        haeTreenit(0);
        chooserTreenit.setSelectedIndex(index);
    }

    /**
     * Muokataan valittua treeniä
     */
    private void muokkaaTreeni() {
        if (treeniKohdalla == null) return;
        Treeni treeni = treeniKohdalla;
        treeni = TreeniController.kysyTreeniTiedot(null, treeni, "Muokkaa Treeniä");
        if (treeni == null) return;
        treeniseuranta.muokkaa(treeni);
        chooserTreenit.clear();
        haeTreenit(treeni.getTunnusNro());
    }

    /**
     * Haetaan treenit listaan
     * @param tnro treeninumero
     */
    protected void haeTreenit(int tnro) {
        chooserTreenit.clear();
        String ehto = hakuehto.getText().toLowerCase();
        int index = 0;
        for (int i = 0; i < treeniseuranta.getTreeneja(); i++) {
            Treeni treeni = treeniseuranta.annaTreeni(i);
            if (treeni == null) break;
            if (treeni.getTunnusNro() == tnro) index = i;
            if (treeni.getTNimi().toLowerCase().contains(ehto)) {
                chooserTreenit.add(treeni.getTNimi(), treeni);
            }
        }
        chooserTreenit.setSelectedIndex(index); //muutosviesti joka näyttää jäsenen
        haeLiikkeet();
    }

    /**
     * Tekee uuden tyhjän liikkeen editointia varten
     */
    protected void uusiLiike() { 
        Liike liike = new Liike(treeniKohdalla.getTunnusNro());
        liike = LiikeController.kysyLiikeTiedot(null, liike, "Uusi Liike");
        if (liike == null) return;
        liike.rekisteroi();
        treeniseuranta.lisaa(liike);
        haeLiikkeet();
    }

    /**
     * Poistetaan valittu liike
     */
    private void poistaLiike() {
        Liike liike = liikeKohdalla;
        if (liike == null) return;
        treeniseuranta.poista(liike);
        int index = chooserLiikkeet.getSelectedIndex();
        haeLiikkeet();
        chooserLiikkeet.setSelectedIndex(index);
    }

    /**
     * Muokataan valittua liikettä
     */
    private void muokkaaLiike() {
        if (liikeKohdalla == null) return;
        Liike liike = liikeKohdalla;
        liike = LiikeController.kysyLiikeTiedot(null, liike, "Muokkaa Liikettä");
        if (liike == null) return;
        treeniseuranta.muokkaa(liike);
        chooserTreenit.clear();
        haeTreenit(treeniKohdalla.getTunnusNro());
    }

    /**
     * Haetaan liikkeet listaan
     */
    protected void haeLiikkeet() {
        chooserLiikkeet.clear();
        if (treeniKohdalla == null) return;

        List<Liike> loytyneet = treeniseuranta.annaLiikkeet(treeniKohdalla);
        for (Liike liike : loytyneet) {
            chooserLiikkeet.add(liike.getLiikeTeksti(), liike);
        }
    }

    /**
     * Asetetaan muistiinpano treenille
     */
    private void setMuistiinpano() {
        Treeni treeni = treeniKohdalla;
        treeniseuranta.setMuistiinpanot(treeni, muistiinpanot.getText());
    }


    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkaa sovelluksen, false jos ei
     * @throws SailoException jos tallenuksessa ongelmia
     */
    public boolean voikoSulkea() throws SailoException {
        if (voikoLopettaa() == true) {
            if (tallennetaanko() == true) tallenna();
            return true;
        }
        return false;
    }

    /**
     * Näyttää listasta valitun treenin tiedot.
     */
    protected void naytaTreeni() {
        treeniKohdalla = chooserTreenit.getSelectedObject();
        if (treeniKohdalla == null) return;
        haeLiikkeet();
        naytaMuistiinpano();
    }


    /**
     * Näyttää listasta valitun liikkeen tiedot.
     */
    protected void naytaLiike() {
        liikeKohdalla = chooserLiikkeet.getSelectedObject();
    }
    
    
    /**
     * Haetaan ja näytetään treeniin liittyvät muistiinpanot
     */
    protected void naytaMuistiinpano() {
        Treeni treeni = treeniKohdalla;
        muistiinpanot.setText(treeni.getMuistiinpano());
    }
    
    /**
     * Lasketaan BMI käyttäjän antamilla arvoilla
     */
    public void laskeBMI() {
        if (!painokentta.getText().matches("[0-9,.]+") || 
            !pituuskentta.getText().matches("[0-9]+")) {
            Dialogs.showMessageDialog("Anna vain numeroita");
            return;
        }
        double pituus = Double.parseDouble(pituuskentta.getText());
        double paino = Double.parseDouble(painokentta.getText().replace(',', '.'));
        double vastaus = paino / Math.pow(pituus / 100, 2);
        bmikentta.setText(String.format("%.1f",vastaus));
    }

    /**
     * @return true jos kyllä, false jos ei
     */
    public boolean voikoLopettaa() {
        return Dialogs.showQuestionDialog("Poistuminen", "Haluatko varmasti lopettaa?", "Kyllä", "Ei");
    }
    
    /**
     * Kysytään onko tietoja muutettu
     * @return true jos on, false jos ei
     */
    public boolean tallennetaanko() {
        return treeniseuranta.tallennetaanko();
    }

    /**
     * Tietojen tallennus
     * @throws SailoException jos tallennuksessa ongelmia
     */
    @FXML private void tallenna() throws SailoException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Valitse");
        alert.setHeaderText(null);
        alert.setContentText("Tallennetaanko?");

        ButtonType buttonTypeYes = new ButtonType("Kyllä", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if ( result.get() == buttonTypeYes ) {
            setMuistiinpano();
            treeniseuranta.tallenna();
        }
    }

    /**
     * Luetaan tiedosto
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = "Profiili";
        lueTiedosto(uusinimi);
        return true;
    }

    /**
     * Luetaan tallennettu tiedosto
     */
    private String lueTiedosto(String nimi) {
        treeniseurantanimi = nimi;
        try {
            treeniseuranta.lueTiedostosta(nimi);
            haeTreenit(0);
            return null;
        } catch (SailoException e) {
            haeTreenit(0);
            String virhe = e.getMessage();
            return virhe;
        }

    }

    //    private void uusiProfiili() {
    //        Profiili uusi = new Profiili();
    //        uusi.rekisteroi();
    //        uusi.taytaProfiilinTiedot();
    //        try {
    //            treeniseuranta.lisaa(uusi);
    //        } catch (SailoException e) {
    //            Dialogs.showMessageDialog("Ongelmia uuden luomisessa" + e.getMessage());
    //            return;
    //        }
}
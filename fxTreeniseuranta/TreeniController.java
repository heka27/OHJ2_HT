package fxTreeniseuranta;

import Treeniseuranta.Treeni;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Henrik Seppänen
 * @version 6.5.2019
 *
 */
public class TreeniController implements ModalControllerInterface<Treeni> {

    @FXML private TextField treeniTiedot;
    private Treeni treeniKohdalla = null;

    /**
     * Käsitellään treenin lisäys
     */
    @FXML private void handleOK() {
        treeniKohdalla.setTNimi(treeniTiedot.getText());
        ModalController.closeStage(treeniTiedot);
    }

    /**
     * Käsitellään ikkunasta poistuminen
     */
    @FXML private void handleCancel() {
        ModalController.closeStage(treeniTiedot);
        treeniKohdalla = null;
    }

    /**
     * Mitä dialogista palautetaan 
     */
    @Override
    public Treeni getResult() {
        return treeniKohdalla;
    }

    /**
     * Asetetaan mitä näytetään oletuksena
     */
    @Override
    public void setDefault(Treeni oletus) {
        treeniKohdalla = oletus;
        naytaTreeni(treeniKohdalla);

    }

    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        treeniTiedot.requestFocus();
    }

    /**
     * Näytetään treenin tiedot TextField komponenttiin
     * @param treeni näytettävä treeni
     */
    public void naytaTreeni(Treeni treeni) {
        if (treeni == null) return;
        treeniTiedot.setText(treeni.getTNimi());
    }

    /**
     * Luodaan treenin tietojen kysymysdialogi ja palautetaan siihen kirjoitettu tieto tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä treeniä näytetään oletuksena
     * @param title ikkunan otsikko
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static Treeni kysyTreeniTiedot(Stage modalityStage, Treeni oletus, String title) {
        return ModalController.showModal(
                TreeniController.class.getResource("uusiTreeni.fxml"),
                title,
                modalityStage, oletus);
    }
}


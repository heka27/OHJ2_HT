package fxTreeniseuranta;

import Treeniseuranta.Liike;
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
public class LiikeController implements ModalControllerInterface<Liike> {

    @FXML private TextField liikeTiedot;
    private Liike liikeKohdalla = null;

    /**
     * Käsitellään liikkeen lisääminen
     */
    @FXML private void handleOK() {
        liikeKohdalla.setLiikeTeksti(liikeTiedot.getText());
        ModalController.closeStage(liikeTiedot);
    }

    /**
     * Käsitellään ikkunasta poistuminen
     */
    @FXML private void handleCancel() {
        ModalController.closeStage(liikeTiedot);
        liikeKohdalla = null;
    }

    /**
     * Mitä dialogista palautetaan 
     */
    @Override
    public Liike getResult() {
        return liikeKohdalla;

    }

    /**
     * Mitä näytetään oletuksena
     */
    @Override
    public void setDefault(Liike oletus) {
        liikeKohdalla = oletus;
        naytaLiike(liikeKohdalla);
        

    }

    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        liikeTiedot.requestFocus();
    }
    
    /**
     * Näytetään liikkeen tiedot TextField komponenttiin
     * @param liike näytettävä liike
     */
    public void naytaLiike(Liike liike) {
        if (liike == null) return;
        liikeTiedot.setText(liike.getLiikeTeksti());
    }


    /**
     * Luodaan liikkeen tietojen kysymysdialogi ja palautetaan siihen kirjoitettu tieto tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @param title ikkunan otsikko
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static Liike kysyLiikeTiedot(Stage modalityStage, Liike oletus, String title) {
        return ModalController.showModal(
                LiikeController.class.getResource("uusiLiike.fxml"),
                title,
                modalityStage, oletus);
    }
}


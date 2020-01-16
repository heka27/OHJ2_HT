package fxTreeniseuranta;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class PainoseurantaController implements ModalControllerInterface<String>{

    @FXML
    private Button buttonOK;

    @FXML
    void handleButtonOK() {
        ModalController.closeStage(buttonOK);
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }

}
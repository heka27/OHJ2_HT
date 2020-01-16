package fxTreeniseuranta;

import Treeniseuranta.SailoException;
import Treeniseuranta.Treeniseuranta;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma treeniseuranta-ohjelman käynnistämiseksi.
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class TreeniseurantaMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TreeniseurantaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final TreeniseurantaGUIController treeniseurantaCtrl = (TreeniseurantaGUIController) ldr.getController();
            
            final Scene scene = new Scene(root);
            
            scene.getStylesheets().add(getClass().getResource("treeniseuranta.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Treeniseuranta");
            
            // Platform.setImplicitExit(false); //
            
            primaryStage.setOnCloseRequest((event) -> {
                //Kutsutaan voikoSulkea-metodia
                try {
                    if (!treeniseurantaCtrl.voikoSulkea() ) event.consume();
                } catch (SailoException e) {
                    e.getMessage();
                    e.printStackTrace();
                }
            });
            
            Treeniseuranta treeniseuranta = new Treeniseuranta();
            treeniseurantaCtrl.setTreeniseuranta(treeniseuranta);
            
            primaryStage.show();
            
            if (!treeniseurantaCtrl.avaa()) javafx.application.Platform.exit();
            
            ModalController.showModal(TreeniseurantaGUIController.class.getResource("tietoja.fxml"), "Tietoja", null, "");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Käynnistetään käyttöliittymä
     * @param args komentorivin parametrit
     */
    public static void main(String[] args) {
        launch(args);
    }
}
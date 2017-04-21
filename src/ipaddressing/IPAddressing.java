/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddressing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author myotherself
 */
public class IPAddressing extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainFXML.fxml"));
        
        Scene scene = new Scene(root);
        try {
//            stage.getIcons().add(
//                new Image(IPAddressing.class.getResourceAsStream( "/logo.png" )));
            stage.getIcons().add(new Image("logo.png"));
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/logo.png")));
        } catch (Exception e) {
        }
        stage.setTitle("Rabi's IP Calculator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddressing;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author myotherself
 */
public class mainFXMLController implements Initializable {
    
    @FXML
    private AnchorPane leftPnae;
    @FXML
    private AnchorPane leftRoot;
    @FXML
    private VBox leftVBox;
    @FXML
    private TextField ipTF;
    @FXML
    private TextField cidrTF;
    @FXML
    private TextField routersTF;
    @FXML
    private TextField conBtnRoutersTF;
    @FXML
    private TextField maxDevicesTF;
    @FXML
    private AnchorPane rightPane;
    
    //javafx variables without SB
    private Button partTwoBtn = new Button();
    private Label enterRoutersNumber;
    //private TextField[][] routersConTFs = new TextField[510][3];
    private TextField[] noOfLansTFs;
    
    // logic's variables
    private double height;
    private double tfH;
    private double btnH;
    
    
    private String ip;
    private int subnetLen;
    private int noOfRouters;
    private int conBtnRouters;
    //private int[][] routersGraph = new int[500][500]; // 0-> new index value, then other routers numbers those have connection with this router 
    private int[][] lansGraph;// 0-> how many lan in this router, 1,2,...,n -> number of devices in each lan
    private int maxDevice;
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noOfLansTFs = new TextField[510];
        lansGraph = new int[500][500];
        height = 1000;
        btnH = 70;
        tfH = 45;
        adjustHeight(height);
        partTwoBtn = new Button("new Button");
//        partTwoBtn.setOnAction((event) -> {
//            // Button was clicked, do something...
//            System.out.println("huhuhihihh");
//        });
        partTwoBtn.setOnAction(this::partTwoBtnAction);
//        partTwoBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                leftVBox.getChildren().add(new Label("hellow"));
//                System.out.println("klsdfjklsdjflsjfj");
//            }
//        });
    }    

    @FXML
    private void partOneBtnAction(ActionEvent event) {
        ip = ipTF.getText();
        subnetLen = Integer.parseInt(cidrTF.getText());
        noOfRouters = Integer.parseInt(routersTF.getText());
//        for(int i = 1; i <= noOfRouters; i++){
//            routersGraph[i][0] = 1;
//        }
        conBtnRouters = Integer.parseInt(conBtnRoutersTF.getText());
        maxDevice = Integer.parseInt(maxDevicesTF.getText());
        addPartTwoElements();
    }
    
    private void addPartTwoElements(){
        HBox hb = null;
//        enterRoutersNumber = new Label("Enter Each Routers' Number in The Fields:");
//        enterRoutersNumber.setPrefSize(Double.MAX_VALUE, tfH);
//        partOneVBox.getChildren().add(enterRoutersNumber);
//        for(int i = 1; i <= conBtnRouters; i++){
//            routersConTFs[i][0] = new TextField();
//            routersConTFs[i][1] = new TextField();
//            routersConTFs[i][0].setPrefHeight(Double.MAX_VALUE);
//            routersConTFs[i][1].setPrefHeight(Double.MAX_VALUE);
//            Label lbl = new Label("  has connection with  ");
//            lbl.setPrefHeight(Double.MAX_VALUE);
//            hb = new HBox(routersConTFs[i][0],lbl , routersConTFs[i][1]);
//            hb.setAlignment(Pos.CENTER);
//            hb.setPrefSize(Double.MAX_VALUE, tfH);
//            partOneVBox.getChildren().add(hb);
//        }
        
        Label lanNo = new Label("Number Of LAN(s) In Each Router:");
        lanNo.setPrefSize(Double.MAX_VALUE, tfH);
        leftVBox.getChildren().add(lanNo);
        for(int i = 1; i <= noOfRouters; i++){
            noOfLansTFs[i] = new TextField("3");
            noOfLansTFs[i].setPrefHeight(tfH);
            //noOfLansTFs[i].setPrefWidth(Double.MAX_VALUE);
            Label lbl = new Label("Number Of LAN(s) In Router " + i + ":");
            lbl.setPrefHeight(tfH);
            leftVBox.getChildren().add(lbl);
            leftVBox.getChildren().add(noOfLansTFs[i]);
        }
              

        partTwoBtn.setPrefSize(Double.MAX_VALUE, btnH);
        leftVBox.getChildren().add(partTwoBtn);
        
        height += (tfH*conBtnRouters) + (2*tfH*noOfRouters) + btnH;

        adjustHeight(height);
        leftRoot.getChildren().clear();
        leftRoot.getChildren().add(leftVBox);
    }
    
    private void partTwoBtnAction(ActionEvent ae){
        System.out.println("inside");
        for(int i = 1; i <= noOfRouters; i++){
            lansGraph[i][0] = Integer.parseInt(noOfLansTFs[i].getText());
        }
        addPartThreeElements();
    }
    
    private void addPartThreeElements(){
         System.out.println("working");
         System.out.println(noOfLansTFs[1].getText());
    }
    
    private void adjustHeight(double h){
        //System.out.println("H;" + h);
        leftRoot.setMinHeight(h);
        leftVBox.setMinHeight(h);
    }
}

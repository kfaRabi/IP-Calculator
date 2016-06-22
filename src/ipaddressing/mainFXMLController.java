/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddressing;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane rightPane;
    
    //javafx variables without SB
    private Button partTwoBtn;
    private Button partThreeBtn;
    private Label enterRoutersNumber;
    //private TextField[][] routersConTFs = new TextField[510][3];
    private TextField[] noOfLansTFs;
    private TextField[][] noOfDevicesTFs;
    
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
    private ArrayList<LAN> col;
    private int hostPortionLen;
    private String errorMsg;
    @FXML
    private Label errorMsgLbl;
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col = new ArrayList<LAN>();
        noOfLansTFs = new TextField[510];
        noOfDevicesTFs = new TextField[510][100];
        lansGraph = new int[100][5];
        height = 1000;
        btnH = 70;
        tfH = 45;
        adjustHeight(height);
        partTwoBtn = new Button("Next");
        partTwoBtn.setOnAction(this::partTwoBtnAction);
        partThreeBtn = new Button("Submit");
        partThreeBtn.setOnAction(this::partThreeBtnAction);

    }    

    @FXML
    private void partOneBtnAction(ActionEvent event) {
        ip = ipTF.getText();
        subnetLen = Integer.parseInt(cidrTF.getText());
        noOfRouters = Integer.parseInt(routersTF.getText());
        conBtnRouters = Integer.parseInt(conBtnRoutersTF.getText());
        if(!validIP()){
            errorMsgLbl.setText(errorMsg);
        }
        else errorMsgLbl.setText("OK!");
        addPartTwoElements();
    }
    
    private void addPartTwoElements(){
        Label lanNo = new Label("Number Of LAN(s) In Each Router:");
        lanNo.setPrefSize(Double.MAX_VALUE, tfH);
        leftVBox.getChildren().add(lanNo);
        for(int i = 1; i <= noOfRouters; i++){
            noOfLansTFs[i] = new TextField("2");
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
         //System.out.println("working");
         //System.out.println(noOfLansTFs[1].getText());
         Label info = new Label("Information About The Number Of Devices");
         info.setPrefHeight(tfH);
         leftVBox.getChildren().add(info);
         for(int i = 1; i <= noOfRouters; i++){
             Label lbl = new Label("Router-" + i +"'s Information:");
             lbl.setPrefHeight(tfH);
             leftVBox.getChildren().add(lbl);
             for(int j = 1; j <= lansGraph[i][0]; j++){
                 Label lbl2 = new Label("Number Of Devices In LAN " + j + " of Router-" + i + ":");
                 lbl2.setPrefHeight(tfH);
                 noOfDevicesTFs[i][j] = new TextField("1");
                 noOfDevicesTFs[i][j].setPrefHeight(tfH);
                 leftVBox.getChildren().add(lbl2);
                 leftVBox.getChildren().add(noOfDevicesTFs[i][j]);
             }
         }
         partThreeBtn.setPrefSize(Double.MAX_VALUE,btnH);
         leftVBox.getChildren().add(partThreeBtn);
    }
    private void partThreeBtnAction(ActionEvent ae){
        for(int i = 1; i <= noOfRouters; i++){
            for(int j = 1; j <= lansGraph[i][0]; j++){
                int n = Integer.parseInt(noOfDevicesTFs[i][j].getText());
                LAN lan = new LAN(i, j, n);
                col.add(lan);
            }
        }
        //Collections.sort(col,(la , lb)-> lb.getNoOfDevices() - la.getNoOfDevices());
        Collections.sort(col,(la , lb)-> {
            int difD = lb.getNoOfDevices() - la.getNoOfDevices();
            int difR = la.getRoutersNo() - lb.getRoutersNo();
            if(difD != 0)
                return difD;
            else if(difR != 0)
                return 1;
            else return la.getLANNo()- lb.getLANNo();
        });
        callAllTheFunctions();
    }
    
    private void callAllTheFunctions(){
        if(!validIP()){
            errorMsgLbl.setText(errorMsg);
        }
        else errorMsgLbl.setText("OK!");
    }
    
    private boolean validIP(){
        StringTokenizer str = new StringTokenizer(ip, ".");
        if(ip.length() > 15){
            if(ip.length() > 35){
                errorMsg = "Invalid IP Address - TOO BIG. SHOULD CONTAIN EXACTLY 8 DIGITS ON EACH BLOCK";
                return false;
            }
            for(int i = 0; i < ip.length(); i++){
                int x = ip.charAt(i) - '0';
                if(x>1){
                    errorMsg = "Invalid IP Address - ALl IP DIGITS SHOULD BE ONLY BINRAY OR ONLY DECIAML";
                    return false;
                }
            }
        }
        else{
            if(ip.length() < 7){
                errorMsg = "Invalid IP Address - TOO SHORT. SHOULD CONTAIN ATLEAST ONE DIGIT ON EACH BLOCK";
                return false;
            }
            while(str.hasMoreTokens()){
                String tkn = str.nextToken().toString();
                if(tkn.charAt(0) == '0' && (tkn.length() > 1)){
                    errorMsg = "Invalid IP Address - LEADING ZERO(S). ALl IP DIGITS SHOULD BE ONLY BINRAY OR ONLY DECIAML";
                    return false;
                }
            }
            
        }
        str = new StringTokenizer(ip, ".");
        if(str.countTokens() != 4){
            //errorMsg = (" "+str.countTokens()+" : ");
            errorMsg = "Invalid IP Address - TOO MANY/LESS BLOCKS. SHOULD BE 4";
            return false;
        }
        return true;
    } 
    
    private void adjustHeight(double h){
        leftRoot.setMinHeight(h);
        leftVBox.setMinHeight(h);
    }
}

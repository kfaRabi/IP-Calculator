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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    @FXML
    private Label errorMsgLbl;
    @FXML
    private AnchorPane rightRoot;
    @FXML
    private VBox rightVBox;
    
    //javafx variables without SB
    private Button partTwoBtn;
    private Button partThreeBtn;
    private Label enterRoutersNumber;
    private VBox tmpVBox;
    //private TextField[][] routersConTFs = new TextField[510][3];
    private TextField[] noOfLansTFs;
    private TextField[][] noOfDevicesTFs;
    private ArrayList<Label> outputLabels; 
    
    // logic's variables
    private double height;
    private double tfH;
    private double btnH;
    
    
    private String ip;
    private int subnetLen;
    private int noOfRouters;
    private int conBtnRouters;
    private int parentsLen;
    private int mxReq;
    
    private int[][] lansGraph;// 0-> how many lan in this router, 1,2,...,n -> number of devices in each lan
    private ArrayList<LAN> colLan;
    private ArrayList<NetAdInfo> colNetAd;
    private ArrayList<NetAdInfo> list;
    private int hostPortionLen;
    private String errorMsg;
    private String newIP;
    private int index;

        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colLan = new ArrayList<LAN>();
        colNetAd = new ArrayList<NetAdInfo>();
        list = new ArrayList<NetAdInfo>();
        noOfLansTFs = new TextField[510];
        noOfDevicesTFs = new TextField[510][100];
        outputLabels = new ArrayList<Label>();
        lansGraph = new int[100][5];
        height = 500;
        mxReq = 122;
        btnH = 70;
        tfH = 45;
        adjustHeight(height);
        partTwoBtn = new Button("Next");
        partTwoBtn.setOnAction(this::partTwoBtnAction);
        
        partThreeBtn = new Button("Submit");
        partThreeBtn.setOnAction(this::partThreeBtnAction);
        
        errorMsgLbl.setText("Fill Out All The Fields And Click Next Button.");
        tmpVBox = leftVBox;
        errorMsgLbl.setFont(Font.font("Cambria", 15));
        errorMsgLbl.setTextFill(Color.GREEN);

    }    

    private void callAllTheFunctions(){
        if(!validIP()){
            errorMsgLbl.setText(errorMsg);
        }
        //else errorMsgLbl.setText("OK!");
        if(adjustNetAdd(mxReq)){
            String newIP = toBinaryIP(ip);
            list = new ImportantFunctions().binarySubnet(newIP, parentsLen ,subnetLen);
            if(list.size() > 0)
                colNetAd.addAll(list);
        }
        else if((int)Math.pow(2, hostPortionLen) >= mxReq){
            if(ip.length()<32)
                newIP = toBinaryIP(ip);
            else newIP = ip;
            list = new ImportantFunctions().binarySubnet(newIP, parentsLen ,subnetLen);
            if(list.size() > 0)
                colNetAd.addAll(list);
        }
        else{
            errorMsgLbl.setText("NOT POSSIBLE WITH THIS IP ADDRESS, TOO MANY DEVICES. REDUCE DEVICE NUMBER");
        }
        try{
            if(assignIP()){
                try{
                    displayResult();
                }catch(Exception e){
                    errorMsgLbl.setText("Problem Displaying Results");
                }
            }
            else{
                try{
                    displayError();
                }catch(Exception e){
                    errorMsgLbl.setText("Problem Displaying Results");
                }
            }
        }catch(Exception e){
            errorMsgLbl.setText("Problem Assigning IP");
        }
        try{
            reset();
        }catch(Exception e){
            errorMsgLbl.setText("Problem Resetting The Scene");
        }
        
    }
    
    @FXML
    private void partOneBtnAction(ActionEvent event) {
        errorMsg = "Fill out all the fields properly";
        int flag = 0;
        try {
            ip = ipTF.getText();
            subnetLen = Integer.parseInt(cidrTF.getText());
            noOfRouters = Integer.parseInt(routersTF.getText());
            conBtnRouters = Integer.parseInt(conBtnRoutersTF.getText());
            hostPortionLen = 32 - subnetLen;
            parentsLen = subnetLen;
            flag = 1;
        } catch (Exception e) {
            errorMsgLbl.setText("All Fields Should Contain Valid Values");
        }
        if(validIP() && flag == 1){
            addPartTwoElements();
            errorMsgLbl.setText("Fill Out All The Fields And Click NEXT Button.");
            errorMsgLbl.setTextFill(Color.GREEN);
        }
        else{
            errorMsgLbl.setText(errorMsg);
        }
    }
    
    private void addPartTwoElements(){
        try {
            Label lanNo = new Label("Number Of LAN(s) In Each Router:");
            lanNo.setFont(Font.font("Cambria", 15));
            lanNo.setPrefSize(Double.MAX_VALUE, tfH);
            leftVBox.getChildren().add(lanNo);
            for(int i = 1; i <= noOfRouters; i++){
                noOfLansTFs[i] = new TextField("2");
                noOfLansTFs[i].setPrefHeight(tfH);
                Label lbl = new Label("Number Of LAN(s) In Router " + i + ":");
                lbl.setPrefHeight(tfH);
                leftVBox.getChildren().add(lbl);
                leftVBox.getChildren().add(noOfLansTFs[i]);
            }


            partTwoBtn.setPrefSize(Double.MAX_VALUE, btnH);
            leftVBox.getChildren().add(partTwoBtn);

            height += (2*tfH*noOfRouters) + btnH;

            adjustHeight(height);
            leftRoot.getChildren().clear();
            leftRoot.getChildren().add(leftVBox);
        } catch (Exception e) {
            errorMsgLbl.setText("Unkown Problem On Adding New Elements");
        }
    }
    
    private void partTwoBtnAction(ActionEvent ae){
        int flag = 1;
        try {
            for(int i = 1; i <= noOfRouters; i++){
                if(noOfLansTFs[i].getText().length() == 0){
                    flag = 0;
                    errorMsgLbl.setText("All Fields Should Contain Valid Values");
                    break;
                }
                lansGraph[i][0] = Integer.parseInt(noOfLansTFs[i].getText());
            }
            errorMsgLbl.setText("Fill Out All The Fields And Click SUBMIT.");
            errorMsgLbl.setTextFill(Color.GREEN);
        } catch (Exception e) {
            errorMsgLbl.setTextFill(Color.RED);
            errorMsgLbl.setText("All Fields Should Contain Valid Values");
        }
        if(flag == 1)
            addPartThreeElements();
    }
    
    private void addPartThreeElements(){
        errorMsgLbl.setTextFill(Color.GREEN);
        try {
            Label info = new Label("Information About The Number Of Devices");
            info.setFont(Font.font("Cambria", 15));
            info.setPrefHeight(tfH);
            int elementCnt = 0;
            leftVBox.getChildren().add(info);
            for(int i = 1; i <= noOfRouters; i++){
                Label lbl = new Label("Router-" + i +"'s Information:");
                lbl.setFont(Font.font("Cambria", 14));
                lbl.setPrefHeight(tfH);
                leftVBox.getChildren().add(lbl);
                for(int j = 1; j <= lansGraph[i][0]; j++){
                    Label lbl2 = new Label("Number Of Devices In Router-" + i + "'s LAN-" + j + ":");
                    lbl2.setPrefHeight(tfH);
                    noOfDevicesTFs[i][j] = new TextField("1");
                    noOfDevicesTFs[i][j].setPrefHeight(tfH);
                    leftVBox.getChildren().add(lbl2);
                    leftVBox.getChildren().add(noOfDevicesTFs[i][j]);
                    elementCnt++;
                }
            }
            height += (elementCnt*3*tfH) + (btnH);
            adjustHeight(height);
            partThreeBtn.setPrefSize(Double.MAX_VALUE,btnH);
            leftVBox.getChildren().add(partThreeBtn);
        } catch (Exception e) {
            errorMsgLbl.setTextFill(Color.RED);
            errorMsgLbl.setText("Unkown Problem On Adding New Elements");
        }
    }
    private void partThreeBtnAction(ActionEvent ae){
        colLan.clear();
        colNetAd.clear();
        int flag = 1;
        try {
            for(int i = 1; i <= noOfRouters; i++){
                for(int j = 1; j <= lansGraph[i][0]; j++){
                    if(noOfDevicesTFs[i][j].getText().length() == 0){
                        flag = 0;
                        break;
                    }
                    int n = Integer.parseInt(noOfDevicesTFs[i][j].getText());
                    LAN lan = new LAN(i, j, n+2);
                    colLan.add(lan);
                }
            }
            if(flag == 1){
                for(int i = 1; i <= conBtnRouters; i++){
                    LAN lan = new LAN(Integer.MAX_VALUE, Integer.MAX_VALUE, 4);
                    colLan.add(lan);
                }
                Collections.sort(colLan,(la , lb)-> {
                    int difD = lb.getNoOfDevices() - la.getNoOfDevices();
                    int difR = la.getRoutersNo() - lb.getRoutersNo();
                    if(difD != 0)
                        return difD;
                    else if(difR != 0)
                        return 1;
                    else return la.getLANNo()- lb.getLANNo();
                });
                mxReq = colLan.get(0).getNoOfDevices();
                callAllTheFunctions();
            }
            else{
                errorMsgLbl.setTextFill(Color.RED);
                errorMsgLbl.setText("All Fields Should Contain Valid Values");
            }
            
        } catch (Exception e) {
            errorMsgLbl.setTextFill(Color.RED);
            errorMsgLbl.setText("All Fields Should Contain Valid Values");
        }
    }
    
    private boolean validIP(){
        errorMsgLbl.setTextFill(Color.RED);
        StringTokenizer str = new StringTokenizer(ip, ".");
        if(ip.length() > 15){
              errorMsg = "Invalid IP Address - TOO BIG. SHOULD CONTAIN AT MOST 3 DIGITS ON EACH BLOCK";
              return false;

        }
        else{
            if(ip.length() < 7){
                errorMsg = "Invalid IP Address - TOO SHORT. SHOULD CONTAIN ATLEAST ONE DIGIT ON EACH BLOCK";
                return false;
            }
            while(str.hasMoreTokens()){
                String tkn = str.nextToken().toString();
                if(tkn.charAt(0) == '0' && (tkn.length() > 1)){
                    errorMsg = "Invalid IP Address - LEADING ZERO(S). ALl IP DIGITS SHOULD BE ONLY DECIAML NUMBER";
                    return false;
                }
                if(tkn.length() > 3){
                    errorMsg = "Invalid IP Address - TOO MANY DIGITS IN ONE BLOCK. SHOULD NOT BE MORE THAN 3 DIGITS";
                    return false;
                }
                if(Integer.parseInt(tkn.toString()) > 255){
                    errorMsg = "Invalid IP Address - NO BLOCK SHOULD BE MORE THAN 255";
                    return false;
                }
            }
            
        }
        str = new StringTokenizer(ip, ".");
        if(str.countTokens() != 4){
            errorMsg = "Invalid IP Address - TOO MANY/LESS BLOCKS. SHOULD BE 4";
            return false;
        }
        return true;
    } 
    
    private boolean adjustNetAdd(int required){
        int tmp = hostPortionLen, cnt = 0;
        while(((int)(Math.pow(2.0, (double)tmp))) >= required){
            tmp--;
            cnt++;
        }
        if((tmp + 1 != hostPortionLen) && (((int)(Math.pow(2.0, (double)tmp+1))) >= required)){
            subnetLen += (cnt-1);
            return true;
        }
        return false;
    }
    
    private String toBinaryIP(String inp){
        String full = ""; 
        String s;
        StringTokenizer str = new StringTokenizer(inp, ".");
        while(str.hasMoreTokens()){
            s = str.nextToken().toString();
            full += dToB(s);
        }
        return full;
    }
    
    private void adjustHeight(double h){
        leftRoot.setMinHeight(h);
        leftVBox.setMinHeight(h);
    }
    
    private String dToB(String s){
        int[] val = {128, 64, 32, 16, 8, 4, 2, 1};
        char[] arr = new char[8];
        int block = Integer.parseInt(s), sum = 0;
        for(int i = 0; i < 8; i++){
            if((sum + val[i]) <= block){
                sum += val[i];
                arr[i] =  '1';
            }
            else arr[i] = '0';
        }
        return String.valueOf(arr);
    }
    
    private boolean assignIP(){
        int szn = colNetAd.size();
        int szl = colLan.size(), i = 0;
        while(i < szl){
            int flag = 0;
            Collections.sort(colNetAd, (a, b)-> a.getNoOfDevices() - b.getNoOfDevices());
            for(int j = 0; j < szn; j++){
                if(colLan.get(i).getNoOfDevices() > colNetAd.get(j).getNoOfDevices()){
                    continue;
                }
                else{
                    flag = 1;
                    hostPortionLen = 32 - colNetAd.get(j).getCIDR();
                    parentsLen = colNetAd.get(j).getCIDR();
                    subnetLen = parentsLen;
                    if(adjustNetAdd(colLan.get(i).getNoOfDevices()) == true){
                        list.clear();
                        list = new ImportantFunctions().binarySubnet(colNetAd.get(j).getNetAd(), parentsLen ,subnetLen);
                        colNetAd.remove(j);
                        if(list.size() > 0)
                            colNetAd.addAll(list);
                        szn = colNetAd.size();
                        i--;
                        break;
                    }
                    else{
                        colLan.get(i).setNetInfo(colNetAd.get(j));
                        colLan.get(i).getNetInfo().trigger();
                        colNetAd.remove(j);
                        szn = colNetAd.size();
                        break;
                    }
                }
            }
            if(flag == 0){
                index = i;
                return false;
            }
            i++;
        }
        return true;
    }
    
    private void displayResult(){
        Label lbl = new Label("RESULT");
        lbl.setAlignment(Pos.CENTER);
        lbl.setPrefHeight(tfH*2);
        lbl.setFont(Font.font("Cambria", 32));
        rightVBox.getChildren().clear();
        rightVBox.getChildren().add(lbl);
        Collections.sort(colLan,(la , lb)-> {
            int difR = la.getRoutersNo() - lb.getRoutersNo();
            int difL = la.getLANNo() - lb.getLANNo();
            if(difR != 0)
                return difR;
            else return difL;
        });
        int lth = 20;
        for(LAN l : colLan){
            Label x,y;
            if(l.getRoutersNo() != Integer.MAX_VALUE){
                Label a = new Label("Router No: "+l.getRoutersNo());
                a.setPrefHeight(tfH);
                Label b = new Label("LAN No: "+l.getLANNo());
                b.setPrefHeight(lth);
                x = new Label("Number Of IP Required : "+(l.getNoOfDevices()-2) + " + 2");
                x.setPrefHeight(lth);
                y = new Label("Number Of IP Available : "+l.getNetInfo().getNoOfDevices());
                y.setPrefHeight(lth);
                rightVBox.getChildren().addAll(a, b, x, y);
            }
            else{
                Label a = new Label("Connection Between Two Routers : ");
                a.setPrefHeight(lth);
                rightVBox.getChildren().add(a);
            }
            Label a = new Label("Network Add.: "+l.getNetInfo().getNetAd()+"/"+l.getNetInfo().getCIDR());
            a.setPrefHeight(lth);    
            Label b = new Label("Subnet Mask: "+l.getNetInfo().getSubnet()+"/"+l.getNetInfo().getCIDR());
            b.setPrefHeight(lth);    
            Label c = new Label("Brodcast Add.: "+l.getNetInfo().getBrodcast()+"/"+l.getNetInfo().getCIDR());
            c.setPrefHeight(lth);
            Label d = new Label("Host Range: ");
            d.setPrefHeight(lth);
            Label e = new Label(l.getNetInfo().getFirstHost()+" - "+l.getNetInfo().getLastHost());
            e.setPrefHeight(lth);
            Label f = new Label("---------------------------------------------");
            f.setPrefHeight(tfH);
            rightVBox.getChildren().addAll(a, b, c, d, e, f);
        
        }
        rightVBox.setPrefHeight((tfH*6.0*(double)colLan.size()));
        rightRoot.setPrefHeight((tfH*6.0*(double)colLan.size()));
        displayExtraIPs();
    }
    
    private void displayExtraIPs(){
        Label lbl = new Label("Unused IPs' Information");
        lbl.setAlignment(Pos.CENTER);
        lbl.setPrefHeight(tfH*2);
        lbl.setFont(Font.font("Cambria", 25));
        rightVBox.getChildren().add(lbl);

        int lth = 20;
        for(NetAdInfo l : colNetAd){
            l.trigger();
            Label x = new Label("Number Of Devices Can Be Sarved : "+(l.getNoOfDevices()-2));
            x.setPrefHeight(lth);
            Label a = new Label("Network Add.: "+l.getNetAd()+"/"+l.getCIDR());
            a.setPrefHeight(lth);    
            Label b = new Label("Subnet Mask: "+l.getSubnet()+"/"+l.getCIDR());
            b.setPrefHeight(lth);    
            Label c = new Label("Brodcast Add.: "+l.getBrodcast()+"/"+l.getCIDR());
            c.setPrefHeight(lth);
            Label d = new Label("Host Range: ");
            d.setPrefHeight(lth);
            Label e = new Label(l.getFirstHost()+" - "+l.getLastHost());
            e.setPrefHeight(lth);
            Label f = new Label("---------------------------------------------");
            f.setPrefHeight(tfH);
            rightVBox.getChildren().addAll(x, a, b, c, d, e, f);
        
        }
        rightVBox.setPrefHeight((rightVBox.getPrefHeight()+tfH*4.0*(double)colNetAd.size()));
        rightRoot.setPrefHeight((rightRoot.getPrefHeight()+tfH*4.0*(double)colNetAd.size()));
        rightRoot.getChildren().clear();
        rightRoot.getChildren().add(rightVBox);
    }
    
    private void reset(){
        errorMsgLbl.setTextFill(Color.GREEN);
        errorMsgLbl.setText("Form Resseted For A New Instance Of Calculation");
        colLan.clear();
        colNetAd.clear();
        list.clear();
        int sz = leftVBox.getChildren().size();
        while(leftVBox.getChildren().size() > 9){
            leftVBox.getChildren().remove(9);
        }
        adjustHeight(leftVBox.getPrefHeight());
        leftRoot.getChildren().clear();
        leftRoot.getChildren().add(leftVBox);
    }
    private void displayError(){
        rightVBox.getChildren().clear();
        LAN l = colLan.get(index);
        Label lbl = new Label("RESULT:"), lbl2;
        lbl.setAlignment(Pos.CENTER);
        lbl.setPrefHeight(tfH*2);
        lbl.setFont(Font.font("Cambria", 35));
        if(l.getRoutersNo() == Integer.MAX_VALUE)
            lbl2 = new Label("SORRY, WE COULD NOT ASSIGN HOSTS TO THE ROUTER'S INTERCONNETION, AS IT REQUIRES "+(l.getNoOfDevices()-2)+" + 2 IP ADDRESSES, WHICH IS NOT POSSIBLE WITH YOUR CURRENT NETWORK ADDRESS AND THE SUBNET. PLEASE CHANGE YOUR NETWORK ADDRESS OR REDUCE THE NUMBER OF DEVICES REQUIRED AND TRY AGAIAN.");
        else
            lbl2 = new Label("SORRY, WE COULD NOT ASSIGN HOSTS TO THE ROUTER-"+l.getRoutersNo()+"'S LAN-"+l.getLANNo()+" AS IT REQUIRES "+(l.getNoOfDevices()-2)+" + 2 IP ADDRESSES, WHICH IS NOT POSSIBLE WITH YOUR CURRENT NETWORK ADDRESS AND THE SUBNET. PLEASE CHANGE YOUR NETWORK OR REDUCE THE NUMBER OF DEVICES REQUIRED AND TRY AGAIAN.");
        lbl2.setAlignment(Pos.CENTER);
        lbl2.setPrefSize(Double.MAX_VALUE,tfH*8);
        lbl2.setFont(Font.font("Cambria", 23));
        lbl2.setTextFill(Color.RED);
        lbl2.setWrapText(true);
        rightVBox.getChildren().addAll(lbl,lbl2);
        rightRoot.getChildren().clear();
        rightRoot.getChildren().add(rightVBox);
    }
    
}

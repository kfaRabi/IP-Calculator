/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddressing;

/**
 *
 * @author myotherself
 */
public class LAN{
    private int routersNo;
    private int LANNo;
//    private int CIDR;
    private int noOfDevices;
//    private String netAd;
//    private String firstHost;
//    private String lastHost;
//    private String brodAd;
//    private String subnet;
    
    private NetAdInfo netInfo;
    
    public LAN(){
        
    }

    public LAN(int routersNo, int LANNo, int noOfDevices) {
        this.routersNo = routersNo;
        this.LANNo = LANNo;
        this.noOfDevices = noOfDevices;
        netInfo = null;
    }

    public LAN(int routersNo, int LANNo, int noOfDevices, NetAdInfo netInfo) {
        this.routersNo = routersNo;
        this.LANNo = LANNo;
        this.noOfDevices = noOfDevices;
        this.netInfo = netInfo;
    }

    public NetAdInfo getNetInfo() {
        return netInfo;
    }

    public void setNetInfo(NetAdInfo netInfo) {
        this.netInfo = netInfo;
    }

    public int getRoutersNo() {
        return routersNo;
    }

    public void setRoutersNo(int routersNo) {
        this.routersNo = routersNo;
    }

    public int getLANNo() {
        return LANNo;
    }

    public void setLANNo(int LANNo) {
        this.LANNo = LANNo;
    }


    public int getNoOfDevices() {
        return noOfDevices;
    }

    public void setNoOfDevices(int noOfDevices) {
        this.noOfDevices = noOfDevices;
    }
    
}

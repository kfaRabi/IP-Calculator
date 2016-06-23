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
public class NetAdInfo {
    private int CIDR;//
    private String netAd;//
    private String brodcast;
    private int noOfDevices;//
    private String firstHost;
    private String lastHost;
    private String subnet;

    public NetAdInfo() {
    }
    
    public NetAdInfo(int CIDR, String netAd, int noOfDevices) {
        this.CIDR = CIDR;
        this.netAd = netAd;
        this.noOfDevices = noOfDevices;
    }

    public int getCIDR() {
        return CIDR;
    }

    public void setCIDR(int CIDR) {
        this.CIDR = CIDR;
    }

    public String getNetAd() {
        return netAd;
    }

    public void setNetAd(String netAd) {
        this.netAd = netAd;
    }

    public String getBrodcast() {
        return brodcast;
    }

    public void setBrodcast(String brodcast) {
        this.brodcast = brodcast;
    }

    public int getNoOfDevices() {
        return noOfDevices;
    }

    public void setNoOfDevices(int noOfDevices) {
        this.noOfDevices = noOfDevices;
    }

    public String getFirstHost() {
        return firstHost;
    }

    public void setFirstHost(String firstHost) {
        this.firstHost = firstHost;
    }

    public String getLastHost() {
        return lastHost;
    }

    public void setLastHost(String lastHost) {
        this.lastHost = lastHost;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }
    
    
    
}

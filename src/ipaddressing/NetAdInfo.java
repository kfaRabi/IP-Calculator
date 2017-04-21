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
    private int CIDR;
    private String netAd;
    private String brodcast;
    private int noOfDevices;
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

    public void trigger(){
        calc();
        toDec();
    }
    
    private void calc(){
        brodcast = netAd;
        char[] arr = brodcast.toCharArray();
        for(int i = CIDR; i < 32; i++){
             arr[i] = '1';  
        }
        brodcast = String.valueOf(arr);
        
        subnet = netAd;
        char[] arr0 = subnet.toCharArray();
        for(int i = 0; i < CIDR; i++){
             arr0[i] = '1';  
        }
        for(int i = CIDR; i < 32; i++){
             arr0[i] = '0';  
        }
        subnet = String.valueOf(arr0);
        
        char[] arr1 = netAd.toCharArray();
        arr1[31] = '1';
        firstHost = String.valueOf(arr1);
        
        char[] arr2 = brodcast.toCharArray();
        arr2[31] = '0';
        lastHost = String.valueOf(arr2);
        
    }
    
    private void toDec(){
        int s = 0,e = 8;
        String ss = "";
        for(int i=0; i < 4; i++){
            ss += Integer.toString(Integer.parseInt(netAd.substring(s, e), 2));
            if(i != 3) ss += '.';
            s = e;
            e += 8;
        }
        netAd = ss;
        
        s = 0;e = 8;
        ss = "";
        for(int i=0; i < 4; i++){
            ss += Integer.toString(Integer.parseInt(subnet.substring(s, e), 2));
            if(i != 3) ss += '.';
            s = e;
            e += 8;
        }
        subnet = ss;
        
        s = 0;e = 8;
        ss = "";
        for(int i=0; i < 4; i++){
            ss += Integer.toString(Integer.parseInt(brodcast.substring(s, e), 2));
            if(i != 3) ss += '.';
            s = e;
            e += 8;
        }
        brodcast = ss;
        
        s = 0;e = 8;
        ss = "";
        for(int i=0; i < 4; i++){
            ss += Integer.toString(Integer.parseInt(firstHost.substring(s, e), 2));
            if(i != 3) ss += '.';
            s = e;
            e += 8;
        }
        firstHost = ss;
        
        s = 0;e = 8;
        ss = "";
        for(int i=0; i < 4; i++){
            ss += Integer.toString(Integer.parseInt(lastHost.substring(s, e), 2));
            if(i != 3) ss += '.';
            s = e;
            e += 8;
        }
        lastHost = ss;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddressing;

import java.util.ArrayList;

/**
 *
 * @author myotherself
 */
public class ImportantFunctions {
    
    private ArrayList<String> list;
    private ArrayList<NetAdInfo> retList;
    private int[] val;
    
    public ImportantFunctions() {
        list = new ArrayList<>();
        retList = new ArrayList<>();
    }
    public ArrayList<NetAdInfo> binarySubnet(String netAd, int li, int ri){
        if(ri >= li)
            populateVal(ri - li);
        else return retList;
        String firstAdd, brodcastAdd,po,pt,pth,ptmp;
        
        char[] arr = netAd.toCharArray();
        for(int i = ri; i < netAd.length(); i++){
                arr[i] = '0';
        }
        firstAdd = String.valueOf(arr);

        
        if(li == ri){
            NetAdInfo obj = new NetAdInfo(ri, firstAdd, (int)Math.pow(2, 32 -ri));
            retList.add(obj);
            return retList;
        }
        
        po = firstAdd.substring(0, li);
        pt = firstAdd.substring(li, ri);
        pth = firstAdd.substring(ri, 32);
        
        for(int i = 0; i < (int)Math.pow(2, ri - li); i++){
            list.add(dToB(i, ri - li));
        }
        
        for (String l : list) {
            String s = po + l + pth;
            NetAdInfo obj = new NetAdInfo(ri, s, (int)Math.pow(2, 32 -ri));
            retList.add(obj);
        }
        return  retList;
    }
    
    private int getCnt(int x){
        int cnt = 0;
        while(Math.pow(2, cnt) <= x){
            cnt ++;
        }
        cnt--;
        return cnt;
    }
    
    private String dToB(int block, int x){
        char[] arr = new char[x];
        int sum = 0;
        for(int i = 0; i < x; i++){
            if((sum + val[i]) <= block){
                sum += val[i];
                arr[i] =  '1';
            }
            else arr[i] = '0';
        }
        return String.valueOf(arr);
    }
    
    private void populateVal(int x){
        val = new int[x];
        int ind = x-1;
        for(int i = 0; i < x; i++){
            val[i] = (int) Math.pow(2, ind--);
        }
    }
}
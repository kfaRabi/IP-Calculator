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
        populateVal(ri - li);
        System.out.println("netLen: "+ netAd.length());
        String firstAdd, brodcastAdd,po,pt,pth,ptmp;
        int cnt, devTmp = (int) Math.pow(2, 32 - ri), newNetworks = (int) Math.pow(2, li - ri);
        // devTmp = devices
        
        char[] arr = netAd.toCharArray();
        
        //cnt = getCnt(ri);
        
        for(int i = ri; i < netAd.length(); i++){
            //if(arr[i] != '.')
                arr[i] = '0';
        }
        firstAdd = String.valueOf(arr);
        System.out.println("1st address: "+firstAdd);
        arr = netAd.toCharArray();
        for(int i = ri; i < netAd.length(); i++){
            //if(arr[i] != '.')
                arr[i] = '1';
        }
        brodcastAdd = String.valueOf(arr);
        System.out.println("last address: "+brodcastAdd);
//for(int i = 0; i < ri - li; i++){
        //cnt = getCnt(li);
        po = firstAdd.substring(0, li);
        cnt = getCnt(ri);
        pt = firstAdd.substring(li, ri);

//        int cnt3 = cnt2;
//        for(int j = ri; j < 32; j++){
//            if(brodcastAdd.charAt(j) == '.') {
//                cnt3++;
//            }
//        }
        pth = firstAdd.substring(ri, 32);
        for(int i = 0; i < (int)Math.pow(2, ri - li); i++){
            list.add(dToB(i, ri - li));
            //System.out.println("bin: "+list.get(i));
        }
        for (String l : list) {
            String s = po + l + pth;
            //System.out.println("pt : "+l);
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
        //System.out.println("block: "+block);
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
            //System.out.println("ind: "+i+" val: "+val[i]);
        }
    }
}
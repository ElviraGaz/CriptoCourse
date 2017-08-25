package com.example.elvira.criptocourse.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bibi1 on 25.08.2017.
 */

public class IconsProvider {
    public static Map<String, String> icon = new HashMap<>();

    static String baseurl = "https://www.cryptocompare.com";
    public static void add(){
        icon.put("BTC",baseurl+"/media/19633/btc.png");
        icon.put("ETH", baseurl+"/media/20646/eth.png");
        icon.put("BCH",baseurl+"/media/1383919/bch.jpg");
        icon.put("XRP",baseurl+"/media/19972/ripple.png");
        icon.put("LTC",baseurl+"/media/19782/litecoin-logo.png");
        icon.put("XEM",baseurl+"/media/20490/xem.png");
        icon.put("MIOTA",baseurl+"/media/1383540/iota.jpg");
        icon.put("DASH",baseurl+"/media/20626/dash.png");
        icon.put("NEO",baseurl+"/media/1383858/neo.jpg");
        icon.put("XMR",baseurl+"/media/19969/xmr.png");

    }
}

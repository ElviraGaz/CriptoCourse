package com.example.elvira.criptocourse.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by bibi1 on 24.03.2018.
 */

public class CoinInfo implements Serializable{
    @SerializedName("Data")
    public JsonObject data;

    public JsonObject getData(){
        return data;
    }
}

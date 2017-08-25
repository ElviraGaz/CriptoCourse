package com.example.elvira.criptocourse.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bibi1 on 25.08.2017.
 */

public class Bpi {
    Map<String, Float> bpi = new HashMap<>();

    public Map<String, Float> getBpi() {
        return bpi;
    }
}

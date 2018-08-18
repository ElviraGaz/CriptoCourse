package com.example.elvira.criptocourse.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by bibi1 on 24.03.2018.
 */

public class CoinItem implements Serializable {
    @SerializedName("ImageUrl")
    public String imageUrl;

    public String getImageUrl(){
        return imageUrl;
    }
}

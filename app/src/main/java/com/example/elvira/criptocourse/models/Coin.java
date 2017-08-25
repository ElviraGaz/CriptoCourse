package com.example.elvira.criptocourse.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bibi1 on 24.08.2017.
 */

public class Coin implements Serializable {
    @SerializedName("name")
    String name;
    @SerializedName("symbol")
    String symbol;

    @SerializedName("price_usd")
    String priceUsd;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String imageUrl;
    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}

package com.example.elvira.criptocourse.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bibi1 on 25.08.2017.
 */

public class HistoryBpi implements Serializable {
    @SerializedName("bpi")
    JsonObject bpi;

    public JsonObject getBpi() {
        return bpi;
    }

}

package com.example.elvira.criptocourse.cryptocompare.com.api;

import com.example.elvira.criptocourse.models.CoinInfo;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bibi1 on 24.03.2018.
 */

public interface ImageService {
    @GET("coinlist/")
    Call<CoinInfo> getIcons();
}


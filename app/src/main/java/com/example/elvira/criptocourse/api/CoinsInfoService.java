package com.example.elvira.criptocourse.api;

import com.example.elvira.criptocourse.models.Coin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bibi1 on 24.08.2017.
 */

public interface CoinsInfoService {
    @GET("ticker")
    Call<List<Coin>> getCoins(@Query("limit") int limit);
}

package com.example.elvira.criptocourse.coindesk.api;

import com.example.elvira.criptocourse.models.Coin;
import com.example.elvira.criptocourse.models.HistoryBpi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bibi1 on 25.08.2017.
 */

public interface CoinHistoryService {
    @GET("historical/close.json")
    Call<HistoryBpi> getHistory(@Query("start") String start, @Query("end") String end);
}

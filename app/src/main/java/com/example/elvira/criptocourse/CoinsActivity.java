package com.example.elvira.criptocourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.elvira.criptocourse.coinRecyclerView.CoinsAdapter;
import com.example.elvira.criptocourse.coindesk.api.ApiFactory;
import com.example.elvira.criptocourse.coindesk.api.CoinHistoryService;
import com.example.elvira.criptocourse.models.Coin;
import com.example.elvira.criptocourse.coinRecyclerView.*;
import com.example.elvira.criptocourse.models.HistoryBpi;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinsActivity extends AppCompatActivity {
    ArrayList<Coin> response;
    private RecyclerView recyclerView;
    private CoinHistoryService coinHistoryService;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);
        Bundle extra = getIntent().getBundleExtra("extra");
        response = (ArrayList<Coin>) extra.getSerializable("response");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_coins);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Создаём и задаём класс, отвечающий за "декорирование" элементов (добавляем разделители)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        // Создаём и задаём класс, отвечающий за анимации
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        final CoinsAdapter countriesAdapter = new CoinsAdapter(response);
        recyclerView.setAdapter(countriesAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
              //  startActivity(new Intent(CoinsActivity.this, CourseActivity.class));
                Bundle extra = new Bundle();
                extra.putSerializable("response", response.get(position));
                intent = new Intent(CoinsActivity.this, CourseActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
                //coinHistoryService = ApiFactory.getRetrofitInstance().create(CoinHistoryService.class);
              //  getHistory("2017-01-01", "2017-08-01");
            }

            @Override
            public void onLongClick(View view, int position) {
              //  startActivity(new Intent(CoinsActivity.this, CourseActivity.class));
                Bundle extra = new Bundle();
                extra.putSerializable("response", response.get(position));
                Intent intent = new Intent(CoinsActivity.this, CourseActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);

            }
        }));
    }

}

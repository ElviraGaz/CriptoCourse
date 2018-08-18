package com.example.elvira.criptocourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.elvira.criptocourse.coinRecyclerView.ClickListener;
import com.example.elvira.criptocourse.coinRecyclerView.CoinsAdapter;
import com.example.elvira.criptocourse.coinRecyclerView.RecyclerTouchListener;
import com.example.elvira.criptocourse.cryptocompare.com.api.ImageService;
import com.example.elvira.criptocourse.models.Coin;
import com.example.elvira.criptocourse.models.CoinInfo;
import com.example.elvira.criptocourse.models.IconsProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CoinsActivity extends AppCompatActivity {
    ArrayList<Coin> response;
    private RecyclerView recyclerView;
    private Intent intent;
    private ImageService imageService;


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

        imageService = com.example.elvira.criptocourse.cryptocompare.com.api.ApiFactory.getRetrofitInstance().create(ImageService.class);
        getIcons();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Bundle extra = new Bundle();
                extra.putSerializable("response", response.get(position));
                intent = new Intent(CoinsActivity.this, CourseActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Bundle extra = new Bundle();
                extra.putSerializable("response", response.get(position));
                Intent intent = new Intent(CoinsActivity.this, CourseActivity.class);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        }));
    }



    private void getIcons() {
        // Создаем экземпляр запроса со всем необходимыми настройками
        Call<CoinInfo> call = imageService.getIcons();


        // Выполняем запрос асинхронно
        call.enqueue(new Callback<CoinInfo>() {

            // В случае если запрос выполнился успешно, то мы переходим в метод onResponse(...)
            @Override
            public void onResponse(@NonNull Call<CoinInfo> call, @NonNull Response<CoinInfo> response) {
                if (response.isSuccessful()) {
                    // Если в ответ нам пришел код 2xx, то отображаем содержимое запроса
                    fillInfo2(response.body());

                } else {
                    // Если пришел код ошибки, то обрабатываем её
                    Toast.makeText(CoinsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }

            }

            // Если запрос не удалось выполнить, например, на телефоне отсутствует подключение к интернету
            @Override
            public void onFailure(@NonNull Call<CoinInfo> call, @NonNull Throwable t) {
                Toast.makeText(CoinsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void fillInfo2(CoinInfo coinInfo) {
        String symbol, url;
        String baseurl = "https://www.cryptocompare.com";
        for (int i = 0; i < response.size(); i++) {
            symbol = response.get(i).getSymbol();
            if(coinInfo.getData().get(symbol)!=null) {
                url = baseurl.concat(coinInfo.getData().get(symbol).getAsJsonObject().get("ImageUrl").toString()).replaceAll("\"","");
                response.get(i).setImageUrl(url);
            }
        }

        final CoinsAdapter countriesAdapter = new CoinsAdapter(response);
        recyclerView.setAdapter(countriesAdapter);
    }


}

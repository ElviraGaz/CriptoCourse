package com.example.elvira.criptocourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elvira.criptocourse.api.ApiFactory;
import com.example.elvira.criptocourse.api.CoinsInfoService;
import com.example.elvira.criptocourse.models.Coin;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CoinsInfoService coinsInfoService;
    private int limit = 10;
    private ProgressBar progressBar;
    private TextView loadingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingText = (TextView) findViewById(R.id.loading_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        coinsInfoService = ApiFactory.getRetrofitInstance().create(CoinsInfoService.class);
        getCoinsInfo(limit);
    }


    private void getCoinsInfo(int limit) {
        // Создаем экземпляр запроса со всем необходимыми настройками
        Call<List<Coin>> call = coinsInfoService.getCoins(limit);


        // Выполняем запрос асинхронно
        call.enqueue(new Callback<List<Coin>>() {

            // В случае если запрос выполнился успешно, то мы переходим в метод onResponse(...)
            @Override
            public void onResponse(@NonNull Call<List<Coin>> call, @NonNull Response<List<Coin>> response) {
                if (response.isSuccessful()) {
                    // Если в ответ нам пришел код 2xx, то отображаем содержимое запроса
                    fillInfo(response.body());

                } else {
                    // Если пришел код ошибки, то обрабатываем её
                    Toast.makeText(MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }

                // Скрываем progress bar
                progressBar.setVisibility(View.INVISIBLE);
                loadingText.setVisibility(View.INVISIBLE);
            }

            // Если запрос не удалось выполнить, например, на телефоне отсутствует подключение к интернету
            @Override
            public void onFailure(@NonNull Call<List<Coin>> call, @NonNull Throwable t) {
                // Скрываем progress bar
                progressBar.setVisibility(View.INVISIBLE);
                loadingText.setVisibility(View.INVISIBLE);

                Toast.makeText(MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });
    }


    private  void fillInfo(List<Coin> result){
        Bundle extra = new Bundle();
        extra.putSerializable("response", (Serializable) result);
        Intent intent = new Intent(MainActivity.this, CoinsActivity.class);
        intent.putExtra("extra", extra);
        this.finish();
        startActivity(intent);
    }


}

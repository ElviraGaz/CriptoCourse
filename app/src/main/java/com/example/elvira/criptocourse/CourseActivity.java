package com.example.elvira.criptocourse;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elvira.criptocourse.coindesk.api.ApiFactory;
import com.example.elvira.criptocourse.coindesk.api.CoinHistoryService;
import com.example.elvira.criptocourse.models.Coin;
import com.example.elvira.criptocourse.models.HistoryBpi;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Float.parseFloat;

public class CourseActivity extends AppCompatActivity {
    Coin response;
    TextView course;
    TextView coinName;
    LineChart lineChart;
    ArrayList<Entry> entries;
    ImageView backBtn;
    private CoinHistoryService coinHistoryService;
    Map<String, String> myMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Bundle extra = getIntent().getBundleExtra("extra");
        response = (Coin) extra.getSerializable("response");


        course = (TextView) findViewById(R.id.coin_course);
        coinName = (TextView) findViewById(R.id.name);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        coinName.setText(response.getName() + " (" + response.getSymbol() + ")");
        course.setText("1 " + response.getSymbol() + " = " + response.getPriceUsd() + " $");
        lineChart = (LineChart) findViewById(R.id.lineChart);
        coinHistoryService = ApiFactory.getRetrofitInstance().create(CoinHistoryService.class);
        getHistory("2017-08-20", "2017-08-29");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseActivity.this.finish();
            }
        });

    }


    private void getHistory(String start, String end) {
        // Создаем экземпляр запроса со всем необходимыми настройками
        Call<HistoryBpi> call = coinHistoryService.getHistory(start, end);
        // Отображаем progress bar
        //loadingDialog.show();

        // Выполняем запрос асинхронно
        call.enqueue(new Callback<HistoryBpi>() {

            // В случае если запрос выполнился успешно, то мы переходим в метод onResponse(...)
            @Override
            public void onResponse(@NonNull Call<HistoryBpi> call, @NonNull Response<HistoryBpi> response) {
                if (response.isSuccessful()) {
                    // Если в ответ нам пришел код 2xx, то отображаем содержимое запроса
                    fillInfo(response.body());

                } else {
                    // Если пришел код ошибки, то обрабатываем её
                    Toast.makeText(CourseActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }

            }

            // Если запрос не удалось выполнить, например, на телефоне отсутствует подключение к интернету
            @Override
            public void onFailure(@NonNull Call<HistoryBpi> call, @NonNull Throwable t) {

                Toast.makeText(CourseActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });

    }


    private void fillInfo(HistoryBpi historyBpi) {
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Gson gson = new Gson();
        myMap = gson.fromJson(historyBpi.getBpi().toString(), type);
        if(response.getSymbol().equals("BTC")) {
            setupChart();
            addDataToChart();
        }
    }


    private void addDataToChart() {
        lineChart = (LineChart) findViewById(R.id.lineChart);
        entries = new ArrayList<>();
        float i = 1;
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            entries.add(new Entry(i, parseFloat(entry.getValue())));
            i++;
        }
        LineDataSet set = new LineDataSet(entries, "");
        LineData data = new LineData(set);
        lineChart.setData(data);
        lineChart.invalidate();


    }

    private void setupChart() {
        lineChart.setNoDataText("Данный функционал будет позже...");
        lineChart.getDescription().setEnabled(false);
        lineChart.setMaxVisibleValueCount(60);
        lineChart.setDrawGridBackground(true);
        lineChart.setFitsSystemWindows(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawLabels(false);
    }

}

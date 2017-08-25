package com.example.elvira.criptocourse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elvira.criptocourse.coindesk.api.ApiFactory;
import com.example.elvira.criptocourse.coindesk.api.CoinHistoryService;
import com.example.elvira.criptocourse.models.Bpi;
import com.example.elvira.criptocourse.models.Coin;
import com.example.elvira.criptocourse.models.HistoryBpi;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    HistoryBpi history;
    private CoinHistoryService coinHistoryService;
    Map<String, String> myMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Bundle extra = getIntent().getBundleExtra("extra");
        response = (Coin) extra.getSerializable("response");

        Bundle extra2 = getIntent().getBundleExtra("extra2");
        history = (HistoryBpi) extra2.getSerializable("history");

        course = (TextView) findViewById(R.id.coin_course);
        coinName = (TextView) findViewById(R.id.name);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        coinName.setText(response.getName() + " (" + response.getSymbol() + ")");
        course.setText("1 " + response.getSymbol() + " = " + response.getPriceUsd() + " $");
        lineChart = (LineChart) findViewById(R.id.lineChart);
        // addData();
        // initLineChart();

        coinHistoryService = ApiFactory.getRetrofitInstance().create(CoinHistoryService.class);
        getHistory("2017-01-01", "2017-05-01");

        setupChart();
        addDataToChart();
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

                // Скрываем progress bar
                //loadingDialog.dismiss();
            }

            // Если запрос не удалось выполнить, например, на телефоне отсутствует подключение к интернету
            @Override
            public void onFailure(@NonNull Call<HistoryBpi> call, @NonNull Throwable t) {
                // Скрываем progress bar
                //loadingDialog.dismiss();

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
    }

    private void addData() {
        lineChart = (LineChart) findViewById(R.id.lineChart);
        entries = new ArrayList<>();
        float i = 1;
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            boolean add = entries.add(new Entry(i, parseFloat(entry.getValue())));
            i++;
        }
        //  entries.add(new Entry(4f, 0));
        //  entries.add(new Entry(8f, 1));
        //  entries.add(new Entry(6f, 2));
        //  entries.add(new Entry(2f, 3));
        //   entries.add(new Entry(18f, 4));
        //  entries.add(new Entry(9f, 5));
    }

    private void initLineChart() {
        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineData data = new LineData(dataset);
        lineChart.setData(data);
    }


    //--------------------------------------------------
    private void addDataToChart() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 30f));
        entries.add(new Entry(1f, 80f));
        entries.add(new Entry(2f, 60f));
        entries.add(new Entry(3f, 50f));
        // gap of 2f
        entries.add(new Entry(5f, 70f));
        entries.add(new Entry(6f, 60f));


        LineDataSet set = new LineDataSet(entries, "Test bar data set");

        LineData data = new LineData(set);
        // устанавливаем ширину полосок графика
        lineChart.setData(data);
        lineChart.invalidate(); // refresh
    }

    private void setupChart() {


        lineChart.getDescription().setEnabled(false);

        // Если на экране отображается больше 60 значение, то подписи будут скрываться
        lineChart.setMaxVisibleValueCount(60);

        lineChart.setDrawGridBackground(false);

        // Устанавливаем что ось x будет подстраиваться под максимальное значение
        lineChart.setFitsSystemWindows(true);
    }

}

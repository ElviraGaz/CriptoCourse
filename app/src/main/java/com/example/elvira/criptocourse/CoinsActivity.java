package com.example.elvira.criptocourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.elvira.criptocourse.coinRecyclerView.ClickListener;
import com.example.elvira.criptocourse.coinRecyclerView.CoinsAdapter;
import com.example.elvira.criptocourse.coinRecyclerView.RecyclerTouchListener;
import com.example.elvira.criptocourse.models.Coin;
import com.example.elvira.criptocourse.models.IconsProvider;

import java.util.ArrayList;



public class CoinsActivity extends AppCompatActivity {
    ArrayList<Coin> response;
    private RecyclerView recyclerView;
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

        IconsProvider.add();
        for (int i = 0; i < response.size(); i++)
            response.get(i).setImageUrl(IconsProvider.icon.get(response.get(i).getSymbol()));

        final CoinsAdapter countriesAdapter = new CoinsAdapter(response);
        recyclerView.setAdapter(countriesAdapter);


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


}

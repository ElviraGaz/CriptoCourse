package com.example.elvira.criptocourse.coinRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elvira.criptocourse.R;
import com.example.elvira.criptocourse.models.Coin;

import java.util.List;

/**
 * Created by bibi1 on 24.08.2017.
 */

public class CoinsAdapter extends RecyclerView.Adapter<CoinsViewHolder> {
    private List<Coin> coins;

    public CoinsAdapter(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    public CoinsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rowView = layoutInflater.inflate(R.layout.coin_item, parent, false);
        return new CoinsViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(CoinsViewHolder holder, int position) {
        holder.coinName.setText(coins.get(position).getName());
        holder.coinDesc.setText(coins.get(position).getSymbol());
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }
}

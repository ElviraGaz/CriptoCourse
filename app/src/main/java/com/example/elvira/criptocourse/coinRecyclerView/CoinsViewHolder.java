package com.example.elvira.criptocourse.coinRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elvira.criptocourse.R;

/**
 * Created by bibi1 on 24.08.2017.
 */

public class CoinsViewHolder extends RecyclerView.ViewHolder {
    public ImageView coinIcon;
    public TextView coinName;
    public TextView coinDesc;

    public CoinsViewHolder(View itemView) {
        super(itemView);
        coinIcon = (ImageView) itemView.findViewById(R.id.icon_coin);
        coinName = (TextView) itemView.findViewById(R.id.name_coin);
        coinDesc = (TextView) itemView.findViewById(R.id.desc_coin);
    }
}

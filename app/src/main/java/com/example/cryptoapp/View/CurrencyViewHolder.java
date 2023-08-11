package com.example.cryptoapp.View;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptoapp.R;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {

    TextView name,symbol,priceUsd,price;

    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        symbol = itemView.findViewById(R.id.symbol);
        price = itemView.findViewById(R.id.price);

    }
}

package com.example.cryptoapp.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptoapp.Model.CurrencyModel;
import com.example.cryptoapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private Context context;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private ArrayList<CurrencyModel> items;


    public CurrencyAdapter(Context context, ArrayList<CurrencyModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.currency_items,parent,false);
        CurrencyViewHolder viewHolder = new CurrencyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {

        holder.name.setText(items.get(position).getName());
        holder.symbol.setText(items.get(position).getSymbol());
        holder.price.setText(decimalFormat.format(items.get(position).getPrice()).toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filterList(ArrayList<CurrencyModel> filteredList){
        items = filteredList;
        notifyDataSetChanged();

    }
}

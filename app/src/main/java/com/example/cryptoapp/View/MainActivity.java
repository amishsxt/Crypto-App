package com.example.cryptoapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cryptoapp.Repository.DataModel.CurrencyModel;
import com.example.cryptoapp.R;
import com.example.cryptoapp.ViewModel.CurrencyViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView noSearchFound;
    private TextInputLayout lsearch;
    private TextInputEditText search;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<CurrencyModel> currencyAdapterArrayList;
    private CurrencyAdapter currencyAdapter;
    private CurrencyViewModel currencyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //casting views
        lsearch=findViewById(R.id.search_bar_layout);
        search=findViewById(R.id.search_bar_edit_text);
        recyclerView=findViewById(R.id.recyler_view);
        progressBar=findViewById(R.id.progress_bar);
        noSearchFound=findViewById(R.id.error_text);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        //observing data
        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyViewModel.getLiveData().observe(this, new Observer<ArrayList<CurrencyModel>>() {
            @Override
            public void onChanged(ArrayList<CurrencyModel> currencyModels) {
                currencyAdapterArrayList = currencyModels;

                launchAdapter();

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });

        //Search logic
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterCurrencies(editable.toString());
            }
        });

        // To Close the keyboard
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_NEXT){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }

    private void launchAdapter(){
        //setting adapter
        currencyAdapter = new CurrencyAdapter(this,currencyAdapterArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(currencyAdapter);
    }

    private void filterCurrencies(String currency){

        ArrayList<CurrencyModel> filteredList = new ArrayList<>();
        for (CurrencyModel item : currencyAdapterArrayList){
            if(item.getName().toLowerCase().contains(currency.toLowerCase())){
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty() || filteredList.size() < 1){
            recyclerView.setVisibility(View.GONE);
            noSearchFound.setVisibility(View.VISIBLE);
        }
        else{
            currencyAdapter.filterList(filteredList);
            recyclerView.setVisibility(View.VISIBLE);
            noSearchFound.setVisibility(View.GONE);
        }

    }

}
package com.example.cryptoapp.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptoapp.Model.CurrencyModel;
import com.example.cryptoapp.Model.ResponseListener;
import com.example.cryptoapp.Repository.CurrencyRepo;

import java.util.ArrayList;

public class CurrencyViewModel extends AndroidViewModel {

    private CurrencyRepo currencyRepo;
    private ArrayList<CurrencyModel> currencyArrayList;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);
        currencyRepo = new CurrencyRepo(application);
        currencyArrayList = new ArrayList<>();
    }

    public void getCurrentData(ResponseListener<ArrayList<CurrencyModel>> listener){
        currencyRepo.getCurrentData(new ResponseListener<ArrayList<CurrencyModel>>() {
            @Override
            public void onSuccess(ArrayList<CurrencyModel> data) {
                currencyArrayList = data;
                listener.onSuccess(currencyArrayList);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle failure if needed

                listener.onFailure(errorMessage);
            }
        });
    }
}

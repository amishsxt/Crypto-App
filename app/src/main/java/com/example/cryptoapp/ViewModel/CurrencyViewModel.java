package com.example.cryptoapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cryptoapp.Repository.DataModel.CurrencyModel;
import com.example.cryptoapp.Repository.CurrencyRepo;

import java.util.ArrayList;

public class CurrencyViewModel extends AndroidViewModel {

    private CurrencyRepo currencyRepo;
    private ArrayList<CurrencyModel> currencyArrayList;
    private LiveData<ArrayList<CurrencyModel>> liveData;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);
        currencyRepo = new CurrencyRepo(application);
        liveData = currencyRepo.getCurrentData();
    }

    public LiveData<ArrayList<CurrencyModel>> getLiveData(){
        return liveData;
    }
}

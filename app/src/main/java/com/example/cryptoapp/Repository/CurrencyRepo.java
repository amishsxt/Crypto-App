package com.example.cryptoapp.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptoapp.Repository.DataModel.CurrencyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CurrencyRepo {

    private Application application;
    private ArrayList<CurrencyModel> currencyArrayList;
//    private MutableLiveData<ArrayList<CurrencyModel>> mutableLiveData;

    public CurrencyRepo(Application application) {
        this.application = application;
        currencyArrayList = new ArrayList<>();
//        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<ArrayList<CurrencyModel>> getCurrentData() {
        MutableLiveData<ArrayList<CurrencyModel>> mutableLiveData = new MutableLiveData<>();

        // Fetch data initially
        fetchDataFromApi(mutableLiveData);

        // Create a timer for periodic updates
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                fetchDataFromApi(mutableLiveData);
            }
        }, 0, 10, TimeUnit.SECONDS); // Update every 10 seconds

        return mutableLiveData;
    }

    private void fetchDataFromApi(MutableLiveData<ArrayList<CurrencyModel>> mutableLiveData) {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(application);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<CurrencyModel> updatedData = new ArrayList<>();

                try {
                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");

                        updatedData.add(new CurrencyModel(name, symbol, price));
                    }

                    // Update the mutableLiveData using postValue()
                    mutableLiveData.postValue(updatedData);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    Toast.makeText(application, "Failed to extract json data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(application, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "7cf29235-4855-45c1-810d-0ef6a078c219");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }



//    public LiveData<ArrayList<CurrencyModel>> getCurrentData(){
//        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
//        RequestQueue requestQueue = Volley.newRequestQueue(application);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray dataArray = response.getJSONArray("data");
//
//                    for (int i=0;i<dataArray.length();i++){
//                        JSONObject dataObj = dataArray.getJSONObject(i);
//                        String name = dataObj.getString("name");
//                        String symbol = dataObj.getString("symbol");
//                        JSONObject quote = dataObj.getJSONObject("quote");
//                        JSONObject USD = quote.getJSONObject("USD");
//                        double price = USD.getDouble("price");
//
//                        currencyArrayList.add(new CurrencyModel(name,symbol,price));
//                        mutableLiveData.postValue(currencyArrayList);
//                    }
//
//                }catch (JSONException ex){
//                    ex.printStackTrace();
//                    Toast.makeText(application, "Failed to extract json data", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(application, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String,String> headers = new HashMap<>();
//                headers.put("X-CMC_PRO_API_KEY","7cf29235-4855-45c1-810d-0ef6a078c219");
//                return  headers;
//            }
//        };
//
//        requestQueue.add(jsonObjectRequest);
//        return mutableLiveData;
//    }
}

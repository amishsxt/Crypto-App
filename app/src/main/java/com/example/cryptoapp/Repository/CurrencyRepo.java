package com.example.cryptoapp.Repository;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptoapp.Model.CurrencyModel;
import com.example.cryptoapp.Model.ResponseListener;
import com.example.cryptoapp.View.CurrencyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrencyRepo {

    private Application application;
    private ArrayList<CurrencyModel> currencyArrayList;

    public CurrencyRepo(Application application) {
        this.application = application;
        currencyArrayList = new ArrayList<>();
    }


    public void getCurrentData(ResponseListener<ArrayList<CurrencyModel>> listener){
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(application);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i=0;i<dataArray.length();i++){
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");

                        currencyArrayList.add(new CurrencyModel(name,symbol,price));
                    }

                    listener.onSuccess(currencyArrayList);

                }catch (JSONException ex){
                    ex.printStackTrace();
                    Toast.makeText(application, "Failed to extract json data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure("Failed to get the data!");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","7cf29235-4855-45c1-810d-0ef6a078c219");
                return  headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}

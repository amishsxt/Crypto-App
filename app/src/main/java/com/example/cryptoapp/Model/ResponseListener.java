package com.example.cryptoapp.Model;

public interface ResponseListener<T> {
    void onSuccess(T data);
    void onFailure(String errorMessage);
}

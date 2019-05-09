package com.example.abedeid.dmstask.Services;

import com.example.abedeid.dmstask.API.API;
import com.example.abedeid.dmstask.URL.URL;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abed eid on 09/05/2019.
 */


public class WebService {
    public static WebService instance;
    public API api;

    public WebService() {

        OkHttpClient client=new OkHttpClient();
        try {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(new TLSSocketFactory())
                    .build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//
//
//
//
//        OkHttpClient client = new OkHttpClient.Builder().build();
               Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL.URL)
                .build();
        api = retrofit.create(API.class);

    }

    public static WebService getInstance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    public API getApi() {
        return api;
    }
}
package com.example.pavani.bakingapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by pavani on 2/6/18.
 */

public interface BakingAPI {



    @GET("2017/May/59121517_baking/baking.json")

    Call<ArrayList<Recipies>> listRecipes();

    Retrofit retrofit=new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/")
            .addConverterFactory(GsonConverterFactory.
                    create()).build();

}

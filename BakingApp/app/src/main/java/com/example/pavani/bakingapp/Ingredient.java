package com.example.pavani.bakingapp;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

/**
 * Created by pavani on 4/6/18.
 */
@Parcel
class Ingredient {

    @Expose
    public String ingredient;

    @Expose
    public String measure;

    @Expose
    public String quantity;
}

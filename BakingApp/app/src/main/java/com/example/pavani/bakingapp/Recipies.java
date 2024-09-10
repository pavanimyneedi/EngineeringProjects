package com.example.pavani.bakingapp;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by pavani on 4/6/18.
 */

class Recipies {
    @Expose
    public Integer id;
    @Expose
    public String name;
    @Expose
    public List<Ingredient> ingredients=null;
    @Expose
    public List<Step> steps=null;
    @Expose
    public String image;
}

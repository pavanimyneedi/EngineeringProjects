package com.example.pavani.bakingapp;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

/**
 * Created by pavani on 4/6/18.
 */
@Parcel
class Step {
    @Expose
    public  Integer id;
    @Expose
    public String shortDescription;
    @Expose
    public String description;
    @Expose
    public String videoURL;
    @Expose
    public String thumbnailURL;

}

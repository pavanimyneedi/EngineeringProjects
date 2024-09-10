package com.example.pavani.movieinfo;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pavani on 29/5/18.
 */

public class TaskContract {


    public static final String AUTHORITY="com.example.pavani.movieinfo";
    public static final Uri Base_Content_Uri=Uri.parse("content://"+AUTHORITY);
    public static final String PATH_TASKS="MovieInfo";

    private TaskContract(){}
    public static final class TaskEntry implements BaseColumns{
        public static final Uri CONTENT_URI=Base_Content_Uri.buildUpon().appendPath(PATH_TASKS).build();


        public static final String Table_Name="Favourite";


        public static final String id_mov="id_mov";
        public static final String title="title";
        public static final String rating="rating";
        public static final String image_path="image_path";
        public static final String description="description";
        public static final String backdrop_path="backdrop_path";
        public static final String release_date="release_date";
    }
}

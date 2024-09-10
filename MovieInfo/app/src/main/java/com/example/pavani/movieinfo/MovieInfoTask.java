package com.example.pavani.movieinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pavani on 26/5/18.
 */

class MovieInfoTask extends AsyncTask<String, Void, String> {
    public static final String image_url="https://image.tmdb.org/t/p/w500";
    static GridLayoutManager gLayoutManager;
    RecyclerView recyclerView;

    Context ctx;
    ProgressDialog progressDialog;

    ArrayList<MoviePojo> mPojo=new ArrayList<>();

    public MovieInfoTask(MainActivity mainActivity, RecyclerView recyclerView) {

        this.recyclerView=recyclerView;
        this.ctx=mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(ctx);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... strings) {
        String string = strings[0];
        String s1 = "";
        URL url = NetworkConnection.BuildUrl(strings);
        s1 = NetworkConnection.HttpResponse(url);
        return s1;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            String mov_name, poster_path, orig_lang, oview, o_tit, back_drop, rel_date;
            double vcount, mov_id, v_avg, popular;
            boolean video, adult;
            for (int i=0;i<jsonArray.length();i++){
                
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                mov_name = jsonObject1.getString("original_title");
                Log.i("Title",mov_name.toString());
                vcount = jsonObject1.getDouble("vote_count");
                mov_id = jsonObject1.getDouble("id");
                video = jsonObject1.getBoolean("video");
                v_avg = jsonObject1.getDouble("vote_average");
                popular = jsonObject1.getDouble("popularity");
                orig_lang = jsonObject1.getString("original_language");
                o_tit = jsonObject1.getString("original_title");
                rel_date = jsonObject1.getString("release_date");
                back_drop = image_url + jsonObject1.getString("backdrop_path");
                poster_path = image_url + jsonObject1.getString("poster_path");
                adult = jsonObject1.getBoolean("adult");
                oview = jsonObject1.getString("overview");


                MoviePojo pojo=new MoviePojo(mov_name,vcount,mov_id,video,v_avg,popular,orig_lang,o_tit,rel_date,back_drop,poster_path,adult,oview);
                mPojo.add(pojo);



            }
            gLayoutManager=new GridLayoutManager(ctx,2);
            recyclerView.setLayoutManager(gLayoutManager);
            recyclerView.setAdapter(new MovieAdapter(ctx,mPojo));
            recyclerView.scrollToPosition(MainActivity.scrollValue);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
    }
}

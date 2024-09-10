package com.example.pavani.movieapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pavani on 15/5/18.
 */

public class MyAsyncTask extends AsyncTask<String,Void,String>{

    RecyclerView rec;


    ProgressDialog pd;
    Context ctx;

    ArrayList<Pojo> mList=new ArrayList<>();

    public MyAsyncTask(MainActivity mainActivity, RecyclerView recyclerView) {

        this.ctx=mainActivity;
        this.rec=recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(ctx);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait ...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String url=strings[0];
      //  String url1=strings[1];
        StringBuilder result=new StringBuilder();
        try {
            URL buildUrl=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) buildUrl.openConnection();
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null){
                line=bufferedReader.readLine();
                result.append(line);
            }
            return result.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String name,image,lang,oview,otit,back,reldate;
        double vcount,idd,vavg,pop;
        boolean vid,adlt;
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                name=jsonObject1.getString("original_title");
                vcount=jsonObject1.getDouble("vote_count");
                idd=jsonObject1.getDouble("id");
                vid=jsonObject1.getBoolean("video");
                vavg=jsonObject1.getDouble("vote_average");
                pop=jsonObject1.getDouble("popularity");
                lang=jsonObject1.getString("original_language");
                otit=jsonObject1.getString("original_title");
                reldate=jsonObject1.getString("release_date");
                back="https://image.tmdb.org/t/p/w500"+jsonObject1.getString("backdrop_path");
                image="https://image.tmdb.org/t/p/w500"+jsonObject1.getString("poster_path");
                adlt=jsonObject1.getBoolean("adult");
                oview=jsonObject1.getString("overview");

                Pojo pojo=new Pojo(name,vcount,idd,vid,vavg,pop,lang,otit,reldate,back,image,adlt,oview);
                mList.add(pojo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //rec.setLayoutManager(new GridLayoutManager(ctx,2));
        rec.setLayoutManager(new GridLayoutManager(ctx,2));
        rec.setAdapter(new MyAdapter(ctx,mList));

        pd.dismiss();

    }
}

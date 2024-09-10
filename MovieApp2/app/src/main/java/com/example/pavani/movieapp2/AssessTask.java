/*
package com.example.pavani.movieapp2;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

*/
/**
 * Created by pavani on 17/5/18.
 *//*


public class AssessTask extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<AssessPojo>>{


    //public static String reviewUrl="http://api.themoviedb.org/3/movie/337167/reviews?api_key=db6dc4364382cea940ba1111f3e82781";

    String reviewUrl="http://api.themoviedb.org/3/movie/";
    //String videoId;
    StringBuilder stringBuilder;
    RecyclerView assess_rv;
   // ArrayList<Pojo> p;
    ArrayList<AssessPojo> assesList=new ArrayList<>();


    @Override
    public Loader<ArrayList<AssessPojo>> onCreateLoader(int i, Bundle bundle) {


       // videoId=p.
        return new AsyncTaskLoader<ArrayList<AssessPojo>>(AssessTask.this) {
            @Override
            public ArrayList<AssessPojo> loadInBackground() {


                };

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

        };

    }


    @Override
    public void onLoadFinished(Loader<ArrayList<AssessPojo>> loader, ArrayList<AssessPojo> assessPojos) {

        assess_rv.setLayoutManager(new LinearLayoutManager(AssessTask.this));
        assess_rv.setAdapter(new AssessAdapter(AssessTask.this,assessPojos));


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AssessPojo>> loader) {

    }
} */
/*extends AsyncTask<Void,Void,ArrayList<AssessPojo>>{
    String id="";
    //public static String reviewUrl="http://api.themoviedb.org/3/movie/337167/reviews?api_key=db6dc4364382cea940ba1111f3e82781";

   String reviewUrl="http://api.themoviedb.org/3/movie/";
   Context ctx;

    StringBuilder stringBuilder;
    RecyclerView assess_rv;
    ArrayList<AssessPojo> assesList=new ArrayList<>();

    public AssessTask(Main2Activity main2Activity, String id, RecyclerView recyclerView_assess) {

        ctx=main2Activity;
        this.id=id;
        this.assess_rv=recyclerView_assess;



    }

    @Override
    protected ArrayList<AssessPojo> doInBackground(Void... voids) {
      stringBuilder=new StringBuilder();
        Uri uri=Uri.parse(reviewUrl).buildUpon().appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter("api_key","db6dc4364382cea940ba1111f3e82781").build();

        Log.d("doInback",uri.toString());
        URL url=null;
        try {
            url=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null){

                stringBuilder.append(line).append("\n");
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        *//*
*/
/*if (stringBuilder!=null){*//*
*/
/*

            try {
                JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    //author,content,id,url,total_pages,total_results,
                    String auth=jsonObject1.getString("author");
                    String cont=jsonObject1.getString("content");
                    String id=jsonObject1.getString("id");
                    String url_url=jsonObject1.getString("url");
                   // String tpages=jsonObject1.getString("total_pages");
                    //String tresults=jsonObject1.getString("total_results");

                    //Log.d("Ref:"+i,id+""+auth);
                   // Toast.makeText(ctx, id+"\n"+auth, Toast.LENGTH_SHORT).show();
                    AssessPojo assessPojo=new AssessPojo(auth,cont,id,url_url);
                    assesList.add(assessPojo);



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        return assesList;

    }

    @Override
    protected void onPostExecute(ArrayList<AssessPojo> assessPojos) {
        super.onPostExecute(assessPojos);
       // Toast.makeText(ctx, stringBuilder.toString(), Toast.LENGTH_LONG).show();


        *//*
*/
/*recyclerView_trailers.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_trailers.setAdapter(new VideoAdapter(context,videoPojos));
*//*
*/
/*
        assess_rv.setLayoutManager(new LinearLayoutManager(ctx));
        assess_rv.setAdapter(new AssessAdapter(ctx,assessPojos));
    }*//*


*/

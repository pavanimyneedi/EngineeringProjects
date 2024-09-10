package com.example.pavani.movieapp2;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
/*

import static com.example.pavani.movieapp2.VideoLoader.ldr_id;
import static com.example.pavani.movieapp2.VideoTask.context;
import static com.example.pavani.movieapp2.VideoTask.id;
*/

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<VideoPojo>> {

    public static final int ldr_id=6;

    public static final int asses_id=2;
    ArrayList<AssessPojo> assesList=new ArrayList<>();


    String reviewUrl="http://api.themoviedb.org/3/movie/";
    //String videoId;
    StringBuilder stringBuilder;

   // public static String id="";
    public static RecyclerView recyclerView_trailers;
    public static RecyclerView recyclerView_assess;
    ArrayList<VideoPojo> vList=new ArrayList<>();

    public static String Videourl="http://api.themoviedb.org/3/movie/";
    //Tpublic static String Reviewurl="";


    TextView tit,redate,sis,vavg;
   public static String s;

    ImageView image1,image2;
    //String id,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        recyclerView_trailers=(RecyclerView)findViewById(R.id.id_trailar);
        recyclerView_assess=(RecyclerView)findViewById(R.id.id_asses);




        image1=(ImageView)findViewById(R.id.image_poster);
        image2=(ImageView)findViewById(R.id.image_back);


        tit=(TextView)findViewById(R.id.title_tv);
        redate=(TextView)findViewById(R.id.rel_date);
        sis=(TextView)findViewById(R.id.tv_synopsis);
        vavg=(TextView)findViewById(R.id.vote_avg);


        //String url1="http://api.themoviedb.org/3/movie/337167/videos?api_key=db6dc4364382cea940ba1111f3e82781";





        String[] getDetails=getIntent().getStringArrayExtra("details_required");
        //poster path
        Picasso.with(this).load(getDetails[1]).into(image2);
        Log.i("image","image1");
        //Log.i("image",getDetails[1]);
        //back drop path
        Picasso.with(this).load(getDetails[11]).into(image1);


        s = (getDetails[5]);
        //release date
        redate.setText(getDetails[10]);
        //title
        tit.setText(getDetails[0]);
        //vote average
        vavg.setText(getDetails[7]);
        //synopsis
        sis.setText(getDetails[3]);

        getLoaderManager().initLoader(ldr_id,null,Main2Activity.this);

        getLoaderManager().initLoader(asses_id,null,Main2Activity.this);

    }

    public void favourite(View view) {

        Toast.makeText(this, "added to favourites", Toast.LENGTH_LONG).show();
    }


    @Override
    public Loader<ArrayList<VideoPojo>> onCreateLoader(int i, Bundle bundle) {
        switch (i){
            case ldr_id:


                return new AsyncTaskLoader<ArrayList<VideoPojo>>(this) {
                    @Override
                    public ArrayList<VideoPojo> loadInBackground() {
                        StringBuilder result1=new StringBuilder();
                        // Uri uri=Uri.parse("http://api.themoviedb.org/3/movie/337167/videos?api_key=db6dc4364382cea940ba1111f3e82781");

                        Uri uri=Uri.parse(Videourl).buildUpon().appendPath(s)
                                .appendPath("videos")
                                .appendQueryParameter("api_key","db6dc4364382cea940ba1111f3e82781").build();
                        Log.i("do in background",uri.toString());

                        URL url=null;

                        try {
                            url=new URL(uri.toString());
                            // Toast.makeText(Main2Activity.this, url.toString(), Toast.LENGTH_SHORT).show();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            InputStream inputStream=urlConnection.getInputStream();
                            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                            String line="";
                            while ((line=bufferedReader.readLine())!=null){

                                result1.append(line).append("\n");
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(result1!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(result1.toString());
                                JSONArray jsonArray=jsonObject.getJSONArray("results");
                                for (int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                    String key=jsonObject1.getString("key");
                                    String type=jsonObject1.getString("type");


                                    VideoPojo videoPojo = new VideoPojo(key,type);
                                    vList.add(videoPojo);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return  vList;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
            case asses_id:
                return new AsyncTaskLoader<ArrayList<VideoPojo>>(this) {
                    @Override
                    public ArrayList<VideoPojo> loadInBackground() {
                        stringBuilder=new StringBuilder();
                        Uri uri=Uri.parse(reviewUrl).buildUpon().appendPath(s)
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
                        if (stringBuilder!=null) {

                            try {
                                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    //author,content,id,url,total_pages,total_results,
                                    String auth = jsonObject1.getString("author");
                                    String cont = jsonObject1.getString("content");
                                    String id = jsonObject1.getString("id");
                                    String url_url = jsonObject1.getString("url");
                                    // String tpages=jsonObject1.getString("total_pages");
                                    //String tresults=jsonObject1.getString("total_results");

                                    //Log.d("Ref:"+i,id+""+auth);
                                    // Toast.makeText(ctx, id+"\n"+auth, Toast.LENGTH_SHORT).show();
                                    AssessPojo assessPojo = new AssessPojo(auth, cont, id, url_url);
                                    assesList.add(assessPojo);


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };


            }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<VideoPojo>> loader, ArrayList<VideoPojo> videoPojos) {

        switch (loader.getId()){
            case ldr_id:
                recyclerView_trailers.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                recyclerView_trailers.setAdapter(new VideoAdapter(Main2Activity.this,videoPojos));
                break;
            case asses_id:
                recyclerView_assess.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                recyclerView_assess.setAdapter(new AssessAdapter(Main2Activity.this,assesList));
                break;
        }


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<VideoPojo>> loader) {

    }
}

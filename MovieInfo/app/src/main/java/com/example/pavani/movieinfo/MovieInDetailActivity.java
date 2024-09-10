package com.example.pavani.movieinfo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.pavani.movieinfo.MainActivity.API_KEY;

public class MovieInDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MoviePojo>>{

    public static final int ldr_id = 6;

    public static final int asses_id = 2;

    public static String s="";
    Snackbar snackbar1;
    CoordinatorLayout coordinatorLayout1;

    public static RecyclerView recyclerView_trailers;
    public static RecyclerView recyclerView_assess;

    Button fav;
    FavDBHelper favDBHelper;

    TextView release_tv,rating_tv,des,title;
    ImageView poster_iv,back_drop_iv;
    ArrayList<MoviePojo> assesList = new ArrayList<>();
    String reviewUrl = "http://api.themoviedb.org/3/movie/";
    ArrayList<MoviePojo> vList = new ArrayList<>();
    public static String Videourl = "http://api.themoviedb.org/3/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_in_detail);

        release_tv=(TextView)findViewById(R.id.rel_date);
        rating_tv=(TextView)findViewById(R.id.vote_avg);
        des=(TextView)findViewById(R.id.tv_synopsis);
        title=(TextView)findViewById(R.id.title_tv);

        fav=(Button)findViewById(R.id.fav_button);
        coordinatorLayout1=(CoordinatorLayout)findViewById(R.id.id_main2_coordinate_layout);

        poster_iv=(ImageView)findViewById(R.id.image_poster);
        back_drop_iv=(ImageView)findViewById(R.id.image_back);

        recyclerView_trailers=(RecyclerView)findViewById(R.id.video_rv);
        recyclerView_assess=(RecyclerView)findViewById(R.id.asses_rv);


        final String[] getDetails=getIntent().getStringArrayExtra("details_required");
        s=getDetails[5];
        boolean conn=new MainActivity().isConnected();
        if (conn==false) {
            String poster=getDetails[1];
            String back_drop=getDetails[11];

            Picasso.with(this).load(poster).into(poster_iv);
            Picasso.with(this).load(back_drop).into(back_drop_iv);

            title.setText(getDetails[0]);
            release_tv.setText(getDetails[10]);
            rating_tv.setText(getDetails[7]);
            des.setText(getDetails[3]);
        }
        else
        {
            snackbar1=Snackbar.make(coordinatorLayout1,getString(R.string.not_connected),Snackbar.LENGTH_LONG);
            View snackBarView = snackbar1.getView();
            snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
            snackbar1.show();
        }
        getSupportLoaderManager().initLoader(ldr_id,null,MovieInDetailActivity.this);

        getSupportLoaderManager().restartLoader(asses_id,null,MovieInDetailActivity.this);

        Cursor q=getContentResolver().query(Uri.parse(TaskContract.TaskEntry.CONTENT_URI + "/*"),null,TaskContract.TaskEntry.id_mov + " LIKE ? ",new String[]{s},null);
        if (q.getCount()>0) {
            fav.setText("remove from fav");
        } else {
            fav.setText("add to fav");
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav.setText("remove from fav");
                Cursor q=getContentResolver().query(Uri.parse(TaskContract.TaskEntry.CONTENT_URI + "/*"),null,TaskContract.TaskEntry.id_mov + " LIKE ? ",new String[]{s},null);
                if ((q.getCount())>=1)
                {
                    getContentResolver().delete(Uri.parse(TaskContract.TaskEntry.CONTENT_URI + "/*"), TaskContract.TaskEntry.id_mov + " = ? ", new String[]{s});
                    fav.setText("add to fav");
                    snackbar1=Snackbar.make(coordinatorLayout1, R.string.removed_as_fav,Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar1.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.brown));
                    snackbar1.show();
                }
                else {
                    ContentValues cv=new ContentValues();
                    cv.put(TaskContract.TaskEntry.id_mov,s);
                    cv.put(TaskContract.TaskEntry.image_path,getDetails[1]);
                    cv.put(TaskContract.TaskEntry.backdrop_path,getDetails[11]);
                    cv.put(TaskContract.TaskEntry.title,getDetails[0]);
                    cv.put(TaskContract.TaskEntry.rating,getDetails[7]);
                    cv.put(TaskContract.TaskEntry.description,getDetails[3]);
                    cv.put(TaskContract.TaskEntry.release_date,getDetails[10]);
                    FavDBHelper favDBHelper=new FavDBHelper(MovieInDetailActivity.this);
                    getContentResolver().insert(Uri.parse(TaskContract.TaskEntry.CONTENT_URI+""),cv);
                    snackbar1= Snackbar.make(coordinatorLayout1, R.string.added_as_fav,Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar1.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.brown));
                    snackbar1.show();

                }
            }
        });


    }




    @Override
    public Loader<ArrayList<MoviePojo>> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ldr_id:
                return new AsyncTaskLoader<ArrayList<MoviePojo>>(this) {
                    @Override
                    public ArrayList<MoviePojo> loadInBackground() {

                        StringBuilder result1=new StringBuilder();


                        Uri uri=Uri.parse(Videourl).buildUpon().appendPath(s)
                                .appendPath("videos")
                                .appendQueryParameter("api_key",API_KEY).build();
                        URL url=null;

                        try {
                            url=new URL(uri.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                            String line="";
                            while ((line=bufferedReader.readLine())!=null){

                                result1.append(line).append("\n");
                                inputStream.close();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (result1!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(result1.toString());
                                JSONArray jsonArray=jsonObject.getJSONArray("results");
                                for (int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                    String key=jsonObject1.getString("key");
                                    String type=jsonObject1.getString("type");


                                    MoviePojo videoPojo = new MoviePojo(key,type);
                                    vList.add(videoPojo);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        return vList;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };

            case asses_id:
                return new AsyncTaskLoader<ArrayList<MoviePojo>>(this) {
                    @Override
                    public ArrayList<MoviePojo> loadInBackground() {
                         StringBuilder stringBuilder=new StringBuilder();
                        Uri uri=Uri.parse(reviewUrl).buildUpon().appendPath(s)
                                .appendPath("reviews")
                                .appendQueryParameter("api_key",API_KEY).build();
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
                        if (stringBuilder!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String auth = jsonObject1.getString("author");
                                    String cont = jsonObject1.getString("content");
                                    String id = jsonObject1.getString("id");
                                    String url_url = jsonObject1.getString("url");
                                    MoviePojo assessPojo = new MoviePojo(auth, cont, id, url_url);
                                    assesList.add(assessPojo);
                                }
                            }
                            catch (JSONException e) {
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
    public void onLoadFinished(Loader<ArrayList<MoviePojo>> loader, ArrayList<MoviePojo> data) {

        switch (loader.getId()){

            case ldr_id:
                recyclerView_trailers.setLayoutManager(new LinearLayoutManager(MovieInDetailActivity.this));
                recyclerView_trailers.setAdapter(new VideoAdapter(MovieInDetailActivity.this,data));
                break;
            case asses_id:
                recyclerView_assess.setLayoutManager(new LinearLayoutManager(MovieInDetailActivity.this));
                recyclerView_assess.setAdapter(new AssessAdapter(MovieInDetailActivity.this,assesList));
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MoviePojo>> loader) {

    }
}

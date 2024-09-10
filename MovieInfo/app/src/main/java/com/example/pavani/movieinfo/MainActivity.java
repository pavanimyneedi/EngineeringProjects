package com.example.pavani.movieinfo;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String API_KEY = BuildConfig.api;
    String key="storeState";
    static String valueState="";
    private String scrollKey="aKey";
   static int scrollValue;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;


    public static RecyclerView recyclerView;

    String url = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    String url1 = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rec_tv);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.id_coordinate);

        if (isConnected()) {

            snackbar=Snackbar.make(coordinatorLayout,getString(R.string.network_connected),Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
            snackbar.show();
            if(savedInstanceState!=null)
            {
                if(savedInstanceState.containsKey(key))
                {
                    String v=savedInstanceState.getString(key);
                    scrollValue=savedInstanceState.getInt(scrollKey);
                    if(v.equals("popular"))
                    {
                        MovieInfoTask info = new MovieInfoTask(this, recyclerView);
                        info.execute(url);
                    }
                    else if(v.equals("top_rated"))
                    {
                        MovieInfoTask info = new MovieInfoTask(this, recyclerView);
                        info.execute(url1);
                    }
                    else if(v.equals("favourites"))
                    {
                        ArrayList<MoviePojo> fav=Favourites();
                        MainActivity.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                        MainActivity.recyclerView.setAdapter(new MovieAdapter(this, fav));
                    }
                    else {
                        MovieInfoTask info = new MovieInfoTask(this, recyclerView);
                        info.execute(url);
                    }

                }
                else {
                    MovieInfoTask info = new MovieInfoTask(this, recyclerView);
                    info.execute(url);

                }

            }
            else
            {
                MovieInfoTask info = new MovieInfoTask(this, recyclerView);
                info.execute(url);
            }
        }
        else {
            snackbar=Snackbar.make(coordinatorLayout, R.string.not_connected,Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
            snackbar.show();
        }

    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public ArrayList<MoviePojo> Favourites()
    {
        String otit,reldate,back,image,oview;
        double idd,vavg;
        Cursor movieInfo=getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI,null,null,null,null);
        ArrayList<MoviePojo> favPojos=new ArrayList<>();
        if(movieInfo.getCount()>0){
            if (movieInfo.moveToFirst()) {
                do {
                    idd=movieInfo.getDouble(1);
                    vavg=movieInfo.getDouble(4);
                    otit=movieInfo.getString(2);
                    reldate=movieInfo.getString(3);
                    back=movieInfo.getString(7);
                    image=movieInfo.getString(6);
                    oview=movieInfo.getString(5);

                    MoviePojo pojo=new MoviePojo(idd,vavg,otit,reldate,back,image,oview);
                    favPojos.add(pojo);

                } while (movieInfo.moveToNext());
            }
        }
        else {
            snackbar=Snackbar.make(coordinatorLayout, R.string.no_favourites,Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
            snackbar.show();
        }
        return favPojos;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_popular:
                scrollValue=0;
                if (isConnected()) {
                    valueState="popular";
                    snackbar=Snackbar.make(coordinatorLayout,getString(R.string.network_connected),Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
                    snackbar.show();
                    new MovieInfoTask(MainActivity.this,recyclerView).execute(url);
                } else {
                    snackbar=Snackbar.make(coordinatorLayout,getString(R.string.not_connected),Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
                    snackbar.show();
                }


                break;
            case R.id.id_topRated:
                scrollValue=0;
                if (isConnected()) {
                    valueState="top_rated";
                    snackbar=Snackbar.make(coordinatorLayout,getString(R.string.network_connected),Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
                    snackbar.show();
                    new MovieInfoTask(MainActivity.this,recyclerView).execute(url1);
                } else {
                    snackbar=Snackbar.make(coordinatorLayout,getString(R.string.not_connected),Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(this.getResources().getColor(R.color.brown));
                    snackbar.show();
                }
                break;
            case R.id.id_favourites:
                scrollValue=0;
                valueState="favourites";
                ArrayList<MoviePojo> favArray=new ArrayList<>();
                favArray=Favourites();
                MainActivity.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                MainActivity.recyclerView.setAdapter(new MovieAdapter(this, favArray));
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(key,valueState);
        scrollValue=MovieInfoTask.gLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(scrollKey,scrollValue);
    }
}

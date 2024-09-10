package com.example.pavani.movieapp2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.id_recycler_view);

        //String url1="http://api.themoviedb.org/3/movie/337167/reviews?api_key=db6dc4364382cea940ba1111f3e82781";

        String url="http://api.themoviedb.org/3/movie/popular?api_key=db6dc4364382cea940ba1111f3e82781";
        MyAsyncTask task=new MyAsyncTask(this,recyclerView);
        task.execute(url);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text;
        switch (item.getItemId()){
            case R.id.id_popular:
                new MyAsyncTask(this,recyclerView).execute("http://api.themoviedb.org/3/movie/popular?api_key=db6dc4364382cea940ba1111f3e82781");
                break;
            case R.id.id_rating:
                new MyAsyncTask(this,recyclerView).execute("http://api.themoviedb.org/3/movie/top_rated?api_key=db6dc4364382cea940ba1111f3e82781");
                break;

        }
        return true;
    }



   }

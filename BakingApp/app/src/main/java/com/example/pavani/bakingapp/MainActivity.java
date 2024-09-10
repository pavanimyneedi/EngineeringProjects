package com.example.pavani.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pavani.bakingapp.BakingAPI.retrofit;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recipes) RecyclerView mRecyclerView;
    @BindBool(R.bool.isTablet)
    boolean isTablet;
    private RecipeAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private int cardsInRow;
    private ArrayList<Recipies> list;
    RecipeAdapter.OnItemClickListener onItemClickListener = new RecipeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            List<Ingredient> ingredientWidget = list.get(position).ingredients;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ingredientWidget.size(); i++) {
                int serial = i + 1;
                builder.append(serial + "- " + ingredientWidget.get(i).quantity +" "+ ingredientWidget.get(i).measure +" of "+ ingredientWidget.get(i).ingredient + "\n");
            }
            SharedPreferences preferences = getSharedPreferences("Recipe", 0);
            Toast.makeText(MainActivity.this, "added to widget", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ingredientsWidget", builder.toString());
            editor.putString("title", list.get(position).name);
            editor.apply();

            int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidget.class));


            RecipeWidget myWidget = new RecipeWidget();
            myWidget.onUpdate(getBaseContext(), AppWidgetManager.getInstance(getBaseContext()), ids);

            Intent intent = new Intent(getBaseContext(), RecipeListActivity.class);
            intent.putExtra("title", list.get(position).name);
            intent.putExtra("ingredients", Parcels.wrap(list.get(position).ingredients));
            intent.putExtra("steps", Parcels.wrap(list.get(position).steps));
            startActivity(intent);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cardsInRow = (isTablet)? 3: 1;

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(cardsInRow, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        BakingAPI api = retrofit.create(BakingAPI.class);

        Call<ArrayList<Recipies>> call = api.listRecipes();

        call.enqueue(new Callback<ArrayList<Recipies>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipies>> call, Response<ArrayList<Recipies>> response) {
                if (response.code() == 200) {
                    list = response.body();
                    mAdapter = new RecipeAdapter(getBaseContext(), response.body());
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(onItemClickListener);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipies>> call, Throwable t) {


            }
        });
    }

}
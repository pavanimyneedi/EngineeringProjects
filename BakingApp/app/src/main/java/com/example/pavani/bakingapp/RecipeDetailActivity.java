package com.example.pavani.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_toolbar) Toolbar toolbar;
    @BindBool(R.bool.isTablet) boolean isTablet;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            toolbar.setTitle(getIntent().getStringExtra("shortDescription"));
            Bundle arguments = new Bundle();
            arguments.putString("shortDescription",
                    getIntent().getStringExtra("shortDescription"));
            arguments.putInt("id",getIntent().getIntExtra("id",-1));
            if (getIntent().hasExtra("ingredients")){
                List<Ingredient> in= Parcels.unwrap(getIntent().getParcelableExtra("ingredients"));
                arguments.putParcelable("ingredients",getIntent().getParcelableExtra("ingredients"));

            }else {
                arguments.putString("description",getIntent().getStringExtra("description"));
                arguments.putString("videoURL",getIntent().getStringExtra("videoURL"));
                arguments.putString("thumbnailURL",getIntent().getStringExtra("thumbnailURL"));
                if (!isTablet){
                    if (this.getResources().getConfiguration().orientation == 2){
                        appBar.setVisibility(View.GONE);
                    }
                }
            }

            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("videoURL")){
            if (!isTablet){
                if (this.getResources().getConfiguration().orientation == 2){
                    appBar.setVisibility(View.GONE);
                }
            }
        }
    }
}

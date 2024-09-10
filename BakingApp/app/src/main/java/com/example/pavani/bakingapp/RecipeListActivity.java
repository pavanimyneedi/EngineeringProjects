package com.example.pavani.bakingapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import org.parceler.Parcels;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.item_list) View recyclerView;
    @BindString(R.string.ingredients) String ingredientsText;
    private boolean mTwoPane;
    private Intent intent;
    private String title;
    private List<Step> stepsList;
    private List<Ingredient> ingredientsList;
    private int stepsListSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        /*toolbar.setTitle(getTitle());*/
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        intent=getIntent();
        if (intent.getExtras()!=null){
            title=intent.getStringExtra("title");
            stepsList= Parcels.unwrap(intent.getParcelableExtra("steps"));
            ingredientsList=Parcels.unwrap(intent.getParcelableExtra("ingredients"));
            toolbar.setTitle(title);

            stepsListSize=(savedInstanceState!=null)? savedInstanceState.getInt("stepsListSize") :stepsList.size();

            if (stepsList.size() == stepsListSize){
                Step s=new Step();
                s.shortDescription=ingredientsText;
                s.id=-1;
                stepsList.add(0,s);
            }
        }

       /* ActionBar actionBar=getActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        setupRecyclerView((RecyclerView) recyclerView);
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(stepsList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        private final List<Step> mValues;

        public SimpleItemRecyclerViewAdapter(List<Step> items) {
            mValues=items;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem=mValues.get(position);
            holder.mContentView.setText(mValues.get(position).shortDescription);
            if (holder.mItem.shortDescription == ingredientsText){
                holder.mContentView.setTextColor(Color.argb(255,0,0,255));

            }
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTwoPane){
                        Bundle arguments=new Bundle();
                        arguments.putString("shortDescription",holder.mItem.shortDescription);
                        arguments.putInt("id",holder.mItem.id);
                        if (holder.mItem.shortDescription==ingredientsText){
                            toolbar.setTitle(ingredientsText);
                            arguments.putParcelable("ingredients",intent.getParcelableExtra("ingredients"));
                        }
                        else {
                            arguments.putString("description",holder.mItem.description);
                            arguments.putString("videoURL",holder.mItem.videoURL);
                            arguments.putString("thumbnailURL",holder.mItem.thumbnailURL);
                        }
                        RecipeDetailFragment fragment=new RecipeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container,fragment).commit();

                    }
                    else {
                        Context context=view.getContext();
                        Intent intent=new Intent(context,RecipeDetailActivity.class);
                        intent.putExtra("shortDescription",holder.mItem.shortDescription);
                        if (holder.mItem.shortDescription==ingredientsText){
                            intent.putExtra("ingredients",Parcels.wrap(ingredientsList));

                        }
                        else {
                            intent.putExtra("description",holder.mItem.description);
                            intent.putExtra("videoURL",holder.mItem.videoURL);
                            intent.putExtra("thumbnailURL",holder.mItem.thumbnailURL);
                        }
                        context.startActivity(intent);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public Step mItem;
            @BindView(R.id.content) TextView mContentView;


           public ViewHolder(View view) {
                super(view);
                mView=view;
                ButterKnife.bind(this,view);

            }
            public String toString(){
               return super.toString() + " '" + mContentView.getText() + "'";
            }

        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backPressed:
                finish();
        }
        return super.onOptionsItemSelected(item);

    }*/
}

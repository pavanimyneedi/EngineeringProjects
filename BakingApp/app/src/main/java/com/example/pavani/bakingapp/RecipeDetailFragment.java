package com.example.pavani.bakingapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    @BindView(R.id.recipe_detail) TextView details;
    @BindView(R.id.stepList) LinearLayout stepList;
    @BindView(R.id.video_view) SimpleExoPlayerView playerView;
    @BindView(R.id.recipeDetails_list) View recyclerView;
   // @BindView(R.id.thumb_nail_image) ImageView thumbNailImage;
    @BindBool(R.bool.isTablet) boolean isTablet;
    private String mItem, description, videoURL;
    private List<Ingredient> ingredients;
    private Activity activity;
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true, isIngredients = false;
    private String thumbnailURL="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4";

    public RecipeDetailFragment() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this.getActivity();

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("playerPosition", 0);
        }

        if (getArguments().containsKey("shortDescription")) {
            mItem = getArguments().getString("shortDescription");
            isIngredients = true;
            android.support.v7.widget.Toolbar appBarLayout = activity.findViewById(R.id.toolbar);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments().containsKey("ingredients")) {
            ingredients = Parcels.unwrap(getArguments().getParcelable("ingredients"));
            setupRecyclerView((RecyclerView) recyclerView);
            stepList.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            releasePlayer();
        } else {
                if (savedInstanceState!=null){
                    playbackPosition=savedInstanceState.getLong("playerPosition");
                    playWhenReady=savedInstanceState.getBoolean("ready");
                }
            description = getArguments().getString("description");
            Log.i("desc in fragment",description);
            videoURL = getArguments().getString("videoURL");
            Log.i("video in frag",videoURL);
            Log.i("thumb in frag",thumbnailURL);
            recyclerView.setVisibility(View.GONE);
            if (!videoURL.toString().contains("https")) {
                Log.i("https://","inside if condition!");
                if (getArguments().getString("thumbnailURL").equals("mp4")){
                     Toast.makeText(activity, "no Thumbnail Image", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                if (!isTablet) {
                    if (activity.getResources().getConfiguration().orientation == 2) {
                        Point size = new Point();
                        activity.getWindowManager().getDefaultDisplay().getSize(size);
                        playerView.setBackgroundColor(Color.parseColor("black"));
                        playerView.getLayoutParams().height = size.y;
                        playerView.getLayoutParams().width = size.x;
                        playerView.layout(0, 0, size.x, size.y);
                        stepList.setVisibility(View.GONE);
                        playerView.setVisibility(View.VISIBLE);
                        hideSystemUi();
                    }
                }
                initializePlayer();
            }
            details.setText(description);

        }

        return rootView;
    }


    private void initializePlayer() {

        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(videoTrackSelectionFactory), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }

            MediaSource mediaSource = buildMediaSource(Uri.parse(videoURL));
            player.prepare(mediaSource, false, false);
            player.setPlayWhenReady(playWhenReady);
            playerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if(!isIngredients){

                initializePlayer();}

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || player == null)) {
            if (!isIngredients) {
                if (player!=null){
                    initializePlayer();
                }

            }

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (player!=null){
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private MediaSource buildMediaSource(Uri uri) {

        DataSource.Factory dataSoueceFactory = new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
        return new ExtractorMediaSource(uri, dataSoueceFactory, new DefaultExtractorsFactory(), null, null);
    }

    private void hideSystemUi() {

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    private void releasePlayer() {

        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ingredients));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playerPosition",playbackPosition);
        if (player!=null){
            outState.putLong("playerPosition",player.getCurrentPosition());
            outState.putBoolean("ready",player.getPlayWhenReady());
        }
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Ingredient> mValues;

        public SimpleItemRecyclerViewAdapter(List<Ingredient> items) {

            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {

            holder.mItem = mValues.get(position);
            holder.mContentView.setText(mValues.get(position).quantity +" "+ mValues.get(position).measure +" of "+ mValues.get(position).ingredient);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public Ingredient mItem;
            @BindView(R.id.content) TextView mContentView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                ButterKnife.bind(this, view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}

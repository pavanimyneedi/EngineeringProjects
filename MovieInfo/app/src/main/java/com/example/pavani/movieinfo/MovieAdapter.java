package com.example.pavani.movieinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pavani on 26/5/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    ArrayList<MoviePojo> AdapterPojo=new ArrayList<>();

    Context context;
    String string="https://image.tmdb.org/t/p/w500";

    public MovieAdapter(Context ctx, ArrayList<MoviePojo> mPojo) {

        this.context=ctx;
        this.AdapterPojo=mPojo;
    }


    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_info,parent,false));
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {
        String ss=AdapterPojo.get(position).getPoster_path().toString();
        Picasso.with(context).load(ss).placeholder(R.drawable.ic_action_name).into(holder.image_view);
    }

    @Override
    public int getItemCount() {
        return AdapterPojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView image_view;

        public MyViewHolder(View itemView) {
            super(itemView);

            image_view=itemView.findViewById(R.id.movie_image_id);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int array=getAdapterPosition();
            String[] string=new String[15];
            string[0]=AdapterPojo.get(array).getO_tit();
            string[1]=AdapterPojo.get(array).getPoster_path();
            string[2]=AdapterPojo.get(array).getOrig_lang();
            string[3]=AdapterPojo.get(array).getOview();
            string[4]=Double.toString(AdapterPojo.get(array).getPopular());
            string[5]=Double.toString(AdapterPojo.get(array).getMov_id());
            string[6]=Double.toString(AdapterPojo.get(array).getVcount());
            string[7]=Double.toString(AdapterPojo.get(array).v_avg);
            string[8]=Boolean.toString(AdapterPojo.get(array).video);
            string[9]=Boolean.toString(AdapterPojo.get(array).adult);
            string[10]=AdapterPojo.get(array).getRel_date();
            string[11]=AdapterPojo.get(array).getBack_drop();
            Intent intent=new Intent(context,MovieInDetailActivity.class);
            intent.putExtra("details_required",string);
            view.getContext().startActivity(intent);



        }
    }


}

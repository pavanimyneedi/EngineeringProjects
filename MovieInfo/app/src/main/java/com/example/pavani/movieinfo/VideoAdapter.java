package com.example.pavani.movieinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pavani on 29/5/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{
    Context context;
    ArrayList<MoviePojo> vpojos;

    public VideoAdapter(Context context, ArrayList<MoviePojo> data) {
        super();

        this.context=context;
        this.vpojos=data;

    }

    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.video,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.MyViewHolder holder, int position) {

        holder.textView.setText(vpojos.get(position).getMtype());

    }

    @Override
    public int getItemCount() {
        return vpojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_trailar_list);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+""+vpojos.get(getAdapterPosition()).getMkey())));


        }
    }
}

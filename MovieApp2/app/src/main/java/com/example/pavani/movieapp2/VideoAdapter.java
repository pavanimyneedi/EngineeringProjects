package com.example.pavani.movieapp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by pavani on 16/5/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoPojo> vpojos;
    public VideoAdapter(Context context, ArrayList<VideoPojo> vList){

        super();
        this.context= context;
        this.vpojos=vList;
    }

    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.trailers_list,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.MyViewHolder holder, int position) {


        holder.textView.setText(vpojos.get(position).getMtype());
        //Toast.makeText(context, "on onbind view holder", Toast.LENGTH_SHORT).show();
        //VideoPojo p=vpojos.get(position);
        // holder.name.setText(p.getmTitle());
       // Picasso.with(context).load(p.getMurl()).into((Target) holder.textView);


    }

    @Override
    public int getItemCount() {
        return vpojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        //ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.text_trailar_list);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+""+vpojos.get(getAdapterPosition()).getMtype())));

        }
    }
}

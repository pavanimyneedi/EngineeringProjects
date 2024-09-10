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

public class AssessAdapter extends RecyclerView.Adapter<AssessAdapter.ViewHolder> {

    Context context;
    ArrayList<MoviePojo> apojos;

    public AssessAdapter(Context context, ArrayList<MoviePojo> assesList) {
        super();
        this.context=context;
        this.apojos=assesList;
    }


    @Override
    public AssessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.asses,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssessAdapter.ViewHolder holder, int position) {
        holder.tv_id.setText(apojos.get(position).getMid());
        holder.tv_auth.setText(apojos.get(position).getMauthor());
        holder.tv_cont.setText(apojos.get(position).getMcount());
    }

    @Override
    public int getItemCount() {
        return apojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_id,tv_auth,tv_cont;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.id_id);
            tv_auth=itemView.findViewById(R.id.id_author);
            tv_cont=itemView.findViewById(R.id.id_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apojos.get(getAdapterPosition()).getMurl_url())));

        }
    }
}

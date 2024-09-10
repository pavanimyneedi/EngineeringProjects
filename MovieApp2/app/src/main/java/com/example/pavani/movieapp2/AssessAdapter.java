package com.example.pavani.movieapp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pavani on 17/5/18.
 */

public class AssessAdapter extends RecyclerView.Adapter<AssessAdapter.MyViewHolder>{

    Context context;
    ArrayList<AssessPojo> apojos;


    public AssessAdapter(Context ctx, ArrayList<AssessPojo> assessPojos) {

        super();
        this.context=ctx;
        this.apojos=assessPojos;
    }


    @Override
    public AssessAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.asses_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssessAdapter.MyViewHolder holder, int position) {

        holder.tv_id.setText(apojos.get(position).getMid());
        holder.tv_auth.setText(apojos.get(position).getMauthor());
        holder.tv_cont.setText(apojos.get(position).getMcount());
        holder.tv_url.setText(apojos.get(position).getMurl_url());

    }

    @Override
    public int getItemCount() {
        return apojos.size();
    }

    public class MyViewHolder extends ViewHolder implements View.OnClickListener {
        TextView tv_id,tv_auth,tv_cont,tv_url;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.id_id);
            tv_auth=itemView.findViewById(R.id.id_author);
            tv_cont=itemView.findViewById(R.id.id_content);
            tv_url=itemView.findViewById(R.id.id_url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apojos.get(getAdapterPosition()).getMid())));
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apojos.get(getAdapterPosition()).getMauthor())));
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apojos.get(getAdapterPosition()).getMcount())));*/
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apojos.get(getAdapterPosition()).getMurl_url())));
        }
    }
}

package com.example.pavani.movieapp2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pavani on 15/5/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    ArrayList<Pojo> pojos;
   // ArrayList<VideoPojo>videoPojos;


    public MyAdapter(Context ctx, ArrayList<Pojo> mList) {

        context=ctx;
        pojos=mList;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.row,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {


        Pojo p=pojos.get(position);
        // holder.name.setText(p.getmTitle());
        Picasso.with(context).load(p.getmPoster()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return pojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int array=getAdapterPosition();
            String[] string=new String[15];
            string[0]=pojos.get(array).getmTitle();
            string[1]=pojos.get(array).getmPoster();
            string[2]=pojos.get(array).getMlang();
            string[3]=pojos.get(array).getOview();
            string[4]=Double.toString(pojos.get(array).getPopul());
            string[5]=Double.toString(pojos.get(array).getIdd());
            string[6]=Double.toString(pojos.get(array).vcount);
            string[7]=Double.toString(pojos.get(array).vavg);
            string[8]=Boolean.toString(pojos.get(array).vid);
            string[9]=Boolean.toString(pojos.get(array).adlt);
            string[10]=pojos.get(array).getmReldate();
            string[11]=pojos.get(array).getBack();

            //string[12]=videoPojos.get(array).getAut();
           /* string[12]=videoPojos.get(array).getMkey();
            string[13]=videoPojos.get(array).getMtype();
*/

            /*string[4]= String.valueOf(data.get(array).getId());*/

            /*//Intent intent=new Intent(context,Main2Activity.class);
           // intent.putExtra("details required",string);
            //view.getContext().startActivity(intent);
            */Intent intent=new Intent(context,Main2Activity.class);
            intent.putExtra("details_required",string);
            view.getContext().startActivity(intent);

        }
    }
}


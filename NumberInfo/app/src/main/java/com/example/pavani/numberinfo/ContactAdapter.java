package com.example.pavani.numberinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    String[] names;

    Context context;

    ContactInterface contactInterface;

    interface ContactInterface{
        void onItemClick(int position);
    }


    public ContactAdapter(Context context,ContactInterface contactInterface){

        this.contactInterface=contactInterface;
        this.context=context;
    }
    @NonNull
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater=LayoutInflater.from(context).inflate(R.layout.contacts,parent,false);
        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MyViewHolder holder, int position) {

        holder.tv.setText(names[position]);

    }

    @Override
    public int getItemCount() {
        if (names==null){
            return 0;
        }
        return names.length;
    }

    public void name(String[] names){
        this.names=names;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.contact_id);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            contactInterface.onItemClick(position);



        }
    }
}

package com.example.pavani.numberinfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactListActivity extends AppCompatActivity  implements ContactAdapter.ContactInterface{

    int pos=0;

    String[] names;
    String[] numbers;

    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ContactAdapter adapter;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        if (savedInstanceState!= null){

            pos=savedInstanceState.getInt(getResources().getString(R.string.position));

        }

        uid = getIntent().getStringExtra(getResources().getString(R.string.usrId));

        databaseReference= FirebaseDatabase.getInstance().getReference().child(uid);



        recyclerView=(RecyclerView)findViewById(R.id.id_rec);
        adapter=new ContactAdapter(this,ContactListActivity.this);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (isConnected()){

            new Task().execute();

        }
        else {

            Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pos=layoutManager.findFirstVisibleItemPosition();
        outState.putInt(getResources().getString(R.string.position),pos);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus_activity, menu);
        return true;
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e(getResources().getString(R.string.connectivity_exception), e.getMessage());
        }
        return connected;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int num=item.getItemId();
        if (num==R.id.addNewContact){
            Intent intent=new Intent(ContactListActivity.this,AddContactActivity.class);
            intent.putExtra(getResources().getString(R.string.usrId),uid);
            startActivity(intent);
            return true;
        }
        else if (num==R.id.signout){
            if (isConnected()){

                firebaseAuth.signOut();
                Intent intent=new Intent(ContactListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
            else {
                Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
            }


            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onItemClick(int position) {

        Intent intent=new Intent(ContactListActivity.this,ContactDetailActivity.class);
        intent.putExtra(getResources().getString(R.string.name),names[position]);
        intent.putExtra(getResources().getString(R.string.number),numbers[position]);
        startActivity(intent);

    }
    public class Task extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount()== 0){
                        Toast.makeText(ContactListActivity.this, getResources().getString(R.string.no_numbers_to_display), Toast.LENGTH_SHORT).show();
                    }
                    names=new String[(int) dataSnapshot.getChildrenCount()];
                    numbers=new String[(int) dataSnapshot.getChildrenCount()];

                    int i=0;
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        names[i]=dataSnapshot1.getKey();
                        numbers[i]= (String) dataSnapshot1.getValue();
                        i++;

                    }
                    adapter.name(names);
                    recyclerView.scrollToPosition(pos);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {



                }
            });



            return null;
        }
    }
}


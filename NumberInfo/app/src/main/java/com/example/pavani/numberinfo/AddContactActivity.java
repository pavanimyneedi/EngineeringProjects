package com.example.pavani.numberinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContactActivity extends AppCompatActivity {

    EditText addContactActivityName,addContactActivityPhnno;
    Button addContact;

    DatabaseReference databaseReference;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        addContactActivityName=(EditText)findViewById(R.id.activity_contact_name);
        addContactActivityPhnno=(EditText)findViewById(R.id.activity_contact_phnno);
        addContact=(Button)findViewById(R.id.add_contact);

        uid=getIntent().getStringExtra(getResources().getString(R.string.usrId));


               addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()){

                    databaseReference= FirebaseDatabase.getInstance().getReference().child(uid);


                    databaseReference.child(addContactActivityName.getText().toString()).setValue(addContactActivityPhnno.getText().toString());
                    addContactActivityName.setText("");
                    addContactActivityPhnno.setText("");
                    Toast.makeText(AddContactActivity.this, getResources().getString(R.string.number_added_to_contact), Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(AddContactActivity.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }


            }
        });



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


}

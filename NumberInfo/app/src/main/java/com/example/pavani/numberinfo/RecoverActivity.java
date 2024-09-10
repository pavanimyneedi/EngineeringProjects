package com.example.pavani.numberinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverActivity extends AppCompatActivity {

    EditText recoveryMail;
    Button resetButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        recoveryMail=(EditText)findViewById(R.id.recover_email_id);
        resetButton=(Button)findViewById(R.id.reset_pwd_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()){
                    if (recoveryMail.getText().toString().isEmpty()){
                        Toast.makeText(RecoverActivity.this, getResources().getString(R.string.please_enter_your_email_address), Toast.LENGTH_SHORT).show();
                    }
                    else {

                        firebaseAuth.sendPasswordResetEmail(recoveryMail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(RecoverActivity.this, getResources().getString(R.string.verification_link_has_been_sent), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }




                }
                else {

                    Toast.makeText(RecoverActivity.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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

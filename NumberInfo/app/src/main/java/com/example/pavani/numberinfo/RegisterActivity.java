package com.example.pavani.numberinfo;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    AdView adView;

    FirebaseAuth mAuth;

    EditText reg_email,reg_password;

    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        adView=(AdView)findViewById(R.id.add_id);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        register=(Button)findViewById(R.id.reg_btn_id);


        mAuth=FirebaseAuth.getInstance();

        reg_email=(EditText)findViewById(R.id.reg_email);
        reg_password=(EditText)findViewById(R.id.reg_pass);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()){
                    if (reg_email.getText().toString().isEmpty() || reg_password.getText().toString().isEmpty()){

                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.email_or_password_isempty), Toast.LENGTH_SHORT).show();
                    }
                    else {

                        mAuth.createUserWithEmailAndPassword(reg_email.getText().toString(),reg_password.getText().toString())
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){


                                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.successfully_registered), Toast.LENGTH_SHORT).show();
                                            reg_email.setText("");
                                            reg_password.setText("");
                                            mAuth.signOut();
                                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                        else {
                                            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }


                }
                else {
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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

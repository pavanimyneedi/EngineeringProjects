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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText loginEt,passwordEt;

    Button loginButton,registerButton;
    TextView forgotTv;
    FirebaseAuth mAuth;

    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=(Button)findViewById(R.id.login_id);
        registerButton=(Button)findViewById(R.id.reg_main_btn);
        forgotTv=(TextView)findViewById(R.id.forgot_id);

        user=FirebaseAuth.getInstance().getCurrentUser();

        if (user!= null){
            Intent intent=new Intent(MainActivity.this,ContactListActivity.class);
            intent.putExtra(getResources().getString(R.string.usrId),user.getUid());
            startActivity(intent);
            finish();
        } else {
            loginEt=(EditText)findViewById(R.id.login_activity_mail_id);
            passwordEt=(EditText)findViewById(R.id.login_activity_pwd);



            mAuth=FirebaseAuth.getInstance();


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected()){
                        if (loginEt.getText().toString().isEmpty() || passwordEt.getText().toString().isEmpty()){

                            Toast.makeText(MainActivity.this, getResources().getString(R.string.email_or_password_isempty), Toast.LENGTH_SHORT).show();
                        }
                        else {

                            mAuth.signInWithEmailAndPassword(loginEt.getText().toString(),passwordEt.getText().toString())
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){

                                                user = FirebaseAuth.getInstance().getCurrentUser();

                                                Intent i=new Intent(MainActivity.this,ContactListActivity.class);
                                                i.putExtra(getResources().getString(R.string.usrId),user.getUid());
                                                startActivity(i);
                                                finish();

                                            } else{

                                                Toast.makeText(MainActivity.this,getResources().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                                            }
                                        }


                                    });

                        }



                    }
                    else {

                        Toast.makeText(MainActivity.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                    }



                    }



            });
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(i);

                }
            });
            forgotTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(MainActivity.this,RecoverActivity.class);
                    startActivity(i);
                }
            });



        }



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

package com.example.pavani.numberinfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ContactDetailActivity extends AppCompatActivity {

    Button call;

    TextView contactDetailName,contactDetailNumber;

    String name,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        contactDetailName=(TextView)findViewById(R.id.contact_deatail_activity_name);
        contactDetailNumber=(TextView)findViewById(R.id.contact_detail_activity_number);
        call=(Button)findViewById(R.id.call);

        name=getIntent().getStringExtra(getResources().getString(R.string.name));
        number=getIntent().getStringExtra(getResources().getString(R.string.number));

        contactDetailName.setText(name);
        contactDetailNumber.setText(number);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telNum="tel:"+number;
                Toast.makeText(ContactDetailActivity.this, number, Toast.LENGTH_SHORT).show();
                Uri uri=Uri.parse(telNum);
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contact_detail_widget, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.id_widget){

            WidgetService.widget(ContactDetailActivity.this,name+"\n"+number);
            Toast.makeText(this, getResources().getString(R.string.added_widget), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}

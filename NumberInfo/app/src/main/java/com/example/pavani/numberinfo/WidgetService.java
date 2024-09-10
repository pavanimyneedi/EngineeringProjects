package com.example.pavani.numberinfo;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WidgetService extends IntentService {

     static String s="puppy";

     static String s1="bunny";


    public WidgetService() {
        super("widget");
    }

    static void widget(Context context,String s2){

        Intent intent=new Intent(context,WidgetService.class);
        intent.putExtra("data",s2);
        intent.setAction(s1);
        context.startService(intent);


    }

    static void service(Context context){
        Intent intent=new Intent(context,WidgetService.class);
        intent.setAction(s);
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent!= null){
            String string=intent.getAction();

            if (string.equals(s)){

                String string1="please select contact";

                AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
                int[] appWidgetId=appWidgetManager.getAppWidgetIds(new ComponentName(this,WidgetActivity.class));
                WidgetActivity.setWidget(WidgetService.this,appWidgetManager,appWidgetId,string1);

            }
            else if (string.equals(s1)){

                String s3=intent.getStringExtra(getResources().getString(R.string.data));

                AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
                int[] appWidgetId=appWidgetManager.getAppWidgetIds(new ComponentName(this,WidgetActivity.class));
                WidgetActivity.setWidget(WidgetService.this,appWidgetManager,appWidgetId,s3);


            }

        }


    }
}

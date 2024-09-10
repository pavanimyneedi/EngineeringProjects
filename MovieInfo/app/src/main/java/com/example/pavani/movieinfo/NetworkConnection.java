package com.example.pavani.movieinfo;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pavani on 26/5/18.
 */

public class NetworkConnection {

    public static URL BuildUrl(String[] strings){
        Uri builtUri=Uri.parse(strings[0]);
        URL url=null;
        try {
            url=new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String HttpResponse(URL url){

        HttpURLConnection httpURLConnection=null;
        try {
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                builder.append(line).append("\n");
            }
            inputStream.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

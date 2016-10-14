package com.example.starwarsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MovieDetailScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);   // Does nothing?
        setTheme(R.style.DetailTheme);  // Ok this works...
        setContentView(R.layout.activity_movie_detail_screen);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LandingScreen.EXTRA_MESSAGE);
        //System.out.println(message);


        try {
            String info = new SwapiCommunicator().execute("http://swapi.co/api/films/1/").get();
            System.out.println(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

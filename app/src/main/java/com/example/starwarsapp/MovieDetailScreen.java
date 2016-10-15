package com.example.starwarsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        String urlString = "";

        switch(message){
            case "IV – A New Hope":
                urlString = "http://swapi.co/api/films/1/";
                break;
            case "V – The Empire Strikes Back":
                urlString = "http://swapi.co/api/films/2/";
                break;
            case "VI – Return of the Jedi":
                urlString = "http://swapi.co/api/films/3/";
                break;
            case "I – The Phantom Menace":
                urlString = "http://swapi.co/api/films/4/";
                break;
            case "II – Attack of the Clones":
                urlString = "http://swapi.co/api/films/5/";
                break;
            case "III – Revenge of the Sith":
                urlString = "http://swapi.co/api/films/6/";
                break;
            case "VII – The Force Awakens":
                urlString = "http://swapi.co/api/films/7/";
                break;
            default:
                urlString = "Error";
                break;
        }

        try {
            String info = new SwapiCommunicator().execute(urlString).get();
            JSONObject parentObject = new JSONObject(info);

            String title = extractTitle(parentObject);
            String release = extractRelease(parentObject);
            String crawl = extractCrawl(parentObject);
            String[] characters = extractCharacters(parentObject);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractTitle(JSONObject json) throws JSONException {
        String title = json.getString("title");
        return title;
    }

    private String extractRelease(JSONObject json) throws JSONException {
        String release = json.getString("release_date");
        return release;
    }

    private String extractCrawl(JSONObject json) throws JSONException {
        String crawl = json.getString("opening_crawl");
        return crawl;
    }

    private String[] extractCharacters(JSONObject json) throws JSONException, ExecutionException, InterruptedException {
        JSONArray chars = json.getJSONArray("characters");
        String[] characters = new String[chars.length()];

        for (int i = 0; i < chars.length(); i++){
            String characterURL = chars.getString(i);
            String characterInfo = new SwapiCommunicator().execute(characterURL).get();
            JSONObject obj = new JSONObject(characterInfo);
            String name = obj.getString("name");
            characters[i] = name;
            System.out.println("Setting characters[" + i + "] to equal " + name);
        }
        return characters;
    }
}

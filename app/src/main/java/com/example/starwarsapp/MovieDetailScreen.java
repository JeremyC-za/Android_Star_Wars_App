package com.example.starwarsapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
            // Getting all the info and storing it as a JSON object
            String info = new SwapiCommunicator().execute(urlString).get();
            JSONObject parentObject = new JSONObject(info);
            Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Starjedi.ttf");  // custom pretty star wars font

            // Extracting the title and displaying it
            String title = extractTitle(parentObject).toLowerCase();  // upper case characters messes up the font
            TextView movieTitle = (TextView) findViewById(R.id.titleView);
            movieTitle.setTypeface(custom_font);
            movieTitle.setText(title);

            // Extracting the release date and displaying it
            String release = extractRelease(parentObject).toLowerCase();
            TextView releaseDate = (TextView) findViewById(R.id.releaseView);
            releaseDate.setTypeface(custom_font);
            releaseDate.setText(release);

            // Extracting the characters and displaying them... this slows down the app
            String characters = extractCharacters(parentObject);
            TextView allCharacters = (TextView) findViewById(R.id.characterView);
            allCharacters.setTypeface(custom_font);
            allCharacters.setText(characters);

            // Extracting and displaying the crawl text
            String crawl = extractCrawl(parentObject).toLowerCase();
            TextView crawlText = (TextView) findViewById(R.id.crawlView);
            crawlText.setTypeface(custom_font);
            crawlText.setText(crawl);

            final ScrollView scrView = (ScrollView)findViewById(R.id.ScrollView);

            final CountDownTimer scroller = new CountDownTimer(200000, 20) {
                public void onTick(long millisUntilFinished) {
                    scrView.smoothScrollBy(0, 2); // Slowly scrolls the screen downwards
//                    if (scrView.getScrollY() >= scrView.getBottom()){    // for some reason scrView.getBottom() sometimes == 0...
//                        System.out.println("scrView.getBottom() = " + scrView.getBottom());
//                        cancel();  // so this probably would be more efficient... but for some reason it works sometimes, and doesn't work at other times
//                    }
                }
                public void onFinish() {}
            };
            scroller.start();

            RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_movie_detail_screen);
            rl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    scroller.cancel();  // Stops scrolling if the user interacts with the screen
                    return false;
                }
            });

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

    private String extractCharacters(JSONObject json) throws JSONException, ExecutionException, InterruptedException {
        JSONArray chars = json.getJSONArray("characters");
        String characters = "";

        for (int i = 0; i < chars.length(); i++){
            String characterURL = chars.getString(i);
            String characterInfo = new SwapiCommunicator().execute(characterURL).get();
            JSONObject obj = new JSONObject(characterInfo);
            String name = obj.getString("name").toLowerCase();
            characters += name + "\n";
        }
        return characters;
    }
}

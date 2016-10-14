package com.example.starwarsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Context;

public class LandingScreen extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.starwarsapp.MESSAGE";
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        ctx = this;

        String[] allMovies = {"IV – A New Hope", "V – The Empire Strikes Back",
                "VI – Return of the Jedi", "I – The Phantom Menace",
                "II – Attack of the Clones", "III – Revenge of the Sith",
                "VII – The Force Awakens"};

        final ListAdapter movieAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, allMovies);

        ListView movieList = (ListView) findViewById(R.id.movie_list);
        movieList.setAdapter(movieAdapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieSelected = String.valueOf(movieAdapter.getItem(position));

                Intent intent = new Intent(ctx, MovieDetailScreen.class);
                intent.putExtra(EXTRA_MESSAGE, movieSelected);
                startActivity(intent);
            }
        });
    }
}

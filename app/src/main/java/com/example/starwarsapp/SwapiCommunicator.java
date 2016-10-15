package com.example.starwarsapp;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jeremy on 2016/10/15.
 */

public class SwapiCommunicator extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
                return stringBuilder.toString();
            }
            finally {
                connection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR:" + e);
            return null;
        }

    }
}


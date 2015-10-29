package com.yang.luke.redditreaderdemo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by luke on 10/28/2015.
 *
 * makes a http connection to the giving url and return the content of the url in a string
 *
 * use this like this:
 * String rawData = new ReadJSONTask().execute("https://www.reddit.com/r/gadgets/.json").get();
 */
public class ReadJSONTask extends AsyncTask<String, String, String> {
    private static final String P1TAG = "VeryImportantMessage";

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        URL url = null;
        try {
            url = new URL(params[0]);
            // make http connection
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.i(P1TAG, "Connection to "+url+" successful");
            // store content into a string
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine())!=null){
                buffer.append(line);
            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            Log.i(P1TAG, "Malformed url exception : " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(P1TAG, "IO exception : " + e.toString());
            e.printStackTrace();
        } finally{
            if (connection != null)
                connection.disconnect();
            try {
                if (connection != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //delegate.processFinish(result);
        super.onPostExecute(result);
    }
}

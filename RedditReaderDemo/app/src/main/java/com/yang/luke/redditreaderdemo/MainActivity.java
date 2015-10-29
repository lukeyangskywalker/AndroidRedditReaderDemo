package com.yang.luke.redditreaderdemo;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by luke on 10/28/2015.
 *
 * the main activity
 */

public class MainActivity extends Activity {
    private static final String P1TAG = "VeryImportantMessage";

    ListView lv;
    Context context;

    List<RedditPost> list = new ArrayList<RedditPost>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPosts();
    }

    /**
     * fetch reddit posts and populate the view
     */
    private void fetchPosts(){

        RedditPostParser parser = new RedditPostParser();
        try {
            list = parser.getPosts();
            context = this;
            lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new RedditPostAdapter(this, list));
        } catch (ExecutionException e) {
            Log.i(P1TAG, "Execution exception : " + e.toString());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.i(P1TAG, "Interrupted exception : " + e.toString());
            e.printStackTrace();
        }
    }

        /*
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //String s = new ReadJSONTask().execute("https://www.reddit.com/r/gadgets/.json").get();
                    RedditPostParser parser = new RedditPostParser();
                    parser.getPosts();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        */



}

    

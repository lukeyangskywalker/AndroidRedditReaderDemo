package com.yang.luke.redditreaderdemo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Parses the JSON into a list of RedditPosts
 * Created by luke on 10/27/2015.
 *
 * RedditPostParser by default gets the posts from "all"
 * unless specify using RedditPostParser(String subReddit)
 */
public class RedditPostParser {
    private static final String P1TAG = "VeryImportantMessage";

    // default json reddit url
    private final String REDDIT_JSON_URL = "http://www.reddit.com/r/";
    private final String REDDIT_JSON_SUFFIX = "/.json";
    private final String REDDIT_JSON_AFTER ="?after=";
    private String defaultSubReddit = "all";
    private String after = "";

    private String mySubRedditURL = "";
    RedditPostParser(String subReddit){
        //TODO: check if the subReddit is legit
        if (subReddit != null){
            defaultSubReddit = subReddit;
        }
        mySubRedditURL = REDDIT_JSON_URL + defaultSubReddit + REDDIT_JSON_SUFFIX;
    }
    RedditPostParser(){
        mySubRedditURL = REDDIT_JSON_URL + defaultSubReddit + REDDIT_JSON_SUFFIX;
    }

    /**
     * Get reddit posts in json and return the list of RedditPosts
     *
     */
    List<RedditPost> getPosts() throws ExecutionException, InterruptedException {
        // get the content of the url in a string
        String rawData = new ReadJSONTask().execute(mySubRedditURL).get();
        List<RedditPost> list = new ArrayList<RedditPost>();

        // parse the string using JSONObject
        try{
            JSONObject data = new JSONObject(rawData).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");

            // used when loading more posts, it will start after this post
            after=data.getString("after");
            // convert each JSONObject into a RedditPost class and store into the list
            for (int i = 0; i < children.length(); i++){
                JSONObject temp = children.getJSONObject(i).getJSONObject("data");
                RedditPost rp = new RedditPost();
                rp.setAuthor(temp.optString("author"));
                rp.setTitle(temp.optString("title"));

                // get created date
                String date = temp.optString("created_utc");
                Date time = new java.util.Date(Double.valueOf(date).longValue()*1000);
                rp.setCreationTime(time);

                // get thumbnail
                rp.setImgThumbnail(temp.optString("thumbnail"));
                if (rp != null){
                    list.add(rp);
                }
            }
        }catch(Exception e){
            Log.i(P1TAG, "Exception at getPosts : " + e.toString() );
        }
        Log.i(P1TAG, "List length is : " + list.size() );
        return list;
    }

    // loading more posts after String after
    List<RedditPost> loadMorePosts() throws ExecutionException, InterruptedException {
        mySubRedditURL = mySubRedditURL + REDDIT_JSON_AFTER + after;
        return getPosts();
    }


}


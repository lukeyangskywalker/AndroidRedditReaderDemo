package com.yang.luke.redditreaderdemo;

import java.util.Date;

/**
 * This is a class of a Reddit post details
 * Created by luke on 10/27/2015.
 */
public class RedditPost {
    private String title;
    private String author;
    private Date creationTime;
    private String imgThumbnail;

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public Date getCreationTime(){
        return creationTime;
    }
    public String getImgThumbnail(){
        return imgThumbnail;
    }

    public void setTitle(String t){
        title = t;
    }
    public void setAuthor(String a){
        author = a;
    }
    public void setCreationTime(Date t){
        creationTime = t;
    }
    public void setImgThumbnail(String i){
        imgThumbnail = i;
    }

}

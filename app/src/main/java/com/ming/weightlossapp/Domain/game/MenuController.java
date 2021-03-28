package com.ming.weightlossapp.Domain.game;

import com.ming.weightlossapp.TechnicalServices.TwitterAPI.TwitterAPI;

import twitter4j.TwitterException;

public class MenuController {


    public MenuController(){

    }

    public void postTweet(String message) throws TwitterException {
        TwitterAPI twitterAPI=new TwitterAPI();
        twitterAPI.postTweet(message);
    }
}

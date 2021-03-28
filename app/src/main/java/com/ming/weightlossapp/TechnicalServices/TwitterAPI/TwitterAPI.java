package com.ming.weightlossapp.TechnicalServices.TwitterAPI;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI {

    static String consumerKey = "gbSdUOCLNWr9FCQ14c2vlEF2t";
    static String consumerSecret = "QBgH6TgCRLL0S5LupIwlEvbE3cqNZVMtWmH9GGxoq3kOvxJ74f";
    static String twitterToken = "978010248307814400-bJ2szl9L7CIZkarCybjhvB2rHwQpB6w";
    static String twitterSecret = "V0jIU93gwpQmMX3X18PDeak4HfP7xz8awTBIuIws69VcB";

    public TwitterAPI(){

    }

    public void postTweet(String message) throws TwitterException {
        ConfigurationBuilder builder=new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration=builder.build();
        TwitterFactory factory=new TwitterFactory(configuration);
        Twitter twitter=factory.getInstance();
        AccessToken accessToken=new AccessToken(twitterToken,twitterSecret);
        twitter.setOAuthAccessToken(accessToken);
        twitter.updateStatus(message);
    }
}

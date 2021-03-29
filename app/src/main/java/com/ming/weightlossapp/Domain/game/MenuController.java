package com.ming.weightlossapp.Domain.game;

import com.ming.weightlossapp.TechnicalServices.PersistentData.Game;
import com.ming.weightlossapp.TechnicalServices.PersistentData.GameDAO;
import com.ming.weightlossapp.TechnicalServices.TwitterAPI.TwitterAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.TwitterException;

public class MenuController {


    public MenuController(){

    }

    public void postTweet(String message) throws TwitterException {
        TwitterAPI twitterAPI=new TwitterAPI();
        twitterAPI.postTweet(message);
    }

    public static List<Map<String,Object>> getGameList(){
        List<Game> gameList=new ArrayList<>();
        GameDAO dao=new GameDAO();
        gameList=dao.getGameList();
        if(gameList!=null){
            List<Map<String,Object>> theGameList=new ArrayList<>();
            for(Game game : gameList){
                Map<String,Object> attributes=new HashMap<String, Object>();
                attributes.put("gameId",game.getGameId());
                attributes.put("playerNumber",game.getPlayNumber());
                attributes.put("bmi",game.getBmi());

                theGameList.add(attributes);
            }



            return theGameList;
        }
        return null;
    }
}

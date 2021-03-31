package com.ming.weightlossapp.Domain.game;

import android.util.Log;

import com.ming.weightlossapp.TechnicalServices.PersistentData.Game;
import com.ming.weightlossapp.TechnicalServices.PersistentData.GameDAO;
import com.ming.weightlossapp.TechnicalServices.PersistentData.User;
import com.ming.weightlossapp.TechnicalServices.PersistentData.UserDAO;
import com.ming.weightlossapp.TechnicalServices.TwitterAPI.TwitterAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    public static int joinGame(int gameId){
        int ok=0;
        GameDAO gameDAO=new GameDAO();
        ok=gameDAO.joinGame(gameId);
        return ok;
    }

    public static int quitGame(int gameId){
        int ok=0;
        GameDAO dao=new GameDAO();
        ok=dao.quitGame(gameId);
        Log.i("GameId,ok",gameId+" "+ok);
        return ok;
    }

    public static int createGame(int uid,double bmi){
        Game game=new Game();
        game.setPlayNumber(1);
        game.setBmi(bmi);
        game.setHolsterId(uid);
        GameDAO dao=new GameDAO();

        int gameId=-1;
        int ok=dao.createNewGame(game);
        if(ok!=0){
            gameId=dao.getGameByHosterId(uid);
        }
        return gameId;
    }

    public static int getPlayerNumber(int gameId){
        GameDAO dao=new GameDAO();
        int gameNumber=dao.getPlayerNumber(gameId);
        return gameNumber;
    }

    public static void deleteGame(int gameId){
        GameDAO dao=new GameDAO();
        dao.deleteGame(gameId);
    }

    public static List<Map<String,Object>> getPlayerList(int gameId){
        UserDAO dao=new UserDAO();
        List<User> list=dao.getPlayerList(gameId);

        if(list !=null){
            Collections.sort(list, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return  (int) (100*o2.getWeightChange()-100*o1.getWeightChange());
                }
            });

            List<Map<String,Object>> playerList=new ArrayList<>();

            for(User user:list){
                Map<String,Object> map=new HashMap<>();
                map.put("uid",user.getUid());
                map.put("bmi",user.getBMI());
                map.put("weightChange",user.getWeightChange());

                playerList.add(map);
            }

            return playerList;
        }
       return null;
    }
}

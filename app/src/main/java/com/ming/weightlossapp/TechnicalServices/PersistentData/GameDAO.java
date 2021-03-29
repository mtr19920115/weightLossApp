package com.ming.weightlossapp.TechnicalServices.PersistentData;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class GameDAO extends DatabaseHelper {
    public int createNewGame(Game game){
       int ok=0;
       try {
           getConnection();
           String sql="INSERT INTO game (userNumber,BMI) VALUES (?,?)";
           pstmt=(PreparedStatement) conn.prepareStatement(sql);
           pstmt.setInt(1,game.getPlayNumber());
           pstmt.setDouble(2,game.getBmi());
           ok=pstmt.executeUpdate();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           closeAll();
       }
       return ok;
    }

    public List<Game> getGameList(){
        List<Game> gameList=new ArrayList<>();

        try {
            getConnection();
            String sql="SELECT * FROM game";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            while(rs.next()){
                Game game=new Game();
                game.setGameId(rs.getInt("gameId"));
                game.setPlayNumber(rs.getInt("userNumber"));
                game.setBmi(rs.getDouble("BMI"));
                gameList.add(game);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }



        return gameList;
    }

    public int joinGame(int gameId){
        int ok=0;
        try {
            getConnection();
            String sql="UPDATE game SET userNumber=userNumber+1 WHERE gameId=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,gameId);
            ok=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }

        return ok;
    }

    public int quitGame(int gameId){
        int ok=0;
        try {
            getConnection();
            String sql="UPDATE game SET userNumber=userNumber-1 WHERE gameId=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,gameId);
            ok=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }

        return ok;
    }

    public int getPlayerNumber(int gameId){
        int playerNumber=0;
        try {
            getConnection();
            String sql="SELECT userNumber FROM game WHERE gameId=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,gameId);
            rs=pstmt.executeQuery();
            if(rs.next()){
                playerNumber=rs.getInt("userNumber");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return playerNumber;
    }
}

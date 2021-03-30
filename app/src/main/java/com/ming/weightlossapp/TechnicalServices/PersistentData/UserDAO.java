package com.ming.weightlossapp.TechnicalServices.PersistentData;

import android.util.Log;

import java.sql.PreparedStatement;

public class UserDAO extends DatabaseHelper {


    public User getUser(String userName){
        User user=null;
        try{
            getConnection();
            String sql="SELECT * FROM user WHERE userName=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,userName);
            rs=pstmt.executeQuery();
            if(rs.next()){
                user=new User();
                user.setUid(rs.getInt("uid"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("passWord"));
                user.setEmail(rs.getString("email"));
                user.setTwitterAccount(rs.getString("twitterAccount"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setBMI(rs.getDouble("BMI"));
                user.setJoinedGame(rs.getBoolean("joinedGame"));
                user.setJoinedGameId(rs.getInt("joinedGameId"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }

        return user;
    }

    public User getUserById(int id){
        User user=null;
        try{
            getConnection();
            String sql="SELECT * FROM user WHERE uid=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs=pstmt.executeQuery();
            if(rs.next()){
                user=new User();
                user.setUid(rs.getInt("uid"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("passWord"));
                user.setEmail(rs.getString("email"));
                user.setTwitterAccount(rs.getString("twitterAccount"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setBMI(rs.getDouble("BMI"));
                user.setJoinedGame(rs.getBoolean("joinedGame"));
                user.setJoinedGameId(rs.getInt("joinedGameId"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }

        return user;
    }

    public int addUser(User user){
        int iRow=0;
        try {
            getConnection();
            String sql="INSERT INTO user(userName,passWord,email,twitterAccount" +
                    ",height,weight,BMI,joinedGame,joinedGameId)" +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,user.getUserName());
            pstmt.setString(2,user.getPassWord());
            pstmt.setString(3,user.getEmail());
            pstmt.setString(4,user.getTwitterAccount());
            pstmt.setDouble(5,user.getHeight());
            pstmt.setDouble(6,user.getWeight());
            pstmt.setDouble(7,user.getBMI());
            pstmt.setBoolean(8,user.isJoinedGame());
            pstmt.setInt(9,user.getJoinedGameId());
            iRow=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    public boolean checkExists(String userName){
        boolean exists=false;
        try {
            getConnection();
            String sql="SELECT * FROM user WHERE userName=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,userName);
            rs=pstmt.executeQuery();
            if(rs.next()){
                exists=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return exists;
    }

    public int updateUserWeight(User user){
        int iRow=0;
        try {
            getConnection();
            String sql="UPDATE user SET weight=?, BMI=? WHERE uid=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setDouble(1,user.getWeight());
            pstmt.setDouble(2,user.getBMI());
            pstmt.setInt(3,user.getUid());
            iRow=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    public int updateJoinedGame(User user){
        int iRow=0;
        try {
            getConnection();
            String sql="UPDATE user SET joinedGame=?, joinedGameId=? WHERE uid=?";
           // Log.i("uid,gameId,joinedGame: ",String.valueOf(user.getUid())+String.valueOf(user.getJoinedGameId())+String.valueOf(user.isJoinedGame()));
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setBoolean(1,user.isJoinedGame());
            pstmt.setInt(2,user.getJoinedGameId());
            pstmt.setInt(3,user.getUid());
            iRow=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    public int doQuit(int uid){
        int ok=0;
        try{
            getConnection();
            String sql="UPDATE user SET joinedGame=?,joinedGameId=? WHERE uid=?";
            pstmt=(PreparedStatement) conn.prepareStatement(sql);
            pstmt.setBoolean(1,false);
            pstmt.setInt(2,-1);
            pstmt.setInt(3,uid);
            ok=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }

        return ok;
    }
}

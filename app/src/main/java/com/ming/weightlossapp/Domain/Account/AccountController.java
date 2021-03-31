package com.ming.weightlossapp.Domain.Account;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ming.weightlossapp.TechnicalServices.PersistentData.User;
import com.ming.weightlossapp.TechnicalServices.PersistentData.UserDAO;

import java.util.List;
import java.util.Map;

public class AccountController {




    public int doSignUp(User user){
        UserDAO dao=new UserDAO();
        if(dao.checkExists(user.getUserName())){
            return 1;
        }else{
            final int ok=dao.addUser(user);
            if(ok!=0){
                return 2;
            }else{
                return 3;
            }
        }
    }

    public int doLogin(String userName,String passWord,Context context){
        UserDAO dao=new UserDAO();
        User user=dao.getUser(userName);
        if(user!=null){
            if(passWord.equals(user.getPassWord())){
                SharedPreferences inputData=context.getSharedPreferences("inputData", Context.MODE_PRIVATE);;

                SharedPreferences.Editor editor=inputData.edit();
                editor.putInt("uid",user.getUid());
                editor.putString("userName",user.getUserName());
                editor.putString("passWord",user.getPassWord());
                editor.putString("email",user.getEmail());
                editor.putString("twitterAccount",user.getTwitterAccount());
                editor.putString("height",String.valueOf(user.getHeight()));
                editor.putString("weight",String.valueOf(user.getWeight()));
                editor.putString("bmi",String.valueOf(user.getBMI()));
                editor.putBoolean("joinedGame",user.isJoinedGame());
                editor.putInt("joinedGameId",user.getJoinedGameId());
                editor.commit();
                return 1;
            }else{
                return 0;
            }
        }else{
            return 2;
        }

    }

    public static int joinGame(int uid,int gameId){


        UserDAO dao=new UserDAO();
        User user=new User();
        user.setUid(uid);
        user.setJoinedGame(true);
        user.setJoinedGameId(gameId);
        int userOk=dao.updateJoinedGame(user);
        Log.i("userOK: ",String.valueOf(userOk));
        return userOk;
    }

    public static int quitGame(int uid){
        UserDAO dao=new UserDAO();

        int ok=0;
        ok=dao.doQuit(uid);


        return ok;
    }

}

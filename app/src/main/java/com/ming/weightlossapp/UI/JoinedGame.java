package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.Account.AccountController;
import com.ming.weightlossapp.Domain.game.MenuController;
import com.ming.weightlossapp.R;

import java.util.Random;

import twitter4j.TwitterException;

public class JoinedGame extends AppCompatActivity {

    ListView userList;

    Button quit;
    Button back;
    Button updateWeight;

    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_game);

        initViews();
        initData();
        addListener();
    }

    private void initViews(){
        userList=(ListView) findViewById(R.id.jgl_userList);
        quit=(Button) findViewById(R.id.jgbt_quit);
        back=(Button) findViewById(R.id.jgbt_back);
        updateWeight=(Button) findViewById(R.id.jgbt_updateWeight);
        mainHandler=new Handler(getMainLooper());
    }

    private void initData(){

    }

    private void addListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    doQuit();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });

        updateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpdateWeight();
            }
        });
    }

    private void doUpdateWeight(){
        weightDialog dialog=new weightDialog(JoinedGame.this);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
    }

    private void doQuit() throws TwitterException {

        SharedPreferences inputData=getApplicationContext().getSharedPreferences("inputData",MODE_PRIVATE);
        SharedPreferences.Editor editor=inputData.edit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int gameOk=0;
                int userOk=0;
                gameOk=MenuController.quitGame(inputData.getInt("joinedGameId",-1));
                userOk = AccountController.quitGame(inputData.getInt("uid",0));
                if(gameOk!=0&&userOk!=0){

                    int playerNumber=MenuController.getPlayerNumber(inputData.getInt("joinedGameId",0));

                    if(playerNumber==0){
                        MenuController.deleteGame(inputData.getInt("joinedGameId",-1));
                    }

                    MenuController controller=new MenuController();
                    try {
                        Random rand=new Random();
                        controller.postTweet(new String("Test for post Tweet for Weight loss app"+rand.nextInt(1000)+1));
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            editor.putBoolean("joinedGame",false);
                            editor.putInt("joinedGameId",-1);
                            editor.commit();
                          Toast.makeText(JoinedGame.this,"quit game succeed",Toast.LENGTH_SHORT).show();
                          finish();
                        }
                    });
                }else{
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                          Toast.makeText(JoinedGame.this,"quit game failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
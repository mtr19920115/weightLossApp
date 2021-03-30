package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.ming.weightlossapp.TechnicalServices.PersistentData.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameList extends AppCompatActivity {

    ListView gameList;
    Button back;
    Button refresh;
    Button startNewGame;

    Handler mainHandler;

    SharedPreferences inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        initView();
        initData();
        addListener();
    }

    private void initView(){
        gameList=(ListView) findViewById(R.id.gll_gameList);
        back=(Button) findViewById(R.id.glbt_back);
        refresh=(Button) findViewById(R.id.glbt_refresh);
        startNewGame=(Button) findViewById(R.id.glbt_startNewGame);

        inputData=getApplicationContext().getSharedPreferences("inputData",MODE_PRIVATE);

        mainHandler=new Handler(getMainLooper());
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String,Object>> theGameList = MenuController.getGameList();
                if(theGameList!=null){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            GameListAdapter adapter=new GameListAdapter(GameList.this);
                            adapter.setList(theGameList);
                            gameList.setAdapter(adapter);
                        }
                    });
                }else{
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameList.this,"Game list loading failed",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }
            }
        }).start();

    }

    private void addListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputData.getBoolean("joinedGame",false)){
                    Toast.makeText(GameList.this,"You already joined a game",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int gameId= MenuController.createGame(inputData.getInt("uid",0),Double.parseDouble(inputData.getString("bmi","0")));
                            if(gameId!=-1){
                                int userOK= AccountController.joinGame(inputData.getInt("uid",0),gameId);
                                if(userOK!=0){
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences.Editor editor=inputData.edit();
                                            editor.putBoolean("joinedGame",true);
                                            editor.putInt("joinedGameId",gameId);
                                            editor.commit();
                                            Intent intent=new Intent(GameList.this,JoinedGame.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }else{
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GameList.this,"Create game failed",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                }
                            }else{
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GameList.this,"Create game failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }
}
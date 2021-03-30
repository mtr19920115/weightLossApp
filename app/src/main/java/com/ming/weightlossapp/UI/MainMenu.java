package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.Account.AccountController;
import com.ming.weightlossapp.Domain.game.MenuController;
import com.ming.weightlossapp.R;

public class MainMenu extends AppCompatActivity {

    TextView userName;
    TextView userId;
    TextView currentWeight;
    TextView currentBMI;
    TextView height;

    Button gameList;
    Button joinedGame;
    Button inputWeight;
    Button logOut;
    Button createGame;

    SharedPreferences inputData;

    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initViews();
        initData();
        addListener();
    }

    private void initViews(){
        userName=(TextView) findViewById(R.id.mmT_userName);
        userId=(TextView) findViewById(R.id.mmT_userId);
        currentWeight=(TextView) findViewById(R.id.mmT_userWeight);
        currentBMI=(TextView) findViewById(R.id.mmT_userBMI);
        height=(TextView) findViewById(R.id.mmT_Height);

        gameList=(Button) findViewById(R.id.mmbt_gameList);
        joinedGame=(Button) findViewById(R.id.mmbt_joinedGame);
        inputWeight=(Button) findViewById(R.id.mmbt_inputWeight);
        logOut=(Button) findViewById(R.id.mmbt_logout);
        createGame=(Button) findViewById(R.id.mmbt_createGame);

        mainHandler=new Handler(getMainLooper());

        if(inputData==null){
            inputData=getApplicationContext().getSharedPreferences("inputData", Context.MODE_PRIVATE);
        }
    }

    private void initData(){
        userName.setText(userName.getText()+" "+inputData.getString("userName",""));
        userId.setText(userId.getText()+" "+inputData.getInt("uid",0));
        currentWeight.setText(currentWeight.getText()+String.valueOf(inputData.getString("weight","")));
        currentBMI.setText(currentBMI.getText()+String.valueOf(inputData.getString("bmi","")));
        height.setText(height.getText()+String.valueOf(inputData.getString("height","")));
    }

    private void addListener(){
        gameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toGameList();
            }
        });

        joinedGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toJoinedGame();
            }
        });

        inputWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toInputWeight();
            }
        });

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCreate();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void toGameList(){
        Intent intent=new Intent(MainMenu.this,GameList.class);
        startActivity(intent);
    }

    private void toJoinedGame(){

        if(inputData.getBoolean("joinedGame",false)){
            Intent intent=new Intent(MainMenu.this,JoinedGame.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"You have not joined any game yet",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void toInputWeight(){

        weightDialog dialog=new weightDialog(this);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                currentWeight.setText("Current weight: "+inputData.getString("weight",""));
                currentBMI.setText("Current BMI: "+inputData.getString("bmi",""));
            }
        });
    }

    private void doCreate(){

        if(inputData.getBoolean("joinedGame",false)){
            Toast.makeText(this,"You already joined a game",Toast.LENGTH_SHORT).show();
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
                                    Intent intent=new Intent(MainMenu.this,JoinedGame.class);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainMenu.this,"Create game failed",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }
                    }else{
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainMenu.this,"Create game failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
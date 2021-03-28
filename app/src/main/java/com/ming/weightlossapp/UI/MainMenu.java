package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Intent intent=new Intent(MainMenu.this,JoinedGame.class);
        startActivity(intent);
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
        Toast.makeText(this,"created a new game",Toast.LENGTH_SHORT).show();
        toJoinedGame();
    }


}
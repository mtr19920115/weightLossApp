package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ming.weightlossapp.R;

public class GameList extends AppCompatActivity {

    ListView gameList;
    Button back;
    Button refresh;
    Button startNewGame;

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

        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameList.this,JoinedGame.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
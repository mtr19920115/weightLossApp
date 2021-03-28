package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.game.MenuController;
import com.ming.weightlossapp.R;

import java.util.Random;

import twitter4j.TwitterException;

public class JoinedGame extends AppCompatActivity {

    ListView userList;

    Button quit;
    Button back;
    Button updateWeight;

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


        new Thread(new Runnable() {
            @Override
            public void run() {
                MenuController controller=new MenuController();
                try {
                    Random rand=new Random();
                    controller.postTweet(new String("Test for post Tweet for Weight loss app"+rand.nextInt(100)+1));
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Toast.makeText(JoinedGame.this,"quit game success",Toast.LENGTH_SHORT).show();
        finish();
    }
}
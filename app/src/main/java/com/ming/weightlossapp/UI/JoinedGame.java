package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.Account.AccountController;
import com.ming.weightlossapp.Domain.PhysicalInformation.physicalInformationController;
import com.ming.weightlossapp.Domain.game.MenuController;
import com.ming.weightlossapp.R;
import com.ming.weightlossapp.TechnicalServices.PersistentData.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Random;

import twitter4j.TwitterException;

public class JoinedGame extends AppCompatActivity {

    ListView userList;

    TextView gameId;

    Button quit;
    Button back;
    Button updateWeight;

    SharedPreferences inputData;

    Handler mainHandler;

    List<Map<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_game);

        initViews();
        initData();
        addListener();
        timer.start();
    }

    private void initViews(){
        gameId=(TextView) findViewById(R.id.jgtv_gameId);
        userList=(ListView) findViewById(R.id.jgl_userList);
        quit=(Button) findViewById(R.id.jgbt_quit);
        back=(Button) findViewById(R.id.jgbt_back);
        updateWeight=(Button) findViewById(R.id.jgbt_updateWeight);
        inputData=(SharedPreferences) getApplicationContext().getSharedPreferences("inputData",MODE_PRIVATE);
        mainHandler=new Handler(getMainLooper());
    }

    private void initData(){
        //show game id
        gameId.setText(String.valueOf(inputData.getInt("joinedGameId",-1)));

        //show player list
        new Thread(new Runnable() {
            @Override
            public void run() {
                list=MenuController.getPlayerList(inputData.getInt("joinedGameId",-1));
                if(list!=null){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           PlayerListAdapter adapter=new PlayerListAdapter(JoinedGame.this);
                           adapter.setList(list);
                           userList.setAdapter(adapter);
                        }
                    });
                }else{
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           Toast.makeText(JoinedGame.this,"Player list load failed",Toast.LENGTH_SHORT).show();
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

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               doQuit();
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

                if(inputData.getBoolean("doUpdate",false)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int ok= physicalInformationController.updateWeightChange(inputData.getInt("uid",0),
                                    Double.parseDouble(inputData.getString("weight","")),Double.parseDouble(inputData.getString("lastWeight","")));

                            if(ok!=0){
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initData();
                                    }
                                });
                            }else{
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(JoinedGame.this,"weightChange update failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void doQuit() {

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

                    if(playerNumber<=0){
                        MenuController.deleteGame(inputData.getInt("joinedGameId",-1));
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

    private CountDownTimer timer=new CountDownTimer(150*1000,15*1000) {
        @Override
        public void onTick(long millisUntilFinished){

            if(list!=null){
                System.out.println("10 second timer, uid: "+String.valueOf(list.get(list.size()-1).get("uid")));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int userQuitOk=AccountController.quitGame((Integer) list.get(list.size()-1).get("uid"));
                        if(userQuitOk!=0){
                            int gameQuitOk=MenuController.quitGame(inputData.getInt("joinedGameId",-1));
                            if(gameQuitOk!=0){
                                if(MenuController.checkWin(list)){

                                    timer.cancel();

                                    User user=AccountController.getUserById((Integer) list.get(0).get("uid"));

                                    MenuController controller=new MenuController();
                                    try {
                                        controller.postTweet(new String("Congratulations, You are the winner of "+inputData.getInt("joinedGameId",-1)+
                                                " weight loss battle. @"+user.getTwitterAccount()));
                                        controller.sendMail(user.getUserName(),user.getEmail(),true);

                                    } catch (TwitterException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(JoinedGame.this,"Got winner, ID is:+"+list.get(0).get("uid"),Toast.LENGTH_SHORT).show();
                                            doQuit();
                                        }
                                    });
                                }else{
                                    if((Integer) list.get(list.size()-1).get("uid")==inputData.getInt("uid",-1)){

                                        User user=AccountController.getUserById((Integer) list.get(list.size()-1).get("uid"));
                                        try {
                                            MenuController.sendMail(user.getUserName(),user.getEmail(),false);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        mainHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                SharedPreferences.Editor editor=inputData.edit();
                                                editor.putBoolean("joinedGame",false);
                                                editor.putInt("joinedGameId",-1);
                                                editor.commit();
                                                Toast.makeText(JoinedGame.this,"You been dropped from the game",Toast.LENGTH_SHORT).show();
                                                timer.cancel();
                                                finish();
                                            }
                                        });
                                    }else{

                                        /**
                                        User user=AccountController.getUserById((Integer) list.get(list.size()-1).get("uid"));
                                        try {
                                            MenuController.sendMail(user.getUserName(),user.getEmail(),false);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                         */


                                        mainHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                User user=AccountController.getUserById((Integer) list.get(list.size()-1).get("uid"));
                                                initData();
                                            }
                                        });
                                    }

                                }
                            }else{
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(JoinedGame.this,"drop player list failed",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }else{
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(JoinedGame.this,"drop user failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

            }
        }

        @Override
        public void onFinish() {
            System.out.println("count down finished over");
            timer.cancel();
        }
    };
}
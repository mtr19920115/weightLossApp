package com.ming.weightlossapp.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.Account.AccountController;
import com.ming.weightlossapp.Domain.game.MenuController;
import com.ming.weightlossapp.R;
import com.ming.weightlossapp.TechnicalServices.PersistentData.GameDAO;
import com.ming.weightlossapp.TechnicalServices.PersistentData.User;
import com.ming.weightlossapp.TechnicalServices.PersistentData.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import static android.os.Looper.getMainLooper;

public class GameListAdapter extends BaseAdapter {

    List<Map<String,Object>> list;
    LayoutInflater inflater;
    Context context;


    public GameListAdapter(Context context){
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.list_view_layout,null);

        TextView gameId=(TextView) view.findViewById(R.id.lvt_gameId);
        TextView playerNumber=(TextView) view.findViewById(R.id.lvt_playerNumber);
        TextView bmi=(TextView) view.findViewById(R.id.lvt_bmi);

        Button join=(Button) view.findViewById(R.id.lvbt_Join);

        Map map=list.get(position);

        gameId.setText(String .valueOf((Integer) map.get("gameId")));
        playerNumber.setText(String.valueOf((Integer) map.get("playerNumber")));
        bmi.setText(String.valueOf((Double) map.get("bmi")));

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toJoin(position);
            }
        });

        return view;
    }

    private void toJoin(int position){
        Map<String,Object> map=list.get(position);
        SharedPreferences inputData= context.getApplicationContext().getSharedPreferences("inputData", Context.MODE_PRIVATE);
        if(inputData.getBoolean("joinedGame",false)){
            Toast.makeText(context,"You already joined a game",Toast.LENGTH_SHORT).show();
            return;
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                   int gameOk=MenuController.joinGame((Integer) map.get("gameId"));
                   int userOk=AccountController.joinGame(inputData.getInt("uid",0),(Integer) map.get("gameId"));
                    if(gameOk!=0&&userOk!=0) {
                        SharedPreferences.Editor editor=inputData.edit();
                        editor.putBoolean("joinedGame",true);
                        editor.putInt("joinedGameId",((Integer) map.get("gameId")));
                        editor.commit();
                        Intent intent=new Intent(context,JoinedGame.class);
                        context.startActivity(intent);
                    }
                }
            }).start();
            }
        }

    }


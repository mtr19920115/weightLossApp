package com.ming.weightlossapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.weightlossapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Toast.makeText(context,"The gameId is: "+String.valueOf((Integer) map.get("gameId"))+"" +
                "userNumber: "+(String.valueOf((Integer) map.get("playerNumber")))+"BMI: "+(String.valueOf((Double) map.get("bmi"))),Toast.LENGTH_SHORT).show();
    }
}

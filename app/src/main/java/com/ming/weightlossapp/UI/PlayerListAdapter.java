package com.ming.weightlossapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ming.weightlossapp.R;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class PlayerListAdapter extends BaseAdapter {


    List<Map<String,Object>> list;
    LayoutInflater inflater;
    Context context;

    public PlayerListAdapter(Context context){
        this.context=context;
        this.inflater= LayoutInflater.from(context);
    }

    public void setList(List<Map<String,Object>> list){
        this.list=list;
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
        View view=inflater.inflate(R.layout.player_listview_layout,null);

        TextView playerId=(TextView) view.findViewById(R.id.pltv_playerID);
        TextView bmi=(TextView) view.findViewById(R.id.pltv_playerBMI);
        TextView weightChange=(TextView) view.findViewById(R.id.pltv_weightChange);

        Map map=list.get(position);

        playerId.setText(String.valueOf((Integer) map.get("uid")));
        bmi.setText(String.valueOf((Double) map.get("bmi")));
        weightChange.setText(String.valueOf((Double) map.get("weightChange")));

        return view;
    }
}

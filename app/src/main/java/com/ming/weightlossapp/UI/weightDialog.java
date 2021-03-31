package com.ming.weightlossapp.UI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.PhysicalInformation.physicalInformationController;
import com.ming.weightlossapp.R;
import com.ming.weightlossapp.TechnicalServices.PersistentData.User;
import com.ming.weightlossapp.TechnicalServices.PersistentData.UserDAO;


public class weightDialog extends Dialog {
    Activity context;

    Button cancel;
    Button input;

    EditText inputWeight;

    //TextView currentWeight;

    SharedPreferences inputData;

    //View.OnClickListener listener;

    Handler mainHandler;

    public weightDialog(Activity context) {
        super(context);
        this.context=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.weight_dialog);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p =getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9);
        getWindow().setAttributes(p);




        initViews();
        addListener();
    }

    private void initViews(){
        cancel=(Button) findViewById(R.id.wdbt_cancel);
        input=(Button) findViewById(R.id.wdbt_input);
        inputWeight=(EditText) findViewById(R.id.wdet_weight);
        //currentWeight=(TextView) findViewById(R.id.mmT_userWeight);
        mainHandler=new Handler(Looper.getMainLooper());

        if(inputData==null){
            inputData= context.getApplicationContext().getSharedPreferences("inputData", Context.MODE_PRIVATE);
        }
    }

    private void addListener(){
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(inputWeight.getText().toString())){
                    Toast.makeText(context,"please input current weight!!!",Toast.LENGTH_SHORT).show();
                    inputWeight.requestFocus();
                }else {
                    double bmi=physicalInformationController.getBMI(Double.parseDouble(inputWeight.getText().toString()),Double.parseDouble( inputData.getString("height","")));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           User user=physicalInformationController.updateWeight(
                                   Double.parseDouble(inputWeight.getText().toString()),bmi,inputData.getInt("uid",0));

                           if(user!=null){
                               mainHandler.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       SharedPreferences.Editor editor = inputData.edit();
                                       editor.putBoolean("doUpdate",true);
                                       editor.putString("lastWeight",inputData.getString("weight",""));
                                       editor.putString("weight", inputWeight.getText().toString());
                                       editor.putString("bmi",String.valueOf(user.getBMI()));
                                       editor.apply();
                                       Toast.makeText(context,"update weight succeed",Toast.LENGTH_SHORT).show();
                                       dismiss();
                                   }
                               });

                           }else{
                               mainHandler.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(context,"update weight failed",Toast.LENGTH_SHORT).show();
                                       return;
                                   }
                               });
                           }
                        }
                    }).start();

                }
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=inputData.edit();
                editor.putBoolean("doUpdate",false);
                cancel();
            }
        });
    }


}

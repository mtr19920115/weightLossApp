package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.Account.AccountController;
import com.ming.weightlossapp.R;

public class MainActivity extends AppCompatActivity {

    Button login;
    Button signUp;

    CheckBox rememberPassword;

    EditText userName;
    EditText passWord;

    private SharedPreferences inputData;

    Handler mainHandle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        addListener();
        initData();
    }

    private void initViews(){
        login=(Button) findViewById(R.id.lbt_login);
        signUp=(Button) findViewById(R.id.lbt_signup);
        userName=(EditText) findViewById(R.id.let_userName);
        passWord=(EditText) findViewById(R.id.let_passWord);
        rememberPassword=(CheckBox) findViewById(R.id.lcb_remeberPassword);

        mainHandle=new Handler(getMainLooper());

        if(inputData==null){
            inputData=getApplicationContext().getSharedPreferences("inputData", Context.MODE_PRIVATE);
        }
    }

    private void addListener(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignUp();
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences.Editor editor=inputData.edit();
                editor.putString("userName",userName.getText().toString());
                rememberPassword.setChecked(false);
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences.Editor editor=inputData.edit();
                editor.putString("passWord",passWord.getText().toString());
                rememberPassword.setChecked(false);
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rememberPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor=inputData.edit();
                editor.putBoolean("isRememberPassword",rememberPassword.isChecked());
                editor.commit();
            }
        });
    }

    private void initData(){
        userName.setText(inputData.getString("userName",""));
        boolean isRemember=inputData.getBoolean("isRememberPassword",false);
        if(isRemember==true){
            rememberPassword.setChecked(true);
            passWord.setText(inputData.getString("passWord",""));
        }else{
            rememberPassword.setChecked(false);
            passWord.setText("");
        }
    }

    private void doLogin(){
        if(TextUtils.isEmpty(userName.getText())){
            Toast.makeText(this,"please enter user name!!!",Toast.LENGTH_SHORT).show();
            userName.requestFocus();
            return;
        }else if(TextUtils.isEmpty(passWord.getText())){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            passWord.requestFocus();
            return;
        }else{

            AccountController controller=new AccountController();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int ok=controller.doLogin(userName.getText().toString(),passWord.getText().toString(),MainActivity.this);
                    if(ok==1){
                        mainHandle.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(MainActivity.this,MainMenu.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }else if(ok==0){
                        mainHandle.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    }else if(ok==2){
                        mainHandle.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"User Name do not exists",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    }
                }
            }).start();



        }
    }

    private void doSignUp(){
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
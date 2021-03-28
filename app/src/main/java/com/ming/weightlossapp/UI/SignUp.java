package com.ming.weightlossapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ming.weightlossapp.Domain.Account.AccountController;
import com.ming.weightlossapp.Domain.PhysicalInformation.physicalInformationController;
import com.ming.weightlossapp.R;
import com.ming.weightlossapp.TechnicalServices.PersistentData.User;
import com.ming.weightlossapp.TechnicalServices.PersistentData.UserDAO;

public class SignUp extends AppCompatActivity {

    Button signUp;
    Button cancel;

    EditText userName;
    EditText passWord;
    EditText rePassWord;
    EditText email;
    EditText twitterAccount;
    EditText weight;
    EditText height;

    Handler mainHandler;

    UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        addListener();
    }

    private void initViews(){
        signUp=(Button) findViewById(R.id.subt_signUp);
        cancel=(Button) findViewById(R.id.subt_cancel);

        userName=(EditText) findViewById(R.id.suet_userName);
        passWord=(EditText) findViewById(R.id.suet_passWord);
        rePassWord=(EditText) findViewById(R.id.suet_RePassWord);
        email=(EditText) findViewById(R.id.suet_Email);
        twitterAccount =(EditText) findViewById(R.id.suet_twitterAccount);
        height=(EditText) findViewById(R.id.suet_height);
        weight=(EditText) findViewById(R.id.suet_weight);

        mainHandler=new Handler(getMainLooper());
        dao=new UserDAO();
    }

    private void addListener(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignUp();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        rePassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!rePassWord.getText().toString().equals(passWord.getText().toString())){
                    Toast.makeText(SignUp.this,"The repeat password is different with password!!!",Toast.LENGTH_SHORT).show();
                    rePassWord.setTextColor(Color.parseColor("#FF0000"));
                }else{
                    rePassWord.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void doSignUp(){
        if(TextUtils.isEmpty(userName.getText().toString())){
            Toast.makeText(SignUp.this,"please input username!!!",Toast.LENGTH_SHORT).show();
            userName.requestFocus();
            return;
        }else if(TextUtils.isEmpty(passWord.getText().toString())){
            Toast.makeText(SignUp.this,"please input password!!!",Toast.LENGTH_SHORT).show();
            passWord.requestFocus();
            return;
        }else if(TextUtils.isEmpty(rePassWord.getText().toString())){
            Toast.makeText(SignUp.this,"please input rePassword!!!",Toast.LENGTH_SHORT).show();
            rePassWord.requestFocus();
            return;
        }else if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(SignUp.this,"please input Email Address!!!",Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }else if(TextUtils.isEmpty(twitterAccount.getText().toString())){
            Toast.makeText(SignUp.this,"please input Twitter Account!!!",Toast.LENGTH_SHORT).show();
            twitterAccount.requestFocus();
            return;
        }else if(TextUtils.isEmpty(height.getText().toString())){
            Toast.makeText(SignUp.this,"please input height!!!",Toast.LENGTH_SHORT).show();
            height.requestFocus();
            return;
        }else if(TextUtils.isEmpty(weight.getText().toString())){
            Toast.makeText(SignUp.this,"please input your weight!!!",Toast.LENGTH_SHORT).show();
            weight.requestFocus();
            return;
        }else {

            final User user=new User();
            user.setUserName(userName.getText().toString());
            user.setPassWord(passWord.getText().toString());
            user.setEmail(email.getText().toString());
            user.setTwitterAccount(twitterAccount.getText().toString());
            user.setHeight(Double.parseDouble(height.getText().toString()));
            user.setWeight(Double.parseDouble(weight.getText().toString()));
            user.setBMI(physicalInformationController.getBMI(Double.parseDouble(weight.getText().toString()),Double.parseDouble(height.getText().toString())));
            user.setJoinedGame(false);
            user.setJoinedGameId(-1);

            AccountController controller=new AccountController();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int check=controller.doSignUp(user);
                    if(check==1){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUp.this,"The user name already exists, change your user name please",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }else if(check==2){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUp.this,"Sign up succeeded",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }else if(check==3){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUp.this,"Sign up failed",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }).start();

            //Toast.makeText(SignUp.this, "Sign Up success!!!", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }
}
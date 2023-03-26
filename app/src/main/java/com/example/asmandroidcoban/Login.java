package com.example.asmandroidcoban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {

    private EditText edtUser,edtPass;
    private Button btnLogin;
    private DataBase mDataBase;
    private CheckBox chkRemember;
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);
        mDataBase = new DataBase(getApplicationContext());

        mPreferences = getSharedPreferences("remenber_acc",MODE_PRIVATE);
        edtUser.setText(mPreferences.getString("Auser",""));
        edtPass.setText(mPreferences.getString("Apass",""));
        chkRemember.setChecked(mPreferences.getBoolean("chkBox",false));

        mDataBase.querSQL("CREATE TABLE IF NOT EXISTS ReAcc(Auser nvarchar(50),Apass nvarchar(50))");
        mDataBase.querSQL("INSERT INTO ReAcc VALUES('admin','123')");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                SharedPreferences.Editor mEditor = mPreferences.edit();

                if(user.isEmpty()){
                    Toast.makeText(Login.this, "Vui lòng nhập Usernaem", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty()){
                    Toast.makeText(Login.this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                }else{

                    if (chkRemember.isChecked()){
                        mEditor.putString("Auser",user);
                        mEditor.putString("Apass",pass);
                        mEditor.putBoolean("chkBox",true);
                        mEditor.commit();
                    }else{
                        mEditor.remove("Auser");
                        mEditor.remove("Apass");
                        mEditor.remove("chkBox");
                        mEditor.commit();
                    }
                    if (chkUser(user,pass)){
                        Intent intent = new Intent(getApplicationContext(),Hub.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    private boolean chkUser(String user,String pass){
        Cursor mCursor = mDataBase.getData("SELECT * FROM ReAcc");

        while (mCursor.moveToNext()){
            if(mCursor.getString(0).equals(user) && mCursor.getString(1).equals(pass)){
                return true;
            }
        }
        return false;
    }
}
package com.example.debtnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtUser,txtPass;
    ImageButton btnLogin;
    CheckBox chkRemember;
    int cout = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addControls() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);
    }

    private void addEvents(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionLogin();
            }
        });
    }

    private void actionLogin() {

        if(txtUser.getText().toString().equals("quytrinh") && txtPass.getText().toString().equals("123"))
        {
            if (cout!=5)
                cout=5;
            txtUser.setText("");
            txtPass.setText("");
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        }
        else
        {
            cout--;
            if(cout>3) {
                Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_LONG).show();
            }
            else if(cout<=3 && cout>0)
            {
                String s = "Nhập sai. Bạn còn " + cout + " lần nhập"  ;
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
            else
            {
                finish();
            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("loginInfor",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName",txtUser.getText().toString());
        editor.putString("Pass",txtPass.getText().toString());
        editor.putBoolean("Save",chkRemember.isChecked());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("loginInfor",MODE_PRIVATE);
        if(preferences.getBoolean("Save",false))
        {
            txtPass.setText(preferences.getString("UserName",""));
            txtUser.setText(preferences.getString("Pass",""));
        }
    }
}
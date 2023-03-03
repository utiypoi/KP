package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
    }
    public void backLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void registration(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("login", login.getText().toString());
        if(password.getText().toString().length() <8){
            Toast.makeText(this,"Пароль должен содержать не менее 8 символов", Toast.LENGTH_LONG).show();
        }else {
            intent.putExtra("password", password.getText().toString());
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
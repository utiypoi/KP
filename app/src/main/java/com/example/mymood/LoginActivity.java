package com.example.mymood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextInputEditText login,password;
    private TextView signUp;
    private Button input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(intent);
                } else {

                }
            }
        };
        login = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        input = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.haveAccountTextView);
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.haveAccountTextView).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String emailReg = bundle.get("login").toString();
            String passwordReg = bundle.get("password").toString();
            login.setText(emailReg);
            password.setText(passwordReg);
            registration(emailReg, passwordReg);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            if (login.getText().toString().length() == 0 || password.getText().toString().length() == 0) {
                Toast.makeText(LoginActivity.this, "Проверьте заполнение полей", Toast.LENGTH_LONG).show();
            } else {
                signing(login.getText().toString(), password.getText().toString());
                //checkFirstStart();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        } else if (view.getId() == R.id.haveAccountTextView) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Успешная авторизация", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Неверный логин и/или пароль", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Успешная регистрация", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(LoginActivity.this, "Регистрация невозможна", Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.example.netflixmoviesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixmoviesapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private EditText emailLogin, passLogin;
    private Button loginBtn;

    private TextView registerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        Login();
    }

    private void Login() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=passLogin.getText().toString().trim();
                String user=emailLogin.getText().toString().trim();

                if(!user.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(user).matches()){
                    if(!pass.isEmpty()){
                        Auth.signInWithEmailAndPassword(user, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else{
                        passLogin.setError("Password can not be empty");
                    }
                }else if(user.isEmpty()){
                    emailLogin.setError("Email can not be empty");
                }else{
                    emailLogin.setError("Please enter a valid email address");
                }

            }
        });
    }

    private void initView() {
        Auth=FirebaseAuth.getInstance();
        emailLogin=findViewById(R.id.emailogin);
        passLogin=findViewById(R.id.passLogin);
        loginBtn=findViewById(R.id.loginBtn);
        registerTxt=findViewById(R.id.registerTxt);
        registerTxt.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SigninActivity.class)));


    }
}
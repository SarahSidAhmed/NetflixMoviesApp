package com.example.netflixmoviesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixmoviesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private AppCompatButton signinBtn;
    private EditText userSignin, emailSignin, passSignin, conpassSignin;
    private TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
        Signin();
    }

    private void Signin() {
    signinBtn.setOnClickListener(view -> {
        String user=userSignin.getText().toString().trim();
        String email=emailSignin.getText().toString().trim();
        String pass=passSignin.getText().toString().trim();
        String conpass=conpassSignin.getText().toString().trim();

        if(user.isEmpty()){
            emailSignin.setError("Email can not be empty");
        }
        else if(pass.isEmpty()){
            passSignin.setError("Password can not be empty");
        }
        else if(!pass.equals(conpass)){
            conpassSignin.setError("Check your Password again");
        }
        else{
            Auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SigninActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SigninActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(SigninActivity.this, "Signup Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    });
    }

    private void initView() {
        Auth=FirebaseAuth.getInstance();
        signinBtn=findViewById(R.id.signinBtn);
        userSignin=findViewById(R.id.userSignin);
        emailSignin=findViewById(R.id.signinEmail);
        passSignin=findViewById(R.id.passSignin);
        conpassSignin=findViewById(R.id.conpassSignin);
        login=findViewById(R.id.loginTxt);

        login.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, LoginActivity.class)));
    }
}
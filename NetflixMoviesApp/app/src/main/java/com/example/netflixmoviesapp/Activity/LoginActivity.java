package com.example.netflixmoviesapp.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.netflixmoviesapp.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Login();
    }

    private void Login() {
        binding.loginBtn.setOnClickListener(view -> {
            String pass=binding.passLogin.getText().toString().trim();
            String email=binding.emailogin.getText().toString().trim();

            if(email.isEmpty()){
                Toast.makeText(LoginActivity.this, "Email can not be empty", Toast.LENGTH_SHORT).show();
            }
            else if(pass.isEmpty()){
                Toast.makeText(LoginActivity.this, "Password can not be empty", Toast.LENGTH_SHORT).show();
            }
            else{
                Boolean checkCredentials = databaseHelper.checkEmailPassword(email, pass);
                if(checkCredentials){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.registerTxt.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SigninActivity.class)));

    }
}
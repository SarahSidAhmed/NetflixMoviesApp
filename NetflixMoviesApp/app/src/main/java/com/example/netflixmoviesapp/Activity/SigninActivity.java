package com.example.netflixmoviesapp.Activity;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.netflixmoviesapp.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity {


    ActivitySigninBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        Signin();
    }

    private void Signin() {
    binding.signinBtn.setOnClickListener(view -> {
        String user=binding.userSignin.getText().toString().trim();
        String email=binding.signinEmail.getText().toString().trim();
        String pass=binding.passSignin.getText().toString().trim();
        String conpass=binding.conpassSignin.getText().toString().trim();

        if(user.isEmpty()){
            Toast.makeText(SigninActivity.this, "Email can not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(pass.isEmpty()){
            Toast.makeText(SigninActivity.this, "Password can not be empty", Toast.LENGTH_SHORT).show();

        }
        else if(!pass.equals(conpass)){
            Toast.makeText(SigninActivity.this, "Check your Password again", Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean checkUserEmail = databaseHelper.checkEmail(email);

            if(!checkUserEmail){
                Boolean insert = databaseHelper.insertData(email, pass, user);
                
                if(insert){
                    Toast.makeText(SigninActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(SigninActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(SigninActivity.this, "User Already Exists, Please login", Toast.LENGTH_SHORT).show();
            }
        }

    });
        binding.loginTxt.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, LoginActivity.class)));

    }

}
package com.example.netflixmoviesapp.Activity;

import static java.lang.Thread.*;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.lang.Thread;
import androidx.core.splashscreen.SplashScreen;

import com.example.netflixmoviesapp.R;

public class IntroActivity extends AppCompatActivity {
    private Button getIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_intro);
        
        getIn=findViewById(R.id.getIn);
        getIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });
        
    }
}
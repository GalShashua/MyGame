package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Animation shake;
//        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        button= (ImageView) findViewById(R.id.start_game_btn);
//        shake.setRepeatCount(100);
//        button.startAnimation(shake); // starts animation



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
    }

    public void openGameActivity() {
        Intent intent = new Intent(this, TheGame.class);
        startActivity(intent);
    }
}

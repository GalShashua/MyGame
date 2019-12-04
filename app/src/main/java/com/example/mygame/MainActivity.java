package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView startAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAgain= (ImageView) findViewById(R.id.start_game_btn);
        startAgain.setOnClickListener(new View.OnClickListener() {
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

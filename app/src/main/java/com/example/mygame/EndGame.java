package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {
    private TextView textScore;
    private ImageView play_again;
    private ImageView evil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        int getTotalScore = getIntent().getIntExtra("TOTAL_SCORE", 0);
        this.textScore = (TextView) findViewById(R.id.game_over);
        textScore.setText("YOUR SCORE: "+ getTotalScore);
        evil=(ImageView) findViewById(R.id.evil_actor);
        this.play_again = (ImageView) findViewById(R.id.play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGameActivity();
            }
        });
    }

    public void openNewGameActivity() {
        Intent intent = new Intent(this, TheGame.class);
        startActivity(intent);
    }

}

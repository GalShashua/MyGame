package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        int getTotalScore = getIntent().getIntExtra("TOTAL_SCORE", 0);
        TextView textScore = findViewById(R.id.game_over);
        textScore.setText("YOUR SCORE: "+ getTotalScore);
        ImageView play_again = findViewById(R.id.play_again);
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

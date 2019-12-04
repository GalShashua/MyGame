package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class TheGame extends AppCompatActivity {
    final int BOUND=1600;
    final int NUM=2000;
    final int THREAD_SLEEP_TIME=20;
    private Vibrator v;
    private RelativeLayout layout;
    private GridLayout layoutBalls;
    private MediaPlayer ring;
    private ImageView player;
    private ImageView mute;
    private TextView score = null;
    private ImageView[] balls;
    private volatile boolean playing = true;
    private ImageView life1,life2, life3;
    private double points = 0;
    private int countCollision=0;
    Random rand = new Random();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);
        initGame();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(THREAD_SLEEP_TIME);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!playing)
                                    return;
                                Render();
                            }
                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        t.start();
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ring.isPlaying())
                    ring.pause();
            }
        });
    }

    public void initGame(){
        this.layout = findViewById(R.id.big_layout);
      //  this.layoutBalls = findViewById(R.id.layout_for_balls);
        this.score = findViewById(R.id.scoreText);
        this.player = findViewById(R.id.playerView);
        this.mute = findViewById(R.id.music_off);
        ring= MediaPlayer.create(TheGame.this,R.raw.song);
        ring.start();
        life1= findViewById(R.id.life1);
        life2= findViewById(R.id.life2);
        life3= findViewById(R.id.life3);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        createBalls();
    }

    private void Render() {
        points = points + 0.02;
        int scoreCastInt=(int) points;
        this.score.setText("       SCORE: " + scoreCastInt);
        changePosition();
        for (ImageView ball : balls) {
            if (Collision(player, ball) && ball.getVisibility() == View.VISIBLE) {
                countCollision++;
                if (countCollision == 1) {
                    ball.setVisibility(View.INVISIBLE);
                    life1.setVisibility(View.INVISIBLE);
                    v.vibrate(200);
                }
                if (countCollision == 2) {
                    ball.setVisibility(View.INVISIBLE);
                    life2.setVisibility(View.INVISIBLE);
                    v.vibrate(200);

                }
                if (countCollision == 3) {
                    v.vibrate(200);
                    ball.setVisibility(View.INVISIBLE);
                    life3.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(this, EndGame.class);
                    intent.putExtra("TOTAL_SCORE", scoreCastInt);
                    startActivity(intent);
                    ring.pause();
                }
            }
        }
    }

        private boolean Collision(ImageView net, ImageView ball) {
        Rect BallRect = new Rect();
        ball.getHitRect(BallRect);
        Rect NetRect = new Rect();
        net.getHitRect(NetRect);
        return BallRect.intersect(NetRect);
    }

    public void changePosition() {
        float ballDownY = 10;
        for (ImageView imageView : balls) {
            imageView.setY(ballDownY + imageView.getY());
        }
        for (ImageView ball : balls) {
            if (ball.getY() > layout.getHeight()) {
                ball.setY(rand.nextInt(BOUND) - NUM);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       if(event.getAction() == MotionEvent.ACTION_MOVE && event.getX() < getScreenWidth() - player.getWidth()){
           player.setX(event.getX());
       }
       return true;
    }

    public int getScreenWidth() {
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size.x;
    }

//    public void createBalls() {
//        int ball_size = 120;
//        balls= new ImageView[6];
//        float curX=(((getScreenWidth()/3)/2)-(ball_size/2));
//        for(int i=0; i<balls.length; i++){
//            balls[i]= new ImageView(this);
//            balls[i].setImageResource(R.drawable.yellowball);
//            balls[i].setLayoutParams(new android.view.ViewGroup.LayoutParams(ball_size, ball_size));
//            balls[i].setY(rand.nextInt(BOUND) - NUM);
//            //      balls[i].setY(locationY.get(rand.nextInt(locationY.size())));
//            balls[i].setX(curX);
//            layout.addView(balls[i]);
//            curX=curX+(getScreenWidth()/3);
//            if(i%3==0) {
//                curX=(((getScreenWidth()/3)/2)-(ball_size/2));
//            }
//        }
//    }



//        public void createBalls () {
//            balls = new ImageView[5];
//            for (int i = 0; i < balls.length; i++) {
//                balls[i] = new ImageView(this);
//                balls[i].setImageResource(R.drawable.ballforgame);
//                int BALL_SIZE = 120;
//                balls[i].setLayoutParams(new android.view.ViewGroup.LayoutParams(BALL_SIZE, BALL_SIZE));
//                balls[i].setY(rand.nextInt(BOUND) - NUM);
//                layoutBalls.setOrientation(GridLayout.HORIZONTAL);
//                layoutBalls.setColumnCount(3);
//                layoutBalls.addView(balls[i]);
//            }
//        }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

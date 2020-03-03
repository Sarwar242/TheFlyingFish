package com.example.theflyingfish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startGameAgain;
    private TextView sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent gameOverIntent=getIntent();
        Bundle b = gameOverIntent.getExtras();
        int score=b.getInt("Score");
        String s=Integer.toString(score);

        startGameAgain=(Button)findViewById(R.id.play_again_btn);
        sc=(TextView) findViewById(R.id.score);

        sc.setText(s);

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent= new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            System.exit(0);
        }
       return true;
    }

}

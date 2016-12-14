package com.example.root.ooxx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int Max_Image = 9;
    public enum Player {
        yellow,
        red,
        none  //empty
    }

    private Player mPlayer;
    private boolean mGameIsActive;

    private  Player[] mGameState;
    private int[][] sWinningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        reset();
    }
    public  void init(){
        mGameState = new Player[Max_Image];
        mGameIsActive = true;
    }

    public void reset()
    {
        mGameIsActive = true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        mPlayer = Player.yellow;

        for (int i = 0; i < Max_Image; i++) {
            mGameState[i] = Player.none;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        for (int i = 0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (mGameState[tappedCounter] == Player.none && mGameIsActive) {
            mGameState[tappedCounter] = mPlayer;
            counter.setTranslationY(-1000f);

            if (mPlayer == Player.yellow)
            {
                counter.setImageResource(R.drawable.yellow);
                mPlayer = Player.red;
            }else if (mPlayer == Player.red)
            {
                counter.setImageResource(R.drawable.red);
                mPlayer = Player.yellow;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
        }

        Check();
    }

    public void Check()
    {
        for (int[] winningPosition : sWinningPositions) {
            if (mGameState[winningPosition[0]] == Player.none)
                continue;

            if (mGameState[winningPosition[0]] == mGameState[winningPosition[1]] &&
                    mGameState[winningPosition[1]] == mGameState[winningPosition[2]]) { // ANSWER OF CHALLENGE 2

                // Someone has won!
                mGameIsActive = false;
                String winner = "Red";
                if (mGameState[winningPosition[0]] == Player.yellow) {
                    winner = "Yellow";
                }

                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner + " has won!");
                LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);
                break;
            } else {
                boolean gameIsOver = true;

                for (Player counterState : mGameState) {
                    if (counterState == Player.none) gameIsOver = false;
                }

                if (gameIsOver) {
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a draw");
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public void playAgain(View view) {
        reset();
    }
}

package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Max amount of dice
    private static final int MAX_AMOUNT_DICE = 3;

    // Field to hold the roll result text
    TextView rollResult;

    // Field to hold the score
    int score;

    // Dice Manager stores a list of dice
    DiceManager dice = new DiceManager();

    // Field to hold the score text
    TextView scoreText;

    // ArrayList to hold all three dice images
    ArrayList<ImageView> diceImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load dice manager with 3 six-sided die
        dice.loadDie(new SixSidedDie());
        dice.loadDie(new SixSidedDie());
        dice.loadDie(new SixSidedDie());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        // Set initial score
        score = 0;

        // Bind TextView and Button
        rollResult = (TextView)findViewById(R.id.rollResult);

        scoreText = (TextView)findViewById(R.id.scoreText);

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);

    }

    private Handler messageHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);

            for(int dieIndex = 0; dieIndex < MAX_AMOUNT_DICE; dieIndex++){
                String imageName = "die_"+dice.indexOfDie(dieIndex).getValue() + ".png";

                try{
                    InputStream stream = getAssets().open(imageName);
                    Drawable d = Drawable.createFromStream(stream, null);
                    diceImageViews.get(dieIndex).setImageDrawable(d);
                } catch(IOException e){
                    e.printStackTrace();
                }
            }

            // Build message for result
            String msg;

            // TODO: Use CONST for index
            int valueOfDie1 = dice.indexOfDie(0).getValue();
            int valueOfDie2 = dice.indexOfDie(1).getValue();
            int valueOfDie3 = dice.indexOfDie(2).getValue();

            // TODO: Split into another method
            if(valueOfDie1 == valueOfDie2 && valueOfDie1 == valueOfDie3){
                //Triples
                int scoreDelta = valueOfDie1 * 100;
                msg = "You rolled a triple " + valueOfDie1 + "! You score " + scoreDelta + " points!";
                score += scoreDelta;
            } else if(valueOfDie1 == valueOfDie2 || valueOfDie1 == valueOfDie3 || valueOfDie2 == valueOfDie3){
                // Double
                msg = "You rolled a doubles for 50 points!";
                score += 50;
            } else {
                msg = "You didn't score this roll. Try again!";
            }

            // Update app to display result
            rollResult.setText(msg);
            scoreText.setText("Score: " + score);

        }
    };


    private void rollDice(View v){

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){

                for(int dieIndex = 0; dieIndex < dice.numberOfDice(); dieIndex++){
                    dice.indexOfDie(dieIndex).roll();
                }

                messageHandler.sendEmptyMessage(0);
            }
        });
        thread.start();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

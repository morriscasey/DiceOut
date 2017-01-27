package com.example.diceout;

import java.util.Random;

/**
 * Created by Casey Morris on 1/26/17.
 */

public abstract class BasicDie {
    private int value;
    private int sides;

    public BasicDie(int numberOfSides){
        this.value = 1;
        this.sides = numberOfSides;
    }

    // Roll die
    public void roll(){
        // Init random number generator
        Random rand = new Random();

        // Value based on the amount of sides
        this.value = rand.nextInt(this.sides) + 1;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }
}

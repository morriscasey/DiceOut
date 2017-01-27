package com.example.diceout;

import java.util.ArrayList;

/**
 * Created by Casey Morris on 1/26/17.
 */

public class DiceManager {
    // Create ArrayList container for the dice values
    private ArrayList<BasicDie> dice = null;

    public DiceManager(){
        dice = new ArrayList<BasicDie>();
    }

    public void loadDie(BasicDie die){
        this.dice.add(die);
    }

    public int numberOfDice(){
        return dice.size();
    }

    public BasicDie indexOfDie(int index){
        return this.dice.get(index);
    }

}

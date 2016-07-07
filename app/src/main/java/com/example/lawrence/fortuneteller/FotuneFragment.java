package com.example.lawrence.fortuneteller;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lawrence on 4/5/2016.
 * <p/>
 * This is the data model (layer) for app.
 */
public class FotuneFragment {

    /*
    // for the future to color text.
    private final String RED = "#e15258";
    private final String GREEN = "#51b46d";
    private final String YELLOW = "#e0ab18";
    */

    // choices from original magic 8 ball.
    /*
    String[][] mFortunes = {
            {"It is certain.", GREEN},
            {"It is decidedly so.", GREEN},
            {"Without a doubt.", GREEN},
            {"Yes, definitely.", GREEN},
            {"You may rely on it.", GREEN},
            {"As I see it, yes.", GREEN},
            {"Most likely.", GREEN},
            {"Outlook good.", GREEN},
            {"Yes.", GREEN},
            {"Signs point to yes.", GREEN},
            {"Reply hazy try again.", YELLOW},
            {"Ask again later.", YELLOW},
            {"Better not tell you now.", YELLOW},
            {"Cannot predict now.", YELLOW},
            {"Concentrate and ask again.", YELLOW},
            {"Don't count on it.", RED},
            {"My reply is no.", RED},
            {"My sources say no.", RED},
            {"Outlook not so good.", RED},
            {"Very doubtful.", RED}
    };
    */

    List<Fortune> mFortuneList;

    public FotuneFragment(){
        mFortuneList = new ArrayList<>();

        // load the fortunes in the constructor instead of a seperate load() method.
        mFortuneList.add(new Fortune("It is certain.", Color.GREEN));
        mFortuneList.add(new Fortune("It is decidedly so.", Color.GREEN));
        mFortuneList.add(new Fortune("Without a doubt.", Color.GREEN));
        mFortuneList.add(new Fortune("Yes, definitely.", Color.GREEN));
        mFortuneList.add(new Fortune("You may rely on it.", Color.GREEN));
        mFortuneList.add(new Fortune("As I see it, yes.", Color.GREEN));
        mFortuneList.add(new Fortune("Most likely.", Color.GREEN));
        mFortuneList.add(new Fortune("Outlook good.", Color.GREEN));
        mFortuneList.add(new Fortune("Yes.", Color.GREEN));
        mFortuneList.add(new Fortune("Signs point to yes.", Color.GREEN));
        mFortuneList.add(new Fortune("Reply hazy try again.", Color.LTGRAY));
        mFortuneList.add(new Fortune("Ask again later.", Color.LTGRAY));
        mFortuneList.add(new Fortune("Better not tell you now.", Color.LTGRAY));
        mFortuneList.add(new Fortune("Cannot predict now.", Color.LTGRAY));
        mFortuneList.add(new Fortune("Concentrate and ask again.", Color.LTGRAY));
        mFortuneList.add(new Fortune("Don't count on it.", Color.RED));
        mFortuneList.add(new Fortune("My reply is no.", Color.RED));
        mFortuneList.add(new Fortune("My sources say no.", Color.RED));
        mFortuneList.add(new Fortune("Outlook not so good.", Color.RED));
        mFortuneList.add(new Fortune("Very doubtful.", Color.RED));
    }

    private int getRandomNum() {
        // all these int variables are to make the random num gen algorithm
        // more readable and flexible if ranges happen to change.
        Random rand = new Random();
        int min = 0;
        int max = mFortuneList.size() - 1;
        int range = max - min;
        int randNum;

        return randNum = rand.nextInt(range + 1) + min;
        // nextInt() generates a random number between [min, max+1) (i.e. max+1 is excluded, so add 1 to get max)
    }

    public Fortune getFortune() {
        return mFortuneList.get(getRandomNum());
    }

}

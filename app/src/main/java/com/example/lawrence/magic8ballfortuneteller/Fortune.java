package com.example.lawrence.magic8ballfortuneteller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lawrence on 4/5/2016.
 * <p/>
 * This is the data model (layer) for app.
 */
public class Fortune {

    // for the future to color text.
    private final String RED = "#e15258";
    private final String GREEN = "#51b46d";
    private final String YELLOW = "#e0ab18";

    // choices from original magic 8 ball.
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

    private int getRandomNum() {
        // all these int variables are to make the random num gen algorithm
        // more readable and flexible if ranges happen to change.
        Random rand = new Random();
        int min = 0;
        int max = mFortunes.length - 1;
        int range = max - min;
        int randNum;

        return randNum = rand.nextInt(range + 1) + min;
        // nextInt() generates a random number between [min, max+1) (i.e. max+1 is excluded, so add 1 to get max)
    }

    public String[] getFortune() {
        return mFortunes[getRandomNum()];
    }

}

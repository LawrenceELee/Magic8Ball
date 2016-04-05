package com.example.lawrence.magic8ballfortuneteller;

import java.util.Random;

/**
 * Created by Lawrence on 4/5/2016.
 * <p/>
 * This is the data model (layer) for app.
 *
 */
public class Fortune {
    private final String[] fortunes = {
                    "It is certain.",
                    "It is decidedly so.",
                    "Without a doubt.",
                    "Yes, definitely.",
                    "You may rely on it.",
                    "As I see it, yes.",
                    "Most likely.",
                    "Outlook good.",
                    "Yes.",
                    "Signs point to yes.",
                    "Reply hazy try again.",
                    "Ask again later.",
                    "Better not tell you now.",
                    "Cannot predict now.",
                    "Concentrate and ask again.",
                    "Don't count on it.",
                    "My reply is no.",
                    "My sources say no.",
                    "Outlook not so good.",
                    "Very doubtful."
    };

    public String getFortune(){
        Random rand = new Random();

        // all these int variables are to make the random num gen algorithm
        // more readable and flexible if ranges happen to change.
        int min = 0;
        int max = fortunes.length;
        int range = max - min;
        int randNum = rand.nextInt(range + 1) + min;
        // nextInt() generates a random number between [min, max) (i.e. max is excluded, so add 1 to get max)
        // range = [min, max+1)

        return fortunes[randNum];
    }
}

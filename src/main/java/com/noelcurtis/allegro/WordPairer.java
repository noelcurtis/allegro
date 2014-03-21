package com.noelcurtis.allegro;

import com.google.gson.Gson;

public class WordPairer implements Runnable
{
    private final String _operator;

    public WordPairer(String operator)
    {
        _operator = operator;
    }

    /**
     * Creates a list of paired words
     */
    private void pairUp()
    {
        System.out.println("Processing Line: " + _operator);
        // split the operator up into an array of words.
        // we have an assumption that the operator is , separated
        String[] words = _operator.split(",");
        // create a list of paired words
        for(int i=0; i<words.length; i++)
        {
            for(int j=i+1; j<words.length; j++)
            {
                WordPair wordPair = new WordPair(words[i], words[j]);
                synchronized (Mediator.WordPairsCount)
                {
                    if (Mediator.WordPairsCount.containsKey(wordPair))
                    {
                        Mediator.WordPairsCount.put(wordPair, Mediator.WordPairsCount.get(wordPair) + 1);
                    }
                    else
                    {
                        Mediator.WordPairsCount.put(wordPair, 1);
                    }
                }
            }
        }
    }

    @Override
    public void run()
    {
        pairUp();
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}

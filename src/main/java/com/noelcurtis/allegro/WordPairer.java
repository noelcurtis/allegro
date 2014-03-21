package com.noelcurtis.allegro;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class WordPairer implements Runnable
{
    private final String _operator;
    //using a Set here with the assumption that there is a constraint on not having a pair occur twice on a line.
    private final Set<WordPair> _wordPairs;
    private boolean _isComplete;

    public WordPairer(String operator)
    {
        _operator = operator;
        _wordPairs = new HashSet<WordPair>();
        _isComplete = false;
    }

    /**
     * Check if WordPairing is complete
     * @return
     */
    public boolean isComplete()
    {
        return _isComplete;
    }

    /**
     * Use to get word pairs
     * @return
     */
    public Set<WordPair> getWordPairs()
    {
        return _wordPairs;
    }

    /**
     * Creates a list of paired words
     */
    private void pairUp()
    {
        //System.out.println("Processing Line: " + _operator);
        // split the operator up into an array of words.
        // we have an assumption that the operator is , separated
        String[] words = _operator.split(",");
        // create a list of paired words
        for(int i=0; i<words.length; i++)
        {
            for(int j=i+1; j<words.length; j++)
            {
                _wordPairs.add(new WordPair(words[i], words[j]));
            }
        }
        //System.out.println("Finished line" + _operator);
        _isComplete = true;
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

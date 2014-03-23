package com.noelcurtis.allegro;

import com.google.gson.Gson;

/**
 * A WordPair class that holds 2 words.
 */
public class WordPair
{
    private final String _first;
    private final String _second;

    /**
     * Use to initialize WordPair with 2 words
     * @param first
     * @param second
     */
    public WordPair(String first, String second)
    {
        _first = first;
        _second = second;
    }

    public String getFirst()
    {
        return _first;
    }

    public String getSecond()
    {
        return _second;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof WordPair))
            return false;
        else if (((WordPair)obj).getFirst().equals(_first) && ((WordPair)obj).getSecond().equals(_second))
            return true;
        else if  (((WordPair)obj).getFirst().equals(_second) && ((WordPair)obj).getSecond().equals(_first))
            return true;
        else return false;
    }

    /**
     * Use to generate hash code for the word pair.
     * @return
     */
    @Override
    public int hashCode() {
        return _first.hashCode() + _second.hashCode();
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }

    public String format()
    {
        return _first + "," + _second;
    }
}

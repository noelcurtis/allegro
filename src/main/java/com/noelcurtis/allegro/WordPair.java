package com.noelcurtis.allegro;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;

/**
 * A WordPair class that holds 2 words.
 * The once initialized its immutable.
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

    @Override
    public int hashCode() {
        HashFunction hf = Hashing.md5();
        int h1 = hf.newHasher()
                .putString(_first, Charsets.UTF_8)
                .hash().asInt();
        int h2 = hf.newHasher()
                .putString(_second, Charsets.UTF_8)
                .hash().asInt();
        int hashCode = h1 + h2;
        return hashCode;
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

package com.noelcurtis.allegro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LineProcessor implements Runnable
{
    private HashMap<WordPair, Integer> _pairsCount;
    private String[] _lines;
    private HashMap<WordPair, Integer> _sectionPairsCount;
    private boolean _isComplete;

    public LineProcessor(String[] lines, HashMap<WordPair, Integer> pairsCount)
    {
        _sectionPairsCount = new HashMap<WordPair, Integer>();
        _pairsCount = pairsCount;
        _lines = lines;
        _isComplete = false;
    }

    private void processLines()
    {
        for (String l : _lines)
        {
            WordPairer pairer = new WordPairer(l);
            pairer.run();
            Set<WordPair> wordPairSet = pairer.getWordPairs();
            // count all the lines
            for (WordPair p : wordPairSet)
            {
                if (!_sectionPairsCount.containsKey(p))
                {
                    _sectionPairsCount.put(p, 1);
                }
                else
                {
                    _sectionPairsCount.put(p, _sectionPairsCount.get(p) + 1);
                }
            }
        }
    }

    private void compileResults()
    {
        synchronized (_pairsCount){
            for (Map.Entry<WordPair, Integer> entry : _sectionPairsCount.entrySet())
            {
                if (_pairsCount.containsKey(entry.getKey()))
                {
                    _pairsCount.put(entry.getKey(), _pairsCount.get(entry.getKey()) + entry.getValue());
                }
                else
                {
                    _pairsCount.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public void run()
    {
        processLines();
        compileResults();
        _isComplete = true;
    }

    public boolean isComplete()
    {
        return _isComplete;
    }
}

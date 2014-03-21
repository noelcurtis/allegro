package com.noelcurtis.allegro;

import com.google.gson.Gson;

public class LineProcessor
{
    Reader _reader;

    public LineProcessor(String filePath)
    {
        _reader = new Reader(filePath);
    }

    public void startProcessing()
    {
        _reader.run();
        String line = _reader.getLine();
        while (line != null)
        {
            WordPairer wordPairer = new WordPairer(line);
            // kick it off
            Mediator.getExecutorService().execute(wordPairer);
            //wordPairer.run();
            line = _reader.getLine();
        }

        System.out.println(new Gson().toJson(Mediator.WordPairsCount));
    }
}

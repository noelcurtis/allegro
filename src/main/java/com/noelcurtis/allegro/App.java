package com.noelcurtis.allegro;


import java.io.File;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            String filePath = new File(".").getCanonicalPath() + "/src/test/java/com/noelcurtis/allegro/Artist_lists_small.txt";
            LineProcessor lp = new LineProcessor(filePath);
            lp.startProcessing();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
}

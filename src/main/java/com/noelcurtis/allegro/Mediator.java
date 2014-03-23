package com.noelcurtis.allegro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mediator
{
    // ExecutorService used to mediate on a thread pool
    private static ExecutorService executorService;
    // This is the number to threads that will be made available in the pool
    private static int ThreadCount = 20;
    // Threshold for the number of times words pairs occur
    public static int RecurrenceThreshold = 50;
    // The total number of lines is split up to be counted in parallel
    public static int SplittingFactor = 10;

    /**
     * Helper to get the Executor Service to submit jobs to
     * @return
     */
    public static ExecutorService getExecutorService()
    {
        if (executorService == null)
        {
            executorService = Executors.newFixedThreadPool(ThreadCount);
        }
        return executorService;
    }
}

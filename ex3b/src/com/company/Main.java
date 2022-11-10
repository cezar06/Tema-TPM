package com.company;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException
    {
        int n ;
        n = 5;
        ArrayList<MyThread1> threads = new ArrayList<>();
        for(int i = 1 ; i <= n ; ++i )
        {
            threads.add(new MyThread1(i, n));
        }
        long startTime = System.nanoTime();
        for (MyThread1 thread:threads)
        {
            thread.start();
        }
        for(MyThread1 th : threads)
        {
            try
            {
                th.join();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Generalized Peterson Algorithm total time:" + totalTime);
    }
}

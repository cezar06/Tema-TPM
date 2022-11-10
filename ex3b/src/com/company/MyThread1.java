package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread1 extends Thread
{
    public static volatile int[] level;
    public static volatile int[] victim;
    public static int x;
    public static int y;
    private int id;
    private int n;
    private MyThread1()
    {

    }

    public MyThread1(int id, int n)
    {
        if(level == null )
        {
            level = new int[n + 1];
        }
        if(victim == null )
        {
            victim = new int[n];
        }
        this.n = n;
        this.id = id;
    }

    private boolean exists_k (int i )
    {
        int levelI = level[i];
        for ( int k = 1 ; k < n ; ++k )
        {
            if(i != k )
            {
                if(level[k] > levelI)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void lock()
    {
        int me = id;
         x = me;
        while (y != 0) {};
        y = me;
        if (x != me)
        {
           lock1(id);
        }
    }

    public synchronized void unlock()
    {
        y = 0;
        unlock1(id);
    }

    private synchronized void lock1(int i)
    {
        for (int L = 1; L < n; L++) {
            level[i] = L;
            victim[L] = i;
            while ( exists_k(i)  && victim [L] == i && level[i + 1] != 0) {};
            level[i] = 0 ;
        }
    }

    private synchronized void unlock1(int i)
    {
        level[i] = 0;
    }

    public synchronized void run()
    {
        for(int i = 0 ; i < 20 ; ++i)
        {
            lock();
            System.out.println( this.id );
            unlock();
            try {
                TimeUnit.MICROSECONDS.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

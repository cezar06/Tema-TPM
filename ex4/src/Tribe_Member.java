import java.util.concurrent.locks.ReentrantLock;

public class Tribe_Member implements Runnable
{
    private final Pot pot;
    private final ReentrantLock lock;

    public Tribe_Member(Pot pot, ReentrantLock lock)
    {
        this.pot = pot;
        this.lock = lock;
    }
    public void run()
    {
        lock.lock(); //the tribe member tries to access the pot using the reentrant lock
        try
        {
            if(pot.get_portions() == 0)
            {   //if the pot is empty, the tribe member notifies the cook
                // and waits for the cook to fill the pot and get notified back
                System.out.println("The pot is empty, I will notify the cook");
                synchronized (pot)
                {
                    pot.notify();
                    pot.wait();
                }
            }
            //the pot is guaranteed to not be empty at this point
            pot.remove_portion();
            System.out.println("I ate a portion, there are " + pot.get_portions() + " portions left");

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock(); //the tribe member releases the lock
        }
    }
}

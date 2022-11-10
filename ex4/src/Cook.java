public class Cook implements Runnable
{
    private final Pot pot;
    public Cook(Pot pot)
    {
        this.pot = pot;
    }
    @Override
    public void run()
    {
        while(true)
        {
            synchronized (pot)
            {
                try
                {
                    pot.wait();  //the cook waits to be notified by a tribe member that the pot is empty
                }
                catch (InterruptedException e)
                {
                    System.out.println("The cook has no more tribe members to feed");
                    return;
                }
            }
            pot.fill_pot();  //the cook fills the pot after being notified
            System.out.println("The pot is full, I will notify the tribe members");
            synchronized (pot)
            {
                pot.notify(); //the cook notifies the tribe members that the pot is full
            }
        }
    }
}
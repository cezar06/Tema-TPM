import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    public static void main(String[] args)
    {   Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce the maximum number of portions in the pot: ");
        int nr_portions = scanner.nextInt();
        System.out.println("Introduce the number of tribe members wanting to eat: ");
        int nr_tribe_members = scanner.nextInt();
        //access to the pot is done using a reentrant lock
        ReentrantLock lock = new ReentrantLock();
        if(nr_tribe_members < nr_portions)
        {
            System.out.println("The number of tribe members must be lower than the maximum number of portions in the pot");
            return;
        }

        Pot pot = new Pot(nr_portions);
        Cook cook = new Cook(pot);
        Thread cook_thread = new Thread(cook);
        cook_thread.start();
        Thread[] threads = new Thread[nr_tribe_members];
        for(int i = 0; i < nr_tribe_members; i++)

        {   //start the tribe members threads
            Tribe_Member tribe_member = new Tribe_Member(pot, lock);
            threads[i] = new Thread(tribe_member);
            threads[i].start();
        }

        for(int i = 0; i < nr_tribe_members; i++)
        {   //wait for the tribe members threads to finish
            try
            {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //after the tribe members threads have finished, the cook thread is interrupted
        cook_thread.interrupt();
    }
}

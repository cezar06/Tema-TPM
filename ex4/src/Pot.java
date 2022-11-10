public class Pot
{
    private int portions;
    private final int max_portions;
    public synchronized int get_portions()
    {
        return portions;
    }
    public void fill_pot()
    {
        portions = max_portions;
    }
    public void remove_portion()
    {
        portions--;
    }
    public Pot(int max_portions)
    {
        this.max_portions = max_portions;
        portions = max_portions;
    }
}

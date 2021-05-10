import java.util.Observer ;
import java.util.Observable ;
/**
 * Write a description of class konsolowaPrezentacjaSysTicka here.
 * 
 * @author (Kamil Jamros 226821)
 */
public class konsolowaPrezentacjaSysTicka implements Observer
{
CortexM0SysTick myCounter;
    
    public konsolowaPrezentacjaSysTicka()
    {
       myCounter = new CortexM0SysTick();
       myCounter.addObserver(this) ;
       prezentujLicznik(); 
    }
    
    private void powiedzCos(String co)
    {
        System.out.println(co);
    }
    
    void prezentujLicznik()
    {
        powiedzCos(" rozpoczynam pokaz SysTick") ;
        powiedzCos("Stn poczatkowy to:" + myCounter);
         powiedzCos("wysylam 2 inpulsy") ;
         myCounter.impuls();
         myCounter.impuls();
         powiedzCos("bez zmian?\n" + myCounter) ;
         myCounter.setEnableFlag(true);
         myCounter.setTickintFlag(true);
         powiedzCos("wysylam 2 inpulsy") ;
         myCounter.impuls();
         myCounter.impuls();
         powiedzCos("\n" + myCounter) ;
         myCounter.impuls();
         powiedzCos("\n" + myCounter) ;
         myCounter.impuls();
         powiedzCos("\n" + myCounter) ;
    }    
    //it is like a exception handler
    //ISR
    public void update(Observable subject, Object arg)
    {
        powiedzCos("***** Interrupt ********");
        powiedzCos("***** --------- ********");
    }
    
    /**
     * metoda main - klasa wiodąca aplikacji konsolowej
     */
    // instance variables - replace the example below with your own
    
    public static void main(String[] arg)
    {
        new konsolowaPrezentacjaSysTicka();
        
    }
    
    private int x;

    

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }




}

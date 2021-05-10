import java.util.Observable ;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Kamil Jamros
 * 20 maj 2018
 * Programowanie w Języku Java
 */
public class CortexM0SysTick extends Observable
{
    // instance variables - replace the example below with your own
    private int rejestrRVR, rejestrCVR;
    private boolean countFlag, tickintFlag, enableFlag;
   
    /**
     * Constructor for objects of class CortexM0SysTick
     */
    public CortexM0SysTick() 
    {
        // initialise instance variables
        
       int randomNum1 = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
       int randomNum2 = ThreadLocalRandom.current().nextInt(0, randomNum1);
        rejestrRVR = randomNum1;
        rejestrCVR = randomNum2;
        
    }

    /**
     getCVR 
     */
    public int getCVR()
    {
        return rejestrCVR;
    }
    
     /**
     getRVR 
     */
    public int getRVR()
    {
        return rejestrRVR;
    }
    
     /**
     getTickintFlag
     */
    public boolean isTickintFlag()
    {
        countFlag = false;  
        return tickintFlag;
    }
    
     /**
     getEnableFlag
     */
    public boolean isEnableFlag()
    {
        countFlag = false;
        return enableFlag;
    }
    
    /**
     getCountFlag 
     */
    public boolean isCountFlag()
    {
        boolean tmp = countFlag;
        countFlag = false;
        return tmp;
    }
    
    /**
     setCVR 
     */
    public void setCVR(int rejestrCVR)
    {
        
        this.rejestrCVR = 0;
        countFlag = false;
        
    }
    
    /**
     setRVR 
     */
    public void setRVR(int rejestrRVR)
    {
        if(rejestrRVR>=0 && rejestrRVR <(1<<24) )
        {
        this.rejestrRVR = rejestrRVR;
        }
        
    }
    
    public void setEnableFlag(boolean flag)
    {
        enableFlag=flag;
    }
    
        public void setTickintFlag(boolean flag)
    {
        tickintFlag=flag;
    }
    

    /**
     * metoda symuluje dostarczenie impulsu
     */
    public void impuls()
    {
        if(!enableFlag) return;
        
        if(rejestrCVR == 0 || rejestrRVR==0)
        {
            rejestrCVR = rejestrRVR;
            notifyObservers() ;
            if(rejestrRVR == 0)
                return;
                
        
        }
        else 
        {
            rejestrCVR--;
            if(rejestrCVR == 0)
            {
                countFlag = true;
            if(tickintFlag)
            {
                //interupt
                setChanged() ;
                notifyObservers();
                
                
            }
            }
        }
    }
    public boolean nonDestrCount()
    {
       return countFlag; 
    }
    public String toString()
    {
        return ("\nenableFlag =" + enableFlag + "\n counterFlag = "+ countFlag +"\ntickinitFlag :  " +tickintFlag +"\n RVR: "+ rejestrRVR+"\n CVR:" + rejestrCVR);
        
        
    }
    
    
    
    
    
}
import java.awt.event.*;
import java.awt.*;
public class PulseGenerator extends Thread implements PulseSource {
byte mode ;
boolean on, alive = true;
int delay;
int burstImp; 
long impulsNo, burstCounter;
ActionListener actionListener;//Refers to a list of ActionListener objects
public PulseGenerator(int delay, byte mode)
{
    this.mode = mode;
    this.delay = delay;
    start();
}
public PulseGenerator()
 {
 this(300, PulseSource.BURST_MODE);
 }
 
 
 public void addActionListener(ActionListener listener) {
 actionListener = AWTEventMulticaster.add(actionListener, listener);
 }//end addActionListener()
 //-----------------------------------------------------------------------
 //The following method removes ActionListener objects from the list
 // described above
 public void removeActionListener(ActionListener listener) {
 actionListener = AWTEventMulticaster.remove(actionListener, listener);
 }//end removeActionListener
 
 
 public void run() {
     burstCounter = burstImp;
 while(alive){
     
     if (on) {
// test mode & check if last pulse
// sleep for a moment
        try{
               sleep(delay);
            } catch (InterruptedException e) {}
                
                if(mode == PulseSource.BURST_MODE)
                {
                    burstCounter --;

                    if(burstCounter == 0)
                    {
                    on = false;
                    burstCounter = burstImp;
                    
                    }
                    if(actionListener!=null) actionListener.actionPerformed(new
                    ActionEvent(this, ActionEvent.ACTION_PERFORMED, "impuls"));
                }
                
                else if(mode == PulseSource.CONTINOUS_MODE)
                {
                    if(actionListener!=null) actionListener.actionPerformed(new
                    ActionEvent(this, ActionEvent.ACTION_PERFORMED, "impuls"));
                }
                
        else{
            try{
               sleep(1000);
            } catch (InterruptedException e) {}
        }}
        //while on
      }//while on
}
public void halt() { on = false ;}
public void trigger() { on = true ;}
   
public void setMode(byte mode){this.mode = mode;} 
public byte getMode() {return mode;}
       // zatrzymaj generację 
public void setPulseDelay(int ms) {delay = ms;}
public int getPulseDelay() {return delay;}
public void setPulseCount(int burst) {this.burstImp = burst; this.burstCounter = burst;}
public void  killThread(){
    alive = false;
}


}
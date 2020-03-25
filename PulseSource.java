import java.awt.event.*;
/**
 * Kamil Jamros
 * 20 maj 2018
 * Programowanie w Języku Java
 */

public interface PulseSource
{
final static byte BURST_MODE = 0;
final static byte CONTINOUS_MODE = 1;

    void addActionListener(ActionListener pl);		// upraszczamy (było PulseListener)
    void removeActionListener(ActionListener pl);	// upraszczamy (było PulseListener)

    void trigger() ;
    void setMode(byte mode) ;
    byte getMode() ;
    void halt() ;	// zatrzymaj generację 
    void setPulseDelay(int ms) ;
    int getPulseDelay() ;
    void setPulseCount(int burst) ;
}
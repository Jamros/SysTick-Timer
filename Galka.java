import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Kamil Jamros
 * 20 maj 2018
 * Programowanie w Języku Java
 */

public class Galka extends JComponent implements MouseListener, MouseMotionListener{
    int     value = 0;
    int     newValue;
    boolean pressed = false;
    boolean entered = false;
    boolean dragging =false;
    ActionListener actionListener;


    public Galka() {
        this(0);
    }
    public Galka(int value) {
        this.value = this.newValue = value ;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }

    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    public Dimension getMinimumSize() {
        return new Dimension(300, 300);
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    /**
     * Painting component
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension   d = getSize();
        int alfa = 90 - value;
        int r,w,h;
        r = Math.min(d.height,d.width);
        h = d.height ;
        w = d.width ;
        double x = w/2 + (int) (r/2*Math.cos(alfa*Math.PI/180));
        double y = h/2 - (int) (r/2*Math.sin(alfa*Math.PI/180));

        g.setColor(Color.GRAY);
        g.fillOval((w-r)/2,(h-r)/2,r,r);
        g.setColor(Color.BLACK);
        //g.fillOval((int) (x*0.7)+20,(int) (y*0.7)+20, r/7, r/7);
        g.fillOval((int) (x*0.7+r/10),(int) (y*0.7+r/10), r/7, r/7);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString(new Integer(newValue*1000/360).toString() + " ms", w/2-15, h/2+5);
    }

    public void setValue(int newValue) {
        this.newValue = newValue;
        repaint();
    }

    public int getValue() {
        return(newValue);
    }


    public boolean contains(int x, int y) {
        int mx = getSize().width/2;
        int my = getSize().height/2;
        int r = Math.min(mx,my);
        return (((mx-x)*(mx-x) + (my-y)*(my-y)) <= r*r);
    }

    public void mouseClicked(MouseEvent me) {}

    public void mouseEntered(MouseEvent me) {
        entered = true;
        repaint();
    }

    public void mouseExited(MouseEvent me) {
        entered = false;
        pressed=false;
        repaint();
    }

    public void mousePressed(MouseEvent me) {
        pressed = true;
        dragging = true;
        repaint();
    }

    public void mouseDragged (MouseEvent e){
        int x, y;
        x = e.getX();
        y = e.getY();
        double yy = (getSize().height / 2.0 - y);
        double xx = (x - getSize().width / 2.0);
        newValue = 90 - (int) (180 / Math.PI * Math.atan(yy / xx));
        if (xx < 0) newValue += 180;
        value = newValue;
        if (dragging) {
            repaint();
        }
        if(actionListener!=null)
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Galka"));
    }

    @Override
    public void mouseMoved(MouseEvent e) {}


    public void mouseReleased(MouseEvent me) {
        int x,y;
        dragging = false;
        if(pressed == true) {
            pressed = false;
            x=me.getX(); y=me.getY();
            double yy = (getSize().height/2.0-y);
            double xx = (x-getSize().width/2.0) ;
            newValue = 90 - (int) (180/Math.PI*Math.atan(yy/xx) );
            if (xx<0) newValue+= 180;
            value = newValue ;
            repaint();
            if(actionListener!=null)
                actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "OneImpuls"));
        }
    }

    public static void main (String args[]) {
        JFrame f = new JFrame("Test") ;
        f.add(new Galka()) ;
        f.setSize(300,300) ;
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
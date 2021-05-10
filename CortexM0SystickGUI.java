import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

/**
 * Kamil Jamros
 * 20 maj 2018
 * Programowanie w Języku Java
 */
public class CortexM0SystickGUI extends JFrame implements ActionListener
{   int k, burstValue;
    int initialBurstNumber = 10; //poczatkowa liczba impulsow
    JButton oneImpuls, stoImpuls, readControlStatus, kImpuls, burst, continous;
    JRadioButton enableFlag,countFlag,tickintFlag,genOnOff;
    JTextField rejRVR, rejCVR, kImplusText, noOfBurstField;
    JSlider noOfBurst;
    CortexM0SysTick myDemoCounter;
    PulseGenerator myGen;
    
final static byte BURST_MODE = 0;
final static byte CONTINOUS_MODE = 1;
    /**
     * Constructor for objects of class CortexM0SystickGUI
     */
    public CortexM0SystickGUI()
    {
        myDemoCounter = new CortexM0SysTick();
        //myDemoCounter.setEnableFlag(true);
        setSize(1000,700);
        setTitle("Systick Application");
        
        setVisible(true);
        designGUI() ;
        myGen = new PulseGenerator(); 
        myGen.addActionListener(this);
        myGen.setPulseCount(initialBurstNumber);
        k=0;
    }
    
    void designGUI() {

        setLayout(new BorderLayout());
        
        //dodanie paneli
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu infoMenu = new JMenu("Info");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e-> {
            myGen.killThread();
            dispose();
            System.exit(0);
        });
        JMenuItem infoItem = new JMenuItem("Informacje");
        JFrame frame = new JFrame("JOptionPane showMessageDialog example");
        infoItem.addActionListener(e-> {
          JOptionPane.showMessageDialog(frame, "SysTick Timer GUI\nKamil Jamros\n226821","Informacje",JOptionPane.INFORMATION_MESSAGE);
        });
        infoMenu.add(infoItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        menuBar.add(infoMenu);
        JPanel lewe, centralne, prawe, dolne,gorne;
        lewe = new JPanel();
        centralne = new JPanel();
        prawe = new JPanel();
        dolne = new JPanel();
        gorne = new JPanel();
        
        lewe.setBackground(new Color(0,201,201));
        centralne.setBackground(new Color(0,201,201));
        prawe.setBackground(new Color(0,201,201));
        gorne.setBackground(new Color(0,201,201));
        dolne.setBackground(new Color(0,201,201));
        //przyporządkowanie pozycji paneli
        add(lewe, BorderLayout.WEST); 
        add(prawe, BorderLayout.EAST); 
        add(centralne, BorderLayout.CENTER);
        add(dolne, BorderLayout.SOUTH);
        add(gorne, BorderLayout.NORTH);
        
        //przyporządkowanie siatek poszczególnym panelom
        
        lewe.setLayout(new GridLayout(0,2));
        prawe.setLayout(new GridLayout(6,0));
        dolne.setLayout(new GridLayout(0,4));
        gorne.setLayout(new BorderLayout());
        centralne.setLayout(new GridLayout(3,0));
        //Tworzenie tekstów, pól tekstowych i przycisków
        // OPISY
        JLabel opisCVR = new JLabel("Rejestr CVR") ;
        JLabel opisRVR = new JLabel("Rejestr RVR") ;
        
        JLabel generatorText = new JLabel("Ustaw okres sygnału") ;
        JLabel opisBurst = new JLabel ("Ilość wysyłanych impuslów w paczce");
        //JLabel opisGeneratora = new JLabel("Generator") ;
       
       //naglowek
        JLabel Naglowek = new JLabel("SysTick Timer GUI",SwingConstants.CENTER);
        Font font = new Font("Times New Roman",Font.BOLD,22);
        Naglowek.setFont(font);
        Naglowek.setForeground(Color.RED);
        
        // tworzenie pol tekstowych
        rejRVR = new JTextField(10) ;
        rejRVR.setBackground(new Color(0,201,201));
        rejCVR = new JTextField(10) ;
        rejCVR.setBackground(new Color(0,201,201));
        kImplusText =new JTextField(10);
        kImplusText.setBackground(new Color(0,201,201));
        noOfBurstField = new JTextField(10);
        noOfBurstField.setBackground(new Color(0,201,201));
       
        //dzialanie pol tekstowych
        //uzycie wyrazenia lambda
        rejRVR.addActionListener(e-> {
            try{
            int value = Integer.parseInt(rejRVR.getText());
            myDemoCounter.setRVR(value);
            displayCouterState();
        } catch(NumberFormatException exp) {rejRVR.setText("Błędny format"); }
        });  
        
        rejCVR.addActionListener(e-> {
            try{
            int value = Integer.parseInt(rejCVR.getText());
            myDemoCounter.setCVR(value);
            displayCouterState();
        }catch(NumberFormatException exp) {rejRVR.setText("Błędny format"); }
        });
        
        kImplusText.addActionListener(e-> {
            try{
            k = Integer.parseInt(kImplusText.getText());
            displayCouterState();
        }catch(NumberFormatException exp) {kImplusText.setText("Błędny format"); }
        });  
        // tooltipy do pol tekstowych
        rejCVR.setToolTipText("Wpisz wartość do rejestru CVR");
        rejRVR.setToolTipText("Wpisz wartość do rejestru RVR"); 
        kImplusText.setToolTipText("Wpisz ilość impulsów do wysłania");
        //tworzenie przyciskow
        
        oneImpuls = new JButton(" Wyślij 1 impuls "); 
        stoImpuls = new JButton(" Wyślij 100 impulsów");
        kImpuls = new JButton("Wyślij k impulsów");
        readControlStatus =  new JButton("Odczytaj CountFlag");
        burst = new JButton("Włączenie trybu burst");
        continous = new JButton("Włączenie trybu continous");
        //dzialanie przyciskow
        kImpuls.addActionListener(e-> {
                for(int i =0; i<k ;i++)
                myDemoCounter.impuls();
            displayCouterState();
        });
        
        readControlStatus.addActionListener(e-> {
        
            myDemoCounter.isCountFlag();
            displayCouterState();
            
        });
        // uzycie klasy anonimowej do wysyłania impulsow
        stoImpuls.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            for(int i =0; i<100;i++)
            myDemoCounter.impuls();
            displayCouterState();
        }    
        
        });
        oneImpuls.addActionListener( new ActionListener() {
        public void actionPerformed(ActionEvent e){
            myDemoCounter.impuls();
            displayCouterState();
        }    
        } ); 
        //tooltipy do przyciskow
        stoImpuls.setToolTipText("Wciśnij aby wysyłajać 100 impulsów");
        oneImpuls.setToolTipText("Wciśnij aby wysyłajać 1 impuls");
        kImpuls.setToolTipText("Wciśnij aby  wysyłajać k impulsów");
        readControlStatus.setToolTipText("Wciśnij aby odczytać status CountFlag");
        burst.setToolTipText("Wciśnij aby włączyć tryb burst");
        continous.setToolTipText("Wciśnij aby włączyć tryb continous");
        
        
        //tworzenie radio buttonow
        enableFlag = new JRadioButton("Enable Flag");
        enableFlag.setBackground(new Color(0,201,201));
        tickintFlag = new JRadioButton("Interupt Flag");
        tickintFlag.setBackground(new Color(0,201,201));
        countFlag = new JRadioButton("Count Flag");
        countFlag.setBackground(new Color(0,201,201));
        genOnOff = new JRadioButton("ON/OFF generator");
        genOnOff.setBackground(new Color(0,201,201));
        
        
        //dzialanie radio buttonow
        enableFlag.addActionListener(this);
        tickintFlag.addActionListener(this);
        //tooltipy do radio buttonow
        enableFlag.setToolTipText("Flaga Enable");
        tickintFlag.setToolTipText("Flaga włączajaca przerwanie");
        countFlag.setToolTipText("Flaga count");
        //***  ROZMIESZCZENIE ELEMENTÓW  ***
        //gorne pole tytulowe
        gorne.add(Naglowek);
        //dolne pole
        lewe.add(opisCVR, BorderLayout.NORTH) ;
        lewe.add(rejCVR,BorderLayout.SOUTH);
        lewe.add(opisRVR, BorderLayout.NORTH) ;
        lewe.add(rejRVR,BorderLayout.CENTER);
        //pole srodkowe
        
        prawe.add(enableFlag,BorderLayout.NORTH);
        prawe.add(countFlag,BorderLayout.NORTH);
        prawe.add(tickintFlag,BorderLayout.NORTH);
        prawe.add(readControlStatus,BorderLayout.NORTH);
       
        //pole centralne
        //pozniejsze miejsce generatora

        
        centralne.add(burst,BorderLayout.NORTH);
        centralne.add(continous,BorderLayout.NORTH);
        centralne.add(genOnOff,BorderLayout.NORTH);
        
        burst.addActionListener(e-> {
            
            myGen.setMode(BURST_MODE);
            if(genOnOff.isSelected() ) 
            myGen.trigger();
          displayCouterState();
        });
        
        continous.addActionListener(e-> {
            myGen.setMode(CONTINOUS_MODE);
            if(genOnOff.isSelected() )    
            myGen.trigger();
            displayCouterState();
            
            
        });
        
        genOnOff.addActionListener(e-> {
            if(genOnOff.isSelected() )
            {
            myGen.trigger();
            displayCouterState();
            //myGen.setPulseCount(3);
            }
            else
            myGen.halt();
            displayCouterState();
            
        });
        
       
        noOfBurst = new JSlider(SwingConstants.HORIZONTAL,1,1000,initialBurstNumber);
        noOfBurst.setBackground(new Color(0,201,201));
        centralne.add(opisBurst,BorderLayout.SOUTH);
        centralne.add(noOfBurst,BorderLayout.SOUTH);
        centralne.add(noOfBurstField);
        noOfBurst.addChangeListener(ce-> { // stateChange
            burstValue = noOfBurst.getValue();
            myGen.setPulseCount(burstValue);
            noOfBurstField.setText(Integer.toString(burstValue));
            
        });
        
        
        noOfBurstField.addActionListener(e-> {
            try{
            int newBurstValue = Integer.parseInt(noOfBurstField.getText());
            noOfBurst.setValue(newBurstValue);
            myGen.setPulseCount(newBurstValue);
            displayCouterState();
        }catch(NumberFormatException exp) {noOfBurstField.setText("Błędny format"); }
        });
        
        
        // wlasny komponent
        Galka okresGen = new Galka(360);
        centralne.add(generatorText);
        centralne.add(okresGen);
        okresGen.setToolTipText("Ustawianie częstotliwości wysyłanych impulsów");
        
        okresGen.addActionListener (a -> {
            //myGen.setPulseDelay()
            //myGen.setPulseDelay(okresGen.getValue()); //Działa
            myGen.setPulseDelay(okresGen.getValue()*1000/360);
        
        });
        
        genOnOff.setToolTipText("Wyłączenie/włączenie generatora impulsów");
        noOfBurst.setToolTipText("Określ ilość impulsów w paczce");
        noOfBurstField.setToolTipText("Wpisz ilość impulsów w paczce");
        //tworzenie obramowania pola dolnego (wysrodkowany napis)
        
        TitledBorder centerBorder = BorderFactory.createTitledBorder("Dodaj Impulsy");
        centerBorder.setTitleJustification(TitledBorder.CENTER);
        // tworzenie obramowania pola generatora
        TitledBorder generatorBorder = BorderFactory.createTitledBorder("Pole Generatora");
        generatorBorder.setTitleJustification(TitledBorder.CENTER);
        
        TitledBorder flagBorder = BorderFactory.createTitledBorder("Pole Flag");
        flagBorder.setTitleJustification(TitledBorder.CENTER);
        
        TitledBorder registerBorder = BorderFactory.createTitledBorder("Pole rejestrow SysTicka");
        registerBorder.setTitleJustification(TitledBorder.CENTER);
        
        // pole centralne obramowanie
        centralne.setBorder(generatorBorder);
        // pole lewe obramowanie
        lewe.setBorder(registerBorder);
        // pole srodkowe obramowanie
        prawe.setBorder(flagBorder );
        //pole dolne obramowanie
        dolne.setBorder(centerBorder);
       //pole dolne
        dolne.add(oneImpuls,BorderLayout.NORTH);
        dolne.add(stoImpuls,BorderLayout.NORTH);
        dolne.add(kImplusText,BorderLayout.NORTH);
        dolne.add(kImpuls,BorderLayout.NORTH);
        
        
      
        addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                int o = JOptionPane.showConfirmDialog(
                e.getWindow(),
                "Czy chcesz zakończyć program?",
                "Zamykanie",
                JOptionPane.YES_NO_OPTION
                );
                if(o==JOptionPane.YES_OPTION){
                    myGen.killThread();
                    dispose();
                    System.exit(0);
                //myGen.killThread();
                }
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  
                
                
                
            
            }
        });
        
        
        
        
        validate();
        
        
    }
    //metoda wyswietlajaca status rejestru CVR(flagi)
    public void displayCouterState()
    {
            rejCVR.setText("" + myDemoCounter.getCVR() );
            countFlag.setSelected(myDemoCounter.nonDestrCount());
            rejRVR.setText("" + myDemoCounter.getRVR() );
    }
    //metoda obslugujaca zdarzenia
    public void actionPerformed(ActionEvent ev){
    
        
    if(ev.getSource() == myGen)
    {
        if (ev.getActionCommand() == "impuls") myDemoCounter.impuls();
    }
    if(ev.getSource() == enableFlag)
    {
        
        myDemoCounter.setEnableFlag(enableFlag.isSelected());
    }
        if(ev.getSource() == tickintFlag)
    {
        
        myDemoCounter.setTickintFlag(tickintFlag.isSelected());
    }
        
    displayCouterState();
        
    }
    
 //klasa main
 
    public static void main(String[] arg)
    {
       new CortexM0SystickGUI(); 
        
    }
    
    
    
}
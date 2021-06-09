package jginfosci.covid19.dae.visualEnv;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Box;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import jginfosci.covid19.dae.DateAndTime;
import jginfosci.covid19.dae.Environment;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.*;

/**
 *
 * @author nathanjohnson
 */
public final class WelcomePage implements ActionListener{

    private final JFrame welcomeWinFrame = basic_frame();
    private JPanel 
            parentPanel = parent_panel(),
            loadPanel = display_panel();
    
    private JButton loadButton;
    
    
    @SuppressWarnings("Unused")
    void initComponents(){
        welcomeWinFrame.setContentPane(parentPanel);
        loadPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 250));

        loadButton = new JButton("LOAD");
        loadButton.setFont(new Font("Cambria", 0, 25));
        loadButton.setBackground(Color.WHITE);
        loadButton.setForeground(JG_RED);
        loadButton.addActionListener(this);
        
        loadPanel.add(loadButton);    
        parentPanel.add(header_panel(), BorderLayout.NORTH);
        parentPanel.add(loadPanel, BorderLayout.CENTER);
        welcomeWinFrame.pack();

    }
    
    public WelcomePage(){
    welcomeWinFrame.setTitle("COVID DASHBOARD SESSION : "+DateAndTime.dataDate());
    welcomeWinFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    initComponents();
    welcomeWinFrame.pack();
    welcomeWinFrame.setLocationRelativeTo(null);
    welcomeWinFrame.setVisible(true);
    
    }

   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loadButton){
            //Environment.mapAllCurrentDatasets();
            welcomeWinFrame.dispose();
            Dashboard d = new Dashboard(); 
        }
    }
}


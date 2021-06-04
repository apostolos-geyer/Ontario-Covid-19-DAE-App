
package jginfosci.covid19.dae.visualEnv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import jginfosci.covid19.dae.DateAndTime;

/**
 *
 * @author nathanjohnson
 */
public class Dashboard implements ActionListener {
   private final JFrame dash = new JFrame();
   private JPanel parentPanel,datePanel;
   private Container content;
    
    
    void DashBoard(JPanel p){
        
        dash.setSize(new Dimension(1000,700));
        dash.setMinimumSize(new Dimension(1000,700));
        
        Container c = dash.getContentPane();
        c.setLayout(new BorderLayout());
        
        parentPanel = new JPanel(new BorderLayout());
        parentPanel.setBackground(WelcomePage.JG_RED);
        c.add(parentPanel, BorderLayout.CENTER);
        
        parentPanel.add(p, BorderLayout.NORTH);
        dash.pack();
       

        
       

        
        
    }
    
    public Dashboard(JPanel p){
    
    dash.setTitle("COVID DASHBOARD SESSION : "+DateAndTime.winTitleDate());
    dash.setDefaultCloseOperation(EXIT_ON_CLOSE);
    DashBoard(p);
    dash.pack();
    dash.setLocationRelativeTo(null);
    dash.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

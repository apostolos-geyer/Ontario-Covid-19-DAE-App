package jginfosci.covid19.dae.visualEnv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import jginfosci.covid19.dae.DateAndTime;
import static jginfosci.covid19.dae.Environment.DATASETS;
import static jginfosci.covid19.dae.visualEnv.WelcomePage.JG_RED;

/**
 *
 * @author nathanjohnson
 */
public class Dashboard implements ActionListener {
   private final JFrame dash = new JFrame();
   private JPanel parentPanel,datePanel,PHUpanel;
   private Container content;
   private final JButton PHU = new JButton();
    
    
    void DashBoard(JPanel p){
        
        
        PHUpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 250));
        PHUpanel.setPreferredSize(new Dimension(1000, 500));
        PHUpanel.setBackground(Color.WHITE);
        
        //Statistical Labels
        JLabel cases = new JLabel();
        cases.setText("Change in cases");
        cases.setFont(new Font("Cambria",Font.BOLD,14));
        cases.setHorizontalAlignment(JLabel.CENTER);
       
        JLabel Rcases = new JLabel();
        Rcases.setText("Recent cases");
        Rcases.setFont(new Font("Cambria",Font.BOLD,14));
        Rcases.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        JLabel Tcases = new JLabel();
        Tcases.setText("Total cases");
        Tcases.setFont(new Font("Cambria",Font.BOLD,14));
        Tcases.setHorizontalAlignment(JLabel.CENTER);
        JLabel TcasesText = new JLabel(""+DATASETS.get("Confirmed Covid Cases In Ontario").getTable().rowCount());
        TcasesText.setFont(new Font("Cambria",Font.BOLD,14));
        TcasesText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel deaths = new JLabel();
        deaths.setText("Total deaths");
        deaths.setFont(new Font("Cambria",Font.BOLD,14));
        deaths.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        //Statistical Panels
        JPanel changeCases = new JPanel(new BorderLayout());
        changeCases.setBackground(JG_RED);
        changeCases.setBounds(10,279,150,150);
        changeCases.add(cases, BorderLayout.NORTH);
        
        
        
        JPanel recentCases = new JPanel(new BorderLayout());
        recentCases.setBackground(JG_RED);
        recentCases.setBounds(170,279,150,150);
        recentCases.add(Rcases, BorderLayout.NORTH);
        
        JPanel totalCases = new JPanel(new BorderLayout());
        totalCases.setBackground(JG_RED);
        totalCases.setBounds(10,439,150,150);
        totalCases.add(Tcases, BorderLayout.NORTH);
        totalCases.add(TcasesText, BorderLayout.CENTER);
       
        
        
        JPanel totalDeaths = new JPanel(new BorderLayout());
        totalDeaths.setBackground(JG_RED);
        totalDeaths.setBounds(170,439,150,150);
        totalDeaths.add(deaths, BorderLayout.NORTH);
        
        
        
        
        
        dash.setSize(new Dimension(1000,700));
        dash.setMinimumSize(new Dimension(1000,700));
        
        
        Container c = dash.getContentPane();
        c.setLayout(new BorderLayout());
        
        parentPanel = new JPanel(null);
        parentPanel.setBackground(Color.WHITE);
        parentPanel.add(PHUpanel, BorderLayout.WEST);
        parentPanel.add(changeCases);
        parentPanel.add(recentCases);
        parentPanel.add(totalCases);
        parentPanel.add(totalDeaths);
        
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

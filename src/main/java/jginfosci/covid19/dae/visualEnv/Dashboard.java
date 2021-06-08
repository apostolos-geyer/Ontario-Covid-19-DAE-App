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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import jginfosci.covid19.dae.DateAndTime;
import jginfosci.covid19.dae.Environment;
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
   private JComboBox phuChoice;
   private final JButton updateButton = new JButton("UPDATE");;
    
    
    void DashBoard(JPanel p){
        
        
        PHUpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 250));
        PHUpanel.setPreferredSize(new Dimension(1000, 500));
        PHUpanel.setBackground(Color.WHITE);
        
        //Statistical Labels
        JLabel cases = new JLabel();
        cases.setText("Change in cases");
        cases.setFont(new Font("Cambria",Font.BOLD,22));
        cases.setHorizontalAlignment(JLabel.CENTER);
       
        JLabel Rcases = new JLabel();
        Rcases.setText("Recent cases");
        Rcases.setFont(new Font("Cambria",Font.BOLD,22));
        Rcases.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        JLabel Tcases = new JLabel();
        Tcases.setText("Total cases");
        Tcases.setFont(new Font("Cambria",Font.BOLD,22));
        Tcases.setHorizontalAlignment(JLabel.CENTER);
        JLabel TcasesText = new JLabel(""+DATASETS.get("Confirmed Covid Cases In Ontario").getTable().rowCount());
        TcasesText.setFont(new Font("Cambria",Font.BOLD,30));
        TcasesText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel deaths = new JLabel();
        deaths.setText("Total deaths");
        deaths.setFont(new Font("Cambria",Font.BOLD,22));
        deaths.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        //Statistical Panels
        JPanel changeCases = new JPanel(new BorderLayout());
        changeCases.setBackground(JG_RED);
        changeCases.setBounds(10,234,200,200);
        changeCases.add(cases, BorderLayout.NORTH);
        
        
        
        JPanel recentCases = new JPanel(new BorderLayout());
        recentCases.setBackground(JG_RED);
        recentCases.setBounds(215,234,200,200);
        recentCases.add(Rcases, BorderLayout.NORTH);
        
        JPanel totalCases = new JPanel(new BorderLayout());
        totalCases.setBackground(JG_RED);
        totalCases.setBounds(10,439,200,200);
        totalCases.add(Tcases, BorderLayout.NORTH);
        totalCases.add(TcasesText, BorderLayout.CENTER);
       
        
        
        JPanel totalDeaths = new JPanel(new BorderLayout());
        totalDeaths.setBackground(JG_RED);
        totalDeaths.setBounds(215,439,200,200);
        totalDeaths.add(deaths, BorderLayout.NORTH);
        
        //ComboBox; Drop down menu
       String[] phuList = DATASETS.get("Confirmed Covid Cases In Ontario")
                    .getTable()
                    .column("Reporting_PHU")
                    .unique()
                    .asList()
                    .toArray(new String[0]);
       
        phuChoice = new JComboBox(phuList);
        phuChoice.setEditable(true);
        phuChoice.setBounds(500,234,300,28);
        phuChoice.addActionListener(this);
        
        //Update Button
        updateButton.setFont(new Font("Cambria", 0, 25));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(JG_RED);
        updateButton.addActionListener(this);
        updateButton.setBounds(500,409,150,50);
        
        
        
        
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
        parentPanel.add(phuChoice);
        parentPanel.add(updateButton);
        
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
        if(e.getSource()==phuChoice){
            System.out.println(phuChoice.getSelectedItem());
        }
        if(e.getSource()==updateButton){
            Environment.mapAllDatasetsUpdate();
            
           
        }
    }
    
}

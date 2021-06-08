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
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
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
   private JPanel parentPanel,datePanel,PHUpanel,statsPanel;
   private Container content;
   private final JButton PHU = new JButton();
   private JComboBox phuChoice;
   private JMenuBar menuBar;
   private final JButton updateButton = new JButton("UPDATE"),downloadButton = new JButton("DOWNLOAD");
    
    
    void DashBoard(JPanel header){
        
        PHUpanel = new JPanel(new BorderLayout());
        PHUpanel.setPreferredSize(new Dimension(1380,550));
        PHUpanel.setBackground(Color.BLUE);
        
        statsPanel = new JPanel(new GridLayout(2,2,5,5));
        statsPanel.setPreferredSize(new Dimension(500,500));
        statsPanel.setBackground(Color.WHITE);
        
       
        
        //Statistical Labels
        JLabel cases = new JLabel();
        cases.setText("Change in cases");
        cases.setFont(new Font("Cambria",Font.BOLD,23));
        cases.setForeground(JG_RED);
        cases.setHorizontalAlignment(JLabel.CENTER);
       
        JLabel Rcases = new JLabel();
        Rcases.setText("Recent cases");
        Rcases.setFont(new Font("Cambria",Font.BOLD,23));
        Rcases.setForeground(JG_RED);
        Rcases.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        JLabel Tcases = new JLabel();
        Tcases.setText("Total cases");
        Tcases.setFont(new Font("Cambria",Font.BOLD,23));
        Tcases.setForeground(JG_RED);
        Tcases.setHorizontalAlignment(JLabel.CENTER);
        JLabel TcasesText = new JLabel(""+DATASETS.get("Confirmed Covid Cases In Ontario").getTable().rowCount());
        TcasesText.setForeground(JG_RED);
        TcasesText.setFont(new Font("Cambria",Font.BOLD,30));
        TcasesText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel deaths = new JLabel();
        deaths.setText("Total deaths");
        deaths.setFont(new Font("Cambria",Font.BOLD,23));
        deaths.setForeground(JG_RED);
        deaths.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        //Statistical Panels
        JPanel changeCases = new JPanel(new BorderLayout());
        changeCases.setBackground(new Color(0xC0C0C0));
        changeCases.setBounds(10,234,200,200);
        changeCases.add(cases, BorderLayout.NORTH);
        
        
        
        JPanel recentCases = new JPanel(new BorderLayout());
        recentCases.setBackground(new Color(0xC0C0C0));
        recentCases.setBounds(215,234,200,200);
        recentCases.add(Rcases, BorderLayout.NORTH);
        
        JPanel totalCases = new JPanel(new BorderLayout());
        totalCases.setBackground(new Color(0xC0C0C0));
        totalCases.setBounds(10,439,200,200);
        totalCases.add(Tcases, BorderLayout.NORTH);
        totalCases.add(TcasesText, BorderLayout.CENTER);
       
        
        
        JPanel totalDeaths = new JPanel(new BorderLayout());
        totalDeaths.setBackground(new Color(0xC0C0C0));
        totalDeaths.setBounds(215,439,200,200);
        totalDeaths.add(deaths, BorderLayout.NORTH);
        
        //ComboBox; Drop down menu
       List<String> PHU_List = new ArrayList<>(){{
            add("Ontario");
            DATASETS.get("Confirmed Covid Cases In Ontario")
                    .getTable()
                    .column("Reporting_PHU")
                    .unique()
                    .asList()
                    .forEach(x->{
                    add(x.toString());
                    });
        }};





        phuChoice = new JComboBox(PHU_List.toArray(new String[0]));
    
        phuChoice.setEditable(true);
        phuChoice.setBounds(10,205,300,28);
        phuChoice.addActionListener(this);
        
        //Update/Download Button
        updateButton.setFont(new Font("Cambria", 0, 25));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(JG_RED);
        updateButton.addActionListener(this);
        updateButton.setBounds(500,205,150,50);
        
        downloadButton.setFont(new Font("Cambria", 0, 25));
        downloadButton.setBackground(Color.WHITE);
        downloadButton.setForeground(JG_RED);
        downloadButton.addActionListener(this);
        downloadButton.setBounds(655,205,165,50);
        
        //menu bar
        menuBar = new JMenuBar();
        
        
        
        
        
        
        dash.setSize(new Dimension(1380,800));
        //dash.setMinimumSize(new Dimension(1380,800));
        dash.setJMenuBar(menuBar);
        
        
        Container c = dash.getContentPane();
        c.setLayout(new BorderLayout());
        
        parentPanel = new JPanel(new BorderLayout());
        parentPanel.setBackground(Color.WHITE);
        
        
        //statistics panel 
        statsPanel.add(changeCases);
        statsPanel.add(recentCases);
        statsPanel.add(totalCases);
        statsPanel.add(totalDeaths);
        
        //PHU panel
        PHUpanel.add(statsPanel, BorderLayout.WEST);
        PHUpanel.add(updateButton);
        PHUpanel.add(downloadButton);
        
        
        //Parent Panel
        
        
        c.add(parentPanel, BorderLayout.CENTER);
        
        
        parentPanel.add(PHUpanel, BorderLayout.SOUTH);
        
        parentPanel.add(header, BorderLayout.NORTH);
        dash.pack();
       

        
       

        
        
    }
    
    public Dashboard(JPanel p){
    
    dash.setTitle("COVID DASHBOARD SESSION : "+DateAndTime.dataDate());
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

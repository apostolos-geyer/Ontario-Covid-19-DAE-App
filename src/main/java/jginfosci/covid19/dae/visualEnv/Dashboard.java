package jginfosci.covid19.dae.visualEnv;

import java.awt.*;
import java.awt.event.*;
import java.util.List; import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import jginfosci.covid19.dae.DateAndTime;
import jginfosci.covid19.dae.Environment;
import static jginfosci.covid19.dae.Environment.DATASETS;
import static jginfosci.covid19.dae.Environment.getRegionList;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.*;

/**
 *
 * @author nathanjohnson
 */
public class Dashboard implements ActionListener {
   private final JFrame dash = basic_frame();
   private JTabbedPane pages;
   private JPanel 
           parentPanel = parent_panel(),
           displayPanel = display_panel(),
           statsPanel;
   private final JButton PHU = new JButton();
   private final JComboBox REGION_LIST = new JComboBox(getRegionList().toArray(new String[0]));
   private JMenuBar menuBar;
   private final JButton updateButton = new JButton("UPDATE"),downloadButton = new JButton("DOWNLOAD");
    
    
    void initComponents(){
        
        dash.setContentPane(parentPanel);
        parentPanel.setLayout(new BorderLayout());
        
        displayPanel.setLayout(new BorderLayout());
        UIManager.put("TabbedPane.underlineColor", JG_RED);
        pages = new JTabbedPane(){{
            setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, 25));
            setBackground(new Color(0xF5BCBC));
            add(" TODAYS SNAPSHOT ", new JLabel("what"));
            add(" CASES ", new JLabel("what"));
            add(" DEMOGRAPHICS ", new JLabel("what"));
            add(" REGIONS ", new JLabel("what"));
            add(" VACCINATION ", new JLabel("what"));
        }};
        
        
        displayPanel.add(pages, BorderLayout.CENTER);
        

        parentPanel.add(header_panel(), BorderLayout.NORTH);
        parentPanel.add(displayPanel, BorderLayout.CENTER);
        dash.pack();
        /*
        statsPanel = new JPanel(new GridLayout(2,2,5,5));
        statsPanel.setPreferredSize(new Dimension(500,600));
        
       
        
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
        changeCases.setBackground(new Color(0xFCFCFC));
        changeCases.setBounds(10,234,200,200);
        changeCases.add(cases, BorderLayout.NORTH);
        
        
        
        JPanel recentCases = new JPanel(new BorderLayout());
        recentCases.setBackground(new Color(0xFCFCFC));
        recentCases.setBounds(215,234,200,200);
        recentCases.add(Rcases, BorderLayout.NORTH);
        
        JPanel totalCases = new JPanel(new BorderLayout());
        totalCases.setBackground(new Color(0xFCFCFC));
        totalCases.setBounds(10,439,200,200);
        totalCases.add(Tcases, BorderLayout.NORTH);
        totalCases.add(TcasesText, BorderLayout.CENTER);
       
        
        
        JPanel totalDeaths = new JPanel(new BorderLayout());
        totalDeaths.setBackground(new Color(0xFCFCFC));
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
                    .forEach(x->add(x.toString()));
       }};
        REGION_LIST = new JComboBox(PHU_List.toArray(new String[0]));

        REGION_LIST.setEditable(true);
        REGION_LIST.setBounds(10,205,300,28);
        REGION_LIST.addActionListener(this);
        
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

        
        
        
        
        
        
        
        //statistics panel 
        statsPanel.add(changeCases);
        statsPanel.add(recentCases);
        statsPanel.add(totalCases);
        statsPanel.add(totalDeaths);
        
        //PHU panel
        displayPanel.add(statsPanel, BorderLayout.WEST);
        displayPanel.add(updateButton);
        displayPanel.add(downloadButton);
        
        
        //Parent Panel 
        */
        
    }
    
    public Dashboard(){
    dash.setTitle("COVID DASHBOARD SESSION : "+DateAndTime.dataDate());
    dash.setDefaultCloseOperation(EXIT_ON_CLOSE);
    initComponents();
    dash.setLocationRelativeTo(null);
    dash.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==REGION_LIST){
            System.out.println(REGION_LIST.getSelectedItem());
        }
        if(e.getSource()==updateButton){
            Environment.mapAllDatasetsUpdate();
            
           
        }
    }
    
}

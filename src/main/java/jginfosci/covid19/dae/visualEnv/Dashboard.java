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
           statsPanel,snapshotPanel=new JPanel(),graphPanel=new JPanel(),updatePanel=new JPanel(),
           casePanel = new JPanel(),totalCasesPanel = new JPanel();
   
   private final JButton PHU = new JButton();
   private final JComboBox REGION_LIST = new JComboBox(getRegionList().toArray(new String[0]));
   private JMenuBar menuBar;
   private final JButton updateButton = new JButton("UPDATE"),downloadButton = new JButton("DOWNLOAD");
    
    
    void initComponents(){
        
        dash.setContentPane(parentPanel);
        parentPanel.setLayout(new BorderLayout());
        
        snapshotSetup();
        /*casesSetup();
        demographicsSetup();
        regionsSetup();
        vaccinationSetup();*/
        
        displayPanel.setLayout(new BorderLayout());
        UIManager.put("TabbedPane.underlineColor", JG_RED);
        pages = new JTabbedPane(){{
            setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, 25));
            setBackground(new Color(0xF5BCBC));
            add(" TODAYS SNAPSHOT ", snapshotPanel );
            add(" CASES ", new JLabel("what"));
            add(" DEMOGRAPHICS ", new JLabel("what"));
            add(" REGIONS ", new JLabel("what"));
            add(" VACCINATION ", new JLabel("what"));
        }};
        
        
        displayPanel.add(pages, BorderLayout.CENTER);
        

        parentPanel.add(header_panel(), BorderLayout.NORTH);
        parentPanel.add(displayPanel, BorderLayout.CENTER);
        dash.pack();
        
        
        
       
        
        
        
        
    }
    
    public void snapshotSetup(){
        //Statistical Labels
        statsPanel = new JPanel();
        BoxLayout bLayout = new BoxLayout(statsPanel, BoxLayout.Y_AXIS);
        statsPanel.setPreferredSize(new Dimension(500, 500));
        statsPanel.setLayout(bLayout);
        
        snapshotPanel.setLayout(new GridLayout(1,2));
        
        //BOX 1
        JLabel caseInfo = new JLabel();
        caseInfo.setText("Case Info:");
        caseInfo.setFont(new Font("Cambria",Font.BOLD,23));
        caseInfo.setForeground(JG_RED);
        caseInfo.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel lastUpdate = new JLabel();
        lastUpdate.setText("LAST UPDATED FULL FORMAT");
        lastUpdate.setFont(new Font("Cambria",Font.BOLD,23));
        lastUpdate.setForeground(JG_RED);
        lastUpdate.setHorizontalAlignment(JLabel.CENTER);
        
        updateButton.setFont(new Font("Cambria", 0, 25));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(JG_RED);
        updateButton.addActionListener(this);
       
        
        
        //BOX 2
        casePanel.setLayout(new GridLayout(1,2));
        casePanel.setBackground(new Color(0xFCFCFC));
        casePanel.setPreferredSize(new Dimension(300, 180));
        
        JPanel newCases = new JPanel();
        //BoxLayout b2 = new BoxLayout(newCases, BoxLayout.Y_AXIS);
        newCases.setLayout(new GridLayout(3,1));
        
        JLabel ncText = new JLabel("New Cases");
        ncText.setFont(new Font("Cambria", Font.BOLD, 30));
        ncText.setForeground(JG_RED);
        ncText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel ncUpdate = new JLabel("number reported on last updated date");
        ncUpdate.setFont(new Font("Cambria", Font.BOLD, 23));
        ncUpdate.setForeground(JG_RED);
        ncUpdate.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel ncChange = new JLabel("change since previous period");
        ncChange.setFont(new Font("Cambria", Font.BOLD, 23));
        ncChange.setForeground(JG_RED);
        ncChange.setHorizontalAlignment(JLabel.CENTER);
        
        newCases.add(ncText);
        newCases.add(ncUpdate);
        newCases.add(ncChange);
        
        
        JPanel recentCases = new JPanel();
        //BoxLayout b3 = new BoxLayout(recentCases, BoxLayout.Y_AXIS);
        recentCases.setLayout(new GridLayout(3,1));
        
        JLabel rcText = new JLabel("Recent Cases");
        rcText.setFont(new Font("Cambria", Font.BOLD, 30));
        rcText.setForeground(JG_RED);
        rcText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel rcUpdate = new JLabel("number reported on last updated date");
        rcUpdate.setFont(new Font("Cambria", Font.BOLD, 23));
        rcUpdate.setForeground(JG_RED);
        rcUpdate.setHorizontalAlignment(JLabel.CENTER);

        JLabel rcChange = new JLabel("change since previous period");
        rcChange.setFont(new Font("Cambria", Font.BOLD, 23));
        rcChange.setForeground(JG_RED);
        rcChange.setHorizontalAlignment(JLabel.CENTER);

        recentCases.add(rcText);
        recentCases.add(rcUpdate);
        recentCases.add(rcChange);
        
        
        casePanel.add(newCases);
        casePanel.add(recentCases);
        
        //BOX 3
        totalCasesPanel.setLayout(new GridLayout(1,2));
        totalCasesPanel.setBackground(new Color(0xFCFCFC));
        totalCasesPanel.setPreferredSize(new Dimension(300, 200));
        
        JPanel totalCases = new JPanel();
        //BoxLayout b2 = new BoxLayout(newCases, BoxLayout.Y_AXIS);
        totalCases.setLayout(new GridLayout(4,1));
        
        JLabel tcText = new JLabel("Total Cases");
        tcText.setFont(new Font("Cambria", Font.BOLD, 30));
        tcText.setForeground(JG_RED);
        tcText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel current = new JLabel("currently active");
        current.setFont(new Font("Cambria", Font.BOLD, 23));
        current.setForeground(JG_RED);
        current.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel resolved = new JLabel("resolved");
        resolved.setFont(new Font("Cambria", Font.BOLD, 23));
        resolved.setForeground(JG_RED);
        resolved.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel deathCount = new JLabel("deaths");
        deathCount.setFont(new Font("Cambria", Font.BOLD, 23));
        deathCount.setForeground(JG_RED);
        deathCount.setHorizontalAlignment(JLabel.CENTER);
        
        totalCases.add(tcText);
        totalCases.add(current);
        totalCases.add(resolved);
        totalCases.add(deathCount);
        
        
        JPanel numbers = new JPanel();
        //BoxLayout b3 = new BoxLayout(recentCases, BoxLayout.Y_AXIS);
        numbers.setLayout(new GridLayout(4,1));
        
        JLabel tcNumber = new JLabel(""+DATASETS.get("Confirmed Covid Cases In Ontario").getTable().rowCount());
        tcNumber.setFont(new Font("Cambria", Font.BOLD, 30));
        tcNumber.setForeground(JG_RED);
        tcNumber.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel caNumber = new JLabel("number  %total");
        caNumber.setFont(new Font("Cambria", Font.BOLD, 23));
        caNumber.setForeground(JG_RED);
        caNumber.setHorizontalAlignment(JLabel.CENTER);

        JLabel resolvedNumber = new JLabel("number  %total");
        resolvedNumber.setFont(new Font("Cambria", Font.BOLD, 23));
        resolvedNumber.setForeground(JG_RED);
        resolvedNumber.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel deathsNumber = new JLabel("number  %total");
        deathsNumber.setFont(new Font("Cambria", Font.BOLD, 23));
        deathsNumber.setForeground(JG_RED);
        deathsNumber.setHorizontalAlignment(JLabel.CENTER);

        numbers.add(tcNumber);
        numbers.add(caNumber);
        numbers.add(resolvedNumber);
        numbers.add(deathsNumber);
        
        
        totalCasesPanel.add(totalCases);
        totalCasesPanel.add(numbers);
        

        
        
        
        
        
        
        
        
        
        
        
        
        
       
       
        JLabel deaths = new JLabel();
        deaths.setText("Total deaths");
        deaths.setFont(new Font("Cambria",Font.BOLD,23));
        deaths.setForeground(JG_RED);
        deaths.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        //Statistical Panels
        updatePanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        updatePanel.setBackground(new Color(0xFCFCFC));
        updatePanel.setPreferredSize(new Dimension(300,25));
        updatePanel.add(caseInfo);
        updatePanel.add(lastUpdate);
        updatePanel.add(updateButton);
        
        
        
        
        
        
        /*JPanel totalCases = new JPanel(new BorderLayout());
        totalCases.setBackground(new Color(0xFCFCFC));
        totalCases.setPreferredSize(new Dimension(200,200));
        totalCases.add(Tcases, BorderLayout.NORTH);
        totalCases.add(TcasesText, BorderLayout.CENTER);*/
       
        
        
     
        
        
        
        //Update/Download Button
        
       
        
        downloadButton.setFont(new Font("Cambria", 0, 25));
        downloadButton.setBackground(Color.WHITE);
        downloadButton.setForeground(JG_RED);
        downloadButton.addActionListener(this);
        
        
        
        //statistics panel 
        statsPanel.add(updatePanel);
        statsPanel.add(casePanel);
        statsPanel.add(totalCasesPanel);
       
        //graph panel
        graphPanel.setBackground(Color.WHITE);
        
        //PHU panel
        snapshotPanel.add(statsPanel);
        snapshotPanel.add(graphPanel);
        
       
        
        
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

    private void casesSetup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void demographicsSetup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void regionsSetup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void vaccinationSetup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

package jginfosci.covid19.dae.visualEnv;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List; import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.BevelBorder;
import jginfosci.covid19.dae.DateAndTime;
import jginfosci.covid19.dae.Environment;
import static jginfosci.covid19.dae.Environment.DATASETS;
import static jginfosci.covid19.dae.Environment.getRegionList;
import static jginfosci.covid19.dae.Environment.tableFor;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.*;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.api.TimeSeriesPlot;
import tech.tablesaw.plotly.components.Config;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Margin;
import tech.tablesaw.plotly.traces.ScatterTrace;

/**
 *
 * @author nathanjohnson
 */
public class Dashboard implements ActionListener {
    Table confirmedCOVID = tableFor("Confirmed Covid Cases In Ontario")
                           .sortOn("Case_Reported_Date"); 
    
    LocalDate mostRecentEntry = (confirmedCOVID.dateColumn("Case_Reported_Date")
                                .get(confirmedCOVID.rowCount()-1));
    
   private final JFrame dash = basic_frame();
   private JTabbedPane pages;
   private JPanel 
           parentPanel = parent_panel(),
           displayPanel = display_panel(),
           statsPanel,
           snapshotPanel=new JPanel(),
           graphPanel=new JPanel(),
           snapshotTitlePanel=new JPanel(),
           casePanel = new JPanel(),
           totalCasesPanel = new JPanel();
   
   private final JButton PHU = new JButton();
   private final JComboBox REGION_LIST = new JComboBox(getRegionList().toArray(new String[0]));
   private JMenuBar menuBar;
   private final JButton updateButton = new JButton("UPDATE"),downloadButton = new JButton("DOWNLOAD");
    
    
    void initComponents(){
        
        dash.setContentPane(parentPanel);
        parentPanel.setLayout(new BorderLayout());
        
        updateButton.setFont(Cambria(0,25));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(JG_RED);
        updateButton.addActionListener(this);
        
        downloadButton.setFont(new Font("Cambria", 0, 25));
        downloadButton.setBackground(Color.WHITE);
        downloadButton.setForeground(JG_RED);
        downloadButton.addActionListener(this);
        
        snapshotSetup();
        /*casesSetup();
        demographicsSetup();
        regionsSetup();
        vaccinationSetup();*/
        
        displayPanel.setLayout(new BorderLayout());
        UIManager.put("TabbedPane.underlineColor", JG_RED);
        pages = new JTabbedPane(){{
            setFont(Cambria(3,25));
            setBackground(new Color(0xF5BCBC));
            add(" TODAYS SNAPSHOT ", snapshotPanel);
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
        double totalCasesNum = confirmedCOVID.rowCount();
        
        double newCasesNum = confirmedCOVID
                .where(confirmedCOVID.dateColumn("Case_Reported_Date")
                        .isEqualTo(mostRecentEntry.minusDays(1)))
                            .rowCount();
        
        double newCasesYesterday = confirmedCOVID
                .where(confirmedCOVID.dateColumn("Case_Reported_Date")
                        .isEqualTo(mostRecentEntry.minusDays(2)))
                            .rowCount();
        
        double newCasesChange = ((newCasesNum-newCasesYesterday)/newCasesYesterday)*100;
        
        
        double recentCasesNum = confirmedCOVID
                .where(confirmedCOVID.dateColumn("Case_Reported_Date")
                    .isBetweenIncluding(mostRecentEntry.minusDays(8),mostRecentEntry.minusDays(1)))
                        .rowCount();
        
        double recentCasesPrevWeek = confirmedCOVID
                .where(confirmedCOVID.dateColumn("Case_Reported_Date")
                    .isBetweenIncluding(mostRecentEntry.minusDays(16), mostRecentEntry.minusDays(9)))
                        .rowCount();
        
        double recentCasesChange = ((recentCasesNum-recentCasesPrevWeek)/recentCasesPrevWeek)*100;
        
        
        double currentlyActiveCases = confirmedCOVID
                .where(confirmedCOVID.stringColumn("Outcome1")
                        .isEqualTo("Not Resolved")).rowCount();
        
        double pctActive = (currentlyActiveCases/totalCasesNum)*100;
        
        double resolvedCases = confirmedCOVID
                .where(confirmedCOVID.stringColumn("Outcome1")
                        .isEqualTo("Resolved")).rowCount();
        
        double pctResolved = (resolvedCases/totalCasesNum)*100;
        
        double fatalCases = confirmedCOVID
                .where(confirmedCOVID.stringColumn("Outcome1")
                        .isEqualTo("Fatal")).rowCount();
        
        double pctFatal = (fatalCases/totalCasesNum)*100;
        

                
        
        
        
        
        
        //Statistical Labels
        snapshotPanel.setLayout(new GridLayout(1,2));
        
        statsPanel = new JPanel();
        BoxLayout bLayout = new BoxLayout(statsPanel, BoxLayout.Y_AXIS);
        statsPanel.setPreferredSize(new Dimension(690, 400));
        statsPanel.setLayout(bLayout);
        statsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        
        JLabel todaysSnapshotLabel = new JLabel(" Todays Snapshot: ");
        todaysSnapshotLabel.setFont(Cambria(1,24));
        
        JLabel lastUpdate = new JLabel(""+DateAndTime.displayFormat.format(mostRecentEntry));

        lastUpdate.setFont(Cambria(2,24));
        
        
        
        //Statistical Panels
        BoxLayout titleBoxLayout = new BoxLayout(snapshotTitlePanel, BoxLayout.LINE_AXIS);
        snapshotTitlePanel.setLayout(titleBoxLayout);
        snapshotTitlePanel.setBackground(new Color(0xFCFCFC));
        snapshotTitlePanel.setPreferredSize(new Dimension(700, 75));
        snapshotTitlePanel.add(todaysSnapshotLabel);
        snapshotTitlePanel.add(lastUpdate);
        snapshotTitlePanel.add(Box.createHorizontalStrut(30));
        snapshotTitlePanel.add(updateButton);
        snapshotTitlePanel.add(Box.createHorizontalStrut(20));

        casePanel.setBackground(new Color(0xFCFCFC));
        casePanel.setPreferredSize(new Dimension(700,200));
        BoxLayout casePanelLayout = new BoxLayout(casePanel, BoxLayout.LINE_AXIS);
        casePanel.setLayout(casePanelLayout);
        
        JPanel newCases = new JPanel(new GridLayout(3,1));
        newCases.setBorder(BorderFactory.createCompoundBorder
        (BorderFactory.createLineBorder(JG_RED,2,true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        newCases.setPreferredSize(new Dimension(700/2,200));
        
        
        JLabel ncText = new JLabel("New Cases");
        ncText.setFont(Cambria(3,45));
        ncText.setForeground(JG_RED);
        ncText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel ncUpdate = new JLabel(""+(int)newCasesNum);
        ncUpdate.setFont(Cambria(1,40));
        ncUpdate.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel ncChange = new JLabel
        (String.format("%.2f", newCasesChange)+"% change since yesterday");
        
        ncChange.setFont(Cambria(0, 20));
        ncChange.setHorizontalAlignment(JLabel.CENTER);
        
        newCases.add(ncText);
        newCases.add(ncUpdate);
        newCases.add(ncChange);
        
        
        JPanel recentCases = new JPanel();
        recentCases.setPreferredSize(new Dimension(700/2,200));
        recentCases.setLayout(new GridLayout(3,1));
        recentCases.setBorder(BorderFactory.createCompoundBorder
        (BorderFactory.createLineBorder(JG_RED,2,true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        
        JLabel rcText = new JLabel("Recent Cases");
        rcText.setFont(Cambria(3,45));
        rcText.setForeground(JG_RED);
        rcText.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel rcUpdate = new JLabel(""+(int)recentCasesNum);
        rcUpdate.setFont(Cambria(1,40));
        rcUpdate.setHorizontalAlignment(JLabel.CENTER);

        JLabel rcChange = new JLabel
        (String.format("%.2f", recentCasesChange)+"% change since last week");
        rcChange.setFont(Cambria(0,20));
        rcChange.setHorizontalAlignment(JLabel.CENTER);

        recentCases.add(rcText);
        recentCases.add(rcUpdate);
        recentCases.add(rcChange);
       
        casePanel.add(newCases);
        casePanel.add(Box.createVerticalGlue());
        casePanel.add(recentCases);
        
        //BOX 3
        totalCasesPanel.setLayout(new GridLayout(1,2));
        totalCasesPanel.setBackground(new Color(0xFCFCFC));
        totalCasesPanel.setPreferredSize(new Dimension(700,200));
        totalCasesPanel.setBorder(BorderFactory.createCompoundBorder
        (BorderFactory.createLineBorder(JG_RED,2,true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        newCases.setPreferredSize(new Dimension(700/2,200));
        
        JPanel totalCases = new JPanel();
        
        totalCases.setLayout(new GridLayout(4,1));
        
        JLabel tcText = new JLabel("Total Cases: ");
        tcText.setFont(Cambria(1,35));
        tcText.setForeground(JG_RED);
        tcText.setHorizontalAlignment(JLabel.LEFT);
        
        
        
        JLabel current = new JLabel("Currently Active:");
        current.setFont(Cambria(1,35));
        current.setForeground(JG_RED);
        current.setHorizontalAlignment(JLabel.LEFT);
        

        
        JLabel resolved = new JLabel("Resolved: ");
        resolved.setFont(Cambria(1,35));
        resolved.setForeground(JG_RED);
        resolved.setHorizontalAlignment(JLabel.LEFT);
        
        
        JLabel deathCount = new JLabel("Fatal: ");
        deathCount.setFont(Cambria(1,35));
        deathCount.setForeground(JG_RED);
        deathCount.setHorizontalAlignment(JLabel.LEFT);
        
        totalCases.add(tcText);
        totalCases.add(current);
        totalCases.add(resolved);
        totalCases.add(deathCount);
        
        
        JPanel numbers = new JPanel(new GridLayout(4,1));
        JPanel activeStats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel resolvedStats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel fatalityStats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel tcNumber = new JLabel(""+(int)totalCasesNum);
        tcNumber.setFont(Cambria(1,35));
        tcNumber.setHorizontalAlignment(JLabel.LEFT);
        
        JLabel caNumber = new JLabel(""+(int)currentlyActiveCases);
        caNumber.setFont(Cambria(1,35));
        caNumber.setHorizontalAlignment(JLabel.LEFT);
        
        JLabel caPct = new JLabel
        (String.format("    %.2f", pctActive)+"% of Total");
        caPct.setFont(Cambria(0,20));
        caPct.setHorizontalAlignment(JLabel.CENTER);
        
        activeStats.add(caNumber);
        activeStats.add(caPct);

        JLabel resolvedNumber = new JLabel(""+(int)resolvedCases);
        resolvedNumber.setFont(Cambria(1,35));
        resolvedNumber.setHorizontalAlignment(JLabel.LEFT);
        
        JLabel resPct = new JLabel      
        (String.format("    %.2f", pctResolved)+"% of Total");
        resPct.setFont(Cambria(0,20));
        resPct.setHorizontalAlignment(JLabel.CENTER);
        
        resolvedStats.add(resolvedNumber);
        resolvedStats.add(resPct);
        
        JLabel fatalNumber = new JLabel(""+(int)fatalCases);
        fatalNumber.setFont(Cambria(1,35));
        fatalNumber.setHorizontalAlignment(JLabel.LEFT);
        
        JLabel fatalPct = new JLabel      
        (String.format("    %.2f", pctFatal)+"% of Total");
        fatalPct.setFont(Cambria(0,20));
        fatalPct.setHorizontalAlignment(JLabel.CENTER);
        
        fatalityStats.add(fatalNumber);
        fatalityStats.add(fatalPct);
        

        numbers.add(tcNumber);
        numbers.add(activeStats);
        numbers.add(resolvedStats);
        numbers.add(fatalityStats);
        
        
        totalCasesPanel.add(totalCases);
        totalCasesPanel.add(numbers);

        
        
        
        
        //statistics panel 
        statsPanel.add(snapshotTitlePanel);
        statsPanel.add(casePanel);
        statsPanel.add(totalCasesPanel);
       
        //graph panel
        graphPanel.setBackground(Color.BLACK);
        graphPanel.setLayout(new BorderLayout());
        
        Table t = confirmedCOVID.xTabCounts("Case_Reported_Date");
            t.column(0).setName("date");
            t = t.sortOn("date");
            //System.out.println(t.print());
            
            //Layout figLayout = Layout.builder("Cases Per Day", "Day", "# Cases").width(690).height(400).build();
            Figure casesPerDay = TimeSeriesPlot.create("cases by day",t, "date", "Count");
            casesPerDay.setConfig(Config.builder().displayModeBar(false).build());
            casesPerDay.setLayout(Layout.builder("", "Day", "Reported Cases")
                    .margin(Margin.builder().top(0).bottom(60).left(50).right(0).build())
                        .width(600)
                        .height(400)
                        .build());

            //.build();
            /*ScatterTrace.builder(t.column("date"), t.numberColumn("Count"))        
                        .mode(ScatterTrace.Mode.LINE)
                        .build()*/
            
            
        
        
        graphPanel.add(new PlotPanel(casesPerDay
                , 680, 450){{setBackground(JG_RED);}}, BorderLayout.CENTER);
        


        
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
            this.initComponents();
          
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

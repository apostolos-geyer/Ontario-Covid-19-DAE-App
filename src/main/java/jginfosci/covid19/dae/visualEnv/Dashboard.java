package jginfosci.covid19.dae.visualEnv;

import java.awt.*;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List; import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.BevelBorder;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import jginfosci.covid19.dae.DateAndTime;
import jginfosci.covid19.dae.Environment;
import static jginfosci.covid19.dae.Environment.DATASETS;
import static jginfosci.covid19.dae.Environment.getRegionList;
import static jginfosci.covid19.dae.Environment.tableFor;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.*;
import tech.tablesaw.api.*;
import tech.tablesaw.plotly.api.TimeSeriesPlot;
import tech.tablesaw.plotly.components.*;
import tech.tablesaw.plotly.traces.ScatterTrace;
import static tech.tablesaw.aggregate.AggregateFunctions.sum;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.traces.BarTrace;
import tech.tablesaw.plotly.traces.Trace;
import tech.tablesaw.selection.Selection;

/**
 *
 * @author Nathan Johnson
 * @author Paul Geyer
 */
public class Dashboard implements ActionListener {
    Table confirmedCOVID = tableFor("Confirmed Covid Cases In Ontario")
                           .replaceColumn("Age_Group", 
                                   tableFor("Confirmed Covid Cases In Ontario")
                                           .stringColumn("Age_Group")
                                           .replaceAll("<20", "0-19")
                                           .setName("Age_Group"))
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
           totalCasesPanel = new JPanel(),
   
           casesSetupPanel = parent_panel(),
           graphSelectorPanel = display_panel(),
           casesGraphPanel = display_panel(),
           
           demographicsPanel = parent_panel(),
           ageSexMortality = display_panel(),
           newCasesDailyByAge = display_panel(),
           demographicProportions = display_panel(),
           demographicDeathProportions = display_panel(),
           
           regionsPanel = parent_panel(),
           cpcAndDeathRatePanel = display_panel(),
           currentActiveCasesPanel = display_panel();
           
   
   private JScrollPane scrollPane,scrollPane2,scrollPane3,scrollPane4;
   private JCheckBox graphCases,graphDeaths;        
   String[] region_list = getRegionList().toArray(new String[0]);
   private final JButton PHU = new JButton();
   
   private JComboBox phus, phus1,phus2,phus3,phus4,phus5;
   private JMenuBar menuBar;
   private final JButton updateButton = new JButton("UPDATE"),downloadButton = new JButton("DOWNLOAD");
    
    
    void initComponents(){
        
        dash.setContentPane(parentPanel);
        parentPanel.setLayout(new BorderLayout());
        
        updateButton.setFont(Cambria(0,25));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(JG_RED);
        updateButton.addActionListener(this);
        
        /*
        downloadButton.setFont(Cambria(0,25));
        downloadButton.setBackground(Color.WHITE);
        downloadButton.setForeground(JG_RED);
        downloadButton.addActionListener(this);
        */
        
        snapshotSetup();
        demographicsSetup();
        regionsSetup();
        
        
        displayPanel.setLayout(new BorderLayout());
        UIManager.put("TabbedPane.underlineColor", JG_RED);
        pages = new JTabbedPane(){{
            setFont(Cambria(3,25));
            setBackground(new Color(0xF5BCBC));
            add(" TODAYS SNAPSHOT ", snapshotPanel);
            add(" DEMOGRAPHIC METRICS ", demographicsPanel);
            add(" REGIONAL PERFORMANCE ", regionsPanel);
            
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
 
            Figure casesPerDay = TimeSeriesPlot.create("cases by day",t, "date", "Count");
            casesPerDay.setConfig(defaultConfig);
            casesPerDay.setLayout(Layout.builder("", "Day", "Reported Cases")
                        .margin(defaultMargin)
                        .width(600)
                        .height(550)
                        .build());     
        graphPanel.add(new PlotPanel(casesPerDay
                , 680, 580), BorderLayout.CENTER);

        snapshotPanel.add(statsPanel);
        snapshotPanel.add(graphPanel);

    }
    

    private void demographicsSetup(){   

        JPanel scrollPanel2 = new JPanel();
        scrollPanel2.setBackground(Color.WHITE);
        scrollPanel2.setPreferredSize(new Dimension(900,1000));
        scrollPanel2.setLayout(new GridLayout(2,2));
        scrollPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
        BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
         
         
        ageSexMortality = new JPanel();
        ageSexMortality.setBackground(new Color(0xFCFCFC));
        ageSexMortality.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
                                  BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        JLabel ageSexMortalityTitle = new JLabel("Mortality By Age And Sex");
        ageSexMortalityTitle.setFont(Cambria(1,32));
        ageSexMortality.add(ageSexMortalityTitle, BorderLayout.NORTH);
        ageSexMortalityPlot();
        

        newCasesDailyByAge = new JPanel();
        newCasesDailyByAge.setBackground(new Color(0xFCFCFC));
        newCasesDailyByAge.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
                                     BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        JLabel newCasesDailyByAgeTitle = new JLabel("Daily Cases by Age Group");
        newCasesDailyByAgeTitle.setFont(Cambria(1, 32)); 
        newCasesDailyByAge.add(newCasesDailyByAgeTitle, BorderLayout.NORTH); 
        ageDailyCasesPlot();

        demographicProportions = new JPanel();
        demographicProportions.setBackground(new Color(0xFCFCFC));
        demographicProportions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        JLabel demographicProportionsTitle = new JLabel("Proportion of Cases by Age and Sex");
        demographicProportionsTitle.setFont(Cambria(1, 32));
        demographicProportions.add(demographicProportionsTitle, BorderLayout.NORTH); 
        ageSexCaseProportionPlot();
        
        demographicDeathProportions = new JPanel();
        demographicDeathProportions.setBackground(new Color(0xFCFCFC));
        demographicDeathProportions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
         
        JLabel demographicDeathProportionsTitle = new JLabel("Proportion of Fatalities by Age and Sex");
        demographicDeathProportionsTitle.setFont(Cambria(1, 32));
        demographicDeathProportions.add(demographicDeathProportionsTitle, BorderLayout.NORTH);
        ageSexDeathProportionPlot();

                
         JPanel mortalityRate = new JPanel();
         mortalityRate.setBackground(new Color(0xFCFCFC));
         mortalityRate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
                 BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        

         scrollPanel2.add(ageSexMortality);
         scrollPanel2.add(newCasesDailyByAge);
         scrollPanel2.add(demographicProportions);
         scrollPanel2.add(demographicDeathProportions);
         
         
         
         scrollPane2 = new JScrollPane(scrollPanel2);
         scrollPane2.setAlignmentX(LEFT_ALIGNMENT);
         scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         scrollPane2.setPreferredSize(new Dimension(900, 900));
         
         demographicsPanel.setLayout(new BorderLayout());
         demographicsPanel.add(scrollPane2, BorderLayout.CENTER);
    }
    
    PlotPanel ageDailyCasesPlot = new PlotPanel();
    private void ageDailyCasesPlot(){
        SwingWorker<PlotPanel, Void> pltWorker = new SwingWorker<>(){
            @Override
            protected PlotPanel doInBackground(){
                Table t = confirmedCOVID.select("Case_Reported_Date", "Age_Group");
                Table casesPerDayByAge = t.dropWhere(t.dateColumn(0).isInYear(2020))
                        .xTabCounts("Case_Reported_Date", "Age_Group");
                casesPerDayByAge.column(0).setName("Date");
                casesPerDayByAge.removeColumns(casesPerDayByAge.column("total"), casesPerDayByAge.column("UNKNOWN"));
                casesPerDayByAge.dropRows(casesPerDayByAge.rowCount() - 1);

                DateColumn dates = casesPerDayByAge.dateColumn(0);

                Axis yAxis = Axis.builder().range(0, 1250).title("Reported Cases").build();

                Layout layout = Layout.builder("", "Day")
                                .margin(defaultMargin)
                                .yAxis(yAxis)
                                .showLegend(true)
                                .width(650).height(420)
                                .build();

                Trace[] traces = casesPerDayByAge.columns().stream().skip(1)
                        .map(ageGroup -> {
                            return ScatterTrace.builder(dates, ageGroup)
                                    .mode(ScatterTrace.Mode.LINE)
                                    .line(Line.builder().shape(Line.Shape.SPLINE)
                                            .smoothing(1.1)
                                            .build())
                                    .name(ageGroup.name())
                                    .showLegend(true)
                                    .build();
                        }).toArray(Trace[]::new);
                return new PlotPanel(new Figure(layout, defaultConfig, traces), 655, 500);
            }
            
            @Override
            protected void done(){
                try {
                    ageDailyCasesPlot = get();
                    newCasesDailyByAge.add(ageDailyCasesPlot);
                    System.out.println("wagwan");
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            
        };
        pltWorker.execute();
    }
    
    
    PlotPanel ageSexMortalityPlot = new PlotPanel();
    private void ageSexMortalityPlot(){
        
        SwingWorker<PlotPanel,Void> pltWorker = new SwingWorker<>(){
            @Override
            protected PlotPanel doInBackground(){
                Layout layout = Layout.builder("", "Age Group", "Deaths Per Thousand Cases")
                        .showLegend(true)
                        .margin(defaultMargin)
                        .width(650).height(420)
                        .barMode(Layout.BarMode.GROUP)
                        .build();

                BarTrace[] traces = new BarTrace[2];

                Selection[] mfSelect = {confirmedCOVID.stringColumn("Client_Gender").isEqualTo("MALE"),
                    confirmedCOVID.stringColumn("Client_Gender").isEqualTo("FEMALE")};
                String[] traceNames = {"Male Mortality", "Female Mortality"};

                for (int i = 0; i < mfSelect.length; i++) {
                    Table t = confirmedCOVID.where(mfSelect[i]).xTabCounts("Age_Group", "Outcome1").select("[labels]", "Fatal", "Resolved");
                    t.column(0).setName("Age_Group");
                    t = t.where(t.stringColumn(0).isNotIn("Total", "UNKNOWN")).sortOn("Age_Group");

                    StringColumn age_groups = t.stringColumn(0);
                    NumericColumn fatal = t.nCol("Fatal"), resolved = t.nCol("Resolved");
                    NumericColumn mortality = (fatal.divide(resolved.add(fatal))).multiply(1000);
                    traces[i] = BarTrace.builder(age_groups, mortality).orientation(BarTrace.Orientation.VERTICAL)
                            .name(traceNames[i]).showLegend(true).build();
                }

                return new PlotPanel(new Figure(layout, defaultConfig, traces), 655, 500);
            }
            @Override
            protected void done(){
                try {
                    ageSexMortalityPlot = get();
                    ageSexMortality.add(ageSexMortalityPlot);
                    System.out.println("wagwan");

                    
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            
        };
        pltWorker.execute();
    }
    
    PlotPanel ageSexCaseProportionPlot = new PlotPanel();
    private void ageSexCaseProportionPlot(){
        SwingWorker<PlotPanel, Void> pltWorker = new SwingWorker<>(){
            @Override
            protected PlotPanel doInBackground(){
                Table ageSexCaseProportion = confirmedCOVID.xTabTablePercents("Age_Group", "Client_Gender")
                        .select("[labels]", "FEMALE", "MALE");
                ageSexCaseProportion.column(0).setName("Age_Group");
                ageSexCaseProportion = ageSexCaseProportion
                        .dropRows(ageSexCaseProportion.rowCount() - 1,
                                ageSexCaseProportion.rowCount() - 2);
                
                StringColumn ageGroup = ageSexCaseProportion.stringColumn(0);
                
                Layout layout = Layout.builder("", "Age Group", "Proportion of Cases")
                        .showLegend(true)
                        .margin(defaultMargin)
                        .width(650).height(420)
                        .barMode(Layout.BarMode.GROUP)
                        .build();
                
                BarTrace[] traces = ageSexCaseProportion.columns().stream().skip(1)
                                    .map(sex->{
                                        NumericColumn n = (NumericColumn) sex;
                                        return BarTrace.builder(ageGroup, n).orientation(BarTrace.Orientation.VERTICAL)
                                                .name(n.name()).showLegend(true).build();
                                    }).toArray(BarTrace[]::new);
                
                return new PlotPanel(new Figure(layout, defaultConfig, traces), 655, 500);
            }
            
            @Override
            protected void done(){
                try {
                    ageSexCaseProportionPlot = get();
                    demographicProportions.add(ageSexCaseProportionPlot);
                    System.out.println("wagwan");
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            
        };
        pltWorker.execute();
    }
    
    PlotPanel ageSexDeathProportionPlot = new PlotPanel();

    private void ageSexDeathProportionPlot() {
        SwingWorker<PlotPanel, Void> pltWorker = new SwingWorker<>() {
            @Override
            protected PlotPanel doInBackground() {
                Table ageSexDeathProportion = confirmedCOVID.where(confirmedCOVID.stringColumn("Outcome1")
                        .isEqualTo("Fatal")).xTabTablePercents("Age_Group", "Client_Gender")
                        
                        .select("[labels]", "FEMALE", "MALE");
                ageSexDeathProportion.column(0).setName("Age_Group");
                ageSexDeathProportion = ageSexDeathProportion
                        .dropRows(ageSexDeathProportion.rowCount() - 1,
                                ageSexDeathProportion.rowCount() - 2);

                StringColumn ageGroup = ageSexDeathProportion.stringColumn(0);

                Layout layout = Layout.builder("", "Age Group", "Proportion of Fatalities")
                        .showLegend(true)
                        .margin(defaultMargin)
                        .width(650).height(420)
                        .barMode(Layout.BarMode.GROUP)
                        .build();

                BarTrace[] traces = ageSexDeathProportion.columns().stream().skip(1)
                        .map(sex -> {
                            NumericColumn n = (NumericColumn) sex;
                            return BarTrace.builder(ageGroup, n).orientation(BarTrace.Orientation.VERTICAL)
                                    .name(n.name()).showLegend(true).build();
                        }).toArray(BarTrace[]::new);
                

                return new PlotPanel(new Figure(layout, defaultConfig, traces), 655, 500);
            }

            @Override
            protected void done() {
                try {
                    ageSexDeathProportionPlot = get();
                    demographicDeathProportions.add(ageSexDeathProportionPlot);
                    System.out.println("wagwan");
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        pltWorker.execute();
    }
    
    
    
    private void regionsSetup() {
        JPanel scrollPanel3 = new JPanel();
        scrollPanel3.setBackground(Color.WHITE);
        scrollPanel3.setPreferredSize(new Dimension(900, 1500));
        
        scrollPanel3.setLayout(new GridLayout(2,1));
        scrollPanel3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(JG_RED, 2, true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        
        
        Map<String, Figure> plots = new HashMap<String, Figure>();
        phus5 = new JComboBox(region_list);
        phus5.addActionListener(this);
        phus5.setPreferredSize(new Dimension(250,20));
       
        
        JPanel selectPhu = new JPanel();
        selectPhu.setPreferredSize(new Dimension(300,60));
        selectPhu.setBackground(JG_RED);
        selectPhu.add(phus5, BorderLayout.SOUTH);
        GridLayout layout = new GridLayout(1,2);
        layout.setHgap(4);
        layout.setVgap(4);
        cpcAndDeathRatePanel =new JPanel(layout);
        cpcAndDeathRatePanel.setBackground(Color.WHITE);
        
        
        
        currentActiveCasesPanel = new JPanel();
        currentActiveCasesPanel.setBackground(Color.WHITE);
       
       
       JPanel cpcGraphPanel = new JPanel();
       cpcGraphPanel.setBackground(Color.WHITE);
        cpcGraphPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        
        JLabel cpcTitle = new JLabel("CASES PER CAPITA");
        cpcTitle.setFont(Cambria(1, 35));
        cpcGraphPanel.add(cpcTitle, BorderLayout.NORTH);
        
        JPanel deathRateGraphPanel = new JPanel();
        deathRateGraphPanel.setBackground(Color.WHITE);
         deathRateGraphPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
         
          JLabel drTitle = new JLabel("DEATH RATE");
          drTitle.setFont(Cambria(1, 35));
          deathRateGraphPanel.add(drTitle, BorderLayout.NORTH);
         
         JPanel cacGraphPanel = new JPanel();
          cacGraphPanel.setBackground(Color.WHITE);
          cacGraphPanel.setPreferredSize(new Dimension(670,670));
         cacGraphPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
         
          JLabel cacTitle = new JLabel("CURRENT ACTIVE CASES");
          cacTitle.setFont(Cambria(1, 35));
          cacGraphPanel.add(cacTitle, BorderLayout.NORTH);
         
         cpcAndDeathRatePanel.add(cpcGraphPanel);
         cpcAndDeathRatePanel.add(deathRateGraphPanel);
         currentActiveCasesPanel.add(cacGraphPanel, BorderLayout.CENTER);
        
        scrollPanel3.add(cpcAndDeathRatePanel);
        scrollPanel3.add(currentActiveCasesPanel);
         
         scrollPane3 = new JScrollPane(scrollPanel3);
         scrollPane3.setAlignmentX(LEFT_ALIGNMENT);
         scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         scrollPane3.setPreferredSize(new Dimension(900, 900));
           
         
         regionsPanel.setLayout(new BorderLayout());
         regionsPanel.add(scrollPane3);
         regionsPanel.add(selectPhu, BorderLayout.NORTH);
         
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

        if(e.getSource()==updateButton){
            dash.getContentPane().removeAll();
            dash.repaint();
            Environment.mapAllDatasetsUpdate();
            initComponents();
            dash.revalidate();
          
        }
        
        if(e.getSource()==graphCases){
            
        }
        if(e.getSource()==graphDeaths){
            
            
        }
        if(e.getSource()==phus){
            
        }
        if(e.getSource()==phus1){
            
        }
        if(e.getSource()==phus2){
            
        }
        if(e.getSource()==phus3){
            
        }
        if(e.getSource()==phus4){
            
        }
        if(e.getSource()==phus5){
            
        }
        
    }
}

   

   
    

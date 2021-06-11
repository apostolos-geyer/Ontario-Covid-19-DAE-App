/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae;
import jginfosci.covid19.datasets.IO;
import jginfosci.covid19.datasets.Dataset;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import static jginfosci.covid19.dae.Environment.tableFor;
import jginfosci.covid19.dae.visualEnv.PlotPanel;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.io.saw.*;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.VerticalBarPlot;
import tech.tablesaw.plotly.components.Layout;
import static jginfosci.covid19.datasets.IO.DATASET_FOLDER;
import tech.tablesaw.plotly.api.*;
import static tech.tablesaw.aggregate.AggregateFunctions.*;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Font;

/**
 * demo version
 */
public class EnvironmentDemo{
    
    /**
     * default constructor
     */
    public EnvironmentDemo(){
        
    }
    
    /**
     * A list of every {@link Dataset} currently stored in the system.
     */
    public static List<String> DATASET_LIST = new ArrayList<>();
    //For nate: Put this list in a visual menu where u can view the available datasets
    
    /**
     * A {@link HashMap} which maps to each currently initialized {@link Dataset}.
     * <p>
     * The Strings mapping to each Dataset will be the same as the file name, 
     * the {@link Dataset#name}, and the return of {@link DATASETS}{@code .getKeySet()}.
     * <p>
     * Dataset methods can be called on any given Dataset using {@link HashMap#get}
     * as with any HashMap mapping to an object with methods. 
     */
    public static final HashMap<String, Dataset> DATASETS = new HashMap<String, Dataset>();
    
    
    
    /**
     * Load the {@link DATASET_LIST}.
     * <p>
     * <code>DATASET_LIST = IO.getSavedDatasets()</code>
     * @see    IO#getSavedDatasets() 
     * @since  June 1, 2021
     */
    public static void loadList(){
        
        DATASET_LIST = IO.getSavedDatasets();
    }
    
    /**
     * Maps a {@link Dataset} to a String in {@link DATASETS}.
     * @param name      The name of the {@link Dataset} to be mapped.
     * @param update    Whether to update the Dataset from its {@link Dataset#url}
     * @see   IO#load(java.lang.String, java.lang.Boolean);
     * @since June 1, 2021
     */
    public static void mapDataset(String name, Boolean update){
        DATASETS.put(name, IO.load(name, update));
    }
    
    
    
    /**
     * Used to load all currently stored versions of {@link Dataset}'s at once.
     *<pre>
     *DATASET_LIST
     *  .forEach(s -> DATASETS.put(s, IO.load(s, false)))
     *</pre>
     * @since June 1, 2021
     */
    public static void mapAllCurrentDatasets(){
        
        DATASET_LIST
                .forEach(s -> DATASETS.put(s, IO.load(s, false)));
                        
    }
    
    /**
     * Used to load updated versions of Datasets with URLs, if the Dataset does not
     * have a URL, the local version will be loaded..
     * <pre>
     *DATASET_LIST
     *  .forEach(s -> DATASETS.put(s, IO.load(s, true)))
     * </pre>
     *
     * @since June 1, 2021
     */
    public static void mapAllDatasetsUpdate() {
        DATASET_LIST.stream()
                .forEach(s -> DATASETS.put(s, IO.load(s, true)));

    }
    
    @Deprecated
    private static void loadDatasets(){
        DATASET_LIST.stream().forEach(s -> DATASETS.put(s, IO.load(s, Boolean.FALSE)));
    }
    

        /**
         * main method for testing stuff
         * @param args  The command line arguments
         * @throws IOException 
         */
        public static void main(String [] args) throws IOException{
            //Environment.loadList();
           /* Environment.mapDataset("Confirmed Covid Cases In Ontario", false);
            Table confirmedCOVID = Environment.tableFor("Confirmed Covid Cases In Ontario")
                           .sortOn("Case_Reported_Date"); 
            
            Table t = confirmedCOVID.xTabCounts("Case_Reported_Date");
            t.column(0).setName("date");
            t = t.sortOn("date");
            System.out.println(t.print());
            
            Layout figLayout = Layout.builder().width(690).height(400).build();
            Figure f = TimeSeriesPlot.create("cases by day",t, "date", "Count");
            
            
           
            
            
            
            
            
            
            
          
                   
                    
                    
              
            
            
            
            
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));*/
            
            //DATASET_LIST.forEach(System.out::println);
            
        /*Table xTab = DATASETS.get("Confirmed Covid Cases In Ontario").getTable().xTabCounts("Age_Group", "Outcome1");
        System.out.println(xTab.print());
        IntColumn fatal = xTab.intColumn("Fatal");
        IntColumn total = xTab.intColumn("Total");
        DoubleColumn deathRates =  fatal.divide(total);
        deathRates.setPrintFormatter(NumberColumnFormatter.percent(4));
        deathRates.setName("Mortaility%");
        xTab.addColumns(deathRates);
        xTab.column(0).setName("Age_Group");
        xTab.removeColumns("total");
        xTab.sortOn("Age_Group");
        System.out.println(xTab.print());
        Plot.show(VerticalBarPlot.create("Age Group x Mortaility%", xTab, "Age_Group", "Mortaility%"));
        */

        /*
        String in, row, col;
        String [] inArr;
        Dataset d;
        Table t;
        do{
        System.out.println("Enter EXIT to quit, otherwise type the name of the dataset you\n"
        + "wish to analyze.");
        DATASET_LIST.forEach(System.out::println);
        in = br.readLine();
        if(in.equals("EXIT")){ break;}
        d = DATASETS.get(in);
        do{
        System.out.println("Options:\n"
        + "SHOW X: show the first X rows of the dataset\n"
        + "SORT X: sort on column X of the dataset\n"
        + "CROSSTAB ROW COL: grouped occurences of values in columns ROW and COL of the dataset\n"
        + "OCCURRENCES X: occurrences of values in column X \n"
        + "PctCROSSTAB ROW COL: grouped occurences of values in columns ROW and COL of the dataset\n"
        + "PctOCCURRENCES X: percent of column X that each value in the column represents\n"
        + "SWITCH: switch datasets");
        System.out.println("Columns:"+ d.getTable().columnNames()+"\n");
        in = br.readLine();
        if(in.equals("switch")){break;}
        inArr = in.split(" ");
        String choice = inArr[0];
        switch(choice){
        case "SHOW":
        int firstX = Integer.parseInt(inArr[1]);
        System.out.println(d.getTable().first(firstX));
        break;
        case "SORT":
        String sortParam = inArr[1];
        d.setTable(d.getTable().sortOn(sortParam));
        System.out.println("Sorted on "+sortParam+"\n Preview:"
        + d.getTable().first(10));
        break;
        case "CROSSTAB":
        row=inArr[1]; col=inArr[2];
        t = d.getTable().xTabCounts(row, col);
        System.out.println(t.printAll());
        break;
        case "OCCURRENCES":
        col=inArr[1];
        t = d.getTable().xTabCounts(col);
        t.column(0).setName(inArr[1]);
        t = t.sortOn(inArr[1]);
        System.out.println(t.printAll());
        break;
        case "PctCROSSTAB":
        row = inArr[1];
        col = inArr[2];
        t = d.getTable().xTabTablePercents(row, col);
        t.columnsOfType(ColumnType.DOUBLE)
        .forEach(x -> ((NumberColumn) x).setPrintFormatter(NumberColumnFormatter.percent(4)));
        System.out.println(t.printAll());
        break;
        case "PctOCCURRENCES":
        col = inArr[1];
        t = d.getTable().xTabPercents(col);
        t = t.sortOn("Percents");
        t.columnsOfType(ColumnType.DOUBLE)
        .forEach(x -> ((NumberColumn) x).setPrintFormatter(NumberColumnFormatter.percent(4)));
        System.out.println(t.printAll());
        break;
        }
        }while(true);
        }while(true);
            */
            
    
            
            
            

        }

        

}




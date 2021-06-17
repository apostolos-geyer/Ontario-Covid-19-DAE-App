/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae;
import jginfosci.covid19.datasets.IO;
import jginfosci.covid19.datasets.Dataset;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.JFrame;
import static jginfosci.covid19.dae.Environment.tableFor;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.defaultConfig;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.defaultMargin;
import jginfosci.covid19.dae.visualEnv.PlotPanel;
import jginfosci.covid19.datasets.Dataset.DatasetBuilder;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.io.saw.*;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.VerticalBarPlot;
import tech.tablesaw.plotly.components.Layout;
import static jginfosci.covid19.datasets.IO.DATASET_FOLDER;
import tech.tablesaw.plotly.api.*;
import static tech.tablesaw.aggregate.AggregateFunctions.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Font;
import tech.tablesaw.plotly.components.Layout.BarMode;
import tech.tablesaw.plotly.components.Line;
import tech.tablesaw.plotly.components.Marker;
import tech.tablesaw.plotly.traces.BarTrace;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.traces.Trace;
import tech.tablesaw.selection.Selection;

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
            /*
            Dataset PHUpop = new Dataset.DatasetBuilder()
                    .withName("PHU Populations")
                    .withCsv("SavedDatasets/PHU Total Population/CSV/PHU Total Population.csv")
                    .withTableFromCsv()
                    .buildDataset();
            IO.save(PHUpop);
            
            */
            
            Environment.loadList();
            Environment.mapAllCurrentDatasets();
            Table confirmedCOVID = tableFor("Confirmed Covid Cases In Ontario");
                    confirmedCOVID = confirmedCOVID.sortOn("Case_Reported_Date");
            Table recently = confirmedCOVID.dropWhere(
                        confirmedCOVID.dateColumn("Case_Reported_Date")
                        .isBefore(confirmedCOVID.dateColumn("Case_Reported_Date")
                                .get(confirmedCOVID.rowCount()-1).minusDays(28)));
                                
            
            Table xtab = recently.xTabCounts("Case_Reported_Date", "Reporting_PHU");
            xtab.column(0).setName("Date");
            System.out.println(xtab);
            
            xtab = xtab.dropRows(xtab.rowCount()-1);
            
                System.out.println(xtab.print());
                DateColumn date = xtab.dateColumn(0);
                Trace[] traces = xtab.columns().stream().skip(1)
                        .map(phu -> {
                            return ScatterTrace.builder(date, phu)
                                    .mode(ScatterTrace.Mode.LINE)
                                    .line(Line.builder().shape(Line.Shape.SPLINE)
                                            .smoothing(1.1)
                                            .build())
                                    .name(phu.name().split("[^a-zA-Z]")[0])
                                    .showLegend(true)
                                    .build();
                        }).toArray(Trace[]::new);
                Layout layout = Layout.builder("", "Day")
                                .margin(defaultMargin)
                                .showLegend(true)
                                .width(650).height(420)
                                .build();
                
                Plot.show(new Figure(layout, traces));
                
                
           
            
              /*
            
              BarTrace[] traces = ageSexDeathProportion.columns().stream().skip(1)
                        .map(sex -> {
                            NumericColumn n = (NumericColumn) sex;
                            return BarTrace.builder(ageGroup, n).orientation(BarTrace.Orientation.VERTICAL)
                                    .name(n.name()).showLegend(true).build();
                        }).toArray(BarTrace[]::new);
            
            Plot.show(new Figure(traces));
            
            //Plot.show(figure);
/*
            BarTrace[] traces = ageSexDeathProportion.columns().stream().skip(1)
                        .map(sex -> {
                            NumericColumn n = (NumericColumn) sex;
                            return BarTrace.builder(ageGroup, n).orientation(BarTrace.Orientation.VERTICAL)
                                    .name(n.name()).showLegend(true).build();
                        }).toArray(BarTrace[]::new);
            
            */

        }

        

}




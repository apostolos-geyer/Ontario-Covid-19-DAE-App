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
import static jginfosci.covid19.dae.visualEnv.GUIUtil.defaultConfig;
import static jginfosci.covid19.dae.visualEnv.GUIUtil.defaultMargin;
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
            Environment.loadList();
            Environment.mapDataset("Confirmed Covid Cases In Ontario", false);
            Table confirmedCOVID = Environment.tableFor("Confirmed Covid Cases In Ontario").replaceColumn("Age_Group",Environment.tableFor("Confirmed Covid Cases In Ontario").stringColumn("Age_Group").replaceAll("<20", "0-19").setName("Age_Group"))
                    .sortOn("Case_Reported_Date"); 
            
            
            Table ageSexCaseProportion = confirmedCOVID.xTabTablePercents("Age_Group","Client_Gender")
                                         .select("[labels]", "FEMALE", "MALE");
            ageSexCaseProportion.column(0).setName("Age_Group");
            ageSexCaseProportion=ageSexCaseProportion
                                 .dropRows(ageSexCaseProportion.rowCount()-1,
                                           ageSexCaseProportion.rowCount()-2);
            
            
            
    
            
            
            

        }

        

}




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae;
import jginfosci.covid19.dae.visualEnv.WelcomePage;
import jginfosci.covid19.datasets.IO;
import jginfosci.covid19.datasets.Dataset;
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.io.saw.*;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.VerticalBarPlot;
import tech.tablesaw.plotly.components.Layout;
import static jginfosci.covid19.datasets.IO.DATASET_FOLDER;
import tech.tablesaw.plotly.api.*;



/**
 * The main class which the program is run from
 * @author Nathan Johnson
 * @author Paul Geyer
 * @since May 24, 2021
*/
public class Environment {
    
    
    /**
     * Default constructor.
     */
    public Environment(){
        
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
    private static void loadList(){
        DATASET_LIST = IO.getSavedDatasets();
    }
    
    /**
     * Maps a {@link Dataset} to a String in {@link DATASETS}.
     * @param name      The name of the {@link Dataset} to be mapped.
     * @param update    Whether to update the Dataset from its {@link Dataset#url}
     * @see   IO#load(java.lang.String, java.lang.Boolean);
     * @since June 1, 2021
     */
    private static void mapDataset(String name, Boolean update){
        DATASETS.put(name, IO.load(name, update));
    }
    
    
    
    /**
     * Used to load all currently stored versions of {@link Dataset}'s at once.
     *<pre>
     *DATASET_LIST
     *  .forEach(s -> DATASETS.put(s, IO.load(s, Boolean,FALSE)))
     *</pre>
     * @since June 1, 2021
     */
    private static void mapAllCurrentDatasets(){
        loadList();
        DATASET_LIST
                .forEach(s -> DATASETS.put(s, IO.load(s, Boolean.FALSE)));
                        
    }
    


        /**
         * Main method
         * @param args  The command line arguments.
         */
        public static void main(String [] args){
            WelcomePage w = new WelcomePage();
            
            
            /*mapAllCurrentDatasets();
            DATASET_LIST.forEach((String k) -> {
                Table t = DATASETS.get(k).getTable();
                System.out.println(k+": \n"
                        +t.shape()+"\n"
                        +t.columnNames()+"\n"
                        +Arrays.toString(t.columnTypes())+"\n");
            });*/
            
           
            
            
    
            
            
            

        }

        

}
        





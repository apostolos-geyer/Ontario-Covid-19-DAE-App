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
     *  .forEach(s -> DATASETS.put(s, IO.load(s, false)))
     *</pre>
     * @since June 1, 2021
     */
    private static void mapAllCurrentDatasets(){
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
    private static void mapAllDatasetsUpdate() {
        DATASET_LIST.stream()
                .forEach(s -> DATASETS.put(s, IO.load(s, true)));

    }
    


        /**
         * Main method
         * @param args  The command line arguments.<br>
         * 
         *              When running from command line, the application can be 
         *              launched with the following arguments, which will configure
         *              how the application runs as follows:<p>
         * 
         *              <strong>No argument:</strong> <br>
         *              If no argument is provided, the program will run in 
         *              "basic" mode <p>
         *              <strong>basic:</strong><br>
         *              In basic mode, the program will sequentially load each 
         *              {@link Dataset}, and the user will be able to decide 
         *              which Datasets will use the data stored on the computer, 
         *              and which will update.<p>
         *              <strong>updated:</strong><br>
         *              <b><i>STARTUP IN UPDATED MODE CAN BE SLOW</i></b><br>
         *              In updated mode, the program will first update each Dataset
         *              to its most recent version, and then launch straight to the 
         *              Dashboard.<p> 
         *              <strong>local:</strong><br>
         *              In local mode, the program will build each Dataset from 
         *              the versions currently stored in the project file, 
         *              and then launch straight to the Dashboard, this is the 
         *              fastest means of using the program, but will not use the
         *              most recent metrics.<br> 
         *              
         */
        public static void main(String... args){
            String argument;
            for(int i=0; i<=args.length; i++){
                if(i==0){
                    argument = (args.length==0)? "basic" : args[i]; 
                }
                else{
                    argument = args[i];
                }
                switch(argument){
                    default:
                        System.out.println("Unrecognized Argument: "+argument+"\n"
                                + "Running in \"basic\" mode.");
                        break; 
                        
                    case("basic"):
                        loadList();
                        mapAllDatasetsUpdate();
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
            }
            
            
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
        





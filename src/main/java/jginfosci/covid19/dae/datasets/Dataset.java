/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template csvPath, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae.datasets;
import java.beans.*;
import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.io.saw.SawReader;
import tech.tablesaw.io.saw.SawWriter;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.*;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.traces.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * A class built on top of {@link Table}, containing various methods to 
 * achieve pseudo object persistence and to import / export data from different 
 * locations.
 * @author Paul Geyer
 * @since May 24, 2021
 * @see IO  
 */
public class Dataset{
    /**
     * The table built from the Dataset
     * @see Table
     */
    Table table;
    String name;
    String csvPath;
    String sawPath;
    String url;
    String lastUpdated;
    
    /**
     * Constructs a Dataset
     * 
     * @param t  the {@link Table} for this Dataset
     * @param name  the name of this Dataset
     * @param csv  the path to the .csv file for this Dataset
     * @param saw  the path to the .saw file for this Dataset
     * @param url  the web URL to download .csv for this Dataset
     */
    public Dataset(Table t, String name, String csv, String saw, String url){
        this.table=t;
        this.name=name;
        this.csvPath=csv;
        this.sawPath=saw;
        this.url=url;
        this.lastUpdated = DateAndTime.now();
        
    }
    
    /**
     * Default constructor.
     */
    public Dataset(){}

    /**
     * Get this {@link Dataset#table}.
     * @return The table
     */
    public Table getTable(){
        return table;
    }
    
    /**
     * Sets this {@link Dataset#table}.
     * @param t  The table it will be set to.
     */
    public void setTable(Table t){
        this.table = t;
    }
    
    /**
     * Get this {@link Dataset#name}.
     * @return the name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Set this {@link Dataset#name}.
     * @param s  The String it will be set to.
     */
    public void setName(String s){
        this.name = s;
    }
    
    /**
     * Get this {@link Dataset#csvPath}.
     * @return the path to the .csv
     */
    public String getCsvPath(){
        return csvPath;
    }
    
    /**
     * Set this {@link Dataset#csvPath}.
     * @param path  The String it will be set to.
     */
    public void setCsvPath(String path){
        this.csvPath = path;
    }
    
    /**
     * Get this {@link Dataset#sawPath}.
     * @return the path to the .saw
     */
    public String getSawPath() {
        return sawPath;
    }

    /**
     * Set this {@link Dataset#sawPath}.
     * @param path  The String it will be set to.
     */
    public void setSawPath(String path) {
        this.sawPath = path;
    }

    /**
     * Get this {@link Dataset#url}.
     * @return the url
     */
    public String getUrl(){
        return url;
    }
    
    /**
     * Set this {@link Dataset#url}.
     * @param url  The String it will be set to.
     */
    public void setUrl(String url){
        this.url = url;
    }
    
    /**
     * Get this {@link Dataset#lastUpdated}.
     * @return the last time this Dataset was updated
     */
    public String getLastUpdated(){
        return lastUpdated;
    }
    
    /**
     * Get this {@link Dataset#lastUpdated}.
     * @param s  The new value for the {@code lastUpdated}, usually now.
     */
    public void setLastUpdated(String s){
        lastUpdated = s;
    }
    
    
    /**
     * A utility class used predominantly during development to build new 
     * {@link Dataset}'s
     */
    public static class DatasetBuilder {
        Table table;
        String name;
        String csvPath;
        String sawPath;
        String url;
        
        /**
         * 
         * @param name  The name
         * @return this 
         */
        public final DatasetBuilder withName(String name){ 
            this.name=name;
            return this;
        }
        
        /**
         * 
         * @param fileName
         * @return this
         */
        public final DatasetBuilder withCsv(String fileName){
            this.csvPath=fileName;
            return this;
        }
        
        /**
         * 
         * @param fileName
         * @return this
         */
        public final DatasetBuilder withSaw(String fileName){
            this.sawPath=fileName;
            return this;
        }
        
        /**
         * 
         * @param url
         * @return this
         */
        public final DatasetBuilder withUrl(String url){
            this.url=url;
            return this;
        }
        
        /**
         * 
         * @return this
         */
        public final DatasetBuilder withEmptyTable(){
            //System.out.println("Creating a Table instance named: "+name);
            this.table = Table.create();
            return this;
        }
        
        /**
         * 
         * @return this
         * @see IO#csvToTable(java.nio.file.Path, java.lang.String) 
         */
        public final DatasetBuilder withTableFromCsv(){
            try{
            this.table = IO.csvToTable(Paths.get(csvPath), name);
            }
            catch(IOException e){
                System.out.println("An error occurred creating the table.\n");
                System.out.println("The table will be instantiated as empty with the name:\n"+name);
                this.table = Table.create(name);
            }
            return this;
        }
        
        /**
         * 
         * @return this
         * @see IO#sawToTable(java.nio.file.Path, java.lang.String) 
         */
        public final DatasetBuilder withTableFromSaw(){
                this.table = IO.sawToTable(Paths.get(sawPath), name);
                return this;
        }
        
        /**
         * 
         * @return this
         * @see IO#urlToTable(java.net.URL, java.nio.file.Path, java.lang.String) 
         */
        public final DatasetBuilder withTableFromUrl(){
            try{
            this.table = IO.urlToTable(new URL(url), Paths.get(csvPath), name);
            }
            catch(IOException e){
                System.out.println("An error occurred creating the table.\n");
                System.out.println("The table will be instantiated as empty with the name:\n"+name);
                this.table = Table.create(name);
            }         
            catch(NullPointerException excep){
                try {
                    this.table = Table.read()
                            .usingOptions(CsvReadOptions
                                    .builder(new URL(url))
                                    .tableName(name));
                } catch (IOException e) {
                    System.out.println("An error occurred creating the table.\n");
                    System.out.println("The table will be instantiated as empty with the name:\n" + name);
                    this.table = Table.create(name);
                }   
            }
            return this;
        }
        

        /**
         * Build the {@code Dataset} after calling other {@code DatasetBuilder}
         * methods.
         * <p>
         * @return {@code Dataset}
         */
        public final Dataset buildDataset(){
            return new Dataset(table, name, csvPath, sawPath, url);
        }
    }

    
    
    /**
     * Makes table from csv
     * @see IO#csvToTable(java.nio.file.Path, java.lang.String) 
     */
    public void makeTableCsv(){
        try{
            this.table = IO.csvToTable(Paths.get(url), name);
            }
            catch(IOException e){
                System.out.println("An error occurred creating the table.\n");
                System.out.println("The table will be instantiated as empty with the name:\n"+name);
                this.table = Table.create(name);
            }
    }
    /**
     * Makes table from saw
     * @see IO#sawToTable(java.nio.file.Path, java.lang.String) 
     */
    public void makeTableSaw(){
        this.table = IO.sawToTable(Paths.get(sawPath), name);
    }
    /**
     * Makes table from url
     */
    public void makeTableUrl(){
        try {
            this.table = IO.urlToTable(new URL(url), Paths.get(csvPath), name);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (IOException ex){
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);      
        } 
    }
    }




    /**
     * Simple utility class for getting current date and time.
     * @author paulgeyer
     */
    class DateAndTime{
        
    /**
     * Format of the {@link LocalDateTime}. 
     * <pre>Format yyyy-MM-dd HH:mm</pre>
     */    
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    /**
     * 
     * @return The current time.
     * @see LocalDateTime#now() 
     */
    public static String now(){
        LocalDateTime dt = LocalDateTime.now();
        String now = dtf.format(dt);
        return now;
    }
    }






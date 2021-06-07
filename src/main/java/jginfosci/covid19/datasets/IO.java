/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.datasets;
import java.beans.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Stream;
import jginfosci.covid19.dae.DateAndTime;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.io.saw.*;

/**
 * Class that handles all methods for operations on {@link Dataset}'s that require importing and exporting.
 * @author paulgeyer
 * @since June 1, 2021
 */
public abstract class IO {
    
    /**
     * Default name for folder that stores .saw files.
     */
    public static final String SAW_FOLDER = "SAW";
    
    /**
     * Default name for folder that stores .csv files.
     */
    public static final String CSV_FOLDER = "CSV";
    
    /**
     * Default name for folder that stores .csv files.
     */
    public static final String XML_FOLDER = "XML";
    
    //public static final String XLSX_FOLDER = "XLSX";
    
    /**
     * Default name for folder that stores information required for building and related to {@link Dataset}'s.
     */
    public static final String DATASET_FOLDER = "SavedDatasets";
    

    /**
     * <pre>Saves a {@link Dataset}.</pre>
     * To be used in cases where changes have been made to a Dataset, and during the development
     * of the application to simplify adding new default Datasets.
     * <p>
     * The {@link Dataset#table} will be saved as a .saw and a .csv, most Dataset
     * attributes will also be saved as an .xml serialized {@code Object}.
     * {@link Dataset#lastUpdated} will also be updated to the current date and
     * time.
     * 
     * @param d     the Dataset to be saved.
     * @see         tableToSaw(Dataset)
     * @see         tableToCsv(Dataset)
     * @see         Dataset#setLastUpdated
     * @see         xmlEncode(Dataset)
     */
    public static void save(Dataset d){
        tableToSaw(d);
        tableToCsv(d);
        d.setLastUpdated(DateAndTime.lastUpDateTime());
        try{
        xmlEncode(d);
        }catch(FileNotFoundException e){
            e.printStackTrace();
                }
    }


    /**
     * Writes a .csv file from {@link Dataset} d.
     * @param d  The Dataset
     * @see Table#write().csv()
     */
    public static void tableToCsv(Dataset d) {
        Path p = Paths.get(DATASET_FOLDER, d.getName(), CSV_FOLDER);
        if(!(p.toFile()).exists()){
            p.toFile().mkdirs();
        }
        p = p.resolve(d.getName()+".csv");
        
        try {
            d.getTable().write().csv(p.toFile());
        } catch (IOException ex) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        }
        d.setCsvPath(p.toString());
    }

    /**
     * Writes a .saw file from {@link Dataset} d.
     * @param d  The Dataset 
     * @see SawWriter#write() 
     */
    public static void tableToSaw(Dataset d) {
        Path p = Paths.get(DATASET_FOLDER, d.getName(), SAW_FOLDER);
        if(!(p.toFile()).exists()){
            p.toFile().mkdirs();
        }
        new SawWriter(p, d.getTable()).write();
        p = p.resolve((d.getName().replaceAll(" ", ""))+".saw");
        d.setSawPath(p.toString());
        
    }
    
    /**
     * Encodes {@link Dataset} d as an .xml file to store attributes.
     * @param d  The Dataset
     * @throws FileNotFoundException 
     * @see XMLEncoder#writeObject(java.lang.Object);
     * @see xmlDecode
     */
    public static void xmlEncode(Dataset d) throws FileNotFoundException {
        Path p = Paths.get(DATASET_FOLDER, d.getName(), XML_FOLDER);
        if(!(p.toFile()).exists()){
            p.toFile().mkdirs();
        }
        p = p.resolve(d.getName()+".xml");
        BufferedOutputStream bOutStream
                = new BufferedOutputStream(new FileOutputStream(p.toFile()));
        
        try (XMLEncoder encode = new XMLEncoder(bOutStream)) {
            encode.writeObject(d);
        }
    }
    
    
    
    //loading related stuff
    
    /**
     * Loads a {@link Dataset}, building it from .saw format by default.
     * @param name  The name of the Dataset to load
     * @param update  To update or not update the Dataset when loading.
     * @return the loaded Dataset
     * @see update
     * @see Dataset#makeTableSaw() 
     */
    public static Dataset load(String name, Boolean update){
        Dataset d = xmlDecode(name);
        if(update && d.getUrl()!=null){
            update(d);
            
        }
        else if(update){ //URL will be null here by default, so it'name not necessary to include it in the condition.
            System.out.println("""
                               Cannot update dataset as there is no url to update from
                               Building from current file.""");
        }
        d.makeTableSaw();
        d.makeTableCsv();
        return d;
    }
    
    /**
     * Gets the saved Datasets.
     * @return a {@code List<String>} of the Dataset names.
     */
    public static List<String> getSavedDatasets() {
        Path p = Paths.get(DATASET_FOLDER);
        List<String> datasets = new ArrayList<>();
        Stream.of(p.toFile().listFiles(File::isDirectory))
                .forEach(file -> datasets.add(file.toString()
                .replace(DATASET_FOLDER + File.separator, "")));
        
        return datasets;
    }
    
    
    /**
     * Decodes an {@link Dataset} from an .xml file.
     * @param name  The name of the Dataset to decode
     * @return  a Dataset built from the .xml file
     */
    private static Dataset xmlDecode(String name) {
        BufferedInputStream bInStream = null;
        Dataset d = null;
        Path p = Paths.get(DATASET_FOLDER, name, XML_FOLDER, name+".xml");
        
        try {
            bInStream = new BufferedInputStream(new FileInputStream(p.toFile()));
            XMLDecoder decode = new XMLDecoder(bInStream);
            d = (Dataset) decode.readObject();
            decode.close();
            return d;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bInStream.close();
            } catch (IOException ex) {
                Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return d;
    }
    
    
    /**
     * Updates a {@link Dataset}'s .csv using its {@link Dataset#url}.
     * @param d  the Dataset to update.
     * @see downloadCsv(URL, Path)
     */
    public static void update(Dataset d) {
        if(d.getUrl()==null){
            System.out.println("Cannot update without a URL.");
            return;
        }
        
        try {
            downloadCsv(new URL(d.getUrl()), Paths.get(DATASET_FOLDER, d.getName(), CSV_FOLDER, d.getName()+".csv"));
            d.setLastUpdated(DateAndTime.lastUpDateTime());
            d.makeTableCsv();
            save(d);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Downloads a .csv file using {@link java.nio} and {@link java.net} methods.
     * @param url  The url to download the .csv from
     * @param p  The path to write the .csv to
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void downloadCsv(URL url, Path p) throws FileNotFoundException, IOException{
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fOutStream = (new FileOutputStream(p.toFile()));
        FileChannel fChannel = fOutStream.getChannel();
        fChannel.transferFrom(rbc, 0, Long.MAX_VALUE);         
    }
    
    /**
     * Reads a .csv file to a {@link Table}
     * @param p  The path from which the .csv will be read
     * @param name  The name the table will be assigned
     * @return  The table built from reading the .csv
     * @throws IOException 
     * @see Table#read() 
     */
    public static Table csvToTable(Path p, String name) throws IOException{  
        return Table.read()
                        .usingOptions(CsvReadOptions
                                .builder(p.toFile())
                                    .tableName(name));
                                    
    }
    
    /**
     * Downloads a .csv file from the web, and then reads it to a {@link Table}
     * @param url  The url of the .csv file
     * @param p  The path the .csv file will be written to
     * @param name  The name the table will be assigned
     * @return  {@link #csvToTable(Path, String)  csvToTable(p, name)}
     * @throws IOException 
     * @see downloadCsv(URL, Path)
     * 
     */
    public static Table urlToTable(URL url, Path p, String name) throws IOException {    
        System.out.println("This is the path: "+p.toString()+" right there");
        downloadCsv(url, p);
        return csvToTable(p, name);
    }
    
    
    /**
     * Reads a .saw file to a {@link Table}
     * @param p  The path from which the .saw will be read
     * @param name  The name the table will be assigned
     * @return  The table built from reading the .saw
     * @see SawReader#read() 
     */
    public static Table sawToTable(Path p, String name){
        return new SawReader(p).read().setName(name);
    }
    

    

    
    
    
    
    
    
    
    
}

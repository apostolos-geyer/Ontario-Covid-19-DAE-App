/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae.visualEnv;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import tech.tablesaw.plotly.Plot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.swing.BorderFactory;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Margin;
import tech.tablesaw.plotly.components.Page;
import tech.tablesaw.plotly.display.Browser;
import tech.tablesaw.plotly.traces.ScatterTrace;
  
/**
 *
 * @author nathanjohnson
 */
public class PlotPanel extends JPanel{
    private Stage stage;
    private WebView render;
    private WebEngine webEngine;
    private JFXPanel fxPanel;
    
    
    public PlotPanel(Figure f, int xDimension, int yDimension){
        initComponents(f, xDimension, yDimension);
    }

    PlotPanel() {
    }
  
      
    private void initComponents(Figure f, int xDimension, int yDimension){
        
        setBorder(GUIUtil.MAJOR_PANEL_BORDER);
        fxPanel = new JFXPanel();
        fxPanel.setSize(xDimension, yDimension);
        setSize(xDimension, yDimension);
        generateScene(f, xDimension, yDimension);
        
        setLayout(new BorderLayout());  
        add(fxPanel, BorderLayout.CENTER);  
        
         
       
        
    }
    
    private void generateScene(Figure f, int xDimension, int yDimension){
         PlatformImpl.startup(new Runnable() {  
            @Override
            public void run() { 
                stage = new Stage();
                stage.setTitle("Graph");
                stage.setResizable(true);
                
                Group root = new Group();
                Scene scene = new Scene(root,xDimension,yDimension);

                render = new WebView();
                render.getEngine().loadContent(EmbeddablePlot.embeddablePlot(f));
                render.setVisible(true);
                render.setMaxSize((double) xDimension,(double) yDimension);
                ObservableList<Node> children = root.getChildren();
                children.add(render); 
                
                fxPanel.setScene(scene); 
                
            }
         });
        
    }
    
    
}




  class EmbeddablePlot extends Plot{
    
    
    
    public static String embeddablePlot(Figure figure){
        return Page.pageBuilder(figure, "target").build().asJavascript();
        
    }
     /* This is the method plot.show from the Plot class. 
            public static void show(Figure figure, String divName, File outputFile) {
            Page page = Page.pageBuilder(figure, divName).build();
            String output = page.asJavascript();

            try {
            try (Writer writer =
            new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
            writer.write(output);
            }
            new Browser().browse(outputFile);
            } catch (IOException e) {
            throw new UncheckedIOException(e);
            }
            }
    */
    
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae.visualEnv;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
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
  
/**
 *
 * @author nathanjohnson
 */
public class DisplayGraph extends JPanel{
    private Stage stage;
    private WebView browser;
    private JFXPanel fxPanel;
    private JButton swingButton;
    private WebEngine webEngine;
    
    public DisplayGraph(){
        initComponents();
    }
     public static void main(String ...args){  
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {  
            @Override
            public void run() {  
                final JFrame graphFrame = GUIUtil.basic_frame();
                graphFrame.getContentPane().add(new DisplayGraph());
                graphFrame.setVisible(true);
                
                 
                            }  
        });     
    }  
    private void initComponents(){
        fxPanel = new JFXPanel();
       
        generateScene();
        
         setLayout(new BorderLayout());  
        add(fxPanel, BorderLayout.CENTER);  
         
        swingButton = new JButton();  
        swingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        webEngine.reload();
                    }
                });
            }
        });  
        swingButton.setText("Reload");  
         
        add(swingButton, BorderLayout.SOUTH);  
       
        
    }
    
    private void generateScene(){
         PlatformImpl.startup(new Runnable() {  
            @Override
            public void run() { 
                stage = new Stage();
                stage.setTitle("Graph");
                stage.setResizable(true);
                
                Group root = new Group();
                Scene scene = new Scene(root,80,20);
                
                
                //brower
                browser = new WebView();
                webEngine = browser.getEngine();
                webEngine.load("file:///Users/nathanjohnson/Library/Messages/Attachments/92/02/D9FE89AB-34E7-4DF4-BF6E-3F5DB1DE1C22/output46a0b427-ad1b-4d10-bb9e-4630a355c8c1.html");
                browser.setVisible(true);
                ObservableList<Node> children = root.getChildren();
                children.add(browser); 
                
                fxPanel.setScene(scene); 
            }
         });
        
    }
    
    
}

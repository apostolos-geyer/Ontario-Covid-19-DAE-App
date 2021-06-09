/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae.visualEnv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import jginfosci.covid19.dae.DateAndTime;

/**
 *
 * @author paulgeyer
 */
public abstract class GUIUtil {
    
    static final String JG_LOGO_PATH = "visuals/logo.png";
    
    public static final Color JG_RED = new Color(0xA21515);
    
    static final Border MAJOR_PANEL_BORDER = BorderFactory.createLineBorder(JG_RED,1,true);

    static final JFrame BASIC_FRAME = basic_frame();
    
    static final JPanel BASIC_PARENT_PANEL = parent_panel();
    
    static final JPanel BASIC_DISPLAY_PANEL = display_panel();

    static final JPanel BASIC_HEADER_PANEL = header_panel();

    static final JFrame basic_frame(){
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(1380,800));
        frame.setMinimumSize(new Dimension(1380,800));
        return frame;
    }
    
    
    static final JPanel parent_panel(){
        JPanel parent = new JPanel(new BorderLayout(10,10));
        parent.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        parent.setOpaque(true);
        return parent;
    }
    
    
    
    static final JPanel display_panel() {
        JPanel display = new JPanel();
        display.setBorder(MAJOR_PANEL_BORDER);
        display.setBackground(Color.WHITE);
        display.setOpaque(true);

        return display;
    }        
    
    static final JPanel header_panel(){   
        JPanel header = new JPanel();
        header.setBorder(MAJOR_PANEL_BORDER);
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1380, 200));
        BoxLayout bLayout = new BoxLayout(header, BoxLayout.X_AXIS);
        header.setLayout(bLayout);
        
        header.add(Box.createRigidArea(new Dimension(50, 0)));
        
        header.add(new JLabel(new ImageIcon(JG_LOGO_PATH)){{
            setBorder(BorderFactory
                                .createBevelBorder(BevelBorder.LOWERED, 
                                        JG_RED.brighter().brighter(), 
                                        JG_RED.brighter(), 
                                        JG_RED.darker().darker(), 
                                        JG_RED.darker()));
        }});
        
        header.add(Box.createRigidArea(new Dimension(100, 0)));
        
        Box headerMessageBox = Box.createVerticalBox();
        headerMessageBox.add(new JLabel("COVID-19 DATA DASHBOARD  "){{
            setFont(new Font("Cambria", Font.ITALIC, 40));
            setForeground(new Color(0xA21515));
        }});
        
        headerMessageBox.add(new JLabel(DateAndTime.dispDate()){{
            setFont(new Font("Cambria", Font.PLAIN, 35));
        }});
        
        header.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50)){{
            setBackground(Color.WHITE);
            add(headerMessageBox);
        }});
        header.setOpaque(true);

        return header;
    }
    
    

    
    
    
    
    
}


package jginfosci.covid19.dae.visualEnv;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jginfosci.covid19.dae.DateAndTime;
import java.io.IOException;
import javax.swing.GroupLayout;
/**
 *
 * @author nathanjohnson
 */
public final class WelcomePage extends JFrame implements ActionListener{
    private final String JG_LOGO_PATH = "visuals/logo.png";
    private JPanel titlePanel,loadpan,datepan;
    private JLabel logo,date;
    private JLabel welcome;
    private JButton load;
    private static int welcomelen;
    
    
    
    void initialize(){
    ImageIcon JG = new ImageIcon(JG_LOGO_PATH);
    
    
    logo = new JLabel(JG);
    logo.setBounds(0,0,200,200);
    welcome = new JLabel("COVID-19 DATA DASHBOARD");
    
    welcome.setBounds(250,0,200,50);
    welcome.setFont(new Font("Cambria",Font.ITALIC,40));
    welcome.setForeground(new Color(0xA21515));
    welcomelen=welcome.getWidth();
    
    date = new JLabel(DateAndTime.dispDate());
    date.setFont(new Font("Cambria", Font.BOLD,18));
    welcome.setForeground(new Color(0xA21515));
   

    load = new JButton("LOAD");
    load.setFont(new Font("Cambria",Font.BOLD,18));
    load.setBounds(200+(welcomelen/2),53,200,60);
    load.setPreferredSize(new Dimension(100,50));
    
    titlePanel = new JPanel();
    titlePanel.setBackground(new Color(0xFFFFFF));
    titlePanel.setPreferredSize(new Dimension(200,200));
    titlePanel.add(logo);
    titlePanel.add(welcome);
    titlePanel.add(date);
    
    loadpan = new JPanel();
    loadpan.setBackground(new Color (0xA21515));
    loadpan.setPreferredSize(new Dimension(200,100));
    loadpan.add(load);
    
    datepan = new JPanel();
    datepan.setBackground(new Color (0xA21515));
    datepan.setPreferredSize(new Dimension(200,100));
    

    //frame
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(null);
    this.setSize(1000,700);
    this.setLayout(new BorderLayout());
    this.setTitle("Johnson-Geyer Covid-19 Data Dashboard");
    this.getContentPane().setBackground(new Color(0xA21515));
    //this.add(logo);
    //this.add(welcome);
    this.add(titlePanel,BorderLayout.NORTH);
    this.add(loadpan,BorderLayout.SOUTH);
    this.setMinimumSize(new Dimension(1000,700));
    //this.add(load);
    this.setVisible(true);    
    }
    
    public WelcomePage(){
    initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }
    
    
}

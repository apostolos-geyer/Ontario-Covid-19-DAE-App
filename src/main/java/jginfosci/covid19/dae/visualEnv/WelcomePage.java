
package jginfosci.covid19.dae.visualEnv;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jginfosci.covid19.dae.DateAndTime;
/**
 *
 * @author nathanjohnson
 */
public final class WelcomePage extends JFrame implements ActionListener{
    private JPanel title,loadpan;
    private JLabel logo,date;
    private JLabel welcome;
    private JButton load;
    private static int welcomelen;
    
    
    void initialize(){
    ImageIcon JG = new ImageIcon("jglogo.png");
    
    logo = new JLabel();
    logo.setIcon(JG);
    logo.setBounds(0,0,150,150);
    
    welcome = new JLabel();
    welcome.setText("COVID-19 DATA DASHBOARD");
    welcome.setBounds(200,0,460,50);
    welcome.setFont(new Font("Cambria",Font.BOLD,30));
    welcomelen=welcome.getWidth();
    
    date = new JLabel();
    date.setText(DateAndTime.dispTime());
  
    
    
    load = new JButton();
    load.setText("LOAD");
    load.setFont(new Font("Cambria",Font.BOLD,18));
    load.setBounds(200+(welcomelen/2),53,200,60);
    
    title = new JPanel();
    title.setBackground(new Color(0xFFFFFF));
    title.setPreferredSize(new Dimension(100,125));
    title.add(logo);
    title.add(welcome);
    
    loadpan = new JPanel();
    loadpan.setBackground(new Color (0xA21515));
    loadpan.setPreferredSize(new Dimension(200,100));
    loadpan.add(load);
    
    

    //frame
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(null);
    this.setSize(500,500);
    this.setLayout(new BorderLayout());
    this.setTitle("Johnson-Geyer Covid-19 Data Dashboard");
    this.getContentPane().setBackground(new Color(0xA21515));
    //this.add(logo);
    //this.add(welcome);
    this.add(title,BorderLayout.NORTH);
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

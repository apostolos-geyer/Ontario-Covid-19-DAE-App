
package jginfosci.covid19.dae.visualEnv;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jginfosci.covid19.dae.DateAndTime;
/**
 *
 * @author nathanjohnson
 */
public final class WelcomePage extends JFrame implements ActionListener{
    private final String JG_LOGO_PATH = "visuals/logo.png";
    private JPanel titlePanel,loadpan,datepan;
    private JLabel logo,date,welcomeMessage;
    private JButton load;
    private static int welcomelen;
    
    
    @SuppressWarnings("Unused")
    void initComponents(){
    logo = new JLabel(new ImageIcon(JG_LOGO_PATH));
    welcomeMessage = new JLabel("COVID-19 DATA DASHBOARD");
    
    welcomeMessage.setFont(new Font("Cambria",Font.ITALIC,40));
    welcomeMessage.setForeground(new Color(0xA21515));
    welcomelen=welcomeMessage.getWidth();
    System.out.println(welcomeMessage.getWidth());
    
    date = new JLabel(DateAndTime.dispDate());
    date.setFont(new Font("Cambria", Font.BOLD,18));
    welcomeMessage.setForeground(new Color(0xA21515));
   

    load = new JButton("LOAD");
    load.setFont(new Font("Cambria",Font.BOLD,18));
    load.setPreferredSize(new Dimension(100,50));
    
    titlePanel = new JPanel();
    titlePanel.setBackground(new Color(0xFFFFFF));
    titlePanel.setPreferredSize(new Dimension(200,200));
    titlePanel.add(logo);
    titlePanel.add(welcomeMessage);
    titlePanel.add(date);
    
    loadpan = new JPanel();
    loadpan.setBackground(new Color (0xA21515));
    loadpan.setPreferredSize(new Dimension(200,100));
    loadpan.add(load);
    
    datepan = new JPanel();
    datepan.setBackground(new Color (0xA21515));
    datepan.setPreferredSize(new Dimension(200,100));
    

    //frame
    this.setSize(1000,700);
    setLayout(new BorderLayout());
    getContentPane().setBackground(new Color(0xA21515));
    //this.add(logo);
    //this.add(welcomeMessage);
    add(titlePanel,BorderLayout.NORTH);
    add(loadpan,BorderLayout.SOUTH);
    this.setMinimumSize(new Dimension(1000,700));
    //this.add(load);
    }
    
    public WelcomePage(){
    setTitle("COVID DASHBOARD SESSION : "+DateAndTime.winTitleDate());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    initComponents();
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }
    
    
    
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                WelcomePage window = new WelcomePage();
            }
    });
    
    
}
}

package jginfosci.covid19.dae.visualEnv;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Box;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.BevelBorder;
import jginfosci.covid19.dae.DateAndTime;
import jginfosci.covid19.dae.Environment;
/**
 *
 * @author nathanjohnson
 */
public final class WelcomePage implements ActionListener{
    public static final String JG_LOGO_PATH = "visuals/logo.png";
    public static final Color JG_RED = new Color(0xA21515);
    private final JFrame welcome = new JFrame();
    private JLabel DATE = new JLabel(DateAndTime.dispDate());
    private JPanel parentPanel,loadPanel,datePanel;
    public JPanel titlePanel;
    private JLabel logo,welcomeMessage;
    private JButton loadButtonLocal, loadButtonUpdate;
    private Container content;
    private static int welcomeLength;
    
    @SuppressWarnings("Unused")
    void initComponents(){
        
        //Setting the minimum size of the JFrame
        welcome.setSize(new Dimension(1000,700));
        welcome.setMinimumSize(new Dimension(1000,700));
        
        //Setting the layout of content pane and adding a top level JPanel
        Container c = welcome.getContentPane();
        c.setLayout(new BorderLayout());
        
        parentPanel = new JPanel(new BorderLayout());
        parentPanel.setBackground(JG_RED);
        c.add(parentPanel, BorderLayout.CENTER);
                
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(1000, 200));
        titlePanel.setBackground(Color.WHITE);
        BoxLayout bl = new BoxLayout(titlePanel, BoxLayout.X_AXIS);
        titlePanel.setLayout(bl);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        logo = new JLabel(new ImageIcon(JG_LOGO_PATH));
        titlePanel.add(logo);
        titlePanel.add(Box.createRigidArea(new Dimension(100, 0)));

        welcomeMessage = new JLabel("COVID-19 DATA DASHBOARD  ");
        welcomeMessage.setFont(new Font("Cambria", Font.ITALIC, 40));
        welcomeMessage.setForeground(new Color(0xA21515));
        welcomeLength = welcomeMessage.getWidth();
        DATE.setFont(new Font("Cambria", Font.PLAIN, 35));
        Box welcMessages = Box.createVerticalBox();
        welcMessages.add(welcomeMessage);
        welcMessages.add(DATE);
        JPanel welcMessagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        welcMessagePanel.setBackground(Color.WHITE);
        welcMessagePanel.add(welcMessages);
        titlePanel.add(welcMessagePanel);

        loadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 250));
        loadPanel.setPreferredSize(new Dimension(1000, 500));
        loadPanel.setBackground(JG_RED);

        loadButtonLocal = new JButton("LOAD");
        loadButtonLocal.setFont(new Font("Cambria", 0, 25));
        loadButtonLocal.setBackground(Color.WHITE);
        loadButtonLocal.setForeground(JG_RED);
        loadButtonLocal.addActionListener(this);

        loadPanel.add(loadButtonLocal);

            
        parentPanel.add(titlePanel, BorderLayout.NORTH);
        parentPanel.add(loadPanel, BorderLayout.SOUTH);
        welcome.pack();

    }
    
    public WelcomePage(){
    welcome.setTitle("COVID DASHBOARD SESSION : "+DateAndTime.winTitleDate());
    welcome.setDefaultCloseOperation(EXIT_ON_CLOSE);
    initComponents();
    welcome.pack();
    welcome.setLocationRelativeTo(null);
    welcome.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loadButtonLocal){
            welcome.dispose();
           
            Dashboard d = new Dashboard(titlePanel);
          
            
        }
       
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

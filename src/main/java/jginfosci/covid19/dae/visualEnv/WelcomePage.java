
package jginfosci.covid19.dae.visualEnv;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Box;
import javax.swing.border.BevelBorder;
import jginfosci.covid19.dae.DateAndTime;
/**
 *
 * @author nathanjohnson
 */
public final class WelcomePage extends JFrame implements ActionListener{
    private final String JG_LOGO_PATH = "visuals/logo.png";
    private final Color JG_RED = new Color(0xA21515);
    private JLabel DATE = new JLabel(DateAndTime.dispDate());
    private JPanel parentPanel, titlePanel,loadPanel,datePanel;
    private JLabel logo,welcomeMessage;
    private JButton loadButton;
    private Container content;
    private static int welcomeLength;
    
    @SuppressWarnings("Unused")
    void initComponents(){
        
        //Setting the minimum size of the JFrame
        setSize(new Dimension(1000,700));
        setMinimumSize(new Dimension(1000,700));
        
        //Setting the layout of content pane and adding a top level JPanel
        Container c = getContentPane();
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
                titlePanel.add(Box.createRigidArea(new Dimension(100,0)));
        
                welcomeMessage = new JLabel("COVID-19 DATA DASHBOARD  ");
                welcomeMessage.setFont(new Font("Cambria", Font.ITALIC, 40));
                welcomeMessage.setForeground(new Color(0xA21515));
                welcomeLength = welcomeMessage.getWidth();
                DATE.setFont(new Font("Cambria", Font.PLAIN, 35)); 
                Box welcMessages = Box.createVerticalBox();
                welcMessages.add(welcomeMessage);
                welcMessages.add(DATE);
        
            titlePanel.add(welcMessages);
            
            loadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 250, 250));
            loadPanel.setPreferredSize(new Dimension(1000, 500));
            loadPanel.setBackground(JG_RED);

                loadButton = new JButton("LOAD");
                loadButton.setFont(new Font("Cambria", 0, 25));
                loadButton.setBackground(Color.WHITE);
                loadButton.setForeground(JG_RED);

            loadPanel.add(loadButton);

            
        parentPanel.add(titlePanel, BorderLayout.NORTH);
        parentPanel.add(loadPanel, BorderLayout.SOUTH);
        pack();

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

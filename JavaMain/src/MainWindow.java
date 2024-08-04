import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private double current_balance;
	private TransactionPage transactionPage;
	private FrontEnd front_end;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public MainWindow(long id, String firstName, String lastName, String email, String phoneNumber, double current_balance) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.current_balance = current_balance;
		
		setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1055, 547);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnNewButton = new JButton("Transactions");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		transactionPage = new TransactionPage(id, MainWindow.this);
        		transactionPage.setVisible(true);
        		MainWindow.this.setVisible(false);
        	}
        });
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnNewButton.setBounds(10, 95, 253, 127);
        contentPane.add(btnNewButton);
        
        JPanel panel = new JPanel();
        panel.setBounds(461, 65, 483, 401);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Personal details");
        lblNewJgoodiesTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblNewJgoodiesTitle.setBounds(10, 10, 328, 43);
        panel.add(lblNewJgoodiesTitle);
        
        JLabel lblNewLabel = new JLabel(firstName + " " + lastName);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(10, 63, 250, 30);
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("ID: "+id);
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_1.setBounds(10, 142, 188, 24);
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Phone Number: " + phoneNumber);
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_2.setBounds(10, 184, 428, 24);
        panel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Email: "+ email);
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_3.setBounds(10, 228, 398, 24);
        panel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_3_1 = new JLabel("Current Balance: " + current_balance);
        lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_3_1.setBounds(10, 269, 398, 24);
        panel.add(lblNewLabel_3_1);
        
        JLabel lblNewJgoodiesTitle_1 = DefaultComponentFactory.getInstance().createTitle("Duong's Bank");
       
        lblNewJgoodiesTitle_1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblNewJgoodiesTitle_1.setBounds(10, 10, 315, 45);
        contentPane.add(lblNewJgoodiesTitle_1);
        
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		front_end = new FrontEnd();
        		front_end.setVisible(true);
        		dispose();
        	}
        });
        btnSignOut.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnSignOut.setBounds(10, 273, 253, 127);
        contentPane.add(btnSignOut);
	}
}

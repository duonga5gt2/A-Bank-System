import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrontEnd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private Backend backend;
	private MainWindow main_window;
	private SignUpWindow sign_up_window;
	private IDGenerator idGenerated;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrontEnd frame = new FrontEnd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private String removeLeadingZero(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+61" + phoneNumber.substring(1);
        }
        else if (phoneNumber.startsWith("+61")) {
        	return phoneNumber;
        }
        else {
        	return "+61" + phoneNumber;
        }
        
    }

	/**
	 * Create the frame.
	 */
	public FrontEnd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		backend = new Backend();
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Duong's Bank");
		lblNewJgoodiesTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
		lblNewJgoodiesTitle.setIcon(new ImageIcon("C:\\Users\\Duong\\Downloads\\66780194ccda2_download.jpg"));
		lblNewJgoodiesTitle.setBounds(35, 22, 440, 147);
		contentPane.add(lblNewJgoodiesTitle);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Phone Number");
		lblNewJgoodiesLabel.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		lblNewJgoodiesLabel.setBounds(36, 236, 163, 21);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Password");
		lblNewJgoodiesLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		lblNewJgoodiesLabel_1.setBounds(36, 282, 88, 13);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JButton btnNewButton = new JButton("Submit");
		
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
		btnNewButton.setBounds(36, 355, 85, 21);
		contentPane.add(btnNewButton);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Login");
		lblNewJgoodiesLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewJgoodiesLabel_2.setBounds(36, 188, 224, 30);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(36, 299, 235, 19);
		contentPane.add(passwordField);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(36, 253, 235, 19);
		contentPane.add(formattedTextField);
		
		JLabel lblNewLabel_1 = new JLabel("New Member? ");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		lblNewLabel_1.setBounds(36, 386, 85, 21);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Sign Up");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openSignUpWindow();
				dispose();
			}
			
		});
		btnNewButton_1.setBounds(124, 386, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(581, 47, 8, 514);
		contentPane.add(separator);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the user input from the text fields
                String phoneNumber = formattedTextField.getText();
                String password = new String(passwordField.getPassword());
                phoneNumber = removeLeadingZero(phoneNumber);
                System.out.println(phoneNumber);
                try {
                    // Call the LoginDetail method from the Backend class
                    boolean log_in = backend.LoginDetail(phoneNumber, password); //This one does all the checking and then goes all over again to the openMainWindow()
                    if (!log_in) {
                    	JOptionPane.showMessageDialog(FrontEnd.this, "Login failed. Invalid phone number or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                    	dispose();                 
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
	}
	  
	private void openSignUpWindow() {
		 sign_up_window = new SignUpWindow();
		 sign_up_window.setVisible(true);
		 
		 
		 }

	public void openMainWindow(long id, String firstName, String lastName, String email, String phoneNumber, double current_balance) {
		// TODO Auto-generated method stub
		MainWindow mainWindow = new MainWindow(id, firstName, lastName, email, phoneNumber, current_balance);
		mainWindow.setVisible(true);
		
		
	}
}

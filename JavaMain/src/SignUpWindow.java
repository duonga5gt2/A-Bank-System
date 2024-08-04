import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private Backend backend; 
    private IDGenerator idGenerated;
    
    
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String Email;
    private String password;
    private JTextField textField_4;
    /**
     * Launch the application.
     */


    public class NumberFilter extends DocumentFilter {
        
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }

    /**
     * Create the frame.
     */
    public SignUpWindow() {
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 895, 536);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Sign Up");
        lblNewJgoodiesTitle.setFont(new Font("Times New Roman", Font.BOLD, 70));
        lblNewJgoodiesTitle.setBounds(326, 10, 353, 82);
        contentPane.add(lblNewJgoodiesTitle);
        
        textField = new JTextField();
        textField.setBounds(20, 172, 368, 19);
        contentPane.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(20, 223, 368, 19);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        // Fixed country code label
        JLabel countryCodeLabel = new JLabel("+61");
        countryCodeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        countryCodeLabel.setBounds(20, 270, 30, 19);
        contentPane.add(countryCodeLabel);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(60, 270, 328, 19); // Adjust the position and size accordingly
        contentPane.add(textField_2);
        ((AbstractDocument) textField_2.getDocument()).setDocumentFilter(new NumberFilter());
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(20, 318, 368, 19);
        contentPane.add(textField_3);
        
        JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Personal Details");
        lblNewJgoodiesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblNewJgoodiesLabel.setBounds(20, 102, 334, 27);
        contentPane.add(lblNewJgoodiesLabel);
        
        JLabel lblNewLabel = new JLabel("First Name");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel.setBounds(20, 149, 98, 13);
        contentPane.add(lblNewLabel);
        
        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblLastName.setBounds(20, 200, 98, 13);
        contentPane.add(lblLastName);
        
        JLabel lblPhoneNumber = new JLabel("Phone Number");
        lblPhoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblPhoneNumber.setBounds(20, 252, 98, 13);
        contentPane.add(lblPhoneNumber);
        
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblEmail.setBounds(20, 299, 98, 13);
        contentPane.add(lblEmail);
        
        textField_4 = new JPasswordField();
        textField_4.setColumns(10);
        textField_4.setBounds(20, 361, 368, 19);
        contentPane.add(textField_4);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblPassword.setBounds(20, 347, 98, 13);
        contentPane.add(lblPassword);
        
        
        JButton btnNewButton = new JButton("Continue");
        btnNewButton.addActionListener(new ActionListener() {
            

			public void actionPerformed(ActionEvent e) {
                backend = new Backend();
                idGenerated = new IDGenerator();
                
                firstName = textField.getText();
                lastName = textField_1.getText();
                phoneNumber = "+61" + removeLeadingZero(textField_2.getText());
                Email = textField_3.getText();
                password = textField_4.getText();
                boolean rs = backend.isValidName(firstName, lastName) && backend.isValidNumber(phoneNumber) && backend.isValidEmail(Email);
                if (rs == false) {
                    JOptionPane.showMessageDialog(SignUpWindow.this, "Incorrect format for names, phone number or email", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    SecondSignUpWindow secondSignUpWindow = null;
					try {
						secondSignUpWindow = new SecondSignUpWindow(idGenerated.generateID(),firstName, lastName, phoneNumber, Email, password);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    secondSignUpWindow.setVisible(true);
                    dispose();
                }
            
            }
        });
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnNewButton.setBounds(20, 402, 185, 34);
        contentPane.add(btnNewButton);
        
        
    }

    // Helper method to remove leading zero
    private String removeLeadingZero(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return phoneNumber.substring(1);
        }
        return phoneNumber;
    }
}

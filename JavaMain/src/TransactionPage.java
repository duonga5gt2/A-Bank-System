import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TransactionPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private long id;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private Backend backend;
    private MainWindow mainWindow;
    private JTextField textField_3;
    private void showConfirmationDialog(String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform any action when OK button is clicked
                // Close the dialog
                try {
                	JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(okButton);
                    dialog.dispose();
					backend.getMainWindow(id);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        pane.setOptions(new Object[] { okButton });
        JDialog dialog = pane.createDialog("Confirmation");
        dialog.setVisible(true);
    }


    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public TransactionPage(long sender_id, MainWindow mainWindow) {
        
        this.id = sender_id;
        this.mainWindow = mainWindow;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1054, 486);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Sender");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(59, 90, 350, 36);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Receiver");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_1.setBounds(59, 167, 350, 36);
        contentPane.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(59, 200, 301, 19);
        contentPane.add(textField);
        textField.setColumns(10);
        
        // Set the document filter to only allow numeric input
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        
        JLabel lblNewLabel_2 = new JLabel("" + id);
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(59, 136, 261, 13);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_1_1 = new JLabel("Reference");
        lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_1_1.setBounds(59, 228, 350, 36);
        contentPane.add(lblNewLabel_1_1);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(59, 266, 301, 19);
        contentPane.add(textField_1);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(59, 325, 301, 19);
        contentPane.add(textField_2);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Description");
        lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_1_1_1.setBounds(59, 291, 350, 36);
        contentPane.add(lblNewLabel_1_1_1);
        
        JButton btnNewButton = new JButton("Go back");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 mainWindow.setVisible(true);
                 TransactionPage.this.dispose();
        	}
        });
        btnNewButton.setBounds(59, 37, 85, 21);
        contentPane.add(btnNewButton);
        
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(398, 200, 301, 19);
        contentPane.add(textField_3);
        
        JLabel lblNewLabel_1_2 = new JLabel("Amount");
        lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_1_2.setBounds(398, 167, 350, 36);
        contentPane.add(lblNewLabel_1_2);
        
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
                    String receiver_id_text = textField.getText();
                    long receiver_id = Long.parseLong(receiver_id_text);

                    String amount_text = textField_3.getText();
                    double amount = Double.parseDouble(amount_text);

                    backend = new Backend();
                    if (backend.checkValidIDBeforeTransactions(id, receiver_id) && backend.sufficientBalance(id, amount)) {
                        backend.Transactions(true, id, receiver_id, amount);
                        showConfirmationDialog("Transaction successful.");
                    } else {
                    	showConfirmationDialog("Invalid sender, receiver ID or insufficient amount");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred during the transaction.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        		
        	
        });
        btnConfirm.setBounds(59, 368, 85, 21);
        contentPane.add(btnConfirm);
        
        
    }
    
    // DocumentFilter to allow only numeric input
    private static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            if (isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            if (isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isNumeric(String text) {
            return text.matches("\\d+");
        }
    }
}

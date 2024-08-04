import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public class SecondSignUpWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String login_password;
    private JTextField codeTextField;
    private Backend backend;

    // Twilio credentials
    public static final String ACCOUNT_SID = "AC129d35acbcd70bb33217937b40917381";
    public static final String AUTH_TOKEN = "6a47c985ed257a6fcb2a2786662ba6a8";
    public static final String SERVICE_SID = "VA9f1dff0b3a2462616041c6cfdd4e5707";

    /**
     * Create the frame.
     */
    public SecondSignUpWindow(long id, String firstName, String lastName, String phoneNumber, String email, String login_password) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.login_password = login_password;

        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send verification code
        sendVerificationCode(phoneNumber);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 747, 482);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCode = new JLabel("Enter verification code:");
        lblCode.setBounds(10, 11, 200, 14);
        contentPane.add(lblCode);

        codeTextField = new JTextField();
        codeTextField.setBounds(10, 36, 200, 20);
        contentPane.add(codeTextField);
        codeTextField.setColumns(10);

        // Add KeyListener to restrict input to numbers only
        codeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore non-digit characters
                }
            }
        });

        JButton btnVerify = new JButton("Verify");
        btnVerify.setBounds(220, 35, 89, 23);
        contentPane.add(btnVerify);

        JLabel lblStatus = new JLabel("");
        lblStatus.setBounds(10, 67, 300, 14);
        contentPane.add(lblStatus);

        btnVerify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String code = codeTextField.getText();
                if (code.isEmpty()) {
                    lblStatus.setText("Verification code cannot be empty.");
                } else {
                    boolean isValid = verifyCode(phoneNumber, code);
                    if (isValid) {
                        lblStatus.setText("Verification successful!");
                        backend = new Backend();
                        try {
                            backend.InfoIntoDB(id, firstName, lastName, email, phoneNumber, login_password);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            lblStatus.setText("Unexpected Error!");
                        }

                    } else {
                        lblStatus.setText("Verification failed. Please try again.");
                    }
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        Verification verification = Verification.creator(SERVICE_SID, phoneNumber, "sms").create();
    }

    private boolean verifyCode(String phoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(SERVICE_SID)
            .setTo(phoneNumber)
            .setCode(code)
            .create();
        return "approved".equals(verificationCheck.getStatus());
    }
}

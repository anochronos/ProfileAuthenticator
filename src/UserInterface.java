/**
 * @author Akshun Kalra, October 17, 2023
 */


// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The `UserInterface` class implements a graphical user interface for user authentication and registration.
 * It provides options for user login and registration.
 */
public class UserInterface {
    private JFrame landing_page;
    private String savedPassword;
    private String savedFirstName, savedLastName, savedDateOfBirth, savedUserName; //respective variables for text-fields
    private String pageState;
    UserDatabaseHandler dbHandler;
    UserProfile profile;
    
    // Constructor

    /**
     * Initializes the `UserInterface` class, creating the main application window and setting up the user interface.
     */
    UserInterface() {
        dbHandler   = new UserDatabaseHandler();
        profile = new UserProfile();
        landing_page = new JFrame();
        landing_page.setTitle("Main Page");
        landing_page.setSize(600, 400);
        landing_page.setLocationRelativeTo(null);
        landing_page.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the application icon
        ImageIcon backImageIcon = new ImageIcon(getClass().getResource("/login.png"));
        Image image = backImageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(image);
        landing_page.setIconImage(resizedImageIcon.getImage());

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        landing_page.add(panel, BorderLayout.CENTER);
        
        // Create the image label
        JLabel label = new JLabel(resizedImageIcon);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.LEFT);
        panel.add(label);

        // Create the input panel for various text fields
        JPanel inputPanel = new JPanel();
        GridLayout inputLayout = new GridLayout(5, 2);
        inputLayout.setVgap(0);  //No change in gap
        inputPanel.setLayout(inputLayout);

        // buttons
        Button sign_up = new Button();
        sign_up.setLabel("Sign Up");
        sign_up.setPreferredSize(new Dimension(50, 25));

        Button log_in = new Button();
        log_in.setLabel("Log In");
        log_in.setPreferredSize(new Dimension(50, 25));

        Button confirm = new Button();
        confirm.setLabel("Confirm");
        confirm.setPreferredSize(new Dimension(50, 25));

        Button back = new Button();
        back.setLabel("Back");
        back.setPreferredSize(new Dimension(50, 25));

        // text-fields
        JPasswordField input_password = new JPasswordField();
        input_password.setColumns(15);
        
        JTextField input_firstName = new JTextField();
        input_firstName.setColumns(15);

        JTextField input_lastName = new JTextField();
        input_lastName.setColumns(15);

        JTextField input_dateOfBirth = new JTextField();
        input_dateOfBirth.setColumns(15);

        JTextField input_userName = new JTextField();
        input_userName.setColumns(15);
        
        Font labelFont = new Font("Arial", Font.PLAIN, 24);


        JLabel message = new JLabel();
        message.setFont(labelFont);
        message.setText("Welcome!");
        // Username
        JLabel userName = new JLabel();
        userName.setText("Username");
        

        // password
        JLabel password = new JLabel();
        password.setText("Password");
        //password.setBorder(compound);

        // labels for respective fields
        JLabel signUp = new JLabel();
        signUp.setText("Successfully signed up!");

        JLabel logIn = new JLabel();
        logIn.setText("Successfully logged in!");

        JLabel firstName = new JLabel();
        firstName.setText("First Name");

        JLabel lastName = new JLabel();
        lastName.setText("Last Name");

        JLabel dateOfBirth = new JLabel();
        dateOfBirth.setText("Date of Birth (YYYY-MM-DD)");

        // panels for respective input fields
        JPanel userNameField = new JPanel();
        userNameField.setLayout(new FlowLayout());
        userNameField.add(userName);
        userNameField.add(input_userName);
        JPanel passwordField = new JPanel();
        passwordField.setLayout(new FlowLayout());
        passwordField.add(password);
        passwordField.add(input_password);

        JPanel firstNameField = new JPanel();
        firstNameField.setLayout(new FlowLayout());
        firstNameField.add(firstName);
        firstNameField.add(input_firstName);

        JPanel lastNameField = new JPanel();
        lastNameField.setLayout(new FlowLayout());
        lastNameField.add(lastName);
        lastNameField.add(input_lastName);

        JPanel dateOfBirthField = new JPanel();
        dateOfBirthField.setLayout(new FlowLayout());
        dateOfBirthField.add(dateOfBirth);
        dateOfBirthField.add(input_dateOfBirth);

        JPanel buttonField = new JPanel();
        buttonField.setLayout(new FlowLayout());
        buttonField.add(sign_up);
        buttonField.add(log_in);

        JPanel signUpButtonField = new JPanel();
        signUpButtonField.setLayout(new FlowLayout());
        signUpButtonField.add(confirm);
        signUpButtonField.add(back);


        // Sign Up Button Event
        sign_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel signUpPanel = new JPanel();
                landing_page.setTitle("Sign-Up Page");
                pageState = "signUp";
                signUpPanel.setLayout(new GridLayout(7, 2));
                signUpPanel.add(message);
                signUpPanel.add(firstNameField);
                signUpPanel.add(lastNameField);
                signUpPanel.add(dateOfBirthField);
                signUpPanel.add(userNameField);
                signUpPanel.add(passwordField);
                signUpPanel.add(signUpButtonField);
                message.setText("Sign Up");
                panel.remove(inputPanel);
                panel.add(signUpPanel);
                panel.revalidate();
                panel.repaint();
            }
        });

        // Log In Button Event
        log_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("User Login");
                landing_page.setTitle("Log-In Page");
                pageState = "logIn";
                inputPanel.removeAll();
                inputPanel.add(message);
                inputPanel.add(userNameField);
                inputPanel.add(passwordField);
                inputPanel.add(signUpButtonField);
                panel.revalidate();
                panel.repaint();
            }
        });

        // Confirm Button Event
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pageState.equals("signUp")){                    // Added logic that takes data according to what page the UserInterface is on (SignUp or LogIn)
                    savedFirstName = input_firstName.getText();
                    savedLastName = input_lastName.getText();
                    savedDateOfBirth = input_dateOfBirth.getText();
                    savedUserName = input_userName.getText();
                    savedPassword = new String(input_password.getPassword());
                    savedPassword = profile.hashPassword(savedPassword);
                    // call writeuserdata
                    dbHandler.writeUserData(savedFirstName, savedLastName, savedUserName, savedDateOfBirth, savedPassword);
                    message.setText("Registration is successful!");
                    panel.setBackground(Color.GREEN);
                    System.out.println(savedFirstName);
                    System.out.println(savedLastName);
                    System.out.println(savedDateOfBirth);
                    System.out.println(savedUserName);
                    System.out.println(savedPassword);
                }
                else if (pageState.equals("logIn")){
                    savedUserName = input_userName.getText();
                    savedPassword = new String(input_password.getPassword());

                    System.out.println(savedUserName);
                    System.out.println(savedPassword);
                    // call 
                    if(dbHandler.authenticateUser(savedUserName, savedPassword)) {
                        message.setText("Login is successful!");
                        panel.setBackground(Color.GREEN);
                    }
                    else {
                        message.setText("Invalid username or password");
                        panel.setBackground(Color.RED);
                    }
                }
                
                // Clear Fields
                input_firstName.setText("");
                input_lastName.setText("");
                input_dateOfBirth.setText("");
                input_userName.setText("");
                input_password.setText("");
            }
        });

        // Back Button Event
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                landing_page.setTitle("Main Page");
                panel.setBackground(Color.WHITE);
                panel.removeAll();
                inputPanel.removeAll();
                panel.add(label);
                panel.add(inputPanel);
                inputPanel.add(message);
                message.setText("Welcome!");
                inputPanel.add(buttonField);

                // Clear Fields
                input_firstName.setText("");
                input_lastName.setText("");
                input_dateOfBirth.setText("");
                input_userName.setText("");
                input_password.setText("");

                panel.revalidate();
                panel.repaint();
            }
        });

        // Add components to the main panel
        panel.add(label);
        panel.add(inputPanel);

        // Add components to the input panel
        inputPanel.add(message);
        inputPanel.add(buttonField);
    }


    /**
     * Displays the main application window.
     */
    public void show() {
        landing_page.setVisible(true);
    }
}
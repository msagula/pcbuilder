/*First thing that user sees after opening the program
* Contains welcome message, sign in fields/button, and create account button
* All the parts are also retrieved from the database*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

class StartUp extends MainFrame{

    private JTextField userNameField;
    private JPasswordField passwordField;
    private Font defaultPlain;

    private static boolean isPartsRetrieved = false;

    StartUp() {
        //Since the user has not logged in yet, the buttons are disabled
        chatButton.setEnabled(false);
        cartButton.setEnabled(false);
        menuNavButton.setEnabled(false);

        //Retrieves parts from database, only once
        if(!isPartsRetrieved) {
            ResultSet rs = DBAccess.getPartsFromDB();
            try {
                while (rs.next()) {
                    new Part(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            isPartsRetrieved = true;
        }

        defaultPlain = new Font("Calibri", Font.PLAIN, 36);
        Font defaultBold = new Font("Forte", Font.BOLD, 96);

        JLabel welcome = new JLabel("Welcome to PC Builder!");
        welcome.setFont(defaultBold);
        welcome.setForeground(Color.YELLOW);
        JLabel prompt = new JLabel("Login to your Account below:");
        prompt.setFont(defaultPlain);
        prompt.setForeground(Color.WHITE);

        //Top panel contains welcome message
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        topPanel.setOpaque(false);
        topPanel.add(welcome);

        //Middle panel contains login message and username/password labels and fields
        JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 100));
        midPanel.setOpaque(false);
        midPanel.add(prompt);
        JPanel midPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        midPanel2.setOpaque(false);

        //Bottom panel contains sign in and create account buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        bottomPanel.setOpaque(false);

        JLabel userNameLabel = new JLabel(" User Name: ");
        userNameLabel.setFont(defaultPlain);
        userNameLabel.setForeground(Color.WHITE);
        userNameField = new JTextField(10);
        userNameField.setFont(defaultPlain);

        JLabel passwordLabel = new JLabel(" Password: ");
        passwordLabel.setFont(defaultPlain);
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(10);
        passwordField.setFont(defaultPlain);

        JButton SignIn = new JButton("Sign In");
        SignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());
                JLabel label = new JLabel();
                label.setFont(defaultPlain);

                //
                if (username.equals("")) {
                    label.setText("User name cannot be empty!");
                    JOptionPane.showMessageDialog(null, label);
                }

                if (password.equals("")) {
                    label.setText("Password cannot be empty!");
                    JOptionPane.showMessageDialog(null, label);
                }

                if (!username.equals("") && !password.equals("")) {
                    int temp = DBAccess.signIn(username, password);

                    if(temp == -99) {
                        label.setText("Invalid username or password!");
                        JOptionPane.showMessageDialog(null, label);
                    }
                    else if(temp == 1) {
                        new AdminUI().start();
                    }
                    else {
                        new WelcomeUser();
                        MainFrame.start();
                    }
                }
            }
        });

        JButton CreateAccount = new JButton("Create Account");
        CreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountCreationForm();
                MainFrame.start();
            }
        });

        SignIn.setFont(defaultPlain);
        CreateAccount.setFont(defaultPlain);

        midPanel2.add(userNameLabel);
        midPanel2.add(userNameField);
        midPanel2.add(passwordLabel);
        midPanel2.add(passwordField);
        bottomPanel.add(SignIn);
        bottomPanel.add(CreateAccount);

        main.setLayout(new GridLayout(0, 1, 15, 15));
        main.add(topPanel);
        main.add(midPanel);
        main.add(midPanel2);
        main.add(bottomPanel);
    }
}

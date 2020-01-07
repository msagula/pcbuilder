/*A frame that extends the MainFrame
* Allows user to create a new account
* Contains first name, last name, username, password, confirm password fields
* Contains submit and cancel buttons
* Once a 'submit' button is pressed, a new account is added to the database*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class AccountCreationForm extends MainFrame {
    private JTextField userNameField, firstNameField, lastNameField;
    private JPasswordField passField1, passField2;
    private Font defaultPlain;

    AccountCreationForm() {
        //Since the user has not logged in yet, the buttons are disabled
        chatButton.setEnabled(false);
        cartButton.setEnabled(false);
        menuNavButton.setEnabled(false);

        initialize();
    }

    private void initialize() {
        JLabel userNameLabel, firstNameLabel, lastNameLabel, passwordLabel, cPasswordLabel;
        JButton Submit;

        defaultPlain = new Font("Calibri", Font.PLAIN, 30);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(6,1,10,40));
        myPanel.setOpaque(false);

        firstNameLabel = new JLabel(" First Name: ");
        firstNameLabel.setFont(defaultPlain);
        firstNameLabel.setForeground(Color.WHITE);
        firstNameField = new JTextField(10);
        firstNameField.setFont(defaultPlain);

        lastNameLabel = new JLabel(" Last Name: ");
        lastNameLabel.setFont(defaultPlain);
        lastNameLabel.setForeground(Color.WHITE);
        lastNameField = new JTextField(10);
        lastNameField.setFont(defaultPlain);

        userNameLabel = new JLabel(" User Name: ");
        userNameLabel.setFont(defaultPlain);
        userNameLabel.setForeground(Color.WHITE);
        userNameField = new JTextField(10);
        userNameField.setFont(defaultPlain);

        passwordLabel = new JLabel(" Password: ");
        passwordLabel.setFont(defaultPlain);
        passwordLabel.setForeground(Color.WHITE);
        passField1 = new JPasswordField(10);
        passField1.setFont(defaultPlain);

        cPasswordLabel = new JLabel("Confirm Password: ");
        cPasswordLabel.setFont(defaultPlain);
        cPasswordLabel.setForeground(Color.WHITE);
        passField2 = new JPasswordField(10);
        passField2.setFont(defaultPlain);

        Submit = new JButton("Submit");
        Submit.setFont(defaultPlain);
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] s1 = passField1.getPassword();
                char[] s2 = passField2.getPassword();
                String pw1 = new String(s1);
                String pw2 = new String(s2);
                String fn = firstNameField.getText();
                String ln = lastNameField.getText();
                String un = userNameField.getText();

                JLabel label = new JLabel();
                label.setFont(defaultPlain);
                boolean valid = true;

                if (fn.equals("") || ln.equals("") || pw1.equals("") || pw2.equals("") || un.equals("")) {
                    label.setText("One or more fields empty");
                    JOptionPane.showMessageDialog(null, label);
                    valid = false;
                }

                if (!pw1.equals(pw2)) {
                    label.setText("Password does not match!");
                    JOptionPane.showMessageDialog(null, label);
                    valid = false;
                }

                // if all fields are okay, create the user
                if (valid) {
                    if(!DBAccess.createAccount(fn, ln, un, pw1)) {
                        label.setText("Username not available!");
                        JOptionPane.showMessageDialog(null,  label);
                    }
                    else {
                        label.setText("Account created successfully. Log in to continue");
                        JOptionPane.showMessageDialog(null,  label);
                        new StartUp();  //use already existing object??
                        MainFrame.start();
                    }
                }
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(defaultPlain);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartUp();
                MainFrame.start();
            }
        });

        myPanel.add(firstNameLabel);
        myPanel.add(firstNameField);
        myPanel.add(lastNameLabel);
        myPanel.add(lastNameField);
        myPanel.add(userNameLabel);
        myPanel.add(userNameField);
        myPanel.add(passwordLabel);
        myPanel.add(passField1);
        myPanel.add(cPasswordLabel);
        myPanel.add(passField2);
        myPanel.add(Submit);
        myPanel.add(cancelBtn);

        main.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
        main.add(myPanel);
    }
}
import javax.swing.*;
import java.awt.*;

public class WelcomeUser extends MainFrame {

    private static Integer currentUserID;   //set and get in functions instead??
    private static String firstName;
    private static String lastName;
    private static String userName;


    private JPanel midPanel2;
    //private JPanel bottomPanel;

    private JTextField tField;
    private JPasswordField pwField;

    WelcomeUser() {



        //IO.prev = "HELLOUSER";
        Font defaultPlain = new Font("Calibri", Font.PLAIN, 36);
        Font defaultBold = new Font("Forte", Font.BOLD, 96);

        JLabel welcome = new JLabel("Welcome to PC Builder!");
        welcome.setFont(defaultBold);
        welcome.setForeground(Color.YELLOW);
        JLabel prompt = new JLabel("You have successfully logged in!");
        prompt.setFont(defaultPlain);
        prompt.setForeground(Color.WHITE);


        //private JLabel userName;
        //private JLabel pass;
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        topPanel.setOpaque(false);
        topPanel.add(welcome);
        JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 100));
        midPanel.setOpaque(false);
        midPanel.add(prompt);
        /*midPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        midPanel2.setOpaque(false);
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        bottomPanel.setOpaque(false);



        userName = new JLabel(" User Name: ");
        userName.setFont(defaultPlain);
        userName.setForeground(Color.WHITE);
        tField = new JTextField(10);
        tField.setFont(defaultPlain);

        pass = new JLabel(" Password: ");
        pass.setFont(defaultPlain);
        pass.setForeground(Color.WHITE);
        pwField = new JPasswordField(10);
        pwField.setFont(defaultPlain);
        midPanel2.add(userName);
        midPanel2.add(tField);
        midPanel2.add(pass);
        midPanel2.add(pwField);*/
        main.add(topPanel);
        main.add(midPanel);
        //main.add(bottomPanel);
    }

    static void setUserID(Integer i) {
        currentUserID = i;
    }

    static Integer getUserID() {
        return currentUserID;
    }

    static void setFirstName(String fn) { firstName = fn; }

    static void setLastName(String ln) { lastName = ln; }

    static void setUserName(String un) { userName = un; }



    static String getFirstName() {
        return firstName;
    }

}

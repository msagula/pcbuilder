/*A Frames that contains components that are visible in all other frames
* Contains main panel(in which all other frames will include their fields, button, etc),
* navigation panel, and a background image*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

abstract class MainFrame {

    static JFrame jframe = new JFrame("PC Builder");
    JPanel main;
    private JPanel menuNavBar, mainNavBar;
    private JSplitPane split;
    JButton cartButton, menuNavButton, chatButton;
    private BufferedImage background;
    private Image navImage;

    MainFrame() {
        try { //Sets the background image
            background = ImageIO.read(new File("src\\images\\circuitryBackground.png"));
            BufferedImage icon = ImageIO.read(new File("src\\images\\computer_icon.png"));
            jframe.setIconImage(icon);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

        //Splits the Frame into two
        //Top will contain navigation panel and bottom will contain main panel
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        split.setDividerSize(0);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) screenSize.getWidth();
        int h = (int) screenSize.getHeight();
        split.setDividerLocation(110);
        split.setDividerSize(0);
        split.setEnabled(false);

        resetGUI();
        mainNavUI();
        menuNavUI();
        main.setOpaque(false);

        jframe.setBackground(Color.BLACK);
        jframe.setContentPane(split);
        jframe.setTitle("PC Builder");
        jframe.setResizable(false);
        jframe.setLocation((w/2 - w/4), (h/2 - h/4));
        jframe.setSize(1500, 1000);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Sets the jframe visible to user
    static void start() {
        jframe.setVisible(true);
    }

    private void resetGUI() {
        main = new JPanel(new FlowLayout(FlowLayout.CENTER));
        split.setBottomComponent(main);
    }

    //Helper function for creating navigation buttons
    private JButton createBtn(String s, JPanel p) {
        JButton newBtn = new JButton(s);

        //Makes the button transparent
        newBtn.setOpaque(false);
        newBtn.setContentAreaFilled(false);
        newBtn.setBorderPainted(false);

        newBtn.setForeground(Color.BLACK);
        newBtn.setFont(new Font("Calibri", Font.PLAIN, 32));

        p.add(newBtn);
        return newBtn;
    }

    //Navigation panel that contains chat, cart, and menu buttons
    private void mainNavUI() {
        mainNavBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 20));

        cartButton = createBtn("", mainNavBar);
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserCart();
                MainFrame.start();
            }
        });

        menuNavButton = createBtn("", mainNavBar);
        menuNavButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                split.setTopComponent(menuNavBar);
            }
        });

        chatButton = createBtn("", mainNavBar);
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Chat();
                MainFrame.start();
            }
        });

        try { //Sets images for cart, menuNav, and chat buttons
            Image cartImg = ImageIO.read(new File("src\\images\\cart_icon.png"));
            cartImg = cartImg.getScaledInstance(50,  50,  100);
            cartButton.setIcon(new ImageIcon(cartImg));
            navImage = ImageIO.read(new File("src\\images\\nav_icon.png"));
            navImage = navImage.getScaledInstance(50,  50,  100);
            menuNavButton.setIcon(new ImageIcon(navImage));
            Image chatImg = ImageIO.read(new File("src\\images\\chat_icon.png"));
            chatImg = chatImg.getScaledInstance(50,  50,  100);
            chatButton.setIcon(new ImageIcon(chatImg));
        }
        catch (IOException ioe){
            chatButton.setText("Account");
            menuNavButton.setText("Modules");
            chatButton.setText("Chat");
            ioe.printStackTrace();
        }

        mainNavBar.setBackground(Color.WHITE);
        split.setTopComponent(mainNavBar);

    }

    //Navigation panel that is displayed after the menu button is pressed
    //Contains research, shopParts, tutorial, interactive test, and back buttons
    private void menuNavUI() {
        menuNavBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));


        JButton researchButton = createBtn("Research", menuNavBar);
        researchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Research();
                MainFrame.start();
            }
        });

        JButton shopPartsButton = createBtn("Shop Parts", menuNavBar);
        shopPartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShopParts();
                MainFrame.start();
            }
        });

        JButton tutorialButotn = createBtn("Tutorial", menuNavBar);
        tutorialButotn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tutorial();
                MainFrame.start();
            }
        });

        JButton interactiveTestButton = createBtn("Interactive Test", menuNavBar);
        interactiveTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InteractiveTest();
                MainFrame.start();
            }
        });

        JButton returnButton = createBtn("", menuNavBar);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                split.setTopComponent(mainNavBar);
            }
        });

        //Sets image for return button
        try {
            navImage = ImageIO.read(new File("src\\images\\return.png"));
            navImage = navImage.getScaledInstance(50,  50,  100);
            returnButton.setIcon(new ImageIcon(navImage));
        }
        catch (IOException ioe){
            returnButton.setText("");
            ioe.printStackTrace();
        }
        menuNavBar.setBackground(Color.WHITE);
    }
}
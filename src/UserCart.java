/*Displays a table of parts and their information that the user has saved
 * Displays user's name, total parts in the cart, and total price for the parts
 * Contains remove part, empty cart, save cart, and checkout buttons*/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class UserCart extends MainFrame{

    static ArrayList<Part> userSavedParts = new ArrayList<>();
    private DefaultTableModel model;
    private JTable mTable;
    private int totalPrice = 0;
    private JButton removePartButton, emptyCartButton, saveCartButton, checkoutButton;
    private JLabel itemsNumValue, totalPriceValue;

    private static boolean isUserCartRetrieved = false;

    UserCart() {
        //Retrieve parts that the user has saved to the database, only once
        if(!isUserCartRetrieved) {
            ResultSet rs = DBAccess.getUserPartsDB(WelcomeUser.getUserID());
            try {
                while (rs.next()) {
                    Integer partID = rs.getInt(1);
                    for (Part p : Part.getPartList()) {
                        if (p.getPartID().equals(partID)) userSavedParts.add(p);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            isUserCartRetrieved = true;
        }

        Font defaultBold = new Font("Calibri", Font.BOLD, 40);
        Font defaultPlain = new Font("Calibri", Font.PLAIN, 32);

        JPanel mPanel = new JPanel(new BorderLayout());
        mPanel.setOpaque(false);

        //Contains name of logged in user, number of items in cart, and total price for those items
        JPanel topPanel = new JPanel(new GridLayout(2,3));
        topPanel.setOpaque(false);

        //Name of user
        JLabel nameText = new JLabel("Name:");
        nameText.setFont(defaultBold);
        nameText.setForeground(Color.WHITE);
        JLabel nameValue = new JLabel(String.valueOf(WelcomeUser.getFirstName()));
        nameValue.setFont(defaultBold);
        nameValue.setForeground(Color.WHITE);

        //Number of items
        JLabel itemsNumText = new JLabel("Number of Items");
        itemsNumText.setFont(defaultBold);
        itemsNumText.setForeground(Color.WHITE);
        itemsNumValue = new JLabel(String.valueOf(userSavedParts.size()));
        itemsNumValue.setFont(defaultBold);
        itemsNumValue.setForeground(Color.WHITE);

        //Total Price
        JLabel totalPriceText = new JLabel(String.valueOf("Total Price:"));
        totalPriceText.setFont(defaultBold);
        totalPriceText.setForeground(Color.WHITE);
        for (Part p : userSavedParts) totalPrice = totalPrice + p.getPartPrice();
        totalPriceValue = new JLabel(String.valueOf(totalPrice));
        totalPriceValue.setFont(defaultBold);
        totalPriceValue.setForeground(Color.WHITE);

        topPanel.add(nameText);
        topPanel.add(itemsNumText);
        topPanel.add(totalPriceText);
        topPanel.add(nameValue);
        topPanel.add(itemsNumValue);
        topPanel.add(totalPriceValue);


        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("#");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Type");

        mTable = new JTable(model);
        mTable.setModel(model);
        mTable.getTableHeader().setFont(defaultBold);
        mTable.setFont(defaultPlain);
        mTable.setRowHeight(40);
        mTable.getColumnModel().getColumn(0).setPreferredWidth(65);
        mTable.getColumnModel().getColumn(1).setPreferredWidth(450);
        mTable.getColumnModel().getColumn(2).setPreferredWidth(115);
        mTable.getColumnModel().getColumn(3).setPreferredWidth(195);

        JScrollPane scroll = new JScrollPane(mTable);
        scroll.setPreferredSize(new Dimension(1000, 500));
        displayTable();

        //Contains all the buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setOpaque(false);

        ActionListener listener = e -> {
            //Removes selected item from the table
            //Updates the amount of items number
            //Updates total price
            if (e.getSource().equals(removePartButton)) {
                int row = mTable.getSelectedRow();
                Integer value = Integer.parseInt(mTable.getValueAt(row, 0).toString());
                for (int i = 0; i < userSavedParts.size(); i++) {
                    if (userSavedParts.get(i).getPartID().equals(value)) {
                        itemsNumValue.setText(String.valueOf(userSavedParts.size()));
                        totalPrice = totalPrice - userSavedParts.get(i).getPartPrice();
                        totalPriceValue.setText(String.valueOf(totalPrice));
                        userSavedParts.remove(userSavedParts.get(i));
                    }
                }
                displayTable();
            }

            //Removes all the items from the table
            //Updates the amount of items number to 0
            //Updates the price to 0
            else if (e.getSource().equals(emptyCartButton)) {
                userSavedParts.clear();
                itemsNumValue.setText(String.valueOf(userSavedParts.size()));
                totalPrice = 0;
                totalPriceValue.setText(String.valueOf(totalPrice));
                displayTable();
            }

            //Updates all the user parts in the database
            //Displays the message that the items were saved to the database
            else if (e.getSource().equals(saveCartButton)) {
                DBAccess.clearUserSelection(WelcomeUser.getUserID());

                for (Part p : userSavedParts) {
                    DBAccess.saveCartDB(p.getPartID());
                }
                JOptionPane.showMessageDialog(null, "All items have been successfully saved to the database");
            }

            //Displays the message that the transaction has been processed
            //Clears the table
            //Removes the user's parts from the database
            //Updates the amount of items number to 0
            //Updates the price to 0
            else if (e.getSource().equals(checkoutButton)) {
                JOptionPane.showMessageDialog(null, "Transaction has been successfully processed. Total Price: " + totalPrice);
                userSavedParts.clear();
                DBAccess.clearUserSelection(WelcomeUser.getUserID());
                itemsNumValue.setText(String.valueOf(userSavedParts.size()));
                totalPrice = 0;
                totalPriceValue.setText(String.valueOf(totalPrice));
                displayTable();
            }
        };

        removePartButton = new JButton("Remove part");
        removePartButton.addActionListener(listener);
        bottomPanel.add(removePartButton);

        emptyCartButton = new JButton("Empty Cart");
        emptyCartButton.addActionListener(listener);
        bottomPanel.add(emptyCartButton);

        saveCartButton = new JButton("Save Cart");
        saveCartButton.addActionListener(listener);
        bottomPanel.add(saveCartButton);

        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(listener);
        bottomPanel.add(checkoutButton);

        mPanel.add(topPanel, BorderLayout.NORTH);
        mPanel.add(scroll, BorderLayout.CENTER);
        mPanel.add(bottomPanel, BorderLayout.SOUTH);

        main.add(mPanel);
    }

    //Displays all the items in the table
    private void displayTable() {
        model.setRowCount(0);

        for(Part p : userSavedParts) {
            model.addRow(new Object[]{Integer.toString(p.getPartID()), p.getPartName(), Integer.toString(p.getPartPrice()), p.getPartType()});
        }
    }
}

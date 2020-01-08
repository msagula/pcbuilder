/*Displays a list of all parts
 * A table displays part's number, name, price, type, best use
 * A user can use a search bar to look up the part by its name
 * A user can sort the parts by type, name, price, best use
 * A user can select and add part to the cart and press the 'next'
 * button to go to the Tutorial tab*/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class ShopParts extends MainFrame {

    private DefaultTableModel model;
    private JButton searchButton, addButton, nextButton;
    private JRadioButton typeSortButton, nameSortButton, priceButton, bestUseSortButton;
    private JTextField textField;
    private JTable mTable;

    ShopParts() {
        Font defaultBold = new Font("Calibri", Font.BOLD, 40);
        Font defaultPlain = new Font("Calibri", Font.PLAIN, 32);

        JPanel mPanel = new JPanel(new BorderLayout());
        mPanel.setOpaque(false);

        //Contains search box and button
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setOpaque(false);

        JLabel label = new JLabel("Search: ");
        label.setFont(defaultBold);
        label.setForeground(Color.WHITE);
        searchPanel.add(label);

        ActionListener listener = e -> {
            //Searches and displaysthe parts that match the user input
            if (e.getSource().equals(searchButton)) {
                ArrayList<Part> searchPartsList = searchParts(textField.getText());
                displayTable(searchPartsList);
            }

            //Adds selected part to the user cart
            else if (e.getSource().equals(addButton)) {
                int row = mTable.getSelectedRow();
                Integer value = Integer.parseInt(mTable.getValueAt(row, 0).toString());
                for (Part p : Part.getPartList()) {
                    if (p.getPartID().equals(value)) UserCart.userSavedParts.add(p);
                }
            }

            //Opens up the Tutorial tab
            else if (e.getSource().equals(nextButton)) {
                new Tutorial();
                MainFrame.start();
            }

            //Sorts the parts in the table by type
            else if (e.getSource().equals(typeSortButton)) {sortList("type");}

            //Sorts the parts in the table by name
            else if (e.getSource().equals(nameSortButton)) {sortList("name");}

            //Sorts the parts in the table by price
            else if (e.getSource().equals(priceButton)) {sortList("price");}

            //Sorts the parts in the table by best use
            else if (e.getSource().equals(bestUseSortButton)) {sortList("bestUse");}
        };

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(500, 40));
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ArrayList<Part> searchPartsList = searchParts(textField.getText());
                    displayTable(searchPartsList);
                }
            }
        });
        searchPanel.add(textField);

        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(140, 50));
        searchButton.setFont(defaultPlain);
        searchButton.addActionListener(listener);
        searchPanel.add(searchButton);

        //Ensures table cells cannot be changed
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
        model.addColumn("Best Use");

        mTable = new JTable(model);
        mTable.setModel(model);
        mTable.getTableHeader().setFont(defaultBold);
        mTable.setFont(defaultPlain);
        mTable.setRowHeight(40);
        mTable.getColumnModel().getColumn(0).setPreferredWidth(65);
        mTable.getColumnModel().getColumn(1).setPreferredWidth(450);
        mTable.getColumnModel().getColumn(2).setPreferredWidth(115);
        mTable.getColumnModel().getColumn(3).setPreferredWidth(195);
        mTable.getColumnModel().getColumn(4).setPreferredWidth(150);

        //Contains the part table
        JScrollPane scroll = new JScrollPane(mTable);
        scroll.setPreferredSize(new Dimension(1000, 500));
        displayTable(Part.getPartList());

        //Contains the 'add to cart' and 'next' buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        addButton = new JButton("Add to cart");
        addButton.setPreferredSize(new Dimension(180, 50));
        addButton.setFont(defaultPlain);
        addButton.addActionListener(listener);
        bottomPanel.add(addButton);

        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(140, 50));
        nextButton.setFont(defaultPlain);
        nextButton.addActionListener(listener);
        bottomPanel.add(nextButton);

        //Contains the sorting buttons
        JPanel sidePanel = new JPanel(new GridLayout(5,1));
        sidePanel.setOpaque(false);

        ButtonGroup bg = new ButtonGroup();

        JLabel sortLabel = new JLabel("Sort By:");
        sortLabel.setForeground(Color.white);
        sortLabel.setFont(defaultBold);
        sidePanel.add(sortLabel);

        typeSortButton = new JRadioButton("Type");
        typeSortButton.setOpaque(false);
        typeSortButton.setForeground(Color.white);
        typeSortButton.setFont(defaultBold);
        bg.add(typeSortButton);
        typeSortButton.addActionListener(listener);
        sidePanel.add(typeSortButton);

        nameSortButton = new JRadioButton("Name(alphabetically)");
        nameSortButton.setOpaque(false);
        nameSortButton.setForeground(Color.white);
        nameSortButton.setFont(defaultBold);
        bg.add(nameSortButton);
        nameSortButton.addActionListener(listener);
        sidePanel.add(nameSortButton);

        priceButton = new JRadioButton("Price");
        priceButton.setOpaque(false);
        priceButton.setForeground(Color.white);
        priceButton.setFont(defaultBold);
        bg.add(priceButton);
        priceButton.addActionListener(listener);
        sidePanel.add(priceButton);

        bestUseSortButton = new JRadioButton("Best Use");
        bestUseSortButton.setOpaque(false);
        bestUseSortButton.setForeground(Color.white);
        bestUseSortButton.setFont(defaultBold);
        bg.add(bestUseSortButton);
        bestUseSortButton.addActionListener(listener);
        sidePanel.add(bestUseSortButton);

        mPanel.add(searchPanel, BorderLayout.NORTH);
        mPanel.add(scroll, BorderLayout.CENTER);
        mPanel.add(bottomPanel, BorderLayout.SOUTH);
        mPanel.add(sidePanel, BorderLayout.EAST);

        main.add(mPanel);
    }

    //Searches and return a ArrayLIst of parts that matches the given string
    private ArrayList<Part> searchParts(String s) {
        ArrayList<Part> newList = new ArrayList<>();

        for(Part p : Part.getPartList()) {
            if(p.getPartName().contains(s)) newList.add(p);
        }
        return newList;
    }

    //Displays all the parts in the table
    private void displayTable(ArrayList<Part> partsList) {
        model.setRowCount(0);

        for(Part p : partsList) {
            model.addRow(new Object[]{Integer.toString(p.getPartID()), p.getPartName(), Integer.toString(p.getPartPrice()), p.getPartType(), p.getPartBestUse()});
        }
    }

    //Sorts parts in the table
    private void sortList(String option) {
        Collections.sort(Part.getPartList(), new Comparator<Part>() {
            @Override
            public int compare(Part o1, Part o2) {
                switch (option) {
                    case "type": return o1.getPartType().compareTo(o2.getPartType());
                    case "name": return o1.getPartName().compareTo(o2.getPartName());
                    case "price": return o1.getPartPrice().compareTo(o2.getPartPrice());
                    case "bestUse": return o1.getPartBestUse().compareTo(o2.getPartBestUse());
                }
                return 0;
            }
        });
        displayTable(Part.getPartList());
    }
}

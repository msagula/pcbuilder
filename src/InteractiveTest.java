/*An interactive frame that allows users to test their knowledge on PC Building
* Contains an image list with PC parts and an image of the motherboard
* A user clicks on one of the part in the image list and drops it
* on motherboard image. If the user places the part in the correct location, the part
* gets placed on the motherboard image. Otherwise a message gets displayed to try again*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class InteractiveTest extends MainFrame {

    InteractiveTest() {
        super();

        //Resizes the window of the program
        jframe.setSize(new Dimension(3000, 1900));
        main.setLayout(null);

        //Labels for each part in the image list
        JLabel cpu = new JLabel();
        JLabel ram = new JLabel();
        JLabel ssd = new JLabel();
        JLabel powerSupply = new JLabel();
        JLabel graphicsCard = new JLabel();
        JLabel fan = new JLabel();

        //Labels for each place on the motherboard that the part is supposed to be
        JLabel ram2 = new JLabel();
        JLabel ssd2 = new JLabel();
        JLabel powerSupply2 = new JLabel();
        JLabel graphicsCard2 = new JLabel();
        JLabel cpuAndFan = new JLabel();
        JLabel motherboard = new JLabel();

        //Locations in the frame where each part of the image list is
        cpu.setBounds(100,1200,300,300);
        fan.setBounds(400,1200,300,300);
        cpuAndFan.setBounds(1610, 750, 250, 250);
        ram.setBounds(100, 800, 600, 250);

        //Locations in the frame of places on motherboard where each part is supposed to be
        ram2.setBounds(1600, 600, 600, 250);
        powerSupply.setBounds(100, 100, 600, 500);
        powerSupply2.setBounds(2400, 400, 600, 500);
        graphicsCard.setBounds(800, 100, 500, 350);
        graphicsCard2.setBounds(2000, 900, 500, 350);
        motherboard.setBounds(1000, 500, 1800, 1100);

        //Sets images for each part in the image list
        cpu.setIcon(new ImageIcon("src\\images\\cpu.png"));
        fan.setIcon(new ImageIcon("src\\images\\cpu-fan.png"));
        ram.setIcon(new ImageIcon("src\\images\\ram.png"));
        ssd.setIcon(new ImageIcon("src\\"));
        powerSupply.setIcon(new ImageIcon("src\\images\\power-supply.png"));
        graphicsCard.setIcon(new ImageIcon("src\\images\\graphics-card.png"));
        motherboard.setIcon(new ImageIcon("src\\images\\motherboard-test.png"));

        //Names of each part
        //It is used to match the part of the image list to a location on motherboard
        cpu.setName("cpu");
        fan.setName("fan");
        cpuAndFan.setName("cpu and fan");
        ram.setName("ram");
        ram2.setName("ram");
        ssd.setName("ssd");
        ssd2.setName("ssd");
        powerSupply.setName("power supply");
        powerSupply2.setName("power supply");
        graphicsCard.setName("graphics card");
        graphicsCard2.setName("graphics card");

        //Text fields that display the name for each part
        //Each of the text field is located under the image that it describes
        JTextField cpuTF = new JTextField("CPU");
        cpuTF.setEditable(false);
        cpuTF.setBounds(100, 1400, 180, 50);
        cpuTF.setFont(new Font("Courier", Font.BOLD,30));
        main.add(cpuTF);
        JTextField fanTF = new JTextField("CPU Fan");
        fanTF.setEditable(false);
        fanTF.setBounds(400, 1400, 180, 50);
        fanTF.setFont(new Font("Courier", Font.BOLD,30));
        main.add(fanTF);
        JTextField ramTF = new JTextField("DDR4 RAM");
        ramTF.setEditable(false);
        ramTF.setBounds(100, 1100, 500, 50);
        ramTF.setFont(new Font("Courier", Font.BOLD,30));
        main.add(ramTF);
        JTextField powerSupplyTF = new JTextField("Power Supply (SATA connectors: 6GB/s)");
        powerSupplyTF.setEditable(false);
        powerSupplyTF.setBounds(100, 610, 550, 50);
        powerSupplyTF.setFont(new Font("Courier", Font.BOLD,30));
        main.add(powerSupplyTF);
        JTextField gcTF = new JTextField("Graphics Card (PCI 3.0)");
        gcTF.setEditable(false);
        gcTF.setBounds(800, 510, 500, 50);
        gcTF.setFont(new Font("Courier", Font.BOLD,30));
        main.add(gcTF);

        MouseListener ml = new MouseListener() {
            String initialComponent = "";
            Component lastEntered;
            String temp = "";
            boolean cpuInserted = false;

            @Override
            public void mouseClicked(MouseEvent e) {}

            //Gets the name the part in the image list that the user clicked on
            @Override
            public void mousePressed(MouseEvent e) {
                JComponent comp = (JComponent) e.getSource();
                initialComponent = comp.getName();
            }

            //Checks if the name of the part that the user grabbed from the image list matches the name
            //of the label on the motherboard where the user dropped that part
            @Override
            public void mouseReleased(MouseEvent e) {
                JComponent jc = (JComponent) e.getSource();
                //Gets the name of the label on motherboard where the mouse pointer last entered
                temp = lastEntered.getName();
                if(temp.contains(initialComponent)){
                    //if fan was dropped before cpu
                    if(initialComponent.equals("fan") && !cpuInserted)
                        JOptionPane.showMessageDialog(null, "You need to insert CPU first");
                    else {
                        if(initialComponent.equals("cpu"))
                            cpuInserted = true;
                        TransferHandler th = jc.getTransferHandler();
                        th.exportAsDrag(jc, e, TransferHandler.COPY);
                    }
                }
                else  JOptionPane.showMessageDialog(null, "This part does'nt go here. Try again");

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lastEntered = e.getComponent();
            }

            @Override
            public void mouseExited(MouseEvent e) { }
        };

        cpu.addMouseListener(ml);
        fan.addMouseListener(ml);
        cpuAndFan.addMouseListener(ml);
        ram.addMouseListener(ml);
        ram2.addMouseListener(ml);
        ssd.addMouseListener(ml);
        ssd2.addMouseListener(ml);
        powerSupply.addMouseListener(ml);
        powerSupply2.addMouseListener(ml);
        graphicsCard.addMouseListener(ml);
        graphicsCard2.addMouseListener(ml);

        cpu.setTransferHandler(new TransferHandler("icon"));
        fan.setTransferHandler(new TransferHandler("icon"));
        cpuAndFan.setTransferHandler(new TransferHandler("icon"));
        ram.setTransferHandler(new TransferHandler("icon"));
        ram2.setTransferHandler(new TransferHandler("icon"));
        ssd.setTransferHandler(new TransferHandler("icon"));
        ssd2.setTransferHandler(new TransferHandler("icon"));
        powerSupply.setTransferHandler(new TransferHandler("icon"));
        powerSupply2.setTransferHandler(new TransferHandler("icon"));
        graphicsCard.setTransferHandler(new TransferHandler("icon"));
        graphicsCard2.setTransferHandler(new TransferHandler("icon"));

        main.add(cpu);
        main.add(fan);
        main.add(cpuAndFan);
        main.add(ram);
        main.add(ram2);
        main.add(ssd);
        main.add(ssd2);
        main.add(powerSupply);
        main.add(powerSupply2);
        main.add(graphicsCard);
        main.add(graphicsCard2);
        main.add(motherboard);

        jframe.setLocationRelativeTo(null);
    }
}

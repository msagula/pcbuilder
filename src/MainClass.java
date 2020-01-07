/*Reads in a file that contain all the parts and inserts them to a database
* Creates the StartUp object*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        new DBAccess();

        //if the part table in the database is empty, it reads in a parts file and inserts all the parts
        //into the database
        if(DBAccess.isPartsDBEmpty()) {
            File file = new File("src\\parts.csv");
            Scanner sc;
            try {
                sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    Scanner temp = new Scanner(sc.nextLine()).useDelimiter(",");
                    String partType = temp.next();
                    String partName = temp.next();
                    Integer partPrice = temp.nextInt();
                    String partBestUse = temp.next();

                    DBAccess.insertPartDB(partType, partName, partPrice, partBestUse);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        new StartUp();
        MainFrame.start();
    }
}

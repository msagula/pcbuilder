/*Contains functions that save and retrieve information from  database
* Connects to database*/
import java.sql.*;

final class DBAccess {
    private static Statement stmt;
    private static ResultSet rs;
    private static String sqlString;

    DBAccess() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pcbuilder","root","PCBuilder1!");
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Returns true if the Parts table in the database is empty
    static Boolean isPartsDBEmpty() {
        sqlString = "Select * From parts;";
        try {
            rs = stmt.executeQuery(sqlString);

            if (!rs.next()) return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Inserts part's information into database
    static void insertPartDB(String partType, String partName, Integer partPrice, String partBestUse) {
        sqlString = "INSERT INTO parts (Type, Name, Price, BestUsage) VALUES ('" +
                partType + "', '" + partName + "', " + partPrice + ", '" + partBestUse + "');";
        try {
            stmt.executeUpdate(sqlString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Retrieves Part's information from database
    static ResultSet getPartsFromDB () {
        sqlString = "SELECT * FROM parts";
        try {
            rs = stmt.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //Retrieves user's information from database
    static Integer signIn(String username, String password) {
        try {
            sqlString = "SELECT * FROM users WHERE UserName LIKE '" + username + "' AND Password LIKE '" + password + "' LIMIT 1;";
            rs = stmt.executeQuery(sqlString);

            //If resultSet is empty, username and password combo does not exist
            if (!rs.next()) {
                return -99;
            }
            //Otherwise assigns user's information to WelcomeUser class
            else {
                WelcomeUser.setUserID(rs.getInt("idusers"));
                WelcomeUser.setFirstName(rs.getString("FirstName"));
                WelcomeUser.setLastName(rs.getString("LastName"));
                WelcomeUser.setUserName(rs.getString("UserName"));

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return WelcomeUser.getUserID();
    }

    //Insert new user's information into the database
    //Returns false if username already exists
    static Boolean createAccount(String firstName, String lastName, String userName, String password) {
        try {
            //Checks if username already exists
            sqlString = "SELECT * FROM users WHERE UserName LIKE '" + userName + "';";
            rs = stmt.executeQuery(sqlString);

            //Username is available
            if (!rs.next()) {
                sqlString = "INSERT INTO users (FirstName, LastName, UserName, Password) VALUES ('" +
                        firstName + "', '" + lastName + "', '" + userName + "', '" + password + "');";
                try {
                    stmt.executeUpdate(sqlString);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                return false;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    //Gets all the parts from the database that the user has saved in their cart
    static ResultSet getUserPartsDB(Integer id) {
        sqlString = "SELECT PartID FROM userselections WHERE UserID LIKE '" + id + "';";
        try {
            rs = stmt.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //Adds all parts to the database that the user's has saved
    static void saveCartDB(Integer partID) {
        sqlString = "INSERT INTO userselections (PartID, UserID) VALUES ('" + partID + "', '" + WelcomeUser.getUserID() + "');";
        try {
            stmt.executeUpdate(sqlString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Clears user's selection from the database
    static void clearUserSelection(int uID) {
        sqlString = "DELETE FROM userselections WHERE UserID LIKE '" + uID + "';";
        try {
            stmt.executeUpdate(sqlString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

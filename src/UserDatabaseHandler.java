/**
 * @author Akshun Kalra, October 17, 2023
 */

// Imports
import java.sql. *;

/**
 * The DatabaseHandler class manages database connections and provides methods
 * for reading and writing user data to a MySQL database.
 */
public class UserDatabaseHandler {
    Connection con;
    UserProfile userProfile; // create object of profile, using methods from profile class

    /**
     * Initializes the DatabaseHandler and establishes a connection to the MySQL database.
     * @throws ClassNotFoundException if the MySQL JDBC driver class is not found.
     * @throws SQLException if there is an error connecting to the database.
     */
    public UserDatabaseHandler(){  
        try{  
        // Load the MySQL JDBC driver class
        Class.forName("com.mysql.jdbc.Driver");  

        // Connect to database (URL, username, password)
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userprofiledb?characterEncoding=latin1","newuser","");  
        //utf8mb4

        // Initialize the userProfile object
        userProfile = new UserProfile();
        }
        catch(Exception e){ 
            // Handles all exceptions that occur during database operations
            System.out.println(e); // prints the exception
        }  
    }  


    /**
     * Reads user data from the "users" table in the database and prints the results.
     */
    public void readUserData() {
        try {
            // Create a statement object to execute SQL queries
            Statement stmt=con.createStatement();  

            // Execute SQL query to select all records from the __ table, result is stored in the ResultSet
            ResultSet rs = stmt.executeQuery("select * from users");  

            // Iterates through the ResultSet and prints the values of columns 1-3, for each row
            while(rs.next())  
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6));  
            //con.close();  // Close the database connection
        } 
        
        catch (SQLException e) {
            System.out.println(e);
        } 
    }


    /**
     * Writes user data to the "users" table in the database.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param userName The user's username.
     * @param dateOfBirth The user's date of birth.
     * @param password The user's password.
     */
    public void writeUserData(String firstName, String lastName, String userName, String dateOfBirth, String password) {
        try {

            // Define the SQL query for inserting user data into the "users" table
            String sql = "insert into users (firstName, lastName, userName, dob, passwordhash) values (?, ?, ?, ?, ?)"; // '?' are placeholders

            // Create a prepared statement with the SQL query
            PreparedStatement stmt = con.prepareStatement(sql); 

            // Set the parameter values for the prepared statement
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, userName);
            stmt.setString(4, dateOfBirth);
            stmt.setString(5, password);

            stmt.execute(); // Execute the SQL statement to insert the user data into the database
           // con.close(); // Close the database connection
        } 
        
        catch(SQLException e) {
            System.out.println(e);
        }
    }


    /**
     * Retrieves the password hash for a given username from the database.
     * @param username The username for which to retrieve the password hash.
     * @return The password hash as a String, or null if the username is not found.
     */
    public String getPasswordbyUsername(String username) {
        try { 
            // Define the SQL query to select the "PasswordHash" column from the "users" table where the username matches.
            String sql = "Select PasswordHash from  users where Username = ?";

            // Create a prepared statement for executing the SQL query
            PreparedStatement stmt = con.prepareStatement(sql);

            // Set the parameter in the prepared statement to the provided username.
            stmt.setString(1, username);

            // Execute the SQL query and store the result in a ResultSet.
            ResultSet rs = stmt.executeQuery();

            // Check if there is a result (user found).
            if (rs.next()) {
                return rs.getString("PasswordHash"); // Return the "PasswordHash" column value from the ResultSet.
            } 
            
            // If there is no result (user not found)
            else {
                return null;
            }
        }

        catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    

    /**
     * Authenticates a user by comparing the entered password with the stored password hash in the database.
     * @param username The username of the user to authenticate.
     * @param enteredpassword The password entered by the user for authentication.
     * @return True if the entered password matches the stored password hash; false otherwise.
     */
    public boolean authenticateUser(String username, String enteredpassword) {

        // Hash the entered password using userProfile's password hashing algorithm.
        String hashedPassword = userProfile.hashPassword(enteredpassword);

        // Retrieve the stored password hash from the DB based on the username.
        String storedPassword = getPasswordbyUsername(username);

        if (storedPassword != null && storedPassword.equals(hashedPassword)) {
            return true;
        }

        else {
            return false;
        }
    }
} 
    

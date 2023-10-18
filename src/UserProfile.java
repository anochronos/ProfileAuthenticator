/**
 * @author Akshun Kalra, October 17, 2023
 */


// Imports
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;


/**
 * The Profile class represents a user profile with various attributes and methods related to the user.
 * Each profile contains information such as first name, last name, date of birth, username, password,
 * last logged in time, and a flag indicating whether the user is currently logged in or not.
 */
public class UserProfile {

    // Attributes
    private LocalDateTime lastLogged;
    private boolean loggedIn;
    private String profilePassword;
    private String userName;
    private LocalDate dateOfBirth;
    private String lastName;
    private String firstName;
    private MessageDigest md;

    /**
     * Print the user's profile information.
     * The method prints the following information:
     * - Username
     * - Name (combining the first name and last name)
     * - Date of Birth
     * - Last logged in time
     */
    public void printProfile() {
        System.out.println("Username: " + userName);
        System.out.println("Name: " + userName);
        System.out.println("D.O.B.: " + dateOfBirth);
        System.out.println("Last logged in: " + lastLogged);
    }


    // Constructor

    /**
     * Default constructor for creating a Profile object with default attribute values.
     * - firstName: "Default first"
     * - lastName: "Default last"
     * - dateOfBirth: January 1, 2023
     * - profilePassword: Hashed value of "Default Password" using SHA-256
     * - userName: Concatenation of default first name and last name
     * - loggedIn: false
     * - lastLogged: Current date and time
     *
     * Creates a default user profile with predefined values and hashed password.
     */
    public UserProfile() {
        firstName = "Default first";
        lastName = "Default last";
        dateOfBirth = LocalDate.of(2023, 01, 01);
        profilePassword = hashText("Default Password");
        userName = firstName +  lastName;
        loggedIn = false;
        lastLogged = LocalDateTime.now();
    }


    // Methods

    /**
     * Check if the entered password matches the profile password.
     * If the entered password matches, the user is considered logged in and the last logged in time is updated.
     * @param enteredPassword The password entered by the user for login.
     * @return True if the entered password matches the profile password; false otherwise.
     */
    public boolean checkPassword(String enteredPassword) {
        if (hashText(enteredPassword).equals(profilePassword)){
            this.loggedIn = true;
            this.lastLogged = LocalDateTime.now();
            return true;
        } else{
            this.loggedIn = false;
            return false;
        }
    }


    /**
     * Hash the input text using SHA-256 algorithm.
     * @param profilePassword The text to be hashed.
     * @return The hashed text as a hexadecimal string.
     */
    private String hashText(String profilePassword) {
        try {
            md = MessageDigest.getInstance("SHA-256"); // Get an instance of the MessageDigest class with the SHA-256 algorithm
            byte[] messageDigest = md.digest(profilePassword.getBytes()); // Compute the hash value of the input text and store it in the byte array messageDigest
            BigInteger messageNumber = new BigInteger(1, messageDigest); // Convert the byte array messageDigest to a positive BigInteger with 1 sign bit
            String hashText = messageNumber.toString(16); // Convert the BigInteger messageNumber to a hexadecimal string representation

            // Ensure that the hexadecimal string has a length of 64 by adding leading zeros if necessary
            while (hashText.length() < 64) {
                hashText = "0" + hashText;
            }
            return hashText; // Return the hashed text
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw a RuntimeException if the specified algorithm (SHA-256) is not available
        }
    }


    /**
    * Hashes the input password using the SHA-256 algorithm.
    *
    * @param password The plain text password to be hashed.
    * @return The hashed password as a hexadecimal string.
    */
    public String hashPassword(String password) {
        return hashText(password); // returns the result of the private hashText method
    }


    // Getters

    /**
     * Get the last logged in date and time of the user profile.
     *
     * @return The last logged in date and time as a LocalDateTime object.
     */
    public LocalDateTime getLastLogged() {
        return lastLogged;
    }

    /**
     * Get the profile password of the user.
     *
     * @return The profile password as a String.
     */
    public String getProfilePassword() {
        return profilePassword;
    }

    /**
     * Get the username of the user.
     *
     * @return The username as a String.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the date of birth of the user profile.
     *
     * @return The date of birth as a LocalDate object.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Get the last name of the user.
     *
     * @return The last name as a String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the first name of the user.
     *
     * @return The first name as a String.
     */
    public String getFirstName() {
        return firstName;
    }


    // Setters

    /**
     * Set the logged-in status of the user.
     * This method is private and is internally used to set the loggedIn attribute.
     *
     * @param loggedIn True if the user is logged in; false otherwise.
     */
    private void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Set the date of birth of the user profile.
     * The date should be provided in the format "YYYY-MM-DD".
     *
     * @param date The date of birth in the format "YYYY-MM-DD".
     */
    public void setDateOfBirth(String date) {
        this.dateOfBirth = LocalDate.parse(date);
    }

    /**
     * Set the last logged in date and time to the current date and time.
     * This method is private and is internally used to update the lastLogged attribute.
     */
    private void setLastLogged() {
        this.lastLogged = LocalDateTime.now();
    }

    /**
     * Set the profile password and hash it using the SHA-256 algorithm.
     * The method takes the input profile password, computes its SHA-256 hash, and sets the hashed value as the profile password.
     *
     * @param profilePassword The plain text profile password to be set.
     */
    public void setProfilePassword(String profilePassword) {
        this.profilePassword = hashText(profilePassword);
    }

    /**
     * Set the username of the user.
     *
     * @param userName The username as a String.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Set the last name of the user.
     *
     * @param lastName The last name as a String.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set the first name of the user.
     *
     * @param firstName The first name as a String.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

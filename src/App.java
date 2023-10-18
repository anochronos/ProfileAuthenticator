/**
 * @author Akshun Kalra, October 17, 2023
 */


/**
 * The `App` class serves as the entry point for the application.
 * It initializes the graphical user interface (GUI) and displays the main application window.
 */
public class App {
    public static void main(String[] args) throws Exception {
            
        // Create a new instance of the `Gui` class
        UserInterface UI = new UserInterface();

        // Show the main application window
        UI.show();
        
    }
}

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String storedFirstName;
    private String storedLastName;
    private String enteredUsername;
    private String enteredPassword;
    
    // Constructor
    public Login(String storedUsername, String storedPassword, String storedFirstName, 
                 String storedLastName, String enteredUsername, String enteredPassword) {
        this.storedUsername = storedUsername;
        this.storedPassword = storedPassword;
        this.storedFirstName = storedFirstName;
        this.storedLastName = storedLastName;
        this.enteredUsername = enteredUsername;
        this.enteredPassword = enteredPassword;
    }
    
    // Getters and Setters
    public String getStoredUsername() { return storedUsername; }
    public void setStoredUsername(String storedUsername) { this.storedUsername = storedUsername; }
    
    public String getStoredPassword() { return storedPassword; }
    public void setStoredPassword(String storedPassword) { this.storedPassword = storedPassword; }
    
    public String getStoredFirstName() { return storedFirstName; }
    public void setStoredFirstName(String storedFirstName) { this.storedFirstName = storedFirstName; }
    
    public String getStoredLastName() { return storedLastName; }
    public void setStoredLastName(String storedLastName) { this.storedLastName = storedLastName; }
    
    public String getEnteredUsername() { return enteredUsername; }
    public void setEnteredUsername(String enteredUsername) { this.enteredUsername = enteredUsername; }
    
    public String getEnteredPassword() { return enteredPassword; }
    public void setEnteredPassword(String enteredPassword) { this.enteredPassword = enteredPassword; }
    
    public boolean loginUser() {
        return storedUsername.equals(enteredUsername) && storedPassword.equals(enteredPassword);
    }
    
    public String returnLoginStatus() {
        if (loginUser()) {
            return "Welcome " + storedFirstName + "," + storedLastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

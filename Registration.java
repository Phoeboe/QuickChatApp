 import java.util.regex.Pattern;

public class Registration {
    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;
    
    // Constructor
    public Registration(String username, String password, String cellPhone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getCellPhone() { return cellPhone; }
    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    // Validation methods
    public boolean checkUserName() {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity() {
        if (password == null) return false;
        
        // Check length
        if (password.length() < 8) return false;
        
        // Check for capital letter
        if (!Pattern.compile("[A-Z]").matcher(password).find()) return false;
        
        // Check for number
        if (!Pattern.compile("[0-9]").matcher(password).find()) return false;
        
        // Check for special character
        if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]").matcher(password).find()) return false;
        
        return true;
    }
    
    public boolean checkCellPhoneNumber() {
        if (cellPhone == null) return false;
        
        // Regular expression created with assistance from ChatGPT (OpenAI, 2024)
        // Reference: OpenAI. (2024). ChatGPT [Large language model]. https://chat.openai.com
        // Pattern explanation: 
        // ^\\+27 - Starts with international code for South Africa (+27)
        // [0-9]{9}$ - Followed by exactly 9 digits
        String cellPhoneRegex = "^\\+27[0-9]{9}$";
        return Pattern.matches(cellPhoneRegex, cellPhone);
    }
    
    public String getDetailedValidationResults() {
        StringBuilder result = new StringBuilder();
        
        boolean usernameValid = checkUserName();
        boolean passwordValid = checkPasswordComplexity();
        boolean cellPhoneValid = checkCellPhoneNumber();
        
        if (!usernameValid) {
            result.append("✗ Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
        } else {
            result.append("✓ Username successfully captured.\n");
        }
        
        if (!passwordValid) {
            result.append("✗ Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
        } else {
            result.append("✓ Password successfully captured.\n");
        }
        
        if (!cellPhoneValid) {
            result.append("✗ Cell phone number incorrectly formatted or does not contain international code.\n");
        } else {
            result.append("✓ Cell phone number successfully added.\n");
        }
        
        return result.toString();
    }
    
    public String registerUser() {
        boolean usernameValid = checkUserName();
        boolean passwordValid = checkPasswordComplexity();
        boolean cellPhoneValid = checkCellPhoneNumber();
        
        if (usernameValid && passwordValid && cellPhoneValid) {
            return "User registered successfully.";
        } else {
            return getDetailedValidationResults();
        }
    }
}
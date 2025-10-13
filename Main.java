import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatApplication chatApp = new ChatApplication();
        
        System.out.println("=== User Registration System ===");
        
        boolean registrationSuccessful = false;
        Registration registration = null;
        
        // Registration retry loop
        while (!registrationSuccessful) {
            // Get user input            System.out.print("Enter first name: ");
             System.out.print("Enter first name:");
            String firstName = scanner.nextLine();
            
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            System.out.print("Enter cell phone number: ");
            String cellPhone = scanner.nextLine();
            

            // Registration process
            registration = new Registration(username, password, cellPhone, firstName, lastName);
            String registrationResult = registration.registerUser();
            System.out.println(registrationResult);
            
            if (registrationResult.equals("User registered successfully.")) {
                registrationSuccessful = true;
            } else {
                System.out.println("\nRegistration failed. Would you like to try again? (yes/no)");
                String retry = scanner.nextLine();
                if (!retry.equalsIgnoreCase("yes")) {
                    System.out.println("Registration cancelled. Exiting application.");
                    scanner.close();
                    return;
                }
                System.out.println("Please enter your details again:\n");
            }
        }
        
        // Login retry loop
        boolean loginSuccessful = false;
        int loginAttempts = 0;
        final int MAX_LOGIN_ATTEMPTS = 3;
        
        while (!loginSuccessful && loginAttempts < MAX_LOGIN_ATTEMPTS) {
            System.out.println("\n--- Login (Attempt " + (loginAttempts + 1) + "/" + MAX_LOGIN_ATTEMPTS + ") ---");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            // Login process
            Login login = new Login(
                registration.getUsername(), 
                registration.getPassword(), 
                registration.getFirstName(), 
                registration.getLastName(), 
                loginUsername, 
                loginPassword
            );
            
            boolean loginSuccess = login.loginUser();
            String loginStatus = login.returnLoginStatus();
            System.out.println(loginStatus);
            
            if (loginSuccess) {
                loginSuccessful = true;
                chatApp.setLoggedIn(true);
                chatApp.startApplication(registration.getFirstName(), registration.getLastName());
            } else {
                loginAttempts++;
                if (loginAttempts < MAX_LOGIN_ATTEMPTS) {
                    System.out.println("Login failed. Please try again.");
                } else {
                    System.out.println("Maximum login attempts reached. Exiting application.");
                }
            }
        }
        
        scanner.close();
    }
}
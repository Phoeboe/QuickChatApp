import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ChatApplication {
    private ArrayList<Message> messages;
    private int totalMessagesSent;
    private boolean isLoggedIn;
    private Scanner scanner;
    
    public ChatApplication() {
        this.messages = new ArrayList<>();
        this.totalMessagesSent = 0;
        this.isLoggedIn = false;
        this.scanner = new Scanner(System.in);
    }
    
    public void startApplication(String firstName, String lastName) {
        if (!isLoggedIn) {
            System.out.println("Please login first to use QuickChat.");
            return;
        }
        
        System.out.println("Welcome to QuickChat.");
        
        // Get number of messages user wants to send with retry
        int messageCount = 0;
        boolean validMessageCount = false;
        
        while (!validMessageCount) {
            System.out.print("How many messages do you wish to send? ");
            try {
                messageCount = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (messageCount > 0) {
                    validMessageCount = true;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    sendMessagesWithRetry(messageCount, firstName, lastName);
                    break;
                case 2:
                    showRecentMessages();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
    
    private int getMenuChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return choice;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number (1-3): ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n=== QuickChat Menu ===");
        System.out.println("1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Enter your choice: ");
    }
    
    private void sendMessagesWithRetry(int messageCount, String firstName, String lastName) {
        for (int i = 0; i < messageCount; i++) {
            boolean messageSent = false;
            
            while (!messageSent) {
                System.out.println("\n--- Message " + (i + 1) + " of " + messageCount + " ---");
                
                // Generate message ID
                String messageId = generateMessageID();
                
                // Get recipient with validation
                String recipient = getValidRecipient();
                
                // Get message with validation
                String messageText = getValidMessage();
                
                // Create message object
                Message message = new Message(messageId, recipient, messageText, firstName, lastName);
                
                // Validate and process message
                if (message.checkMessageLength()) {
                    String action = message.sentMessage();
                    
                    if (action.equals("send")) {
                        message.createMessageHash();
                        messages.add(message);
                        totalMessagesSent++;
                        
                        // Display message details using JOptionPane
                        message.displayMessageDetails();
                        
                        System.out.println("Message sent successfully!");
                        messageSent = true;
                    } else if (action.equals("store")) {
                        message.storeMessage();
                        System.out.println("Message stored for later.");
                        messageSent = true;
                    } else {
                        System.out.println("Message disregarded. Would you like to try this message again? (yes/no)");
                        String retry = scanner.nextLine();
                        if (!retry.equalsIgnoreCase("yes")) {
                            messageSent = true; // Move to next message
                        }
                    }
                } else {
                    System.out.println("Message too long! Please enter a message of less than 250 characters.");
                    System.out.println("Would you like to try again? (yes/no)");
                    String retry = scanner.nextLine();
                    if (!retry.equalsIgnoreCase("yes")) {
                        messageSent = true; // Move to next message
                    }
                }
            }
        }
        
        System.out.println("\nTotal messages sent in this session: " + totalMessagesSent);
        System.out.println("Overall total messages sent: " + returnTotalMessages());
    }
    
    private String getValidRecipient() {
        while (true) {
            System.out.print("Enter recipient cell phone number (format: +27xxxxxxxxx): ");
            String recipient = scanner.nextLine();
            
            Message tempMessage = new Message("0000000000", recipient, "test", "test", "test");
            int recipientValid = tempMessage.checkRecipientCell();
            
            if (recipientValid == 1) {
                return recipient;
            } else {
                System.out.println("Invalid recipient format. Please use South African format: +27 followed by 9 digits.");
                System.out.println("Would you like to try again? (yes/no)");
                String retry = scanner.nextLine();
                if (!retry.equalsIgnoreCase("yes")) {
                    return recipient; // Return even if invalid, let main logic handle it
                }
            }
        }
    }
    
    private String getValidMessage() {
        while (true) {
            System.out.print("Enter your message: ");
            String messageText = scanner.nextLine();
            
            if (messageText.length() <= 250) {
                return messageText;
            } else {
                System.out.println("Message is too long! Maximum 250 characters.");
                System.out.println("Your message length: " + messageText.length());
                System.out.println("Would you like to try again? (yes/no)");
                String retry = scanner.nextLine();
                if (!retry.equalsIgnoreCase("yes")) {
                    return messageText; // Return even if too long, let main logic handle it
                }
            }
        }
    }
    
    private void showRecentMessages() {
        System.out.println("Coming Soon.");
    }
    
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }
    
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    public String printMessages() {
        if (messages.isEmpty()) {
            return "No messages sent yet.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== All Sent Messages ===\n");
        for (Message message : messages) {
            sb.append("Message ID: ").append(message.getMessageId()).append("\n");
            sb.append("Recipient: ").append(message.getRecipient()).append("\n");
            sb.append("Message: ").append(message.getMessageText()).append("\n");
            sb.append("Hash: ").append(message.getMessageHash()).append("\n");
            sb.append("------------------------\n");
        }
        return sb.toString();
    }
}

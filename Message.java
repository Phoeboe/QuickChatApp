import javax.swing.JOptionPane;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Message {
    private String messageId;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String senderFirstName;
    private String senderLastName;
    private static int messageCounter = 0;
    
    public Message(String messageId, String recipient, String messageText, String senderFirstName, String senderLastName) {
        this.messageId = messageId;
        this.recipient = recipient;
        this.messageText = messageText;
        this.senderFirstName = senderFirstName;
        this.senderLastName = senderLastName;
        messageCounter++;
    }
    
    // Validation methods
    public boolean checkMessageID() {
        return messageId != null && messageId.length() == 10 && messageId.matches("\\d+");
    }
    
    public boolean checkRecipientCell() {
        if (recipient == null) return false;
        
        // Regular expression for South African cell numbers with international code
        // +27 followed by 9 digits (total 12 characters including +)
        String cellPhoneRegex = "^\\+27[0-9]{9}$";
        return Pattern.matches(cellPhoneRegex, recipient);
    }
    
    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }
    
    public String createMessageHash() {
        if (messageId == null || messageText == null) {
            return "INVALID:HASH";
        }
        
        // Get first two numbers of message ID
        String firstTwo = messageId.substring(0, 2);
        
        // Get message number (using static counter)
        int messageNum = messageCounter;
        
        // Extract first and last words from message
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Create hash in uppercase
        this.messageHash = (firstTwo + ":" + messageNum + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }
    
    public String sentMessage() {
        System.out.println("\nChoose an action for this message:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Enter your choice (1-3): ");
        
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                return "send";
            case 2:
                return "disregard";
            case 3:
                return "store";
            default:
                System.out.println("Invalid choice. Message will be disregarded.");
                return "disregard";
        }
    }
    
    public void displayMessageDetails() {
        String details = "Message Details:\n" +
                        "Message ID: " + messageId + "\n" +
                        "Message Hash: " + messageHash + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText + "\n" +
                        "Sender: " + senderFirstName + " " + senderLastName;
        
        JOptionPane.showMessageDialog(null, details, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void storeMessage() {
        // Simple JSON storage implementation
        String jsonMessage = "{\n" +
                           "  \"messageId\": \"" + messageId + "\",\n" +
                           "  \"recipient\": \"" + recipient + "\",\n" +
                           "  \"message\": \"" + messageText.replace("\"", "\\\"") + "\",\n" +
                           "  \"sender\": \"" + senderFirstName + " " + senderLastName + "\",\n" +
                           "  \"hash\": \"" + messageHash + "\",\n" +
                           "  \"stored\": true\n" +
                           "}";
        
        // In a real application, you would write this to a file
        System.out.println("Message stored as JSON:");
        System.out.println(jsonMessage);
    }
    
    // Getters
    public String getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getSenderFirstName() { return senderFirstName; }
    public String getSenderLastName() { return senderLastName; }
    
    // Static method to get total message count
    public static int getMessageCounter() { return messageCounter; }
}
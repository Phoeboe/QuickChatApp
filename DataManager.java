import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private ArrayList<Message> sentMessages;
    private ArrayList<Message> disregardedMessages;
    private ArrayList<Message> storedMessages;
    private ArrayList<String> messageHashes;
    private ArrayList<String> messageIDs;
    
    public DataManager() {
        this.sentMessages = new ArrayList<>();
        this.disregardedMessages = new ArrayList<>();
        this.storedMessages = new ArrayList<>();
        this.messageHashes = new ArrayList<>();
        this.messageIDs = new ArrayList<>();
    }
    
    // Add this method to DataManager for loading test data
    public void loadTestData() {
        // Test Data Message 1 - Sent
        Message msg1 = new Message("001", "+27834557896", "Did you get the cake?", "Test", "User");
        msg1.createMessageHash();
        addMessage(msg1, "sent");
        
        // Test Data Message 2 - Stored
        Message msg2 = new Message("002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Test", "User");
        msg2.createMessageHash();
        addMessage(msg2, "stored");
        
        // Test Data Message 3 - Disregarded
        Message msg3 = new Message("003", "+27834484567", "Yohoooo, I am at your gate.", "Test", "User");
        msg3.createMessageHash();
        addMessage(msg3, "disregard");
        
        // Test Data Message 4 - Sent
        Message msg4 = new Message("004", "0838884567", "It is dinner time!", "Test", "User");
        msg4.createMessageHash();
        addMessage(msg4, "sent");
        
        // Test Data Message 5 - Stored
        Message msg5 = new Message("005", "+27838884567", "Ok, I am leaving without you.", "Test", "User");
        msg5.createMessageHash();
        addMessage(msg5, "stored");
    }
    
    // Add message to appropriate arrays
    public void addMessage(Message message, String status) {
        if ("sent".equals(status)) {
            sentMessages.add(message);
        } else if ("disregard".equals(status)) {
            disregardedMessages.add(message);
        } else if ("stored".equals(status)) {
            storedMessages.add(message);
        }
        
        if (message.getMessageHash() != null) {
            messageHashes.add(message.getMessageHash());
        }
        messageIDs.add(message.getMessageId());
    }
    
    // 2a. Display sender and recipient of all sent messages
    public String displaySentMessagesSenders() {
        StringBuilder result = new StringBuilder();
        result.append("=== Sent Messages ===\n");
        for (Message msg : sentMessages) {
            result.append("From: ").append(msg.getSenderFirstName()).append(" ")
                  .append(msg.getSenderLastName())
                  .append(" | To: ").append(msg.getRecipient()).append("\n");
        }
        return result.toString();
    }
    
    // 2b. Display the longest sent message
    public String findLongestMessage() {
        if (sentMessages.isEmpty()) return "No sent messages found.";
        
        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        return longest.getMessageText();
    }
    
    // 2c. Search for message by ID
    public String searchMessageByID(String messageId) {
        // Check sent messages
        for (Message msg : sentMessages) {
            if (msg.getMessageId().equals(messageId)) {
                return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessageText();
            }
        }
        // Check stored messages
        for (Message msg : storedMessages) {
            if (msg.getMessageId().equals(messageId)) {
                return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessageText() + " [STORED]";
            }
        }
        return "Message ID not found: " + messageId;
    }
    
    // 2d. Search messages by recipient
    public String searchMessagesByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        result.append("Messages for ").append(recipient).append(":\n");
        
        boolean found = false;
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.append("- [SENT] ").append(msg.getMessageText()).append("\n");
                found = true;
            }
        }
        
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.append("- [STORED] ").append(msg.getMessageText()).append("\n");
                found = true;
            }
        }
        
        if (!found) {
            result.append("No messages found for this recipient.");
        }
        
        return result.toString();
    }
    
    // 2e. Delete message by hash
    public String deleteMessageByHash(String hash) {
        // Check sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            if (msg.getMessageHash() != null && msg.getMessageHash().equals(hash)) {
                String deletedText = msg.getMessageText();
                sentMessages.remove(i);
                messageHashes.remove(hash);
                return "Message successfully deleted: " + deletedText;
            }
        }
        
        // Check stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            if (msg.getMessageHash() != null && msg.getMessageHash().equals(hash)) {
                String deletedText = msg.getMessageText();
                storedMessages.remove(i);
                messageHashes.remove(hash);
                return "Message successfully deleted: " + deletedText;
            }
        }
        
        return "Message hash not found: " + hash;
    }
    
    // 2f. Display full report of all sent messages
    public String displayFullReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== FULL MESSAGE REPORT ===\n\n");
        
        report.append("SENT MESSAGES (").append(sentMessages.size()).append("):\n");
        for (Message msg : sentMessages) {
            report.append("Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("Recipient: ").append(msg.getRecipient()).append("\n")
                  .append("Message: ").append(msg.getMessageText()).append("\n")
                  .append("----------------------------\n");
        }
        
        report.append("\nSTORED MESSAGES (").append(storedMessages.size()).append("):\n");
        for (Message msg : storedMessages) {
            report.append("Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("Recipient: ").append(msg.getRecipient()).append("\n")
                  .append("Message: ").append(msg.getMessageText()).append("\n")
                  .append("----------------------------\n");
        }
        
        report.append("\nDISREGARDED MESSAGES: ").append(disregardedMessages.size()).append("\n");
        
        return report.toString();
    }
    
    // Getters for unit testing
    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
}

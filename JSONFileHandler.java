import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class SimpleJSONHandler {
    private static final String FILE_NAME = "messages.json";
    
    // Save a single message to JSON file
    public static void saveToJSON(String messageId, String recipient, String message, 
                                String firstName, String lastName, String hash, String status) {
        try {
            String jsonArray;
            
            // Read existing file or start new array
            if (Files.exists(Paths.get(FILE_NAME))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                // Remove the closing bracket to add new object
                jsonArray = content.substring(0, content.length() - 1);
                if (!jsonArray.equals("[")) {
                    jsonArray += ",";
                }
            } else {
                jsonArray = "[";
            }
            
            // Create JSON object manually (escape quotes in message)
            String escapedMessage = message.replace("\"", "\\\"");
            String jsonObject = String.format(
                "{\"messageId\":\"%s\",\"recipient\":\"%s\",\"message\":\"%s\"," +
                "\"firstName\":\"%s\",\"lastName\":\"%s\",\"hash\":\"%s\",\"status\":\"%s\"}",
                messageId, recipient, escapedMessage, firstName, lastName, hash, status
            );
            
            // Write back to file
            jsonArray += jsonObject + "]";
            Files.write(Paths.get(FILE_NAME), jsonArray.getBytes(), 
                       StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            
            System.out.println("Message saved to JSON file: " + FILE_NAME);
            
        } catch (Exception e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }
    
    // Load all messages from JSON file
    public static List<String> loadAllMessages() {
        List<String> messages = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(FILE_NAME))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                
                // Simple parsing - extract messages from JSON array
                String[] parts = content.split("\\},\\{");
                for (String part : parts) {
                    // Extract message text
                    if (part.contains("\"message\":")) {
                        int start = part.indexOf("\"message\":\"") + 11;
                        int end = part.indexOf("\"", start);
                        if (start > 10 && end > start) {
                            String messageText = part.substring(start, end).replace("\\\"", "\"");
                            messages.add(messageText);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading messages from JSON: " + e.getMessage());
        }
        return messages;
    }
    
    // Load stored messages only (status = "stored")
    public static List<String> loadStoredMessages() {
        List<String> storedMessages = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(FILE_NAME))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                
                // Split into individual message objects
                String[] messageObjects = content.split("\\},\\{");
                for (String obj : messageObjects) {
                    // Check if this is a stored message
                    if (obj.contains("\"status\":\"stored\"")) {
                        // Extract message text
                        if (obj.contains("\"message\":")) {
                            int start = obj.indexOf("\"message\":\"") + 11;
                            int end = obj.indexOf("\"", start);
                            if (start > 10 && end > start) {
                                String messageText = obj.substring(start, end).replace("\\\"", "\"");
                                storedMessages.add(messageText);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading stored messages: " + e.getMessage());
        }
        return storedMessages;
    }
    
    // Display all messages from JSON file
    public static void displayAllMessages() {
        try {
            if (Files.exists(Paths.get(FILE_NAME))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                System.out.println("=== ALL MESSAGES IN JSON FILE ===");
                System.out.println(content);
                System.out.println("==================================");
            } else {
                System.out.println("No JSON file found. Send some messages first!");
            }
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }
}

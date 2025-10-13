import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SimpleJSONHandler {
    private static final String FILE_NAME = "messages.json";
    
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
            
        } catch (Exception e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}
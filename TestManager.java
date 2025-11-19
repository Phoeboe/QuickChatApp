public class TestManager {
    public static void main(String[] args) {
        System.out.println("=== STARTING TESTS ===");
        
        // Create DataManager
        DataManager dm = new DataManager();
        System.out.println("DataManager created");
        
        // Load test data
        dm.loadTestData();
        System.out.println("Test data loaded");
        
        // Run simple tests
        System.out.println("\n=== TEST 1: Count Messages ===");
        System.out.println("Sent: " + dm.getSentMessages().size());
        System.out.println("Stored: " + dm.getStoredMessages().size());
        System.out.println("Disregarded: " + dm.getDisregardedMessages().size());
        
        System.out.println("\n=== TEST 2: Longest Message ===");
        System.out.println(dm.findLongestMessage());
        
        System.out.println("\n=== TEST 3: Search ===");
        System.out.println(dm.searchMessageByID("004"));
        
        System.out.println("\n=== TEST 4: Search by Recipient ===");
        System.out.println(dm.searchMessagesByRecipient("+27838884567"));
        
        System.out.println("\n=== TEST 5: Full Report ===");
        System.out.println(dm.displayFullReport());
        
        System.out.println("\n=== TESTS COMPLETED ===");
    }
}

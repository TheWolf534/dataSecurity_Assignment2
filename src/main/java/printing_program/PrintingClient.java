package printing_program;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.Thread.sleep;

public class PrintingClient {
    public final PrintingService service;
    public String token;

    public PrintingClient() throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        service = (PrintingService) registry.lookup("PrintService");
    }

    public static void main(String[] args) {
        try {
            PrintingClient client = new PrintingClient();

            // Login first
            String username = "Alice";
            String password = "Alicepassword";
            Response tokenResponse = client.service.getToken(username, password);

            if (tokenResponse.getStatusCode() != 200) {
                System.out.println("Error: " + tokenResponse.getMessage());
                return;
            }

            String token = tokenResponse.getMessage(); // The token is in the message for successful login

            // Try printing with username/password
            Response printResponse = client.service.print(username, password, "document.pdf", "printer1");
            System.out.println("Print Status: " + printResponse.getStatusCode());
            System.out.println("Print Message: " + printResponse.getMessage());

            //sleep(10000);

            // Try queue with token
            Response queueResponse = client.service.queue(token, "printer1");
            System.out.println("Queue Status: " + queueResponse.getStatusCode());
            System.out.println("Queue Message: " + queueResponse.getMessage());

            // Try unauthorized operation
            Response topQueueResponse = client.service.topQueue("Fred", "Fredpassword", "printer1", 1);
            System.out.println("TopQueue Status: " + topQueueResponse.getStatusCode());
            System.out.println("TopQueue Message: " + topQueueResponse.getMessage());

            // Try authorized operation
            Response topQueueResponse2 = client.service.topQueue( "Bob", "Bobapassword", "printer1", 1);
            System.out.println("TopQueue Status: " + topQueueResponse2.getStatusCode());
            System.out.println("TopQueue Message: " + topQueueResponse2.getMessage());

            // Try authorized operation
            Response topQueueResponse3 = client.service.queue( "George", "Georgepassword", "printer1");
            System.out.printf("TopQueue Status: %d\n", topQueueResponse3.getStatusCode());
            System.out.printf("TopQueue Message: %s\n", topQueueResponse3.getMessage());

            // Try authorized operation
            Response topQueueResponse4 = client.service.restart( "George", "Georgepassword");
            System.out.printf("Restart Status: %d\n", topQueueResponse4.getStatusCode());
            System.out.printf("Restart Message: %s\n", topQueueResponse4.getMessage());

        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
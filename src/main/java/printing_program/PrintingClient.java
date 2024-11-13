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
            String username = "user1";
            String password = "password";
            String token = client.service.getToken(username, password);
            // Then perform operations
            String result = client.service.print(username, password, "document.pdf", "printer1");
            System.out.println("----" + result);
            sleep(10000);
            result = client.service.queue(token, "printer1");
            System.out.println("----" + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
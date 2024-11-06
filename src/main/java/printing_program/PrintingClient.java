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

    public boolean login(String username, String password) {
        try {
            token = service.login(username, password);

            return token != null;
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            PrintingClient client = new PrintingClient();

            // Login first
            boolean loginSuccess = client.login("user1", "password1");
            if (loginSuccess) {
                System.out.println("Login successful. Token: " + client.token);
                // Then perform operations
                String result = client.service.print(client.token, "document.pdf", "printer1");
                System.out.println("----" + result);
                sleep(10000);
                result = client.service.queue(client.token, "printer1");
                System.out.println("----" + result);
            } else {
                System.out.println("Login failed");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
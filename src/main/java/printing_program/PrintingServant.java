package printing_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.IOException;

public class PrintingServant extends UnicastRemoteObject implements PrintingService {
    private final Authentication auth;

    public PrintingServant(String credentialsPath) throws RemoteException, IOException {
        super();
        this.auth = new Authentication(credentialsPath);
    }

    private void authenticateWithCredentials(String username, String password) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            throw new RemoteException("Invalid credentials");
        }
    }

    private void authenticateWithToken(String token) throws RemoteException {
        if (!auth.validateToken(token)) {
            throw new RemoteException("Invalid token");
        }
    }

    @Override
    public String getToken(String username, String password) throws RemoteException {
        return auth.loginSession(username, password);
    }

    // Print operations
    @Override
    public String print(String username, String password, String filename, String printer) throws RemoteException {
        System.out.println("Username: " + username + " Password: " + password);
        authenticateWithCredentials(username, password);
        return "Printing " + filename + " on " + printer;
    }

    @Override
    public String print(String token, String filename, String printer) throws RemoteException {
        authenticateWithToken(token);
        return "Printing " + filename + " on " + printer;
    }

    // Queue operations
    @Override
    public String queue(String username, String password, String printer) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Queue on " + printer;
    }

    @Override
    public String queue(String token, String printer) throws RemoteException {
        authenticateWithToken(token);
        return "Queue on " + printer;
    }

    // TopQueue operations
    @Override
    public String topQueue(String username, String password, String printer, int job) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Job " + job + " moved to the top of the queue on " + printer;
    }

    @Override
    public String topQueue(String token, String printer, int job) throws RemoteException {
        authenticateWithToken(token);
        return "Job " + job + " moved to the top of the queue on " + printer;
    }

    // Start operations
    @Override
    public String start(String username, String password) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Starting printer";
    }

    @Override
    public String start(String token) throws RemoteException {
        authenticateWithToken(token);
        return "Starting printer";
    }

    // Stop operations
    @Override
    public String stop(String username, String password) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Stopping printer";
    }

    @Override
    public String stop(String token) throws RemoteException {
        authenticateWithToken(token);
        return "Stopping printer";
    }

    // Restart operations
    @Override
    public String restart(String username, String password) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Restarting printer";
    }

    @Override
    public String restart(String token) throws RemoteException {
        authenticateWithToken(token);
        return "Restarting printer";
    }

    // Status operations
    @Override
    public String status(String username, String password, String printer) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Status of " + printer;
    }

    @Override
    public String status(String token, String printer) throws RemoteException {
        authenticateWithToken(token);
        return "Status of " + printer;
    }

    // ReadConfig operations
    @Override
    public String readConfig(String username, String password, String parameter) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Reading config " + parameter;
    }

    @Override
    public String readConfig(String token, String parameter) throws RemoteException {
        authenticateWithToken(token);
        return "Reading config " + parameter;
    }

    // SetConfig operations
    @Override
    public String setConfig(String username, String password, String parameter, String value) throws RemoteException {
        authenticateWithCredentials(username, password);
        return "Setting config " + parameter + " to " + value;
    }

    @Override
    public String setConfig(String token, String parameter, String value) throws RemoteException {
        authenticateWithToken(token);
        return "Setting config " + parameter + " to " + value;
    }
}
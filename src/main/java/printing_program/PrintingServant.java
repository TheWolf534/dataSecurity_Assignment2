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

    // Helper method to verify token before any operation
    private void verifyToken(String token) throws RemoteException {
        if (token == null || !auth.validateToken(token)) {
            throw new RemoteException("Authentication required or token invalid");
        }
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        return auth.login(username, password);
    }

    @Override
    public String log(String token, String message) throws RemoteException {
        verifyToken(token);
        return message;
    }

    @Override
    public String print(String token, String filename, String printer) throws RemoteException {
        verifyToken(token);
        return log(token, "Printing " + filename + " on " + printer);
    }

    @Override
    public String queue(String token, String printer) throws RemoteException {
        verifyToken(token);
        return log(token, "Queue on " + printer);
    }

    @Override
    public String topQueue(String token, String printer, int job) throws RemoteException {
        verifyToken(token);
        return log(token, "Job " + job + " moved to the top of the queue on " + printer);
    }

    @Override
    public String start(String token) throws RemoteException {
        verifyToken(token);
        return log(token, "Starting printer");
    }

    @Override
    public String stop(String token) throws RemoteException {
        verifyToken(token);
        return log(token, "Stopping printer");
    }

    @Override
    public String restart(String token) throws RemoteException {
        verifyToken(token);
        return log(token, "Restarting printer");
    }

    @Override
    public String status(String token, String printer) throws RemoteException {
        verifyToken(token);
        return log(token, "Status of " + printer);
    }

    @Override
    public String readConfig(String token, String parameter) throws RemoteException {
        verifyToken(token);
        return log(token, "Reading config " + parameter);
    }

    @Override
    public String setConfig(String token, String parameter, String value) throws RemoteException {
        verifyToken(token);
        return log(token, "Setting config " + parameter + " to " + value);
    }
}
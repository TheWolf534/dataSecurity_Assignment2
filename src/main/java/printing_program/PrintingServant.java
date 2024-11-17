package printing_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.IOException;

public class PrintingServant extends UnicastRemoteObject implements PrintingService {
    private final Authentication auth;
    private final Authorizator accessControl;

    public PrintingServant(String credentialsPath, String hierarchyPath, String groupPath) throws RemoteException, IOException {
        super();
        this.auth = new Authentication(credentialsPath);
        this.accessControl = new Authorizator(hierarchyPath, groupPath);
    }

    @Override
    public Response getToken(String username, String password) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Invalid credentials");
        }
        String token = auth.loginSession(username, password);
        return new Response(200, token);
    }

    // Print operations
    @Override
    public Response print(String username, String password, String filename, String printer) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "print")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Printing " + filename + " on " + printer);
    }

    @Override
    public Response print(String token, String filename, String printer) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "print")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Printing " + filename + " on " + printer);
    }

    // Queue operations
    @Override
    public Response queue(String username, String password, String printer) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "queue")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Queue on " + printer);
    }

    @Override
    public Response queue(String token, String printer) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "queue")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Queue on " + printer);
    }

    // TopQueue operations
    @Override
    public Response topQueue(String username, String password, String printer, int job) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "topQueue")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Job " + job + " moved to the top of the queue on " + printer);
    }

    @Override
    public Response topQueue(String token, String printer, int job) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "topQueue")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Job " + job + " moved to the top of the queue on " + printer);
    }

    // Start operations
    @Override
    public Response start(String username, String password) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "start")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Starting printer");
    }

    @Override
    public Response start(String token) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "start")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Starting printer");
    }

    // Stop operations
    @Override
    public Response stop(String username, String password) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "stop")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Stopping printer");
    }

    @Override
    public Response stop(String token) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "stop")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Stopping printer");
    }

    // Restart operations
    @Override
    public Response restart(String username, String password) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "restart")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Restarting printer");
    }

    @Override
    public Response restart(String token) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "restart")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Restarting printer");
    }

    // Status operations
    @Override
    public Response status(String username, String password, String printer) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "status")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Status of " + printer);
    }

    @Override
    public Response status(String token, String printer) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "status")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Status of " + printer);
    }

    // ReadConfig operations
    @Override
    public Response readConfig(String username, String password, String parameter) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "readConfig")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Reading config " + parameter);
    }

    @Override
    public Response readConfig(String token, String parameter) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "readConfig")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Reading config " + parameter);
    }

    // SetConfig operations
    @Override
    public Response setConfig(String username, String password, String parameter, String value) throws RemoteException {
        if (!auth.verifyPassword(username, password)) {
            return new Response(401, "Authentication failed");
        }
        if (!accessControl.isAuthorized(username, "setConfig")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Setting config " + parameter + " to " + value);
    }

    @Override
    public Response setConfig(String token, String parameter, String value) throws RemoteException {
        if (!auth.validateToken(token)) {
            return new Response(401, "Invalid token");
        }
        String username = auth.getUsernameFromToken(token);
        if (!accessControl.isAuthorized(username, "setConfig")) {
            return new Response(403, "Unauthorized operation");
        }
        return new Response(200, "Setting config " + parameter + " to " + value);
    }
}
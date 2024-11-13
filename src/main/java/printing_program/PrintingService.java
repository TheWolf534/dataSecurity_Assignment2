package printing_program;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintingService extends Remote {
    // Token generation
    String getToken(String username, String password) throws RemoteException;

    // Print operations
    String print(String username, String password, String filename, String printer) throws RemoteException;
    String print(String token, String filename, String printer) throws RemoteException;

    // Queue operations
    String queue(String username, String password, String printer) throws RemoteException;
    String queue(String token, String printer) throws RemoteException;

    // TopQueue operations
    String topQueue(String username, String password, String printer, int job) throws RemoteException;
    String topQueue(String token, String printer, int job) throws RemoteException;

    // Start operations
    String start(String username, String password) throws RemoteException;
    String start(String token) throws RemoteException;

    // Stop operations
    String stop(String username, String password) throws RemoteException;
    String stop(String token) throws RemoteException;

    // Restart operations
    String restart(String username, String password) throws RemoteException;
    String restart(String token) throws RemoteException;

    // Status operations
    String status(String username, String password, String printer) throws RemoteException;
    String status(String token, String printer) throws RemoteException;

    // ReadConfig operations
    String readConfig(String username, String password, String parameter) throws RemoteException;
    String readConfig(String token, String parameter) throws RemoteException;

    // SetConfig operations
    String setConfig(String username, String password, String parameter, String value) throws RemoteException;
    String setConfig(String token, String parameter, String value) throws RemoteException;
}
package printing_program;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintingService extends Remote {
    // Token generation
    Response getToken(String username, String password) throws RemoteException;

    // Print operations
    Response print(String username, String password, String filename, String printer) throws RemoteException;
    Response print(String token, String filename, String printer) throws RemoteException;

    // Queue operations
    Response queue(String username, String password, String printer) throws RemoteException;
    Response queue(String token, String printer) throws RemoteException;

    // TopQueue operations
    Response topQueue(String username, String password, String printer, int job) throws RemoteException;
    Response topQueue(String token, String printer, int job) throws RemoteException;

    // Start operations
    Response start(String username, String password) throws RemoteException;
    Response start(String token) throws RemoteException;

    // Stop operations
    Response stop(String username, String password) throws RemoteException;
    Response stop(String token) throws RemoteException;

    // Restart operations
    Response restart(String username, String password) throws RemoteException;
    Response restart(String token) throws RemoteException;

    // Status operations
    Response status(String username, String password, String printer) throws RemoteException;
    Response status(String token, String printer) throws RemoteException;

    // ReadConfig operations
    Response readConfig(String username, String password, String parameter) throws RemoteException;
    Response readConfig(String token, String parameter) throws RemoteException;

    // SetConfig operations
    Response setConfig(String username, String password, String parameter, String value) throws RemoteException;
    Response setConfig(String token, String parameter, String value) throws RemoteException;
}
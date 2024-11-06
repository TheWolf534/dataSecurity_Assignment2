package printing_program;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintingService extends Remote {
    // Authentication method
    String login(String username, String password) throws RemoteException;

    // Modified existing methods to include token
    String log(String token, String message) throws RemoteException;
    String print(String token, String filename, String printer) throws RemoteException;
    String queue(String token, String printer) throws RemoteException;
    String topQueue(String token, String printer, int job) throws RemoteException;
    String start(String token) throws RemoteException;
    String stop(String token) throws RemoteException;
    String restart(String token) throws RemoteException;
    String status(String token, String printer) throws RemoteException;
    String readConfig(String token, String parameter) throws RemoteException;
    String setConfig(String token, String parameter, String value) throws RemoteException;
}
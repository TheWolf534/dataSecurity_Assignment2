package printing_program;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintingService extends Remote {
    public void print(String filename, String printer) throws RemoteException;
    public String queue() throws RemoteException;
    public void topQueue(String printer, int job) throws RemoteException;
    public void start() throws RemoteException;
    public void stop() throws RemoteException;
    public void restart() throws RemoteException;
    public String status(String printer) throws RemoteException;
    public String readConfig(String parameter) throws RemoteException;
    public void setConfig(String parameter, String value) throws RemoteException;
}

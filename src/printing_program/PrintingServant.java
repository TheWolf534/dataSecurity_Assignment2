package printing_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintingServant extends UnicastRemoteObject implements PrintingService {
    public PrintingServant() throws RemoteException {
        super();
    }

    @Override
    public void print(String filename, String printer) throws RemoteException {

    }

    @Override
    public String queue() throws RemoteException {
        return "";
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {

    }

    @Override
    public void start() throws RemoteException {

    }

    @Override
    public void stop() throws RemoteException {

    }

    @Override
    public void restart() throws RemoteException {

    }

    @Override
    public String status(String printer) throws RemoteException {
        return "";
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return "";
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {

    }
}

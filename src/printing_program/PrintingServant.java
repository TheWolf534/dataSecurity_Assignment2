package printing_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintingServant extends UnicastRemoteObject implements PrintingService {
    public PrintingServant() throws RemoteException {
        super();
    }


    @Override
    public String log(String message) throws RemoteException {
        return message;
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        return log("Printing " + filename + " on " + printer);
    }

    @Override
    public String queue(String printer) throws RemoteException {
        return log("Queue on " + printer);
    }

    @Override
    public String topQueue(String printer, int job) throws RemoteException {
        return log("Job " + job + " moved to the top of the queue on " + printer);
    }

    @Override
    public String start() throws RemoteException {
        return log("Starting printer");
    }

    @Override
    public String stop() throws RemoteException {
        return log("Stopping printer");
    }

    @Override
    public String restart() throws RemoteException {
        return log("Restarting printer");
    }

    @Override
    public String status(String printer) throws RemoteException {
        return log("Status of " + printer);
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return log("Reading config " + parameter);
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        return log("Setting config " + parameter + " to " + value);
    }
}

package sample_RMI_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServant extends UnicastRemoteObject implements HelloService {
    public HelloServant() throws RemoteException {
        super();
    }

    public String echo(String input) throws RemoteException {
        return "Server says: " + input;
    }
}

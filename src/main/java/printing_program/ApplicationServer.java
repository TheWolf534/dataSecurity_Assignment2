package printing_program;

import sample_RMI_program.HelloServant;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    public static void main(String[] args) throws IOException {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("PrintService", new PrintingServant("./src/main/java/printing_program/passwords.txt"));
    }
}

package printing_program;

import sample_RMI_program.HelloServant;
import sample_RMI_program.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        PrintingService service = (PrintingService) Naming.lookup("rmi://localhost:1099/printingServer");
        System.out.println("----" + service.print("file1", "printer1"));
    }
}

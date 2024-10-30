import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:1099/hello");
        System.out.println("----" + service.echo("hey server"));
    }
}

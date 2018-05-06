import java.rmi.Remote ;
import java.rmi.RemoteException ;
import java.lang.StringBuilder;

public interface Blockchain extends Remote{
    public StringBuilder printBlockchainImpl() throws RemoteException ;
}

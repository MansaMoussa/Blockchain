import java.rmi.Remote ;
import java.rmi.RemoteException ;

public interface Blockchain extends Remote{
    public String printBlockchainImpl(Integer numeroNoeud_Block) throws RemoteException ;
}

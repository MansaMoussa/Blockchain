import java.rmi.Remote ;
import java.rmi.RemoteException ;
import java.math.BigDecimal;
import java.lang.StringBuilder;

public interface Blockchain extends Remote{
    //Print a blockchain from a neighbour
    public StringBuilder printBlockchainImpl() throws RemoteException ;
    //Ask a blockchain of a neighbour, from 0 to profondeur
    public Blockchain askBlockchain(BigDecimal profondeur) throws RemoteException ;

}

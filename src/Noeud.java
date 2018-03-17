import java.rmi.Remote ;
import java.rmi.RemoteException ;

public interface Noeud extends Remote{
  public Integer max_size_blockchain = new Integer(10000); //10 kBytes
  public Integer getPublicKey() throws RemoteException ;
  public Float getBlockMoney() throws RemoteException ;
}

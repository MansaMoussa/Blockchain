import java.rmi.Remote ;
import java.rmi.RemoteException ;
import java.math.BigDecimal;

public interface Noeud extends Remote{
  public Integer max_size_blocks = new Integer(10 000); //10 kBytes
  public Integer getPublicKey() throws RemoteException ;
  public BigDecimal getBlockMoney() throws RemoteException ;
}

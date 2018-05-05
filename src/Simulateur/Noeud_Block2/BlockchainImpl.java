import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;

public class BlockchainImpl extends UnicastRemoteObject implements Blockchain{

    public LinkedList<Block> blocks; //liste chaînée des Block-s (de max 10 kBytes?)

    public BlockchainImpl()
    throws RemoteException{
      super();
    };

    public String printBlockchainImpl(Integer numeroNoeud_Block)
    throws RemoteException{
        return ("Here is the blockchain from Noeud_Block2 : "+numeroNoeud_Block);
    }
}

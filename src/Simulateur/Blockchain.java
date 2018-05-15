import java.rmi.Remote ;
import java.rmi.RemoteException ;
import java.math.BigDecimal;
import java.lang.StringBuilder;
import java.util.LinkedList;

import java.io.Serializable;


public interface Blockchain extends Remote, Serializable{
    //Print a blockchain from a neighbour
    public StringBuilder printBlockchainImpl(String myPort) throws RemoteException ;
    //Ask a blockchain of a neighbour, from 0 to profondeur
    public Blockchain askBlockchain(int profondeur) throws RemoteException ;

    public int getHeight() throws RemoteException ;

    public LinkedList<Block> sendBlockList() throws RemoteException;

    public BigDecimal howMuchMoneyDoesAParticipantHas(BigDecimal nP)
    throws RemoteException;
}

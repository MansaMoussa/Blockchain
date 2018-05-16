import java.rmi.Remote ;
import java.rmi.RemoteException ;
import java.math.BigDecimal;
import java.lang.StringBuilder;
import java.util.LinkedList;
import java.io.Serializable;

public interface NoeudBlock extends Remote, Serializable {

    public void connectToNoeudBlockParticipant(Noeud_Participant np) throws RemoteException;
    public void connectToNoeudBlockNoeud(NoeudBlock nb) throws RemoteException;
    public void afficheListParticipants() throws RemoteException;
    public void afficheNbVoisins() throws RemoteException;
    public void afficheListNoeuds() throws RemoteException;
    public int getMy_BlockchainImplHeight() throws RemoteException;
    public int getMyPort() throws RemoteException;
    public BigDecimal getMy_BlockchainImplLastBlockTimeStamp()throws RemoteException;
    public LinkedList<Block> getBlockList()throws RemoteException;
    public BigDecimal howMuchMoneyDoesAParticipantHas(BigDecimal participantID)
		throws RemoteException;
    public void sendMoneyFromTo(BigDecimal participant1ID, BigDecimal participant2ID, BigDecimal moneySent)
    throws RemoteException;
    public void ask_for_his_wainting_transaction(LinkedList<Transaction> his_wainting_transactions)
		throws RemoteException;
    public LinkedList<Transaction> sendWaiting_transaction_list() throws RemoteException;
}

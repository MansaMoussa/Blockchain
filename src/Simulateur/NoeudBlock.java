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
   // public int port = 0;
}

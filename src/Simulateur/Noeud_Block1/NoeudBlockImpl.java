import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuilder;
import java.math.BigDecimal;

public class NoeudBlockImpl extends UnicastRemoteObject implements NoeudBlock{
public static LinkedList<NoeudBlock> neighbours ;
	public static LinkedList<Noeud_Participant> participants ;
    
    public static BigDecimal reward_for_bloc_creation;
    public static Integer max_participant = 10;
    //Liste voisins (leurs numeros de port)
    
    //my blockchain that I share to others or update when others share theirs
    public static BlockchainImpl my_BlockchainImpl;
    //Ceux qui sont inscrits à moi

    //liste chaînée des opérations à transcrire
    public static LinkedList<Transaction> waiting_transaction_list;

    public static int MyPort;

    public NoeudBlockImpl() throws RemoteException{
      super();
      neighbours = new LinkedList<NoeudBlock>();
      participants = new LinkedList<Noeud_Participant>();
      //blocksList = new LinkedList<Block>();
    };

    public void connectToNoeudBlockParticipant(Noeud_Participant np) throws RemoteException {
        participants.add(np);
    }

    public void connectToNoeudBlockNoeud(NoeudBlock nb) throws RemoteException {
    	neighbours.add(nb);
    }

    public void afficheListParticipants() throws RemoteException {
        System.out.println("Mes participants : ");
        for(Noeud_Participant n : participants) {
            System.out.println(n.participantID + " ");
        }
    }

    public void afficheListNoeuds() throws RemoteException {
        /*System.out.println("Mes voisins : ");
        for(NoeudBlock n : neighbours) {
            System.out.println(n.port + " ");
        }*/
    }

}

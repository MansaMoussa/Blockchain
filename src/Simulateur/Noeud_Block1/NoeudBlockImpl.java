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
		public LinkedList<NoeudBlock> neighbours ;
		public LinkedList<Noeud_Participant> participants ;

    public BigDecimal reward_for_bloc_creation;
    public Integer max_participant = 10;
    //Liste voisins (leurs numeros de port)

    //my blockchain that I share to others or update when others share theirs
    public BlockchainImpl my_BlockchainImpl;
    //Ceux qui sont inscrits à moi

    //liste chaînée des opérations à transcrire
    public LinkedList<Transaction> waiting_transaction_list;

    public static int MyPort;

    public NoeudBlockImpl() throws RemoteException{
      super();
      neighbours = new LinkedList<NoeudBlock>();
      participants = new LinkedList<Noeud_Participant>();
			waiting_transaction_list = new LinkedList<Transaction>();
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

    public void afficheNbVoisins() throws RemoteException {
        System.out.println("J'ai " + neighbours.size() + " voisins.");
     /*   for(NoeudBlock n : neighbours) {
            System.out.println(n.port);
        }*/
    }

}

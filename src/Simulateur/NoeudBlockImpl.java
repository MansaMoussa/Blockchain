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
		//List de mes voisins
		public LinkedList<NoeudBlock> neighbours;
		//Ceux qui sont inscrits à moi
		public LinkedList<Noeud_Participant> participants ;
    private Integer reward_for_bloc_creation;
    private int max_participant = 10;
    //my blockchain that I share to others or update when others share theirs
    public BlockchainImpl my_BlockchainImpl;
    //liste chaînée des opérations à transcrire
    public LinkedList<Transaction> waiting_transaction_list;
		//My port number
    public static int MyPort;

    public NoeudBlockImpl() throws RemoteException{
      super();
      neighbours = new LinkedList<NoeudBlock>();
      participants = new LinkedList<Noeud_Participant>();
			waiting_transaction_list = new LinkedList<Transaction>();
			reward_for_bloc_creation = 0;
    };

    public void connectToNoeudBlockParticipant(Noeud_Participant np) throws RemoteException {

				if(participants.size()<=this.max_participant){ //Tant qu'on a pas atteint le nombre max de participant
					Transaction t = new Transaction('I', np.participantID+" to Noeud_Block "+MyPort);
					write_transactionTowaitingList(t);
	        participants.add(np);
				}
    }

    public void connectToNoeudBlockNoeud(NoeudBlock nb) throws RemoteException {
    	neighbours.add(nb);
    }


    public void afficheListParticipants() throws RemoteException {
        System.out.println("Participants connected to me : ");
        for(Noeud_Participant n : this.participants) {
            System.out.println(n.participantID + " ");
        }
    }

		public void afficheListNoeuds() throws RemoteException {
        System.out.println("I'm in peer-to-peer with (neighbours): ");
        for(NoeudBlock n : this.neighbours) {
            System.out.println(n.getMyPort() + " ");
        }
    }



    public void afficheNbVoisins() throws RemoteException {
        System.out.println("J'ai " + neighbours.size() + " voisins.");

    }

		public int getBlockMoney() throws RemoteException{
				int money = 0;
				for(Block b : this.my_BlockchainImpl.sendBlockList())
					if(b.getCreator().equals("Noeud_Block "+this.MyPort))
							money++;

				return money;
		}

		public int getMy_BlockchainImplHeight() throws RemoteException{
			return this.my_BlockchainImpl.getHeight();
		}

		public int getMyPort() throws RemoteException{
			return this.MyPort;
		}

		public BigDecimal getMy_BlockchainImplLastBlockTimeStamp()throws RemoteException{
			return this.my_BlockchainImpl.sendBlockList().getLast().getTimeStamp();
		}

		public LinkedList<Block> getBlockList()throws RemoteException{
			return this.my_BlockchainImpl.sendBlockList();
		}


		//We won't write anything if the transaction is not valid
		public void write_transactionTowaitingList(Transaction t) throws RemoteException{
				//Cette condition ne vaut que lorsqu'un échange
				//de monnaie bloc est solicitée
				if(t.getType() == 'E' && t.valid_transaction(my_BlockchainImpl))
						waiting_transaction_list.addLast(t);
				else if((t.getType() == 'C')||(t.getType() == 'I'))
						waiting_transaction_list.addLast(t);
		}
}

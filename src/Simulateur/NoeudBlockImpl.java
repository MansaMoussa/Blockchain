import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuilder;
import java.math.BigDecimal;

public class NoeudBlockImpl extends UnicastRemoteObject implements NoeudBlock{
		//List de mes voisins
		public LinkedList<NoeudBlock> neighbours;
		//Ceux qui sont inscrits à moi
		public LinkedList<Noeud_Participant> participants;
		//The hash map where the participantID (key) and theirs merit (value) are saved
		public HashMap hashMap_merit_participants;
		//The number max of participant
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
			hashMap_merit_participants = new HashMap();
    };

    public void connectToNoeudBlockParticipant(Noeud_Participant np)
		throws RemoteException {
				boolean alreadyExist = false;
				if(participants.size()<=this.max_participant){ //Tant qu'on a pas atteint le nombre max de participant
					for(Noeud_Participant p : this.participants)
						if(p.participantID.compareTo(np.participantID) == 0)
								alreadyExist = true;

					//On ne l'inscrit que s'il n'existe pas encore
					if(!alreadyExist){
						Transaction t = new Transaction('I', np.participantID+" to Noeud_Block "+MyPort);
						write_transactionTowaitingList(t);
						participants.add(np);
						//On crée une entrée avec l'ID du participant comme KEY et le merit comme VALUE
						hashMap_merit_participants.put(np.participantID, 1);
					}
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

		//On vérifie si le participant a bien travaillé
		public void check_Participants_proof_of_work()
				throws RemoteException {
				for(Noeud_Participant nP : participants)
					if(nP.proof_of_work_for_more_earnings()){
							this.hashMap_merit_participants.put(nP.participantID, new Integer(((int)this.hashMap_merit_participants.get(nP.participantID))+1));
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

		public void sendMoneyFromTo(BigDecimal participant1ID, BigDecimal participant2ID, BigDecimal moneySent)
    throws RemoteException{
				BigDecimal nP1Money = this.my_BlockchainImpl.howMuchMoneyDoesAParticipantHas(participant1ID);
			  //nP1Money>=moneySent
			  if(nP1Money.compareTo(moneySent) != -1){
			    Transaction t = new Transaction('E', participant1ID+" to "+participant2ID+" "+moneySent);
			    this.waiting_transaction_list.add(t);
					System.out.println("\n!!!!!\tMoney Sent : But wait for the blockchain confirmation !!!!");
			  }
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

		public BigDecimal howMuchMoneyDoesAParticipantHas(BigDecimal participantID)
		throws RemoteException{
			return this.my_BlockchainImpl.howMuchMoneyDoesAParticipantHas(participantID);
		}

		public void ask_for_his_wainting_transaction(LinkedList<Transaction> his_wainting_transactions)
		throws RemoteException{
			boolean found = false;
			for(Transaction his_T : his_wainting_transactions){
				for(Transaction my_T : this.waiting_transaction_list)
						if(my_T.equals(his_T))
							found = true;

				if(!found)
					this.waiting_transaction_list.add(his_T);
			}
		}

		public LinkedList<Transaction> sendWaiting_transaction_list() throws RemoteException{
				return this.waiting_transaction_list;
		}

		//We won't write anything if the transaction is not valid
		public void write_transactionTowaitingList(Transaction t) throws RemoteException{
				//Cette condition ne vaut que lorsqu'un échange
				//de monnaie bloc est solicitée
				if(t.getType() == 'E' && t.valid_transaction(my_BlockchainImpl))
						this.waiting_transaction_list.addLast(t);
				else if((t.getType() == 'C')||(t.getType() == 'I'))
						this.waiting_transaction_list.addLast(t);
		}
}

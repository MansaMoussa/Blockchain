import java.rmi.*;
import java.net.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.lang.StringBuilder;

public class Noeud_Block{
 public static BigDecimal reward_for_bloc_creation;
    //public static Integer max_participant = 10;
    //Liste voisins (leurs numeros de port)
    //public static LinkedList<Integer> neighbours;
    //my blockchain that I share to others or update when others share theirs
    //public static BlockchainImpl my_BlockchainImpl;
    //Ceux qui sont inscrits à moi
    //public LinkedList<Noeud_Participant> participants;
    //liste chaînée des opérations à transcrire
    //public static LinkedList<Transaction> waiting_transaction_list;
/*
    public BigDecimal getBlockMoney(){
        return this.reward_for_bloc_creation;
    }

    //Remetre les transactions du bloc non valid dans la liste d'attente
    public Boolean valid_transaction(Transaction t){
        //Possède t-il ce qu'il veut dépenser?
        //Vérifions ça dans la BlockchainImpl
        return true;
    }

    public void write_transaction(Transaction t){
        //Cette condition ne vaut que lorsqu'un échange
        //de monnaie bloc est solicitée
        if(t.getType() == 'E' && valid_transaction(t)){
            //Add this shit in la liste d'attente
        }
    }

    public void order_create_block(Noeud_Block nB){
        //On doit lui dire qu'il crée un block dans sa liste d'attente(?)
    }


    //Je demande à voisin
    public void send_part_of_BlockchainImpl(int from_hauteur, Noeud_Block voisin){
        //Chaque noeud block peut demander tout ou une partie
        //de la BlockchainImpl
    }

    public void check_BlockchainImpl_exist(BlockchainImpl b, int hauteur){
        //Valide sera la première reçu
    }

    public void check_blochain_no_corruption(BlockchainImpl b){
        //Ici on pourrait simplement vérifier si chaque hash_précedent est
        //vraiment le vrai (en recalculant le trucZer)
    }

    //On ne peut pas chiffrer les opérations/transactions des noeuds participants
    // Sinon on ne saurait pas vérifier si des transactions sont vraies ou pas

*/
    //On va supprimer les opérations de la wainting_list déjà contenu dans le block
    //On va supprimer ceux qui ont déjà été validées (ceux qui sont déjà dans le lastBlock)
    /*
    public void check_waitingListTransaction_vs_blockTransaction(Block lastBlock){
        for(Transaction wainting_transaction : waiting_transaction_list)
          for(Transaction block_transaction : lastBlock.getTransactionsList())
              if(wainting_transaction.equals(block_transaction))
                  waiting_transaction_list.remove(wainting_transaction);
    }
    */
    ///////////////////////////////////////////////////////////////////
    ///////////////////C'est ici que ça se passe///////////////////////
    ///////////////////////////////////////////////////////////////////


    public static void main(String [] args)
    {


    	//


    	// AJOUTER LISTE GLOBALE AVEC PORTS DES NOEUDS BLOCS ET CONNEXION RANDOM D'UN PARTICIPANT AUX NOEUDS


    	//
        NoeudBlockImpl my_NoeudBlockImpl;
        if (args.length != 4)
        {
            System.out.println("Usage : java Noeud_Block <port de mon serveur> <port de mon peer> <port de mon blockchain> <port de mon peer>") ;
            System.exit(0) ;
        }

        int breakTime = 10000;
        LinkedList<Transaction> waiting_transaction_list = new LinkedList<Transaction>();
        ///////////////////on lance le serveur///////////////////////
        try{


            my_NoeudBlockImpl = new NoeudBlockImpl();
            my_NoeudBlockImpl.MyPort = Integer.parseInt(args[0]);
            Naming.rebind("rmi://127.0.0.1:"+args[0]+"/NoeudBlock", my_NoeudBlockImpl);
            System.out.println("\nSERVER Noeud_Block AT PORT "+args[0]+" LAUNCHED!!\n") ;

            my_NoeudBlockImpl.my_BlockchainImpl = new BlockchainImpl(); //My blockchain is created
            Naming.rebind("rmi://127.0.0.1:"+args[2]+"/Blockchain",my_NoeudBlockImpl.my_BlockchainImpl) ;
            System.out.println("\nSERVER Block_Chain AT PORT "+args[2]+" LAUNCHED!!\n") ;

            my_NoeudBlockImpl.my_BlockchainImpl = my_NoeudBlockImpl.my_BlockchainImpl.createNewBlock(my_NoeudBlockImpl.waiting_transaction_list, 10, args[0]);
            waiting_transaction_list = my_NoeudBlockImpl.waiting_transaction_list;
            //Si la création est acceptée il faut que les participants reçoivent
            //de l'argent
            //On va accumuler l'argent dans le NoeudB si personne ne s'est
            //encore inscrit
            my_NoeudBlockImpl.my_BlockchainImpl = my_NoeudBlockImpl.my_BlockchainImpl.createNewBlock(waiting_transaction_list, 10, args[0]);

        }
        catch (RemoteException re) { System.out.println(re); re.printStackTrace();}
        catch (MalformedURLException e) { System.out.println(e); e.printStackTrace();}

        ////////////////////on lance la pause////////////////////////
        try{
          Thread.sleep(breakTime);
        }catch(InterruptedException v) { System.out.println(v); }


        ///////////////////on lance le client///////////////////////
        try{

            NoeudBlock noeudBlock_Peer = (NoeudBlock)Naming.lookup("rmi://127.0.0.1:"+args[1]+"/NoeudBlock");
           // my_NoeudBlockImpl.
        //   noeudBlock_Peer.port = Integer.parseInt(args[1]);
            noeudBlock_Peer.connectToNoeudBlockNoeud(noeudBlock_Peer);
            noeudBlock_Peer.afficheNbVoisins();
            //noeudBlock_Peer.connectToNoeudBlockNoeud(noeudBlock_Peer);

            //System.out.println()

            Blockchain blockchain_Peer =
                                (Blockchain) Naming.lookup("rmi://127.0.0.1:"+args[3]+"/Blockchain");
            System.out.println(blockchain_Peer.printBlockchainImpl(args[0]));
        }
        catch (NotBoundException re) { System.out.println(re) ; }
        catch (RemoteException re) { System.out.println(re) ; }
        catch (MalformedURLException e) { System.out.println(e) ; }

        ////////////////////on lance la pause avant de quitter////////////////////////
        try{
          Thread.sleep(breakTime);
        }catch(InterruptedException v) { System.out.println(v); }
    }
}

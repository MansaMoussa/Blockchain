import java.rmi.*;
import java.net.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.Random;
import java.util.ArrayList;

public class Noeud_Block{
    /*
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

    ///////////////////////////////////////////////////////////////////
    ///////////////////C'est ici que ça se passe///////////////////////
    ///////////////////////////////////////////////////////////////////


    public static void main(String [] args){


    	//


    	// AJOUTER LISTE GLOBALE AVEC PORTS DES NOEUDS BLOCS ET CONNEXION RANDOM D'UN PARTICIPANT AUX NOEUDS


    	//
        if (args.length != 4)
        {
            System.out.println("Usage : java Noeud_Block <port de mon serveur> <port de mon peer> <port de mon blockchain> <port de mon peer>") ;
            System.exit(0) ;
        }

        int breakTime = 10000;
        int waitAlea;
        int max = 2;
        int min =  0;
        ArrayList<Integer> intArray = new ArrayList<Integer>();
        intArray.add(300);
        intArray.add(1000);
        intArray.add(9000);

        LinkedList<Transaction> waiting_transaction_list = new LinkedList<Transaction>();
        NoeudBlockImpl my_NoeudBlockImpl = null;
        ///////////////////on lance le serveur///////////////////////
        try{

        	  System.out.println("test "+ args[0] + " " + args[1] + " " + args[2] + " " + args[3]);

            my_NoeudBlockImpl = new NoeudBlockImpl();
            my_NoeudBlockImpl.MyPort = Integer.parseInt(args[0]);
            Naming.rebind("rmi://127.0.0.1:"+args[0]+"/NoeudBlock", my_NoeudBlockImpl);
            System.out.println("\nSERVER Noeud_Block AT PORT "+args[0]+" LAUNCHED!!\n") ;

            my_NoeudBlockImpl.my_BlockchainImpl = new BlockchainImpl(); //My blockchain is created
            Naming.rebind("rmi://127.0.0.1:"+args[2]+"/Blockchain",my_NoeudBlockImpl.my_BlockchainImpl) ;
            System.out.println("\nSERVER Block_Chain AT PORT "+args[2]+" LAUNCHED!!\n") ;



            //Si la création est acceptée il faut que les participants reçoivent
            //de l'argent
            //On va accumuler l'argent dans le NoeudB si personne ne s'est
            //encore inscrit
            //my_NoeudBlockImpl.my_BlockchainImpl = my_NoeudBlockImpl.my_BlockchainImpl.createNewBlock(waiting_transaction_list, 10, args[0]);

            //Print my blockchain
            my_NoeudBlockImpl.my_BlockchainImpl.printMyBlockchain(args[0]);

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
            // noeudBlock_Peer.port = Integer.parseInt(args[1]);
            noeudBlock_Peer.connectToNoeudBlockNoeud(noeudBlock_Peer);
            noeudBlock_Peer.afficheListNoeuds();
            //noeudBlock_Peer.connectToNoeudBlockNoeud(noeudBlock_Peer);

            Blockchain blockchain_Peer =
                                (Blockchain) Naming.lookup("rmi://127.0.0.1:"+args[3]+"/Blockchain");

            while(true){
              my_NoeudBlockImpl.my_BlockchainImpl =
                  my_NoeudBlockImpl.my_BlockchainImpl.createNewBlock(my_NoeudBlockImpl.waiting_transaction_list, 10, args[0]);

              //my_NoeudBlockImpl.check_waitingListTransaction_vs_blockTransaction(my_NoeudBlockImpl.my_BlockchainImpl.sendBlockList().getLast());
              System.out.println("\nmy Heiiight "+my_NoeudBlockImpl.my_BlockchainImpl.getHeight());
              System.out.println("\nHis Heiiight "+blockchain_Peer.getHeight()+"\n\n");
              if(blockchain_Peer.getHeight().compareTo(my_NoeudBlockImpl.my_BlockchainImpl.getHeight())==0){
                    System.out.println("\nmy Time "+my_NoeudBlockImpl.my_BlockchainImpl.sendBlockList().getLast().getTimeStamp());
                    System.out.println("\nHis Time "+blockchain_Peer.sendBlockList().getLast().getTimeStamp()+"\n\n");
                    //Dans ce cas on prends celui qui existe depuis longtemps
                    if(blockchain_Peer.sendBlockList().getLast().getTimeStamp().compareTo(my_NoeudBlockImpl.my_BlockchainImpl.sendBlockList().getLast().getTimeStamp())==-1){
                       //my_NoeudBlockImpl.my_BlockchainImpl.delete_creation_transac_try(my_NoeudBlockImpl.waiting_transaction_list, args[0]);
                       my_NoeudBlockImpl.my_BlockchainImpl.setBlockList(blockchain_Peer.sendBlockList(), my_NoeudBlockImpl.my_BlockchainImpl.getHeight());
                       //my_NoeudBlockImpl.my_BlockchainImpl.blocksList = blockchain_Peer.sendBlockList();
                       System.out.println("\n I N S I D E  v1\n");
                    }
              }
              else if(blockchain_Peer.getHeight().compareTo(my_NoeudBlockImpl.my_BlockchainImpl.getHeight())==1){
                    //Comme ça je nettoie bien la waiting_transaction_list
                    //my_NoeudBlockImpl.my_BlockchainImpl.delete_creation_transac_try(my_NoeudBlockImpl.waiting_transaction_list, args[0]);
                    //my_NoeudBlockImpl.my_BlockchainImpl.blocksList = blockchain_Peer.sendBlockList();
                    my_NoeudBlockImpl.my_BlockchainImpl.setBlockList(blockchain_Peer.sendBlockList());
                    System.out.println("\n I N S I D E  v2\n");
              }

              try{
                Random random = new Random();
                waitAlea = random.nextInt(max+1-min) + min;
                System.out.println("\nHHHHHHH "+blockchain_Peer.getHeight()+"\n");
                my_NoeudBlockImpl.my_BlockchainImpl.printMyBlockchain(args[0]);
                Thread.sleep(intArray.get(waitAlea));
              }catch(InterruptedException v) { System.out.println(v); }

              //On supprime les opérations en attente déjà dans le dernier block
              my_NoeudBlockImpl.my_BlockchainImpl.check_waitingListTransaction_vs_blockTransaction(my_NoeudBlockImpl.waiting_transaction_list);
            }
        }
        catch (NotBoundException re) { System.out.println(re) ; }
        catch (RemoteException re) { System.out.println(re) ; }
        catch (MalformedURLException e) { System.out.println(e) ; }

        ////////////////////on lance la pause avant de quitter////////////////////////

    }
}

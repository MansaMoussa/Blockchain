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

    public void check_blochain_no_corruption(BlockchainImpl b){
        //Ici on pourrait simplement vérifier si chaque hash_précedent est
        //vraiment le vrai (en recalculant le trucZer)
    }

*/

    ///////////////////////////////////////////////////////////////////
    ///////////////////C'est ici que ça se passe///////////////////////
    ///////////////////////////////////////////////////////////////////

    public static void main(String [] args){
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
        intArray.add(30);
        intArray.add(600);
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


            //noeudBlock_Peer.my_BlockchainImpl = (BlockchainImpl) blockchain_Peer.clone();

            while(true){
              my_NoeudBlockImpl.my_BlockchainImpl =
                  my_NoeudBlockImpl.my_BlockchainImpl.createNewBlock(my_NoeudBlockImpl.waiting_transaction_list, my_NoeudBlockImpl.participants, 10, args[0]);


              Random random = new Random();
              waitAlea = random.nextInt(max+1-min) + min;
              int myHeight = my_NoeudBlockImpl.my_BlockchainImpl.getHeight();
              //int hisHeight = blockchain_Peer.getHeight();
              int hisHeight = noeudBlock_Peer.getMy_BlockchainImplHeight();

              Thread.sleep(intArray.get(waitAlea));//Le temps que je récupère l'info d'une manière synchronisée


              //System.out.println("\nmy Heiiight "+myHeight);
              //System.out.println("\nHis Heiiight "+hisHeight+"\n");
              if(hisHeight == myHeight){
                  BigDecimal myTimeStamp = my_NoeudBlockImpl.my_BlockchainImpl.getLastBlock().getTimeStamp();
                  BigDecimal hisTimeStamp = noeudBlock_Peer.getMy_BlockchainImplLastBlockTimeStamp();

                  //Dans ce cas on prends celui qui existe depuis longtemps que moi
                  if(hisTimeStamp.compareTo(myTimeStamp)!=1){
                     my_NoeudBlockImpl.my_BlockchainImpl.setBlockList(noeudBlock_Peer.getBlockList());
                     //System.out.println("\n I N S I D E  v1\n");
                  }
              }
              else if(hisHeight > myHeight){
                    my_NoeudBlockImpl.my_BlockchainImpl.setBlockList(noeudBlock_Peer.getBlockList());
                    //System.out.println("\n I N S I D E  v2\n");
              }


              my_NoeudBlockImpl.my_BlockchainImpl.printMyBlockchain(args[0]);
              Thread.sleep(intArray.get(waitAlea));//On attend un nombre aléatoire afin de pertre les 2 de se concurencer


              //On supprime les opérations en attente déjà dans le dernier block
              my_NoeudBlockImpl.my_BlockchainImpl.check_waitingListTransaction_vs_blockTransaction(my_NoeudBlockImpl.waiting_transaction_list);
              if(my_NoeudBlockImpl.my_BlockchainImpl.getLastBlock().getCreator().equals("Noeud_Block "+my_NoeudBlockImpl.MyPort)){
                System.out.println("\nThis Noeud_Block has got now"+my_NoeudBlockImpl.getBlockMoney()+" $coin (^_^)\n");
              }
            }
        }
        catch (NotBoundException re) { System.out.println(re) ; }
        catch (RemoteException re) { System.out.println(re) ; }
        catch (MalformedURLException e) { System.out.println(e) ; }
        catch(InterruptedException v) { System.out.println(v); }

        ////////////////////on lance la pause avant de quitter////////////////////////

    }
}

import java.rmi.*;
import java.net.*;
import java.math.BigDecimal;
import java.util.LinkedList;

public class Noeud_Block{
    /*
    public BigDecimal reward_for_bloc_creation;
    public Integer max_participant;
    public LinkedList<Integer> neighbours; //Liste voisins (leurs numeros de port)
    //public BlockchainImpl my_BlockchainImpl;  //doit être le même chez tout le monde
    public LinkedList<Noeud_Participant> participants; //Ceux qui sont inscrits à moi
    public LinkedList<Transaction> waiting_transaction_list;//liste chaînée des opérations à transcrire

    public BigDecimal getBlockMoney(){
        return this.reward_for_bloc_creation;
    }

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

     ?? ou échanger les listes de transactions suffit
     public Block take_block(Block ){

     }


    //Je demande à voisin
    public void send_part_of_BlockchainImpl(int from_hauteur, Noeud_Block voisin){
        //Chaque noeud block peut demander tout ou une partie
        //de la BlockchainImpl
    }

    public void check_BlockchainImpl_exist(BlockchainImpl b, int hauteur){
        //Valide sera la première reçu
    }

    public void check_waitingListTransaction_vs_blockTransaction(Block lastBlock){
        //On va supprimer les opérations de la wainting_list déjà contenu dans le block
    }

    public void print_all_BlockchainImpl(){
        //afin de visualiser la BlockchainImpl
        //le nombre de blocks et le contenu de chaque bloc
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


    public static void main(String [] args)
    {
        if (args.length != 2)
        {
            System.out.println("Usage : java Noeud_Block <port de mon serveur> <port de mon peer>") ;
            System.exit(0) ;
        }

        ///////////////////on lance le serveur///////////////////////
        try{
            System.out.println("\nSERVEEEEER\n");
            BlockchainImpl my_BlockchainImpl = new BlockchainImpl() ;
            Naming.rebind("rmi://localhost:" + args[0] + "/Blockchain" ,my_BlockchainImpl) ;
            System.out.println("\nServeur Noeud_Block (Number : "+args[0]+") READY!!\n") ;
        }
        catch (RemoteException re) { System.out.println(re); re.printStackTrace();}
        catch (MalformedURLException e) { System.out.println(e); e.printStackTrace();}
        catch (Exception e){ System.out.println(e); e.printStackTrace();};

        ////////////////////on lance la pause////////////////////////
        try{
          Thread.sleep(5000);
          System.out.println("\nAFTER WAITING__1\n");
        }catch(InterruptedException v) { System.out.println(v); }


        ///////////////////on lance le client///////////////////////

        try{
            System.out.println("\nCLIEEEEENT\n");
            Blockchain blockchain_Peer =
                                (Blockchain) Naming.lookup("rmi://localhost:"+args[1]+"/Blockchain") ;
            System.out.println("Le client "+args[0]+" recoit : "+blockchain_Peer.printBlockchainImpl(new Integer(args[1])) +"\n") ;
        }
        catch (NotBoundException re) { System.out.println(re) ; }
        catch (RemoteException re) { System.out.println(re) ; }
        catch (MalformedURLException e) { System.out.println(e) ; }

        ////////////////////on lance la pause avant de quitter////////////////////////
        try{
          Thread.sleep(5000);
          System.out.println("\nAFTER WAITING__2\n");
        }catch(InterruptedException v) { System.out.println(v); }
    }
}

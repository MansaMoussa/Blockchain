import java.rmi.*;
import java.net.MalformedURLException;
import java.math.BigDecimal;
//import Transaction;

public class Noeud_Participant{
    private BigDecimal earnings_for_work_done;
    private static int participantID = 0;
    /*//LEVEL SECURITY ??
    private String name;
    private Integer private_key;
    public Integer public_key;

    public Bool decrypt(String encrypted_name){ //Pas encore sûr
        //check if this encrypted name is mine?
        //otherwise I can't make an Transaction
    }

    public Integer getPublicKey(){
        return this.public_key;
    }
    */

    public Noeud_Participant(int id) {
      id = participantID++;
    }

    public BigDecimal getBlockMoney(){
        return this.earnings_for_work_done;
    }
    /*
    public Bool proof_of_work_for_more_earnings(){

    }

    public void add_earnings_job_done(){
        if(proof_of_work_for_more_earnings)
            this.earnings_for_work_done++;
    }
    */

    

    ///////////////////////////////////////////////////////////////////
    ///////////////////C'est ici que ça se passe///////////////////////
    ///////////////////////////////////////////////////////////////////

   /* public static void main(String [] args)
    {
      Noeud_Participant np = new Noeud_Participant(participantID);
      if (args.length != 1)
          System.out.println("Usage : java Noeud_Participant <port du serveur à qui je veux demande des services>") ;
      else{
          try{
              NoeudBlock noeudblock_Peer =
                                  (NoeudBlock) Naming.lookup("rmi://localhost:"+args[0]+"/NoeudBlock") ;
              //System.out.println(blockchain_Peer.printBlockchainImpl());

             // noeudblock_Peer.connectToNoeudBlock(np, noeudblock_Peer.participants);

                                  //donner acces aux participans a la liste des participants du noeud ou envoyer le participant au noeud

          }
          catch (NotBoundException re) { System.out.println(re) ; }
          catch (RemoteException re) { System.out.println(re) ; }
          catch (MalformedURLException e) { System.out.println(e) ; }
      }
    }*/
}

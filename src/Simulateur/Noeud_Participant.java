import java.rmi.*;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;
//import Transaction;

public class Noeud_Participant implements Serializable{
    public BigDecimal participantID;

    public boolean proof_of_work_for_more_earnings(){
      boolean answer = false;
      try{
        int max = 42424242;
        int min = 0;
        int range = (max - min) + 1;
        int primeNumber = (int)(Math.random() * range) + min;
        answer =  isPrime(primeNumber);
        System.out.println("\nPrime number = "+primeNumber);
        System.out.println("\nanswer = "+answer);
      }catch(Exception e){System.out.println(e); e.printStackTrace();}

      return answer;
    }

    private boolean isPrime(int number)
    throws RemoteException{
        boolean primeNumber_found = true;
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 2; i < sqrt; i++)
            if (number % i == 0) // number is perfectly divisible - no prime
                primeNumber_found = false;

        return primeNumber_found;
    }

    public BigDecimal getParticipantID(){
      return participantID;
    }


    ///////////////////////////////////////////////////////////////////
    ///////////////////C'est ici que ça se passe///////////////////////
    ///////////////////////////////////////////////////////////////////

    public static void main(String [] args)
    {
      Noeud_Participant np = new Noeud_Participant();

      if (args.length != 2 && args.length != 4)
          System.out.println("Usage : java Noeud_Participant <port du serveur à qui je veux demande des services> <numero du participant >") ;
      else{
          try{
                np.participantID = new BigDecimal(args[1]);
                int breakTime = 1000;
                int attenteRandom = 10000 + (int)(Math.random() * ((70000 - 10000) + 1));
                System.out.println(attenteRandom);

                System.out.println("Bonjour je suis le Noeud Participant " + args[1] + ". Je souhaite me connecter au Noeud Block " + args[0]);
                Thread.sleep(1000);



                NoeudBlock noeudblock_Peer =
                                    (NoeudBlock) Naming.lookup("rmi://127.0.0.1:"+args[0]+"/NoeudBlock") ;

                System.out.println("Bonjour je suis le Noeud Participant " + args[1] + ". Je suis connecté au Noeud Block " + args[0]);
                //System.out.println(blockchain_Peer.printBlockchainImpl(args[0]));

                noeudblock_Peer.connectToNoeudBlockParticipant(np);
                noeudblock_Peer.afficheListParticipants();


                if(args.length != 2){//On envoie directement l'argent
                  // Le temps qu'on crée un notre participant à qui je peux envoyer de la monnaie
                  Thread.sleep(breakTime);
                }

                // How much money do I have
                BigDecimal myMoney = noeudblock_Peer.howMuchMoneyDoesAParticipantHas(np.participantID);

                System.out.println("#***************************************************#");
                System.out.println("#\tI have "+myMoney+" $coins (^_^)");
                System.out.println("#***************************************************#");

                if(args.length == 4){
                  noeudblock_Peer.sendMoneyFromTo(np.participantID, new BigDecimal(args[2]), new BigDecimal(args[3]));
                }
            }
            catch (NotBoundException re) { System.out.println(re) ; }
            catch (RemoteException re) { System.out.println(re) ; }
            catch (MalformedURLException e) { System.out.println(e) ; }
            catch(InterruptedException v) { System.out.println(v); }
       }
    }
}

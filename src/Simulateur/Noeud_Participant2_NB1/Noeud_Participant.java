import java.rmi.*;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class Noeud_Participant{
    private BigDecimal earnings_for_work_done;
    private BigDecimal participantID;

    public boolean proof_of_work_for_more_earnings(){
      boolean answer = false;
      try{
        answer =  isPrime((int)Math.random());
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
      return this.participantID;
    }



    ///////////////////////////////////////////////////////////////////
    ///////////////////C'est ici que ça se passe///////////////////////
    ///////////////////////////////////////////////////////////////////

    public static void main(String [] args)
    {
      if (args.length != 1)
          System.out.println("Usage : java Noeud_Participant <port du serveur à qui je veux demande des services>") ;
      else{
          try{
              Blockchain blockchain_Peer =
                                  (Blockchain) Naming.lookup("rmi://localhost:"+args[0]+"/Blockchain") ;
              System.out.println(blockchain_Peer.printBlockchainImpl());

          }
          catch (NotBoundException re) { System.out.println(re) ; }
          catch (RemoteException re) { System.out.println(re) ; }
          catch (MalformedURLException e) { System.out.println(e) ; }
      }
    }
}

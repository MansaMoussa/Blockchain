import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;
import java.security.MessageDigest;
import java.lang.StringBuilder;
import java.math.BigDecimal;
import java.util.Random;

public class BlockchainImpl extends UnicastRemoteObject implements Blockchain{

    public LinkedList<Block> blocksList;

    public BlockchainImpl() throws RemoteException{
      super();
      blocksList = new LinkedList<Block>();
    };

    public BlockchainImpl(LinkedList<Block> blocksList) throws RemoteException{
        this.blocksList = blocksList;
    }

    //serverPort is the port number where this server is launched
    public StringBuilder printBlockchainImpl() throws RemoteException{
        StringBuilder display = new StringBuilder();
        display.append("\n#####################################################");
        display.append("\n#####################################################");
        display.append("\n#**\tBLOCKCHAIN  FROM  Noeud_Block1");
        display.append("\n#***************************************************#");
        for(Block b : blocksList)
            b.printBlock(display);
        display.append("\n#####################################################");

        return display;
    }


    public void printMyBlockchain() throws RemoteException{
        BlockchainImpl b = new BlockchainImpl(blocksList);
        System.out.println(b.printBlockchainImpl());
    }

    public Blockchain askBlockchain(BigDecimal profondeur) throws RemoteException{
        LinkedList<Block> tempBlockList = new LinkedList<Block>();
        BigDecimal i = new BigDecimal(0);
        for(  ; i.compareTo(profondeur) == -1 || i.compareTo(profondeur) == 0
              ; i.add(BigDecimal.ONE))
            for(Block b : this.blocksList)
              if(i.compareTo(b.getHeight()) == 0)
                  tempBlockList.add(b);
        return new BlockchainImpl(tempBlockList);
    }

    //Here will try to create a new Block
    public BlockchainImpl createNewBlock(LinkedList<Transaction> transactionsList, int secondsToSleep)
    throws RemoteException{
        BlockchainImpl bc = new BlockchainImpl(this.blocksList);
        BigDecimal prof = BigDecimal.ZERO;//
        String creatorName = "Noeud_Block1";
        String hashPreviousBlock = "6553700000000000000000000";
        boolean time_out = false;
        int i = 0;
        //Vu qu'il doit être dans au moins un des 2 cas en bas
        if(!this.blocksList.isEmpty()){
          hashPreviousBlock = hashBlock(getLastBlock());
          prof = getLastBlock().getHeight();
        }
        else{//La blockchain vient de commencer, donc avant 0 y a pas de bloc
          i = secondsToSleep; //Comme ça on sort directement de la boucle
          time_out = true; //Comme ça on revoit la même Blockchain
        }


        Block b = new Block(prof, hashPreviousBlock, creatorName, transactionsList);

        BigDecimal try_nonce = new BigDecimal(Math.random());

        boolean primeNumber_found = false;

        //On vérifier s'il y a un time_out ou qu'on a deviné le bon nonce
        while(i < secondsToSleep || !(primeNumber_found = check_if_good_nonce(b, try_nonce))){

          try_nonce = new BigDecimal(Math.random());
          //We will sleep every 1 sec
          try{
            Thread.sleep(1);
          }catch(InterruptedException v) { System.out.println(v); }
          if(secondsToSleep == i+1)
            time_out = true;
          i++;
        }

        //Si on n'est pas sorti à cause d'un time out, on créee et ajoute le
        //le bloc à la blockchain and we return the edited blockchain
        //otherwise we return the blockchain without an edit
        if(!time_out || primeNumber_found){
            b.setNonce(try_nonce);
            String tmpHash = hashBlock(b);
            b.setMyHash(hashBlock(b));
            Transaction creationTransaction =
                    new Transaction('C', creatorName+" creates Block "+prof);
            b.addTransaction(creationTransaction);
            this.blocksList.addLast(b);
            bc = new BlockchainImpl(this.blocksList);
        }
        //Il va falloir l'envoyer aux Noeud_Block voisins afin qu'ils n'encréent
        //pas à la même profondeur
        return bc;
    }

    //On vérifie si ce nonce donné en paramètre permet d'obtenir un nombre
    //premier au début de la chaîne du Hash de ce block donné en
    //paramètre ex.: hash = "abcde..." et abcde || abcd || abc est premier
    //En gros il nous faut un nombre premier entre l'index 0 et 5
    private boolean check_if_good_nonce(Block b, BigDecimal nonce)
    throws RemoteException{
        boolean primeNumber_found = false;
        Block tmpBlock = b;
        tmpBlock.setNonce(nonce);
        String tmpHash = hashBlock(tmpBlock);
        System.out.println(tmpHash);
        for(int i = 5; i > 2 && !primeNumber_found ; i--){
          int testSubstring = 0; //Dans le cas où on catch une erreur
          try{
              testSubstring = Integer.parseInt(tmpHash.substring(0, i));
              primeNumber_found = isPrime(testSubstring);
          }catch(NumberFormatException e){System.out.println(e); e.printStackTrace();}
        }
        return primeNumber_found;
    }

    //Return the last block  added on the Blockchain
    private Block getLastBlock()
    throws RemoteException{
        return this.blocksList.getLast();
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

    //To get the hash will put in the new block
    private String hashBlock(Block b)
    throws RemoteException{
        // djb2 hash function
        String blockString = b.blockSerialisation();
        BigDecimal hash = new BigDecimal(7);
        for(int i = 0; i < blockString.length(); i++)
          hash = (hash.multiply(new BigDecimal(31))).add(new BigDecimal((int) blockString.charAt(i)));

        return hash.toString();
    }

    //His money is the sum of all he got minus all he'd sent
    private BigDecimal howMuchMoneyDoesAParticipantHas(Noeud_Participant nP){
      BigDecimal moneyReceived = new BigDecimal(0);
      BigDecimal moneySent = new BigDecimal(0);

      for(Block b : blocksList)
        for(Transaction t : b.getTransactionsList())
          moneyReceived = moneyReceived.add(t.moneyReceivedOf(nP.getParticipantID()));


      for(Block b : blocksList)
        for(Transaction t : b.getTransactionsList())
          moneySent = moneySent.add(t.moneySentOf(nP.getParticipantID()));


      return moneyReceived.subtract(moneySent);
    }

    private BigDecimal sendMoneyFromTo(Noeud_Participant nP1, Noeud_Participant nP2, BigDecimal moneySent){
      BigDecimal nP1Money = howMuchMoneyDoesAParticipantHas(nP1);
      BigDecimal sendThis = BigDecimal.ZERO;

       //nP1Money>=moneySent
      if(nP1Money.compareTo(moneySent) == 1 || nP1Money.compareTo(moneySent) == 0){
          //Transaction t = new Transaction('E', nP1.participantID+" to "+nP2.participantID+" "+moneySent);
          //waiting_transaction_list.addLast(t);
          sendThis = moneySent;
      }

      return sendThis;
    }



}

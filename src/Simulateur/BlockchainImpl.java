import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;
import java.security.MessageDigest;
import java.lang.StringBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BlockchainImpl extends UnicastRemoteObject implements Blockchain{

    public LinkedList<Block> blocksList;


    public int getHeight() throws RemoteException{
        int tmp= this.blocksList.size() - 1;
        return tmp;
    }

    public BlockchainImpl() throws RemoteException{
      super();
      blocksList = new LinkedList<Block>();
    };

    public BlockchainImpl(LinkedList<Block> blocksList) throws RemoteException{
        this.blocksList = blocksList;
    }

    //serverPort is the port number where this server is launched
    public StringBuilder printBlockchainImpl(String myPort) throws RemoteException{
        StringBuilder display = new StringBuilder();
        display.append("\n#####################################################");
        display.append("\n#####################################################");
        display.append("\n#**\tBLOCKCHAIN  FROM  Noeud_Block "+myPort);
        display.append("\n#***************************************************#");
        for(Block b : this.blocksList)
            b.printBlock(display);
        display.append("\n#####################################################");

        return display;
    }

    public void printMyBlockchain(String myPort) throws RemoteException{
        BlockchainImpl b = new BlockchainImpl(blocksList);
        System.out.println(b.printBlockchainImpl(myPort));
    }

    public Blockchain askBlockchain(int profondeur) throws RemoteException{
        LinkedList<Block> tempBlockList = new LinkedList<Block>();

        for(int i = 0; i <= profondeur ; i++)
            for(Block b : this.blocksList)
              if(i == b.getHeight())
                  tempBlockList.add(b);
        return new BlockchainImpl(tempBlockList);
    }

    //Here will try to create a new Block
    public BlockchainImpl createNewBlock(LinkedList<Transaction> waiting_T_List, LinkedList<Noeud_Participant> participants,
    HashMap participantsEarnings, int secondsToSleep, String myPort) throws RemoteException{
        BlockchainImpl bc = new BlockchainImpl(this.blocksList);
        int prof = 0;//
        String creatorName = "Noeud_Block "+myPort;
        String hashPreviousBlock = "6553700000000000000000000";
        boolean time_out = false;
        int i = 0;
        //Vu qu'il doit être dans au moins un des 2 cas en bas
        if(!this.blocksList.isEmpty()){
          hashPreviousBlock = getLastBlock().getMyHash();
          prof = getLastBlock().getHeight()+1;
        }
        else{//La blockchain vient de commencer, donc avant 0 y a pas de bloc
          i = secondsToSleep; //Comme ça on sort directement de la boucle
          time_out = true; //Comme ça on revoit la même Blockchain
        }


        //Ecrire aussi dans la waiting_list
        Block b = new Block(prof, hashPreviousBlock, creatorName, new LinkedList<Transaction>());

        BigDecimal try_nonce = new BigDecimal(Math.random());

        boolean primeNumber_found = false;

        //On vérifier s'il y a un time_out ou qu'on a deviné le bon nonce
        while(i < secondsToSleep || !(primeNumber_found = check_if_good_nonce(b, try_nonce))){
          bc = new BlockchainImpl(this.blocksList);
          try_nonce = new BigDecimal(Math.random());
          //We will sleep every 1 sec
          try{
            if(!this.blocksList.isEmpty())
                bc.check_waitingListTransaction_vs_blockTransaction(waiting_T_List);
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
            this.blocksList.add(b);
            creationMoneyTransaction(b, participants, participantsEarnings, myPort);
            //Il faut donner de la thune aux participants
            bc = new BlockchainImpl(this.blocksList);
        }

        bc.write_transactionToBlock(waiting_T_List);

        //Il va falloir l'envoyer aux Noeud_Block voisins afin qu'ils n'encréent
        //pas à la même profondeur
        return bc;
    }

    public void delete_creation_transac_try(LinkedList<Transaction> transactionsList, String myPort)
    throws RemoteException{
        transactionsList.removeLast();
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
        //System.out.println("ı");//I'm doing the proof of work
        for(int i = 5; i > 2 && !primeNumber_found ; i--){
          int testSubstring = 0; //Dans le cas où on catch une erreur
          try{
              testSubstring = Integer.parseInt(tmpHash.substring(0, i));
              primeNumber_found = isPrime(testSubstring);
          }catch(NumberFormatException e){System.out.println(e); e.printStackTrace();}
        }
        return primeNumber_found;
    }

    public void setBlockList(LinkedList<Block> bc, int prof) throws RemoteException{
        this.blocksList.remove(prof);
        this.blocksList.add(prof, bc.get(prof));
    }

    public void setBlockList(LinkedList<Block> bc) throws RemoteException{
          this.blocksList = (LinkedList<Block>) bc.clone();
    }



    public LinkedList<Block> sendBlockList() throws RemoteException{
        return blocksList;
    }

    //Return the last block  added on the Blockchain
    public Block getLastBlock()
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


    public BigDecimal howMuchMoneyDoesAParticipantHas(BigDecimal participantID)
    throws RemoteException{
      BigDecimal moneyReceived = new BigDecimal(0);
      BigDecimal moneySent = new BigDecimal(0);

      for(Block b : this.blocksList)
        for(Transaction t : b.getTransactionsList())
          moneyReceived = moneyReceived.add(t.moneyReceivedOf(participantID));

      for(Block b : this.blocksList)
        for(Transaction t : b.getTransactionsList())
          moneySent = moneySent.add(t.moneySentOf(participantID));

      return moneyReceived.subtract(moneySent);
    }

    //On va supprimer les opérations de la wainting_list déjà contenu dans le block
    //On va supprimer ceux qui ont déjà été validées (ceux qui sont déjà dans le lastBlock)
    public void check_waitingListTransaction_vs_blockTransaction(LinkedList<Transaction> wT_list)
    throws RemoteException{
          LinkedList<Transaction> wt_list = (LinkedList<Transaction>) wT_list.clone();
          for(Transaction block_transaction : this.getLastBlock().getTransactionsList())
            for(Transaction wainting_transaction : wt_list)
              if(wainting_transaction.equals(block_transaction))
                  wT_list.remove(wainting_transaction);
    }

    public void write_transactionToBlock(LinkedList<Transaction> waiting_T_List)
    throws RemoteException{

      for(Transaction t : waiting_T_List){
        //Cette condition ne vaut que lorsqu'un échange
        //de monnaie bloc est solicitée
        if(t.getType() == 'E' && t.valid_transaction(new BlockchainImpl(this.blocksList)))
            getLastBlock().addTransaction(t);
        else if((t.getType() == 'C')||(t.getType() == 'I'))
            getLastBlock().addTransaction(t);
      }

    }

    public void creationMoneyTransaction(Block b, LinkedList<Noeud_Participant> participants,
                          HashMap participantsEarnings,String myPort) throws RemoteException{
      BigDecimal pNumber = new BigDecimal(participants.size());
      for(Noeud_Participant p : participants){
        BigDecimal participantMerit = new BigDecimal((int) participantsEarnings.get(p.getParticipantID()));

        System.out.println("\n"+p.participantID+" MERIT : "+participantMerit);

        BigDecimal money = participantMerit.divide(pNumber, 3, RoundingMode.CEILING);
        Transaction t = new Transaction('C', "Noeud_Block "+myPort+" creation "+p.participantID+" "+money);
        b.addTransaction(t);
      }
    }

}

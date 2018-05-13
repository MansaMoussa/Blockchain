import java.util.LinkedList;
import java.math.BigDecimal;
import java.lang.StringBuilder;

public class Block{
    //A quel profondeur de la blockchain se situe ce block
    private BigDecimal profondeur;
    //Hash du block précedent
    private String hashPreviousBlock;
    //Mon Hash
    private String myHash;
    //The name of the Noeud_Block who create this Block
    private String creator;
    //Le nombre qu'on va essayer de modifier jusqu'à trouver un bon hash
    private BigDecimal nonce;
    //List of Transactions that are inside of the block
    private BigDecimal transactionsNumber;
    //List of Transactions that are inside of the block
    private LinkedList<Transaction> transactionsList;

    public Block(BigDecimal prof, String hashPreviousBlock,
                  String creatorName, LinkedList<Transaction> transactionsList){
        this.profondeur = prof;
        this.hashPreviousBlock = hashPreviousBlock;
        this.creator = creatorName;
        this.transactionsList = transactionsList;
        this.transactionsNumber = getTransactionsNumber();
    }


    public StringBuilder printBlock(StringBuilder display){
        display.append("\n#***************************************************#");
        display.append("\n#---------------------------------------------------#");
        display.append("\n#--\t\tDeep  :  "+this.profondeur);
        display.append("\n#---------------------------------------------------#");
        display.append("\n#--\t\tCreator  :  "+this.creator);
        display.append("\n#---------------------------------------------------#");
        display.append("\n#-- HashPreviousBlock : "+this.hashPreviousBlock);
        display.append("\n#---------------------------------------------------#");
        display.append("\n#-- Hash : "+this.myHash);
        display.append("\n#---------------------------------------------------#");
        display.append("\n#-- Nonce : "+this.nonce);
        display.append("\n#---------------------------------------------------#");
        for(Transaction t : transactionsList)
            t.printTransaction(display);
        display.append("\n#***************************************************#");

        return display;
    }

    //Retourne la liste de transactions de ce Block
    public LinkedList<Transaction> getTransactionsList(){
        return this.transactionsList;
    }

    public void addTransaction(Transaction t){
      transactionsList.addFirst(t);
    }

    //Retourne la profondeur du Block
    public BigDecimal getHeight(){
        return this.profondeur;
    }

    //Retourne le nombre de transactions
    public BigDecimal getTransactionsNumber(){
        BigDecimal answer = BigDecimal.ZERO;
        if(transactionsList != null)
          answer = new BigDecimal(transactionsList.size());

        return answer;
    }

    //On donne une valeur au nonce
    public void setNonce(BigDecimal nonce){
        this.nonce = nonce;
    }

    //On donne une valeur à mon Hash after avoir trouvé le bon nonce
    public void setMyHash(String hash){
        this.myHash = hash;
    }

    public String blockSerialisation(){
        String transactionsString = new String();

        for(Transaction t : transactionsList)
            transactionsString = transactionsString + "~"
                                                + t.transactionSerialisation();

        return "~"+profondeur+"~"+hashPreviousBlock+"~"+nonce+"~"+
                 creator+"~"+getTransactionsNumber()+"~"+transactionsString+"~";
    }


}

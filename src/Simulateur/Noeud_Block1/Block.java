import java.util.LinkedList;
import java.math.BigDecimal;
import java.lang.StringBuilder;

public class Block{
    //A quel profondeur de la blockchain se situe ce block
    private BigDecimal profondeur;
    //Hash du block précedent
    private String hashPreviousBlock;
    //Hash de mon block
    private String hash;
    //The name of the Noeud_Block who create this Block
    private String creator;
    //Le nombre qu'on va essayer de modifier jusqu'à trouver un bon hash
    private BigDecimal nonce;
    //List of Transactions that are inside of the block
    private LinkedList<Transaction> transactionsList;

    public Block(BigDecimal prof, String hashPreviousBlock,
                  String creatorName, LinkedList<Transaction> transactionsList){
        this.profondeur = prof;
        this.hash = hashPreviousBlock;
        this.creator = creatorName;
        this.transactionsList = transactionsList;
    }

    public StringBuilder printBlock(StringBuilder display){
        display.append("\n#*****************************************#");
        display.append("\n#-----------------------------------------#");
        display.append("\n#----\t\t\tDeep : "+this.profondeur+"\t\t\t----#");
        display.append("\n#----------------------------------------#");
        display.append("\n#----\t\t\tHash : "+this.hash+"\t\t\t----#");
        display.append("\n#----------------------------------------#");
        display.append("\n#----\t\t\tCreator : "+this.creator+"\t\t\t----#");
        display.append("\n#-----------------------------------------#");
        for(Transaction t : transactionsList)
            t.printTransaction(display);
        display.append("\n#*****************************************#");

        return display;
    }

    public BigDecimal getHeight(){
        return this.profondeur;
    }

    public String blockSerialisation(){
        String transactionsString = new String();

        for(Transaction t : transactionsList)
            transactionsString = transactionsString + "~"
                                                + t.transactionSerialisation();

        return "~"+profondeur+"~"+hash+"~"+creator+"~"+transactionsString+"~";
    }


}

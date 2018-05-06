import java.util.LinkedList;
import java.math.BigDecimal;
import java.lang.StringBuilder;

public class Block{
    private BigDecimal profondeur;
    private String hash;
    private String creator; //Nom du Noeud_Block qui crée ce Block
    private LinkedList<Transaction> transactionsList;

    public void Block(BigDecimal prof, String hashPreviousBlock,
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

    public String blockSerialisation(){
        String transactionsString = new String();

        for(Transaction t : transactionsList)
            transactionsString = transactionsString + "~"
                                                + t.transactionSerialisation();

        return "~"+profondeur+"~"+hash+"~"+creator+"~"+transactionsString+"~";
    }


}

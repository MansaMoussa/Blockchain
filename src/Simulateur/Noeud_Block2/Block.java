import java.util.LinkedList;
import java.math.BigDecimal;

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

    public void printBlock(){
        System.out.println("\n##########################################");
        System.out.println("\n#****************************************#");
        System.out.println("\n#*\t\tDeep : "+this.profondeur+"\t\t*#");
        System.out.println("\n#****************************************#");
        System.out.println("\n#*\t\tHash : "+this.hash+"\t\t*#");
        System.out.println("\n#****************************************#");
        System.out.println("\n#*\t\tCreator : "+this.creator+"\t\t*#");
        System.out.println("\n#****************************************#");
        for(Transaction t : transactionsList)
            t.printTransaction();
        System.out.println("\n##########################################");
    }

    public String blockSerialisation(){
        String transactionsString = new String();

        for(Transaction t : transactionsList)
            transactionsString = transactionsString + "~"
                                                + t.transactionSerialisation();

        return profondeur+"~"+hash+"~"+creator+"~"+transactionsString+"~";
    }


}

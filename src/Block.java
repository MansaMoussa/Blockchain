import java.util.LinkedList;
import java.math.BigDecimal;

public class Block{
    private BigDecimal profondeur;
    private String hash;
    private String creator; //Nom du Noeud_Block qui crée ce Block
    private LinkedList<Transaction> transactionsList;

    public void Block(BigDecimal prof, String hashPreviousBlock, String creatorName, LinkedList<Transaction> transactionsList){
        this.profondeur = prof;
        this.hash = hashPreviousBlock;
        this.creator = creatorName;
        this.transactionsList = transactionsList;
    }

    public String blockSerialisation(){
      return profondeur+"~"+hash+"~"+creator;
        //return profondeur+"~"+hash+"~"+creator+"~"+transactionsList.transactionSerialisation();
    }

}

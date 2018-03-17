import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.math.BigDecimal;
//import Transaction;

public class Noeud_Block extends UnicastRemoteObject implements Noeud{
    public BigDecimal reward_for_bloc_creation;
    public Integer max_participant;
    public Blockchain my_blockchain;  //doit être le même chez tout le monde
    public List Noeud_Participant participants; //Ceux qui sont inscrits à moi
    public List Transaction waiting_list;//liste des opérations à transcrire
    /* //LEVEL SECURITY
    private String name;
    private Integer private_key;
    public Integer public_key;

    public Bool decrypt(String encrypted_name){
        //check if this encrypted name is mine?
        //otherwise I can't make an Transaction
    }
    */
    public Noeud_Block(){
      //??
    }

    public Integer getPublicKey(){
        return this.public_key;
    }

    public BigDecimal getBlockMoney(){
        return this.reward_for_bloc_creation;
    }

    public Bool valid_transaction(Transaction t){
        //Possède t-il ce qu'il veut dépenser?
        //Vérifions ça dans la blockchain
    }

    public void write_transaction(Transaction t){
        //Cette condition ne vaut que lorsqu'un échange
        //de monnaie bloc est solicitée
        if(t.getType() == 'E' && this.valid_transaction()){
            //Add this shit in la liste d'attente
        }
    }

    public void order_create_block(Noeud_Block nB){
      //On doit lui dire qu'il crée un block dans sa liste d'attente(?)
    }

}

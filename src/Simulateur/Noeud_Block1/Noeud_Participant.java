import java.rmi.*;
import java.net.MalformedURLException;
import java.math.BigDecimal;
//import Transaction;

public class Noeud_Participant{
    public BigDecimal earnings_for_work_done;
    /*//LEVEL SECURITY ??
    private String name;
    private Integer private_key;
    public Integer public_key;

    public Bool decrypt(String encrypted_name){ //Pas encore sûr
        //check if this encrypted name is mine?
        //otherwise I can't make an Transaction
    }
    
    public Integer getPublicKey(){
        return this.public_key;
    }
    */

    public BigDecimal getBlockMoney(){
        return this.earnings_for_work_done;
    }
    /*
    public Bool proof_of_work_for_more_earnings(){

    }

    public void add_earnings_job_done(){
        if(proof_of_work_for_more_earnings)
            this.earnings_for_work_done++;
    }
    */
}

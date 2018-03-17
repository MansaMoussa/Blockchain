import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
//import Transaction;

public class Noeud_Participant extends UnicastRemoteObject implements Noeud{
    public Float earnings_for_work_done;
    /*//LEVEL SECURITY
    private String name;
    private Integer private_key;
    public Integer public_key;

    public Bool decrypt(String encrypted_name){ //Pas encore sûr
        //check if this encrypted name is mine?
        //otherwise I can't make an Transaction
    }
    */
    public Integer getPublicKey(){
        return this.public_key;
    }

    public Float getBlockMoney(){
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

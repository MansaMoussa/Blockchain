import java.math.BigDecimal;
import java.lang.StringBuilder;

public class Transaction{
    private char type;
    private String data; //Différentes selon le type de données qu'on a

    public char getType(){
        return this.type;
    }

    public String getData(){
        return this.data;
    }

    public StringBuilder printTransaction(StringBuilder display){
      display.append("\n#-----\tTranction_type : "+this.type+"\t-----#");
      display.append("\n#-----\tTranction_data : "+this.data+"\t-----#");
      display.append("\n#-----------------------------------------#");

      return display;
    }

    public String transactionSerialisation(){
        return this.type+"~"+this.data+"~";
    }
}

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

    public Transaction(char type, String data){
      this.type = type;
      this.data = data;
    }

    public StringBuilder printTransaction(StringBuilder display){
      display.append("\n#--\t\tTranction_type  :  "+this.type);
      display.append("\n#-- Tranction_data : "+this.data);
      display.append("\n#---------------------------------------------------#");

      return display;
    }

    public boolean equals(Transaction t){
        return  (this.type == t.getType()) &&
                this.data.equals(t.getData());
    }

    public String transactionSerialisation(){
        return this.type+"~"+this.data+"~";
    }
}

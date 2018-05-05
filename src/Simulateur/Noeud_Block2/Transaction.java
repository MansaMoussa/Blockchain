import java.math.BigDecimal;

public class Transaction{
    private char type;
    private String data; //Différentes selon le type de données qu'on a

    public char getType(){
        return this.type;
    }

    public String getData(){
        return this.data;
    }

    public void printTransaction(){
      System.out.println("\n#*\t\tTranction_type : "+this.type+"\t\t*#");
      System.out.println("\n#*\t\tTranction_data : "+this.data+"\t\t*#");
      System.out.println("\n#****************************************#");
    }

    public String transactionSerialisation(){
        return this.type+"~"+this.data+"~";
    }
}

import java.math.BigDecimal;

public class Transaction{
    private char type;
    private String data; //selon le type on a des données différentes

    public char getType(){
        return this.type;
    }

    public String getData(){
        return this.data;
    }

    public String transactionSerialisation(){
        return type+"~"+data;
    }
}

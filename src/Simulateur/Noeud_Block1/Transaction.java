import java.math.BigDecimal;
import java.lang.StringBuilder;
import java.util.StringTokenizer;

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

    public BigDecimal moneyReceivedOf(BigDecimal iD){
      BigDecimal money = new BigDecimal(0);
      //He earn money from exchanges or from blocks CREATION
      if((this.type == 'E')||(this.type == 'C')){
        StringTokenizer st = new StringTokenizer(this.data, " ");
        int i = 0;
        while (st.hasMoreTokens()){
          String dataContent = st.nextToken();
          //Because we know that iD is the second element or a Block for a creation
          if(i == 2 && dataContent.equals(iD.toString())){
            //Because we know that the money value is the third element
            money = new BigDecimal(st.nextToken());
          }
          i++;
        }
      }

      return money;
    }

    public BigDecimal moneySentOf(BigDecimal iD){
      BigDecimal money = new BigDecimal(0);
      if(this.type == 'E'){
        StringTokenizer st = new StringTokenizer(this.data, " ");
        int i = 0;
        while (st.hasMoreTokens()) {
          String dataContent = st.nextToken();
          //Because we know that iD is the second element
          if(i == 0 && dataContent.equals(iD.toString()))
          {
            st.nextToken();// = to
            st.nextToken();// = the who had received money
            //Because we know that the money value is the third element
            money = new BigDecimal(st.nextToken());
          }
          i++;
        }
      }
      return money;
    }

    public boolean equals(Transaction t){
        return  (this.type == t.getType()) &&
                this.data.equals(t.getData());
    }

    public String transactionSerialisation(){
        return this.type+"~"+this.data+"~";
    }
}

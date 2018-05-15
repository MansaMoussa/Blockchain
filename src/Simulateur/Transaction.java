import java.math.BigDecimal;
import java.lang.StringBuilder;
import java.util.StringTokenizer;
import java.io.Serializable;
import java.rmi.RemoteException ;

public class Transaction implements Serializable{
    //Transaction form
    //new Transaction('E', nP1.participantID+" to "+nP2.participantID+" "+moneySent);
    //new Transaction('C', "Noeud_Block port creation "+nP1.participantID+" "+moneySent);
    //new Transaction('C', "Noeud_Block port creates "+Block+" "+deep);
    //new Transaction('I', nP1.participantID+" to "+noeudBlock);
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

    //Remetre les transactions du bloc non valid dans la liste d'attente
		public boolean valid_transaction(BlockchainImpl bc)
    throws RemoteException{
				//Possède t-il ce qu'il veut dépenser?
				//Vérifions ça dans la BlockchainImpl
				boolean answer= true;
				BigDecimal moneyHeGot; //=
				if(this.getType() == 'E'){
					StringTokenizer st = new StringTokenizer(this.getData(), " ");
	        int i = 0;
	        while (st.hasMoreTokens()) {
	          String dataContent = st.nextToken(); //
	          //Because we know that iD is the second element
	          if(i == 0) //dataContent = the sender
	          {
							moneyHeGot = bc.howMuchMoneyDoesAParticipantHas(new BigDecimal(dataContent));
	            st.nextToken();// = to
	            st.nextToken();// = the who had received money ; We suppose that he'll always exist
	            //Because we know that the money value is the third element
							if(moneyHeGot.compareTo(new BigDecimal(st.nextToken())) == -1)
									answer = false;
	          }
	          i++;
	        }
				}
				return answer;
		}
}

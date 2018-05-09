import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuilder;
import java.math.BigDecimal;

public class BlockchainImpl extends UnicastRemoteObject implements Blockchain{

    //liste chaînée des Block-s (qui ont max 10 kBytes?)
    public LinkedList<Block> blocksList;

    public BlockchainImpl() throws RemoteException{
      super();
      blocksList = new LinkedList<Block>();
    };

    public BlockchainImpl(LinkedList<Block> blocksList) throws RemoteException{
        this.blocksList = blocksList;
    }

    //serverPort is the port number where this server is launched
    public StringBuilder printBlockchainImpl() throws RemoteException{
        StringBuilder display = new StringBuilder();
        display.append("\n###########################################");
        display.append("\n###########################################");
        display.append("\n#**\tBLOCKCHAIN FROM Noeud_Block1\t**#");
        display.append("\n#*****************************************#");
        for(Block b : blocksList)
            b.printBlock(display);
        display.append("\n###########################################");

        return display;
    }

    /*
    public void printMyBlockchain() throws RemoteException{
    }
    */

    public Blockchain askBlockchain(BigDecimal profondeur) throws RemoteException{
        LinkedList<Block> tempBlockList = new LinkedList<Block>();
        BigDecimal i = new BigDecimal(0);
        for(  ; i.compareTo(profondeur) == -1 || i.compareTo(profondeur) == 0
              ; i.add(BigDecimal.ONE))
            for(Block b : blocksList)
              if(i.compareTo(b.getHeight()) == 0)
                  tempBlockList.add(b);
        return new BlockchainImpl(tempBlockList);
    }

    //To get the hash will put in the new block
    public String hashBlock(Block b)
    throws RemoteException, NoSuchAlgorithmException {
        String stringToEncrypt = b.blockSerialisation();

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        String encryptedString = new String(messageDigest.digest());

        return encryptedString;
    }
}

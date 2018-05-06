import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.net.InetAddress.* ;
import java.net.* ;
import java.util.LinkedList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuilder;

public class BlockchainImpl extends UnicastRemoteObject implements Blockchain{

    public LinkedList<Block> blocksList; //liste chaînée des Block-s (de max 10 kBytes?)

    public BlockchainImpl()
    throws RemoteException{
      super();
    };

    //serverPort is the port number where this server is launched
    public StringBuilder printBlockchainImpl()
    throws RemoteException{
        StringBuilder display = new StringBuilder();
        display.append("\n###########################################");
        display.append("\n###########################################");
        display.append("\n#**\tBLOCKCHAIN FROM Noeud_Block2\t**#");
        display.append("\n#*****************************************#");
        /*for(Block b : blocksList)
            b.printBlock();*/
        display.append("\n###########################################");

        return display;
    }

    //To get the hash will put in the new block
    public String hashBlockLastetBlock()
    throws RemoteException, NoSuchAlgorithmException {
        String stringToEncrypt = blocksList.getLast().blockSerialisation();

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        String encryptedString = new String(messageDigest.digest());

        return encryptedString;
    }
}

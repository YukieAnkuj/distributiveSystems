import java.rmi.*;
import java.util.LinkedList;

public interface ChatInt extends Remote{
	
	public boolean login (String usr) throws RemoteException;
	
	public void sendMessage (String usr, String message) throws RemoteException;
	
	public boolean logout (String usr) throws RemoteException;


}



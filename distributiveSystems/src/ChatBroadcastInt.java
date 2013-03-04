import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;


public interface ChatBroadcastInt extends Remote{
	
	public void messageBroadcast (String message) throws RemoteException;
	
}

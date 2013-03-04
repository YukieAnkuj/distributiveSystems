import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.LinkedList;
import java.util.List;

public class ChatSystem implements ChatInt{
	private List<ChatBroadcastInt> users = new LinkedList<ChatBroadcastInt>();
	private int maxUsers = 2;
	private int nUsers = 0;
	private LinkedList<String> history = new LinkedList <String>();
	
	ChatSystem() {
	}
	
	public static void main (String[] args) {
		
		
		try {
			//creates a ChatSystem remote object
			ChatSystem c = new ChatSystem ();
			ChatInt c_stub = (ChatInt) UnicastRemoteObject.exportObject(c, 0);
			
			// Register the remote object in RMI
			// Registry with a given identifier
			
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Chat", c_stub);
			
			System.out.println ("Chat ready");

			
			
		}
		catch (Exception e) {
			System.err.println("Error on server: " + e);
			e.printStackTrace();
			return;
			
		}
	}
	
	public boolean login (String usr) throws RemoteException {
		try {
			if (nUsers < maxUsers) {
		
			System.out.println("Entrei no login no System");
			// get remote object reference
			Registry registryUser = LocateRegistry.getRegistry(0);
			System.out.println("Passou a parte de localizar");
			ChatBroadcastInt u = (ChatBroadcastInt) registryUser.lookup(usr);
			System.out.println("Passou a parte do lookup");
			users.add(u);
			nUsers++;

			
			for (int i = 0; i < history.size(); i++) {
				u.messageBroadcast(history.get(i));
			}
			
			
			String notification = usr + " has entered the chat";
			history.add(notification);
			this.broadcasting(notification);
			
			

			
			return true;
		}
		else
			return false;
		}
		catch (Exception e) {
			System.err.println("Error on server: " + e);
			e.printStackTrace();
			return false;
			
		}
	}
	
	public boolean logout (String usr) throws RemoteException {
		try{
			Registry registryUser = LocateRegistry.getRegistry(0);
			ChatBroadcastInt u = (ChatBroadcastInt) registryUser.lookup(usr);
			registryUser.unbind(usr);
			users.remove(u);
			nUsers--;
			
			System.out.println(usr + " has left the chat");
			String notification = usr + " has left the chat";
			history.add(notification);
			this.broadcasting(notification);
			
			return true;
		}
		catch (Exception e) {
			System.err.println("Error on server: " + e);
			e.printStackTrace();
			return false;
			
		}
	}
	
	public void sendMessage(String usr, String message) throws RemoteException {
		String newMsg = usr + ": " + message;
		
		history.add(newMsg);
		
		System.out.println("Received: " + newMsg);
		
		this.broadcasting(newMsg);
		
		
	}

	
	public void broadcasting (String msg) throws RemoteException  {
		for (int i = 0; i < nUsers; i++) {
			users.get(i).messageBroadcast(msg);
		}
	}

}

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;


public class User{
	private String username;
	private ChatInt c;

	
	public void initialize () {
		try {
	        
			System.out.println ("User ready");

			int host = 0;
			
			// get remote object reference
			Registry registryServer = LocateRegistry.getRegistry(host);
			c = (ChatInt) registryServer.lookup("Chat");
			
			
		}
		catch (Exception e) {
			System.err.println("Error on client: "+ e);
		}
	}
	

	
	public void sendMsg(String message) throws RemoteException {
		c.sendMessage(username, message);
	}
	
	public void logout () throws RemoteException{
		System.out.println("Pedido de logout");
		c.logout(username);
	}
	
	public void login() throws RemoteException{
		try {
			
			c.login(username);
			System.out.println("After login");
		}
		catch (Exception e) {
			System.err.println("Error on client: "+ e);
		}
	}
	
	public void setUsername (String usr) {
		this.username = usr;
	}
	
	public String getUsername () {
		return this.username;
	}
	
}
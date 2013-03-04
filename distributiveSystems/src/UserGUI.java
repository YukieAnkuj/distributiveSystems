import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;




public class UserGUI extends JFrame implements ChatBroadcastInt {
	private JPanel contentPane;
	private JTextField message;
	private JTextField username;
	private JPanel chatPanel;
	private JPanel loginPanel;
	private static User user = new User();
	private JTextArea chat = new JTextArea();
	private JScrollPane jsp = new JScrollPane(chat);;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user.initialize();
					
					UserGUI frame = new UserGUI();
					frame.setVisible(true);
					


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}

	/**
	 * Create the frame.
	 */
	public UserGUI() {
		
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * LOGIN PANEL
		 *
		 */
		
		
		/**
		 * Chat Panel
		 * 
		 */
		chatPanel = new JPanel();
		chatPanel.setBackground(Color.CYAN);
		chatPanel.setBounds(10, 11, 229, 218);
		contentPane.add(chatPanel);
		
		message = new JTextField();
		message.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		chat.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		jsp.setAutoscrolls(true);
		
		
		GroupLayout gl_chatPanel = new GroupLayout(chatPanel);
		gl_chatPanel.setHorizontalGroup(
			gl_chatPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_chatPanel.createSequentialGroup()
					.addComponent(message, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
					.addGap(28)
					.addComponent(btnSend)
					.addContainerGap())
				.addComponent(jsp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
		);
		gl_chatPanel.setVerticalGroup(
			gl_chatPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_chatPanel.createSequentialGroup()
					.addComponent(jsp, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_chatPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(message, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend)))
		);
		
		JButton btnLogout = new JButton("logout");
		jsp.setColumnHeaderView(btnLogout);
		chat.setLineWrap(true);
		
		jsp.setViewportView(chat);
		
		chatPanel.setLayout(gl_chatPanel);
		
		loginPanel = new JPanel();
		loginPanel.setBounds(10, 11, 229, 218);
		contentPane.add(loginPanel);
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel usrLabel = new JLabel("Username");
		loginPanel.add(usrLabel);
		
		username = new JTextField();
		
				
		
				loginPanel.add(username);
				username.setColumns(10);
				
				JButton btnLogin = new JButton("Login");
				
						loginPanel.add(btnLogin);
						
						
						loginPanel.setVisible(true);
						
						
								btnLogin.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
						
										System.out.println("Apertou o botao de login");
											try {
												user.setUsername(username.getText());
												loginPanel.setVisible(false);
												chatPanel.setVisible(true);
												loginSteps();
												
												
											} catch (RemoteException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
						
									}
								});
								
								username.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										try {
											System.out.println("Apertou enter no login");
											user.setUsername(username.getText());
											loginPanel.setVisible(false);
											chatPanel.setVisible(true);
											loginSteps();

											
										} catch (RemoteException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								});
		chatPanel.setVisible(false);
		
		
		/**
		 * Events
		 * 
		 */
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Apertou o botao de enviar mensagem");
					user.sendMsg(message.getText());
					message.setText("");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		message.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Apertou enter para enviar mensagem");
					user.sendMsg(message.getText());
					message.setText("");

				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Apertou o botao de logout");

					loginPanel.setVisible(true);
					chatPanel.setVisible(false);
					chat.removeAll();
					user.logout();
				}
				catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
	}
	
	public void loginSteps () throws RemoteException {
		try {

			//creates an User remote object
			UserGUI u = new UserGUI ();
			ChatBroadcastInt u_stub = (ChatBroadcastInt) UnicastRemoteObject.exportObject(u, 0);
			
	
			Registry registry = LocateRegistry.getRegistry(0);
			
			registry.bind(user.getUsername(), u_stub);
			
			
			
			user.login();

			
			
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void messageBroadcast (String message) throws RemoteException {
		chat.setText("\nsbrubleus");
		
	}
}

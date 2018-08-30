package client.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import common.MessageType;
public class Client extends Thread{ //client thread
	Socket socket;
	public boolean isConnected;
	public boolean isWorking;
	BufferedReader inputStream; //incoming stream
	 PrintWriter outputStream; //outcoming stream
	public ClientMain parent;
	boolean isLogged;
	ClientListener clientListener;
	public Client(ClientMain dad)
	{
		socket=null;
		isConnected=false;
		isWorking=true;
		inputStream=null;
		outputStream=null;
		parent=dad;
		System.out.println("Client created");
	}
	public synchronized boolean connect(String adress)//connect and log in
	{
		boolean flag=false;
		if(!isLogged)
		{
			if(!isConnected)
			{
				InetSocketAddress address;
				try {
					socket = new Socket();
					address = new InetSocketAddress(adress, 6000);
					socket.connect(address);
					System.out.println("Polaczenie nawiazane");
					 outputStream = new PrintWriter(socket.getOutputStream(), true);
				     inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					isConnected=true;
					//parent.loginController.logged();
					clientListener=new ClientListener(this);
					clientListener.start();
					this.sendMessage(MessageType.LOGIN, parent.username, "is logged");
					isLogged=true;
					return true;
				}
				catch (Exception e) {
					System.err.println( e.getMessage() );
					e.printStackTrace();
					isConnected=false;
					flag= false;
					}
			}
			else
			{
				//just for sure
				isConnected=false;
				flag=false;
			}
		}
		else
		{
			isLogged=false;
			flag=false;
		}
		return flag;
	}
	public synchronized boolean connect()//just connect
	{
		if(!isConnected)
		{
			InetSocketAddress address;
			try {
				socket = new Socket();
				address = new InetSocketAddress("localhost", 6000);
				socket.connect(address);
				System.out.println("Polaczenie nawiazane");
				outputStream = new PrintWriter(socket.getOutputStream(), true);
			    inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				isConnected=true;
				//parent.loginController.logged();
				clientListener=new ClientListener(this);
				clientListener.setDaemon(true);
				clientListener.start();
				//this.sendMessage(MessageType.REGISTER, parent.username, "is registred");
				this.sendMessage(MessageType.LOGIN, parent.username, "is logged");
				return true;
			}
			catch (Exception e) {
				System.err.println( e.getMessage() );
				e.printStackTrace();
				isConnected=false;
				return false;
				}
		}
		else
		{
			//just for sure
			isConnected=false;
			return false;
		}
	}
	public synchronized boolean disconnect()
	{
		isWorking=false;
		if(socket != null)
		{
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			isConnected=false;
			return true;
		}
		isConnected=false;
		return false;
	}
	public synchronized void sendMessage(int t,String who,String m)
	{
		System.out.println("Sending a message: "+m);
		outputStream.println(t);
		outputStream.println(who);
		outputStream.println(m);
	}
	public void run()
	{
		if(socket.isConnected())
		{
			 //main loop of client
			while (isWorking)
			{
			}
			//ending
			try {
				inputStream.close();
				outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

		}
	}
}

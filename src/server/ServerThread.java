package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread { //runnable independent connection with client
	Socket socket; //client socket
	BufferedReader inputStream; //incoming stream
	PrintWriter outputStream; //outcoming stream
	boolean isWorking; //working flag
	ServerMonitor parent;
	ServerListenerThread listener=null;;
	boolean isLogged;
	public ServerThread(ServerMonitor dad)
	 {
		 socket=null;
		 inputStream=null;
		 outputStream=null;
		 isWorking=true;
		 isLogged=false;
		 parent=dad;
	 }
	public synchronized void setListener()
	{
		listener=new ServerListenerThread(this);
		listener.start();
	}
	public synchronized void setSocket(Socket accept) {
		if(accept!=null)
		{
			socket=accept;
			try {
			outputStream = new PrintWriter(socket.getOutputStream(), true);
		    inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void run()
	{
		//main loop of thread
		System.out.println("ServerThread starter!");
			while (isWorking)
			{
			}
				//END
				try {

					inputStream.close();
					outputStream.close();
		        	socket.close();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
	}
	public void sendMessage(int t,String who,String m)
	{
		outputStream.println(t);
		outputStream.println(who);
		outputStream.println(m);
	}

}

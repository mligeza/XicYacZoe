package server;

import java.io.IOException;

import common.MessageType;

public class ServerListenerThread extends Thread {
	ServerThread parent;
	public ServerListenerThread(ServerThread dad) {
		parent=dad;
	}
public void run(){
	//main loop of thread
	System.out.println("ServerListenerThread started!");
				while (parent.isWorking)
				{

					int t=0;
					String  user = null,message=null;
					try {
						t =Integer.parseInt(parent.inputStream.readLine());
						user=parent.inputStream.readLine();
						message=parent.inputStream.readLine();
						if(t==MessageType.WILL)
						{
							System.out.println(">"+user+" wants to play!");
							parent.parent.addWaiter(parent,user);
						}
						else if(t==MessageType.PRIVATE || t==MessageType.MOVE)
						{
							System.out.println("Inside message from: "+user);
							parent.parent.sendPlayed(t,user,message);
						}
						else
						{
							parent.parent.sendMessage(t, user, message);
							if(!parent.socket.isConnected())break;
						}
					} catch (Exception e) {

						//e.printStackTrace();
					}
					//System.out.println("Recevied a message from: "+user+": "+message);

				}
			//END
			synchronized(this)
			{
				try {
					parent.inputStream.close();
					parent.outputStream.close();
		        	parent.socket.close();
		        	System.out.println("ServerListenerThread closed!");
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
			}
}}

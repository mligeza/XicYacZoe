package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import common.MessageType;

public class ServerMonitor extends Thread {
public class Play{
	public ServerThread first;
	public String firstname;
	public ServerThread second;
	public String secondname;
	Play(ServerThread one, String oneone, ServerThread two, String twotwo)
	{
		first=one;
		firstname=oneone;
		second=two;
		secondname=twotwo;
	}
}
 ServerSocket server_socket;//listener
 ArrayList<ServerThread> clients; //clients
 Play players[];
int games;

ServerMonitorListener listener;
public boolean isWorking;
ServerThread waiter;
String waiterName;

 public ServerMonitor() {
	 games=0;
	 isWorking=true;
	 players=new Play[5];
	 clients=new ArrayList<>();
	 waiter=null;
	 waiterName="";
	 listener=new ServerMonitorListener(this);
	 try {
			server_socket = new ServerSocket(6000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 System.out.println("ServerMonitor started!");
}
public int main(String args[])
 {
	 //start();
	return 0;
 }
 public void run()
 {
	 listener.start();
	 while(isWorking)
	 {
	 }
	 close();
 }

public synchronized void sendMessage(int t,String who,String m)
{
	if(t!=MessageType.EMPTY && t!=MessageType.PRIVATE &&t!=MessageType.WILL && t!=MessageType.MOVE)
	{
		addMessage(t,who,m);
		if(clients!=null && !clients.isEmpty())
		{
			for(ServerThread th:clients)
			{
				if(th.socket.isConnected())	th.sendMessage(t,who,m);
			}
		}
	}
}
public synchronized void addMessage(int t,String who,String m)
{
	System.out.println(t+">"+who+": "+m);
}
public void close() {
	isWorking=false;
	if(!clients.isEmpty())
		for(ServerThread th:clients)
		{
			th.isWorking=false;
		}
	for(ServerThread th:clients){
		try{
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
public void addWaiter(ServerThread st, String name) {
	if(waiter==null)
	{
		waiter=st;
		waiterName=name;
	}
	else
	{
		waiter.sendMessage(MessageType.YES,name ,Integer.toString(1));
		waiter.sendMessage(MessageType.PRIVATE, name, "Start!");
		st.sendMessage(MessageType.YES, waiterName, Integer.toString(2));
		st.sendMessage(MessageType.PRIVATE, waiterName, "Enemy starts!");
		if(games<5)
			{
			players[games]=new Play(waiter,waiterName,st,name);
			games++;
			}
		else
		{
			games=0;
			players[games]=new Play(waiter,waiterName,st,name);
			games++;
		}
		waiter=null;
		waiterName="";
	}
}
public void sendPlayed(int t, String user, String message) {
	System.out.println("user:"+user+" m:"+message);
	for(int i=4;i>=0;i--)
	{
		if(players[i]!=null)
		{
			if(user.equals(players[i].firstname))
			{
				players[i].second.sendMessage(t, user, message);
				System.out.println(user+">"+players[i].secondname+":"+message);
				if(t==MessageType.PRIVATE)
				{
					players[i].first.sendMessage(t, user, message);
				}
				return;
			}
			else if(user.equals(players[i].secondname))
			{
				players[i].first.sendMessage(t, user, message);
				System.out.println(user+">"+players[i].firstname+":"+message);
				if(t==MessageType.PRIVATE)
				{
					players[i].second.sendMessage(t, user, message);
				}
				return;
			}
		}
	}
}
}

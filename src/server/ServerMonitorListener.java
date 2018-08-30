package server;

public class ServerMonitorListener extends Thread {
ServerMonitor parent;
public ServerMonitorListener(ServerMonitor dad)
{
	parent=dad;
	}
public void run()
{
	while(parent.isWorking)
	 {
		 try {
			 //getting new connection
			 ServerThread server=new ServerThread(parent);
			 server.setSocket(parent.server_socket.accept());
			 server.setListener();
			 parent.clients.add(server);
			 server.start();
			 //informing about new connection
			 System.out.println("Zglosil sie klient");
		 }
		 catch (Exception e)
	 	 {
			e.printStackTrace();
		 }
	 }
}
}

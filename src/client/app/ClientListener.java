package client.app;

import java.io.IOException;

public class ClientListener extends Thread{
Client parent;
public ClientListener(Client dad)
{
	parent=dad;
	}
public void run()
{
	if(parent.isConnected)
	{
		 //main loop of client
		while (parent.isWorking && parent.socket.isConnected())
		{

			String  username = " ",message=" ";
			int t=1;
			try {
				t =Integer.parseInt(parent.inputStream.readLine());
				username=parent.inputStream.readLine();
				message=parent.inputStream.readLine();
				System.out.println("ClientListener got a meesage:"+t+":"+username+":"+message);
				parent.parent.addMessage(t, username, message);
			} catch (Exception e) {
				//e.printStackTrace();
				try {
					parent.socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(!parent.socket.isConnected())break;

		}
		//ending
		try {
			parent.inputStream.close();
			parent.outputStream.close();
            parent.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
}

}

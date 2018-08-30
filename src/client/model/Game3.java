package client.model;

public class Game3  {

	public int board[][];
	public Game3()
	{
		board=new int[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				board[i][j]=0;
			}
		}
	}
	public void move(int a,int b, int who)
	{
		board[a][b]=who;
	}
	public boolean ifWon(int who)
	{
		if(board[0][0]==who && board[1][1]==who && board[2][2]==who) return true;
		if(board[0][0]==who && board[1][0]==who && board[2][0]==who) return true;
		if(board[0][0]==who && board[0][1]==who && board[0][2]==who) return true;
		if(board[0][1]==who && board[1][1]==who && board[2][1]==who) return true;
		if(board[1][0]==who && board[1][1]==who && board[1][2]==who) return true;
		if(board[0][2]==who && board[1][2]==who && board[2][2]==who) return true;
		if(board[2][0]==who && board[2][1]==who && board[2][2]==who) return true;
		if(board[2][0]==who && board[1][1]==who && board[0][2]==who) return true;
		return false;
	}
	public boolean ifFull()
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(board[i][j]==0) return false;
			}
		}
		return true;
	}


}

package client.view;
import java.io.InputStream;

import client.app.Client;
import client.app.ClientMain;
import client.model.Game3;
import common.GameType;
import common.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Game3ViewController {

	Game3 game;
	GameType gameType;
	boolean isPlayed=true;
	int who=0;
	ClientMain clientMain;
	Client client;
	@FXML
	public TextArea text;
	@FXML
	TextField input;
	@FXML
	Button send;
	@FXML
	ImageView background;
	@FXML
	ImageView f1_1;
	@FXML
	ImageView f1_2;
	@FXML
	ImageView f1_3;
	@FXML
	ImageView f2_1;
	@FXML
	ImageView f2_2;
	@FXML
	ImageView f2_3;
	@FXML
	ImageView f3_1;
	@FXML
	ImageView f3_2;
	@FXML
	ImageView f3_3;
	Image yes;
	Image no;
	Image def;
	String enemy="enemy";

	@FXML
	public void initialize() {
	game= new Game3();
	 //File file = new File("src/resources/background.png");
	 InputStream im=getClass().getClassLoader().getResourceAsStream("resources/background.png");
	// Image ima = new Image(im);
    Image image = new Image(im);
    background.setImage(image);
    //file = new File("src/resources/yes.png");
    im=getClass().getClassLoader().getResourceAsStream("resources/yes.png");
    yes= new Image(im);
    //file = new File("src/resources/no.png");
    im=getClass().getClassLoader().getResourceAsStream("resources/no.png");
    no= new Image(im);
   // file = new File("src/resources/empty1.png");
    im=getClass().getClassLoader().getResourceAsStream("resources/empty1.png");
    def = new Image(im);
    f1_1.setImage(def);
    f1_2.setImage(def);
    f1_3.setImage(def);
    f2_1.setImage(def);
    f2_2.setImage(def);
    f2_3.setImage(def);
    f3_1.setImage(def);
    f3_2.setImage(def);
    f3_3.setImage(def);
    if(gameType==GameType.AI3)
    {
    	text.appendText("Game started: Player vs Computer\n");
    }
    if(gameType==GameType.HS3)
    {
    	text.appendText("Game started: Hot Seat\n");
    }
    if(gameType==GameType.Online3)
    {
    	text.appendText("Game started: Online\n");
    }
    else
    {
    	send.setDisable(true);
        input.setEditable(false);
    }

}
	public void setClient(Client cl)
	{
		this.client=cl;
	}
	public void randomAImove()
	{
		boolean moved=false;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(moved==false)
				{
					if(game.board[i][j]==0)
					{
						game.board[i][j]=2;
						if(i==0 &&j==0)f1_1.setImage(no);
						if(i==0 &&j==1)f1_2.setImage(no);
						if(i==0 &&j==2)f1_3.setImage(no);
						if(i==1 &&j==0)f2_1.setImage(no);
						if(i==1 &&j==1)f2_2.setImage(no);
						if(i==1 &&j==2)f2_3.setImage(no);
						if(i==2 &&j==0)f3_1.setImage(no);
						if(i==2 &&j==1)f3_2.setImage(no);
						if(i==2 &&j==2)f3_3.setImage(no);
						moved=true;
					}
				}
			}
		}

	}
	public void disable()
	{
		send.setDisable(true);
		input.setEditable(false);
	}
	public void enable()
	{
		send.setDisable(false);
		input.setEditable(true);
	}
	public void handleSend()
	{
		String m=input.getText();
		if(m!=null && clientMain!=null)
		{
			client.sendMessage(MessageType.PRIVATE,clientMain.username,m);
		}
		input.clear();
	}
	public void testWon(int who)
	{
		switch(gameType)
		{
		case HS3:
			if(game.ifWon(who))
			{
				isPlayed=false;
				text.appendText("Player "+(who)+" won!\n");
			}
			else if(game.ifFull())
			{
				isPlayed=false;
				text.appendText("Draw!\n");
			}
			break;
		case AI3:
			if(game.ifWon(who))
			{
				isPlayed=false;
				text.appendText("Player "+(who)+" won!\n");
			}
			else if(game.ifFull())
			{
				isPlayed=false;
				text.appendText("Draw!\n");
			}
			break;
		case Online3:
			if(game.ifWon(who))
			{
				if(who==1)
					text.appendText(clientMain.username+" won!\n");
				else
					text.appendText(enemy+" won!\n");
				isPlayed=false;
			}
			else if(game.ifFull())
			{
				isPlayed=false;
				text.appendText("Draw!\n");
			}
			break;
		default:
			break;
		}
	}

	public void handleIfClickedF1_1()
	{
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[0][0]==0)
				{
					if(who==0)
					{
						game.board[0][0]=1;
						f1_1.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[0][0]=2;
						f1_1.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[0][0]==0)
				{
						game.board[0][0]=1;
						f1_1.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[0][0]==0)
				{
					if(who==0)
					{
						game.board[0][0]=1;
						f1_1.setImage(yes);
						testWon(who+1);
						who=1;
						clientMain.client.sendMessage(MessageType.MOVE, clientMain.username, "00");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}
	}
	public void handleIfClickedF1_2(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[0][1]==0)
				{
					if(who==0)
					{
						game.board[0][1]=1;
						f1_2.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[0][1]=2;
						f1_2.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[0][1]==0)
				{
						game.board[0][1]=1;
						f1_2.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[0][1]==0)
				{
					if(who==0)
					{
						game.board[0][1]=1;
						f1_2.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "01");
						//while(!ifNew()){}

					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF1_3(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[0][2]==0)
				{
					if(who==0)
					{
						game.board[0][2]=1;
						f1_3.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[0][2]=2;
						f1_3.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[0][2]==0)
				{
						game.board[0][2]=1;
						f1_3.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[0][2]==0)
				{
					if(who==0)
					{
						game.board[0][2]=1;
						f1_3.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "02");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF2_1(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[1][0]==0)
				{
					if(who==0)
					{
						game.board[1][0]=1;
						f2_1.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[1][0]=2;
						f2_1.setImage(no);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[1][0]==0)
				{
						game.board[1][0]=1;
						f2_1.setImage(yes);
						testWon(who+1);
						randomAImove();
						testWon(2);
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[1][0]==0)
				{
					if(who==0)
					{
						game.board[1][0]=1;
						f2_1.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "10");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF2_2(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[1][1]==0)
				{
					if(who==0)
					{
						game.board[1][1]=1;
						f2_2.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[1][1]=2;
						f2_2.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[1][1]==0)
				{
						game.board[1][1]=1;
						f2_2.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[1][1]==0)
				{
					if(who==0)
					{
						game.board[1][1]=1;
						f2_2.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "11");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF2_3(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[1][2]==0)
				{
					if(who==0)
					{
						game.board[1][2]=1;
						f2_3.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[1][2]=2;
						f2_3.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[1][2]==0)
				{
						game.board[1][2]=1;
						f2_3.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[1][2]==0)
				{
					if(who==0)
					{
						game.board[1][2]=1;
						f2_3.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "12");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF3_1(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[2][0]==0)
				{
					if(who==0)
					{
						game.board[2][0]=1;
						f3_1.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[2][0]=2;
						f3_1.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[2][0]==0)
				{
						game.board[2][0]=1;
						f3_1.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[2][0]==0)
				{
					if(who==0)
					{
						game.board[2][0]=1;
						f3_1.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "20");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF3_2(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[2][1]==0)
				{
					if(who==0)
					{
						game.board[2][1]=1;
						f3_2.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[2][1]=2;
						f3_2.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[2][1]==0)
				{
						game.board[2][1]=1;
						f3_2.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[2][1]==0)
				{
					if(who==0)
					{
						game.board[2][1]=1;
						f3_2.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "21");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}
	public void handleIfClickedF3_3(){
		switch(gameType)
		{
		case HS3:
			if(isPlayed)
			{
				if(game.board[2][2]==0)
				{
					if(who==0)
					{
						game.board[2][2]=1;
						f3_3.setImage(yes);
						testWon(who+1);
						who=1;
					}
					else if(who==1)
					{
						game.board[2][2]=2;
						f3_3.setImage(no);
						testWon(who+1);
						who=0;
					}
				}
			}
			break;
		case AI3:
			if(isPlayed)
			{
				if(game.board[2][2]==0)
				{
						game.board[2][2]=1;
						f3_3.setImage(yes);
						testWon(who+1);
						if(isPlayed)
						{
						randomAImove();
						testWon(2);
						}
				}
			}
			break;
		case Online3:
			if(isPlayed)
			{
				if(game.board[2][2]==0)
				{
					if(who==0)
					{
						game.board[2][2]=1;
						f3_3.setImage(yes);
						testWon(who+1);
						who=1;
						client.sendMessage(MessageType.MOVE, clientMain.username, "22");
						//while(!ifNew()){}
					}
				}
			}
			break;
		default:
			break;
		}}

	public void setMain(ClientMain clientMain) {
		this.clientMain=clientMain;
	}
	public void setGameType(GameType z) {
		this.gameType=z;
	}
	public void move(String s) {
		//odczytanie  i j
		if(who==1)
		{
		int i,j;
		char a=s.charAt(0);
		char b=s.charAt(1);
		String z="";
		z+=a;
		i=Integer.parseInt(z);
		z=""+b;
		j=Integer.parseInt(z);
		game.board[i][j]=2;
		if(i==0 &&j==0)f1_1.setImage(no);
		if(i==0 &&j==1)f1_2.setImage(no);
		if(i==0 &&j==2)f1_3.setImage(no);
		if(i==1 &&j==0)f2_1.setImage(no);
		if(i==1 &&j==1)f2_2.setImage(no);
		if(i==1 &&j==2)f2_3.setImage(no);
		if(i==2 &&j==0)f3_1.setImage(no);
		if(i==2 &&j==1)f3_2.setImage(no);
		if(i==2 &&j==2)f3_3.setImage(no);
		testWon(2);
		who=0;
	}}
	public void startOnline(String u, boolean t)
	{
		isPlayed=true;
		enable();
		//gameType=GameType.Online3;
		enemy=u;
		if(t==true)who=0;
		else who=1;
	}
	public void waitforyes() {
		who=-1;
		disable();
		//while(!ifNew()){}
	}

}

package client.app;
import java.io.IOException;

import client.view.Game3ViewController;
import client.view.LoginViewController;
import client.view.MenuViewController;
import client.view.RootLayoutController;
import client.view.ShoutboxViewController;
import common.GameType;
import common.MessageType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ClientMain extends Application {

	public Stage primaryStage;
	//root
	private BorderPane borderLayout;
	public RootLayoutController rootController;
	//shoutbox
	private AnchorPane shoutbox;
	public ShoutboxViewController shoutboxController;
	//login
	private AnchorPane loginView;
	public LoginViewController loginController;

	private AnchorPane menuView;
	public MenuViewController menuController;

	private AnchorPane gameView;
	public Game3ViewController g3c;
	public int gType;

	public Client client;
	public String username="";

	public static void main(String[] args) {
	       launch(args);
	    }
	@Override
	public void start(Stage primaryStage2) throws Exception {
		username="visitor";
		client=new Client(this);
		client.setDaemon(true);
		this.primaryStage=new Stage();
		this.primaryStage.setTitle("Xic Yac Zoe");
		initRootLayout();

		 initMenuView();
		 initEmpty();
		 initShoutboxView();
		 //initGameLayout(GameType.HS3);
		 initLoginView();
		 primaryStage.show();

	}
	private void initEmpty()
	{

		try {
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(ClassLoader.getSystemResource("client/view/Empty.fxml"));
	        AnchorPane empty;
			empty = (AnchorPane) loader.load();
			 borderLayout.setCenter(empty);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void initRootLayout() {
		 try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ClassLoader.getSystemResource("client/view/RootLayout.fxml"));
	            borderLayout = (BorderPane) loader.load();
	            Scene scene = new Scene(borderLayout);
	            primaryStage.setScene(scene);
	            primaryStage.setResizable(false);
	            rootController = loader.<RootLayoutController>getController();
	            rootController.setMain(this);


	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	private void initLoginView(){
		try{
			 FXMLLoader loader = new FXMLLoader();
	         loader.setLocation(ClassLoader.getSystemResource("client/view/LoginView.fxml"));
	         loginView = (AnchorPane) loader.load();
	         loginController = loader.<LoginViewController>getController();
	         loginController.setMain(this);
	         loginController.setClient(client);
	         borderLayout.setLeft(loginView);
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	}
	public void initGameLayout(GameType z){
		switch(z)
		{
		case AI3:
			try{
				 FXMLLoader loader = new FXMLLoader();
		         loader.setLocation(ClassLoader.getSystemResource("client/view/Game3View.fxml"));
		         gameView = (AnchorPane) loader.load();
		         g3c = loader.<Game3ViewController>getController();
		         g3c.setMain(this);
		         g3c.setClient(client);
		         g3c.setGameType(z);
		         borderLayout.setCenter(gameView);
		         primaryStage.show();
		     } catch (IOException e) {
		         e.printStackTrace();
		     }
			break;
		case HS3:
			try{
				 FXMLLoader loader = new FXMLLoader();
		         loader.setLocation(ClassLoader.getSystemResource("client/view/Game3View.fxml"));
		         gameView = (AnchorPane) loader.load();
		         primaryStage.show();
		         g3c = loader.<Game3ViewController>getController();
		         g3c.setMain(this);
		         g3c.setClient(client);

		         g3c.setGameType(z);
		         borderLayout.setCenter(gameView);
		     } catch (IOException e) {
		         e.printStackTrace();
		     }

			break;
		case Online3:
			try{
				 FXMLLoader loader = new FXMLLoader();
		         loader.setLocation(ClassLoader.getSystemResource("client/view/Game3View.fxml"));
		         gameView = (AnchorPane) loader.load();
		         g3c = loader.<Game3ViewController>getController();
		         g3c.setMain(this);
		         g3c.setClient(client);
		         g3c.setGameType(z);
		         g3c.waitforyes();
		         borderLayout.setCenter(gameView);
		     } catch (IOException e) {
		         e.printStackTrace();
		     }
			break;
		default:
			break;}
	}
	private void initShoutboxView(){
		try{
		 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(ClassLoader.getSystemResource("client/view/ShoutboxView.fxml"));
         shoutbox = (AnchorPane) loader.load();
         borderLayout.setRight(shoutbox);
         shoutboxController =loader.<ShoutboxViewController>getController();
         shoutboxController.setMain(this);

     } catch (IOException e) {
         e.printStackTrace();
     }
	}
	private void initMenuView()
	{
		 try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ClassLoader.getSystemResource("client/view/MenuView.fxml"));
	            menuView = (AnchorPane) loader.load();
	            borderLayout.setTop(menuView);
	            menuController = loader.<MenuViewController>getController();
	            menuController.setMain(this);
	            menuController.disable();
	            menuController.setGame(g3c);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	public void stageClose() {
		primaryStage.close();

	}
	public void close()
	{
		client.disconnect();
		stageClose();
		try {
			this.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Window getPrimaryStage() {
		return primaryStage;
	}

	public synchronized void addMessage(int t,String who, String s)//incoming
	{
		String message=s;
		if(t==MessageType.PRIVATE)
		{
			Platform.runLater(new Runnable() {
			    public void run() {
			        g3c.text.appendText(who+":"+s+"\n");
			    }
			});
			return;
		}
		else if( t==MessageType.MOVE)
		{
			Platform.runLater(new Runnable() {
			    public void run() {
			       g3c.move(s);
			    }
			});
			return;
		}
		else if(t==MessageType.YES)
		{
			int i= Integer.parseInt(s);
			if(i==1)
			{
				Platform.runLater(new Runnable() {
				    public void run() {
				        g3c.startOnline(who,true );
				    }
				});
			}
			else if(i==2)
			{
				Platform.runLater(new Runnable() {
				    public void run() {
				        g3c.startOnline(who,false );
				    }
				});
			}
			return;
		}
		if(t==MessageType.REGISTER)
		{
			message="REGISTERED :";
		}
		else if(t==MessageType.LOGIN)
		{
			message="LOGIN:";
		}
		else if(t==MessageType.LOGOUT)
		{
			message="LOGOUT:";
		}
		else if(t==MessageType.MESSAGE)
		{
			message=" ";
		}
		else if(t==MessageType.STATISTICS)
		{
			message="STATS:";
		}
		message+=who+": "+s+"\n";
		shoutboxController.addMessage(message);
		System.out.println(message);
	}
	public synchronized void sendMessage(int t, String who,String s)//own
	{
		client.sendMessage(t,who, s);
	}
}

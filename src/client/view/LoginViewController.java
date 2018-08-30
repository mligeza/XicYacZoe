package client.view;

import java.io.File;

import client.app.Client;
import client.app.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class LoginViewController {
	ClientMain clientMain;
	Client client;
@FXML
TextField address;
@FXML
TextField username;
@FXML
Button login;
@FXML
Button clear;
@FXML
ImageView isLogged;
@FXML
Button close;
@FXML
Button def;
	@FXML
	public void initalize()
	{
	 File file = new File("src/resources/no.png");
	  Image image= new Image(file.toURI().toString());
	  isLogged.setImage(image);
	}
	public void setMain(ClientMain cm) {
		this.clientMain=cm;
	}
	public void setClient(Client cl)
	{
		this.client=cl;
	}
	public void login()
	{
		String a=address.getText();
		String u=username.getText();
		System.out.println(a);
		System.out.println(u);
		client.parent.username=u;
		client.connect(a);
		client.start();
		logged();
	}
	public void close()
	{
		clientMain.close();
	}
	public void clear()
	{
	address.clear();
	username.clear();
	}
	public void logged()
	{
		if(clientMain.client.isConnected)
		{
		 File file = new File("src/resources/yes.png");
		  Image image= new Image(file.toURI().toString());
		  isLogged.setImage(image);
		  clientMain.menuController.enable();
		  if(clientMain.g3c!=null)clientMain.g3c.enable();
		  clientMain.shoutboxController.enable();
		  clientMain.menuController.enable();
		}

	}
	public void def()
	{
		client.connect();
		client.start();
		logged();
	}
}
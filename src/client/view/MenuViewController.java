package client.view;

import client.app.ClientMain;
import common.GameType;
import common.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuViewController {
	@FXML
	Button ai;
	@FXML
	Button hs;
	@FXML
	Button online;
	ClientMain clientMain;
	Game3ViewController g3c;
	public void setMain(ClientMain clientMain) {
		this.clientMain=clientMain;
	}
	public void setGame(Game3ViewController gc)
	{
		this.g3c=gc;
	}
	@FXML
	public void initalize()
	{
		online.setDisable(true);
	}
	public void disable()
	{
		online.setDisable(true);
	}
	public void enable()
	{
		online.setDisable(false);
	}
	public void handleHs()
	{
		clientMain.initGameLayout(GameType.HS3);
	}
	public void handleAi()
	{
		clientMain.initGameLayout(GameType.AI3);
	}
	public void handleOnline()
	{
		clientMain.initGameLayout(GameType.Online3);
		clientMain.client.sendMessage(MessageType.WILL, clientMain.username, "");
	}
}

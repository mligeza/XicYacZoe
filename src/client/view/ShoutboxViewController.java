package client.view;

import client.app.ClientMain;
import common.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ShoutboxViewController {
	ClientMain clientMain;
	@FXML
	public TextArea text;
	@FXML
	public TextArea input;
	@FXML
	public Button enter;
	@FXML
	public Button clear;
	@FXML
	Button connect;
	@FXML
	TextField address;
	@FXML
	TextField username;
	public void send()
	{
		String m=input.getText();
		clientMain.sendMessage(MessageType.MESSAGE, clientMain.username, m);
		input.clear();
	}
	public void clear()
	{input.clear();}
	@FXML
	public void initialize()
	{
		input.setEditable(false);
		enter.setDisable(true);
		clear.setDisable(true);
	}
	public void setMain(ClientMain cm) {
		clientMain=cm;
	}
	public void enable()
	{
		input.setEditable(true);
		enter.setDisable(false);
		clear.setDisable(false);
	}
	public void addMessage(String m)
	{
		text.appendText(m);
	}
	public void handleConnect()
	{
		String u=username.getText();
		clientMain.username=u;
		String a=address.getText();
		if(clientMain.client.connect(a))enable();
	}
}

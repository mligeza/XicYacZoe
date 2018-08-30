package client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import client.app.ClientMain;
import common.GameType;

public class RootLayoutController {
	ClientMain clientMain;
	//Games
@FXML
MenuItem hs3;
@FXML
MenuItem hs10;
@FXML
MenuItem ai3;
@FXML
MenuItem ai10;
@FXML
MenuItem online3;
@FXML
MenuItem online10;
	//Others
@FXML
MenuItem about;
@FXML
public BorderPane borderPane;
public boolean isEnabled=false;
void handleHs3(){
	if(isEnabled)
	{
		clientMain.initGameLayout(GameType.HS3);
	}

}
void handleAi3(){if(isEnabled)
{
	clientMain.initGameLayout(GameType.AI3);
}}

void handleOnline3(){
	if(isEnabled)
	{
		clientMain.initGameLayout(GameType.Online3);
	}
}

void handleAbout(){
	Alert a=new Alert(AlertType.INFORMATION);
	a.setHeaderText("XicYacZoe");
	a.setContentText("Game created by Michal Ligeza and Anna Milkos");
}
public void setMain(ClientMain clientMain2) {
	clientMain=clientMain2;

}

}
package server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Server extends Application
{
	final Stage primaryStage=new Stage();
	static ServerMonitor monitor;

	public static void main(String[] args) {
	       launch(args);
	    }
	@Override
	public void start(Stage primaryStage) throws Exception {
		monitor=new ServerMonitor();
		monitor.start();

		Button end=new Button();
		end.setText("End");
		end.setOnAction(e->{
			if(monitor.clients.isEmpty())
			{
				monitor.close();
				primaryStage.close();
				try {
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
			}
			}
		});
		VBox vbox=new VBox();
		vbox.getChildren().add(end);
		Scene scene=new Scene(vbox);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
}

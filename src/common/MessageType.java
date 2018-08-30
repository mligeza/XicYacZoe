package common;

public class MessageType
{
	public static final int EMPTY=0;
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static final int LOGOUT = 3;
	public static final int MESSAGE = 4;
	public static final int STATISTICS = 5;
	public static final int PRIVATE =6; //Private message in game
	public static final int MOVE = 7; //
	public static final int WILL=8; //player wants to play online
	public static final int END=9; //end - logout?
	public static final int YES=10; //server accepted players request for game
}
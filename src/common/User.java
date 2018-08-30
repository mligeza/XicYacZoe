package common;

public class User {
	String username;
	String password;
public User(String username2, String password2) {
		username=username2;
		password=password2;
	}
	public boolean isSameUser(String user)
	{
		if(username==user)return true;
		else return false;
	}
}

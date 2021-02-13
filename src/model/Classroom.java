package model;

import java.util.ArrayList;

public class Classroom {
	private ArrayList<UserAccount> users;
	public Classroom() {
		users = new ArrayList<UserAccount>();
	}
	public void addUser(UserAccount user) {
		users.add(user);
	}
	public boolean authenticate(String username, String password) {
		boolean grantAccess = false;
		for (int i = 0; i < users.size() && !grantAccess; i++) {
			if (users.get(i).getUsername().equalsIgnoreCase(username) && users.get(i).getPassword().equals(password))
				grantAccess = true;
		}
		return grantAccess;
	}
	public int getUser(String username) {
		int index = users.size();
		boolean found = false;
		for (int i = 0; i < users.size() && !found; i++) {
			if (users.get(i).getUsername().equalsIgnoreCase(username)) {
				index = i;
				found = true;
			}
		}
		return index;
	}
	public ArrayList<UserAccount> getUsers(){
		return users;
	}
}
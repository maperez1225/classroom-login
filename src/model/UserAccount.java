package model;

import javafx.scene.image.Image;

public class UserAccount {
	private String username;
	private String password;
	private String gender;
	private String career;
	private String birthday;
	private String favBrowser;
	private Image profilePicture;
	public UserAccount(String username, String password, String gender, String career, String birthday, String favBrowser, Image profilePicture) {
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.career = career;
		this.birthday = birthday;
		this.favBrowser = favBrowser;
		this.profilePicture = profilePicture;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getGender() {
		return gender;
	}
	public String getCareer() {
		return career;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getFavBrowser() {
		return favBrowser;
	}
	public Image getProfilePic() {
		return profilePicture;
	}
}

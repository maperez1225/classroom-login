package ui;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Classroom;
import model.UserAccount;

public class ClassroomGUI {
	private Classroom classroom;
	private Image imgFile;
	private ToggleGroup genderToggle;
	public ClassroomGUI(Classroom classroom) {
		this.classroom = classroom;
	}
	
	@FXML
    private BorderPane mainPane;
	
	@FXML
    private Pane loginPane;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField txtPswd;
	
	@FXML
	private TextField imagePath;
	
	@FXML
	private TextField txtCreateUser;
	
	@FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbOther;
    
    @FXML
    private CheckBox checkSWE;

    @FXML
    private CheckBox checkTELE;

    @FXML
    private CheckBox checkINDE;
    
    @FXML
    private ChoiceBox<String> choiceBrowser;

	@FXML
	private DatePicker birthday;
	
	@FXML
	private PasswordField txtCreatePassword;
	
	@FXML
	private Label userLabel;
	
	@FXML
	private ImageView userImage;
	
	@FXML
	private TableView<UserAccount> tableUsers;
	
	@FXML
	private TableColumn<UserAccount, String> columnUsername;
	
	@FXML
	private TableColumn<UserAccount, String> columnGender;
	
	@FXML
	private TableColumn<UserAccount, String> columnCareer;
	
	@FXML
	private TableColumn<UserAccount, String> columnBirthday;
	
	@FXML
	private TableColumn<UserAccount, String> columnBrowser;
	
	@FXML
	public void launchLogIn(ActionEvent event) throws IOException{
		if (classroom.authenticate(txtUserName.getText(), txtPswd.getText())) {
			int index = classroom.getUser(txtUserName.getText());
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-view.fxml"));
			fxmlLoader.setController(this);
			Parent userView = fxmlLoader.load();
			mainPane.getChildren().clear();
	    	mainPane.setCenter(userView);
	    	userLabel.setText(classroom.getUsers().get(index).getUsername());
	    	userImage.setImage(classroom.getUsers().get(index).getProfilePic());
	    	initializeTableView();
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Authentication Error");
			alert.setHeaderText(null);
			alert.setContentText("The username and password combination is incorrect.");
			alert.show();
			txtUserName.clear();
			txtPswd.clear();
		}
	}
	
	@FXML
	public void launchSignUp(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-account.fxml"));
		fxmlLoader.setController(this);
		Parent userView = fxmlLoader.load();
		mainPane.getChildren().clear();
    	mainPane.setCenter(userView);
    	ObservableList<String> browsersList = FXCollections.observableArrayList("Chrome","Edge","Safari","Opera","Firefox","Other");
    	genderToggle = new ToggleGroup();
		rbMale.setToggleGroup(genderToggle);
        rbFemale.setToggleGroup(genderToggle);
        rbOther.setToggleGroup(genderToggle);
        choiceBrowser.setItems(browsersList);
	}
	
	@FXML
	public void launchImageChooser(ActionEvent event) {
		imgFile = null;
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
        	imgFile = new Image("file:" + file.getAbsolutePath());
        	imagePath.setText(file.getAbsolutePath());
        }
	}
	
	@FXML
	public void returnToLogin(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
		fxmlLoader.setController(this);
		Parent userView = fxmlLoader.load();
		mainPane.getChildren().clear();
    	mainPane.setCenter(userView);
	}
	
	@FXML
	public void signUp(ActionEvent event) {
		int careerCount = 0;
		String career = "";
		if (checkSWE.isSelected()) {
			careerCount++;
			career = checkSWE.getText();
		}
		if (checkTELE.isSelected()) {
			careerCount++;
			career = checkTELE.getText();
		}
		if (checkINDE.isSelected()) {
			careerCount++;
			career = checkINDE.getText();
		}
		if (careerCount > 1)
			career = "Multiple";
		if (careerCount == 0 || genderToggle.getSelectedToggle() == null || imgFile == null || birthday.getValue() == null || choiceBrowser.getValue() == null || txtCreateUser.getText() == null || txtCreatePassword.getText() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unable to create user");
			alert.setHeaderText(null);
			alert.setContentText("Please verify that you are not missing any information.");
			alert.show();
		}
		else {
			UserAccount user = new UserAccount(txtCreateUser.getText(), txtCreatePassword.getText(), ((RadioButton) genderToggle.getSelectedToggle()).getText(), career, birthday.getValue().toString(), choiceBrowser.getValue(), imgFile);
			classroom.addUser(user);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("User created successfully.");
			alert.show();
		}
	}
	
	private void initializeTableView() {
    	ObservableList<UserAccount> observableList;
    	observableList = FXCollections.observableArrayList(classroom.getUsers());
    	tableUsers.setItems(observableList);
		columnUsername.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("username"));
		columnGender.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("gender"));
		columnCareer.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("career"));
		columnBirthday.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("birthday"));
		columnBrowser.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("favBrowser"));
    }
}

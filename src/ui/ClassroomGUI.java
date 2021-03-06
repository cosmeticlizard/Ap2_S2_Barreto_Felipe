package ui;
import java.io.File;
import java.io.IOException;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Career;
import model.Classroom;
import model.Gender;
import model.UserAccount;
public class ClassroomGUI {

	private Classroom classroom;

	public ClassroomGUI(Classroom cr) {
		classroom= cr;
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Load data");
		alert.setHeaderText("The new account has been created");
		alert.showAndWait();
		
		try {
			classroom.loadData();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			alert.setContentText("The file does not exist");
			alert.showAndWait();
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			alert.setContentText("The file is damaged");
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	
	//register
	@FXML
	private FileChooser fileChooser;

	//mainPane
	@FXML
	private Pane mainPane;

	//register
	@FXML
	private DatePicker datePicker = new DatePicker();	

	//login
	@FXML
	private TextField txtUserName;

	//login
	@FXML
	private TextField txtPassword;

	//login
	@FXML
	private Button btnSingIn;

	//login
	@FXML
	private Button btnLogIn;

	//register
	@FXML
	private TextField txtNewUserName;

	//register
	@FXML
	private TextField txtNewPassword;

	//register
	@FXML
	private Label lblFileDirector;

	//register
	@FXML
	private RadioButton rbtnFemale;

	//register
	@FXML
	private RadioButton rbtnMale;

	//register
	@FXML
	private RadioButton rbtnOther;

	//register
	@FXML
	private RadioButton rbtnSoftware;

	//register
	@FXML
	private ToggleGroup genderG;

	//register
	@FXML
	private ToggleGroup careerG;

	//register
	@FXML
	private RadioButton rbtnTelematic;

	//register
	@FXML
	private RadioButton rbtnIdustrial;

	//register
	@FXML
	private  ComboBox<String> cboxFavBrowser;

	//register
	@FXML
	private Button btnBrowse;

	//register
	@FXML
	private File file;

	//account-list
	@FXML
	private ImageView IVprofileImage;

	//account-list
	@FXML
	private Label txtviewUsername;

	//account-list
	@FXML
	private TableView<UserAccount> tvUserAccountList;

	//account-list
	@FXML
	private TableColumn<UserAccount, String> usernameColumn;
	//account-list
	@FXML
	private TableColumn<UserAccount, Gender> genderColumn;
	//account-list
	@FXML
	private TableColumn<UserAccount, Career> careerColumn;
	//account-list
	@FXML
	private TableColumn<UserAccount, LocalDate> birthdayColumn;
	//account-list
	@FXML
	private TableColumn<UserAccount, String> browserColumn;



	//logIn
	@FXML
	public void loadLogin()throws IOException {

		FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("login.fxml"));
		fxmlLoader.setController(this);
		Parent login = fxmlLoader.load();
		mainPane.getChildren().setAll(login);

	}

	//goes from login to register
	@FXML
	public void register(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("register.fxml"));
		fxmlLoader.setController(this);
		Parent register = fxmlLoader.load();
		mainPane.getChildren().setAll(register);
		cboxFavBrowser.getItems().addAll("FireFox","Chrome","Edge","Safari","Opera","Thor","Brave");

	}

	//register
	@FXML
	public void fileBrowse(ActionEvent event) {

		fileChooser= new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("NEF", "*.nef") );
		Stage stage=new Stage();
		file= fileChooser.showOpenDialog(stage);
		lblFileDirector.setText("Nombre "+file.getPath());

	}

	//Register
	@FXML
	public void creeateAccount(ActionEvent event) {
		String genderS="";
		String careerS="";
		if(rbtnFemale.isSelected()) {
			genderS= "FEMALE";
		}else if(rbtnMale.isSelected()) {
			genderS= "MALE";
		}else if(rbtnOther.isSelected()) {
			genderS= "OTHER";
		}



		if(rbtnSoftware.isSelected()) {
			careerS="SOFTWARE_ENGINEERING";	
		}else if(rbtnTelematic.isSelected()) {
			careerS="TELEMATIC_ENGINEERING";
		}else if(rbtnIdustrial.isSelected()) {
			careerS="INDUSTRIAL_ENGINEERING";
		}


		try {
			try {
				classroom.createUser(txtNewUserName.getText(),txtNewPassword.getText(),file,datePicker.getValue(),genderS,careerS,cboxFavBrowser.getSelectionModel().getSelectedItem().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Account created");
			alert.setHeaderText("The new account has been created");
			alert.showAndWait();
		}catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Validation Error");
			alert.setHeaderText("You must fill each field in the form");
			alert.showAndWait();
		}

	}

	//register to logIn
	@FXML
	public void returnToLogIn(ActionEvent event)throws IOException {
		loadLogin();
	}



	//main-pane
	@FXML
	public void SingIn(ActionEvent event) throws IOException{
	

		if(classroom.signIn(txtUserName.getText().toString(),txtPassword.getText().toString() )) {
			loadAccountlist(classroom.signInC(txtUserName.getText().toString() ,txtPassword.getText().toString() ));
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("log in incorrect");
			alert.setHeaderText("The username and password given are incorrect");
			alert.showAndWait();
		}

	}


	//register
	@FXML
	public void showDate(ActionEvent event) {
		datePicker.setEditable(true);
	}

	//LogIn to account-List
	@FXML
	public void loadAccountlist(int c)throws IOException {
		FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("account-list.fxml"));
		fxmlLoader.setController(this);
		Parent loadAccountlist = fxmlLoader.load();
		mainPane.getChildren().setAll(loadAccountlist);
		txtviewUsername.setText(classroom.getUser(c).getUsername());
		Image image= new Image(classroom.getUser(c).getPhoto().toURI().toString());
		IVprofileImage.setImage(image);
		loadTableView();


	}

	//account-list
	public void loadTableView() {
		ObservableList<UserAccount> observableList;
		observableList = FXCollections.observableArrayList(classroom.getUsers());

		tvUserAccountList.setItems(observableList);
		usernameColumn.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("username")); 
		genderColumn.setCellValueFactory(new PropertyValueFactory<UserAccount,Gender>("gender")); 
		careerColumn.setCellValueFactory(new PropertyValueFactory<UserAccount,Career>("career"));
		birthdayColumn.setCellValueFactory(new PropertyValueFactory<UserAccount,LocalDate>("date"));
		browserColumn.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("browser"));


	}

	//account-list to logIN
	@FXML
	void LogOut(ActionEvent event) throws IOException {
		loadLogin();
	}

}

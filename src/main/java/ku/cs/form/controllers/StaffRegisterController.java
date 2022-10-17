package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ku.cs.form.models.Agency;
import ku.cs.form.models.AgencyList;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.services.AgencyDataSource;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.StaffRegistration;
import javafx.scene.control.Alert.AlertType;
import ku.cs.form.services.UploadPicture;

import java.io.*;
import java.util.ArrayList;

public class StaffRegisterController extends UploadPicture {
    @FXML private TextField nameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> agencyComboBox;
    @FXML
    private ImageView regis_pic;
    @FXML private AnchorPane staffRegisteranchorPane;

    @FXML
    private ImageView profile_pic;

    private AgencyDataSource agenciesDataSource;
    private AgencyList agencyList;

    @FXML public void initialize() {
        User admin = (User) com.github.saacsos.FXRouter.getData();
        staffRegisteranchorPane.getStylesheets().add("file:src/main/resources/ku/cs/styles/styles.css");
        SetTheme setTheme = new SetTheme(admin.getUsername());
        setTheme.setting();

        String url = getClass().getResource("/ku/cs/images/register_pic.png").toExternalForm();
        regis_pic.setImage(new Image(url));
        agenciesDataSource = new AgencyDataSource("data","agency.csv");
        agencyList = agenciesDataSource.readData();
        for(Agency agency : agencyList.getAgencies())
            agencyComboBox.getItems().add(agency.getName());
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {

        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String agency = agencyComboBox.getValue();

        StaffRegistration reg = new StaffRegistration("data","users.csv");
        String error = reg.registrationCheck(name,username,password,confirmPassword);
        if(agency == null) {
            error += "โปรดเลือกหน่วยงาน!\n";
        }

        if(error.isBlank()){
            Staff newStaff = new Staff(name, username, password,agency);
            reg.addStaff(newStaff);
            if(profile_pic.getImage() != null)
                addPic(username,profile_pic);

            showPopUp("Registration successful!","Hello Welcome!",null);

            try {
                com.github.saacsos.FXRouter.goTo("admin");
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        } else{
            showPopUp(error,"ERROR",null);
        }
    }

    public static void showPopUp(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert;
        if(titleBar != null && titleBar.equals("ERROR")) alert = new Alert(AlertType.ERROR);
        else alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    @FXML
    private void handleUploadPicBtn(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            profile_pic.setImage(new Image(file.getAbsolutePath()));
        }
    }



}

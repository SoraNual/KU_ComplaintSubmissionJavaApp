package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserDataSource;

import javax.imageio.IIOException;
import javax.swing.*;
import java.io.IOException;

public class NisitChangePasswordController {
    @FXML private Label incorrectWarningLabel;
    @FXML private Label passwordLabel;
    @FXML private Label newPasswordLabel;
    @FXML private Label confirmNewPasswordLabel;
    @FXML private Label usernameLabel;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField newPasswordPasswordField;
    @FXML private PasswordField confirmNewPasswordPasswordField;
    @FXML private PasswordField PasswordPasswordField;
    @FXML private AnchorPane pane;
    @FXML private Rectangle rightRec;
    @FXML private Rectangle leftRec;
    @FXML private Button confirmButton;
    @FXML private Button backButton;
    private User user;
    private UserList userList;
    private UserDataSource userDataSource;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
    }

    public void handleConfirmButton(ActionEvent actionEvent){
        if(user.getUsername().equals(usernameTextField.getText()) && user.getPassword().equals(PasswordPasswordField.getText())){
            if(newPasswordPasswordField.getText().trim().equals("")){
                incorrectWarningLabel.setText("NOT BLANK PASSWORD BRO");
            } else if (newPasswordPasswordField.getText().equals(confirmNewPasswordPasswordField.getText())) {
                user.setPassword(newPasswordPasswordField.getText());
                userDataSource.changeData(user);
                try{
                    com.github.saacsos.FXRouter.goTo("nisitPage",user);
                } catch(Exception e){
                    System.out.println("BUGGGGGGGGGGGGGGGGGGGG!");
                }
            } else{
                incorrectWarningLabel.setText("Theres a problem with new password STUPID :<");
            }
        }
        else{
            incorrectWarningLabel.setText("Something wrong with your password or username :P");
        }
    }
    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage",user);
        } catch (Exception e){
            System.out.println("Look like theres bug here");
        }
    }
}

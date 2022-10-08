package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ku.cs.form.models.*;
import ku.cs.form.services.LoginTimeFileDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.*;

public class LoginPageController {
    @FXML private ImageView img;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label incorrectWarningText;
    @FXML private Button logInButton;
    private UserDataSource unclarifiedUser;
    private User user;
    private TranslateTransition translate;
    @FXML public void initialize() {
        String url = getClass().getResource("/ku/cs/images/catMeow.png").toExternalForm();
        unclarifiedUser = new UserDataSource("data","users.csv");
        img.setImage(new Image(url));
        translate = new TranslateTransition();
        translate.setNode(logInButton);
        runningButton();
        handleField();
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Home ได้");
        }
    }

    public void check(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        user = unclarifiedUser.usernamePasswordCheck(username,password);
        if(user instanceof User){
            if(logInButton.getTranslateX() == -275){
                translate.setByX(275);
            } translate.play();
        }
    }
    public void handleField(){
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                check();
            }
        });
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                check();
            }
        });
    }
    @FXML
    public void runningButton(){
        logInButton.hoverProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                user = unclarifiedUser.usernamePasswordCheck(username,password);
                if(!(user instanceof User)){
                    incorrectWarningText.setText("Incorrect username or password");
                    if(logInButton.getTranslateX() == 0) {
                        translate.setByX(-275);
                    } else if(logInButton.getTranslateX() == -275){
                        translate.setByX(275);
                    } translate.play();
                }
            }
                else {
                    logInButton.setCursor(Cursor.HAND);
            }
        });
    }

    @FXML public void handleLoginButton(ActionEvent actionEvent) {
        try {
            if (user instanceof Admin) {
                addLoginTime();
                FXRouter.goTo("admin",user);
            } else if (user instanceof Staff) {
                addLoginTime();
                FXRouter.goTo("newStaff",user);
            } else if (user instanceof Nisit) {
                addLoginTime();
                FXRouter.goTo("nisitPage",user);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void addLoginTime(){
        LoginTimeFileDataSource loginTimeFileDataSource = new LoginTimeFileDataSource("data","loginTime.csv");
        UserList users = loginTimeFileDataSource.readData();
        users.addUser(user);
        loginTimeFileDataSource.writeData(users);
    }
}

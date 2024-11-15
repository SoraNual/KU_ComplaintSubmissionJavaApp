package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.*;
import ku.cs.form.services.UserDataSource;

import java.io.*;
import java.time.LocalDateTime;

public class LoginPageController {
    @FXML private ImageView img;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label incorrectWarningText;
    private User user;
    private UserDataSource userDataSource;
    private UserList userList;
    @FXML public void initialize() {
        String url = getClass().getResource("/ku/cs/images/catMeow.png").toExternalForm();
        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
        img.setImage(new Image(url));
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Home ได้");
        }
    }

    private void setLoginTime(User user) {
        for(User u : userList.getAllUsers()){
            if(u.getUsername().equals(user.getUsername())){
                u.setLoginTime(LocalDateTime.now().format(u.getFormat()));
                userDataSource.writeData(userList);
                break;
            }
        }
    }

    @FXML public void handleLoginButton(ActionEvent actionEvent) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        UserDataSource unclarifiedUser = new UserDataSource("data","users.csv");
        user = unclarifiedUser.usernamePasswordCheck(username,password);
        try {
            if(user != null){
                if (user instanceof Nisit) {
                    if (((Nisit) user).getUserStatus().equals("banned")) {
                        FXRouter.goTo("banned", user);
                    } else {
                        setLoginTime(user);
                        FXRouter.goTo("nisitPage", user);
                    }
                } else if (user.getRole().equals("admin")) {
                    setLoginTime(user);
                    FXRouter.goTo("admin",user);
                } else if (user instanceof Staff) {
                    setLoginTime(user);
                    FXRouter.goTo("newStaff",user);
                }
            } else{
                incorrectWarningText.setText("รหัสผ่านหรือ Username ผิด");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}

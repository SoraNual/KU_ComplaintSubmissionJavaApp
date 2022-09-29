package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.LoginTimeFileDataSource;

import java.io.IOException;
import java.util.*;

public class LoginTimePageController {

    @FXML private Label usernameLabel;
    @FXML private Label loginTimeLabel;
    @FXML private Label agencyLabel;

    @FXML private ImageView profileImage;
    private UserList loginTimeUserList;

    @FXML private ListView<String> usersLoginListView;
    private LoginTimeFileDataSource dataSource;
    private UserList userList;

    @FXML
    public void initialize() {
        dataSource = new LoginTimeFileDataSource("data","loginTime.csv");
        userList = dataSource.readData();
        loginTimeUserList = new UserList();
        addLoginTimeUserList();
        showUsersLoginListView();
        clearSelectedUser();
        handleSelectedListView();
    }
    private void addLoginTimeUserList() {
        for(int i = userList.getAllUsers().size()-1;i > 0;--i){
            boolean isInUserList = false;
            User user_login = userList.getAllUsers().get(i);
            for(User user : loginTimeUserList.getAllUsers()){
                if (user.getUsername().equals(user_login.getUsername())) {
                    isInUserList = true;
                    break;
                }
            }
            if(!isInUserList) loginTimeUserList.addUser(user_login);
        }

    }

    private void showUsersLoginListView() {
        usersLoginListView.getItems().clear();
        for(User user : loginTimeUserList.getAllUsers()){
            String user_data = user.getLoginTime()
                    + "\nUsername: " + user.getUsername();
            if(user instanceof Staff)
                user_data += "\nAgency: " + ((Staff) user).getAgency();
            usersLoginListView.getItems().add(user_data);
        }
        usersLoginListView.refresh();
    }

    private void handleSelectedListView() {

        usersLoginListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observable,
                                        String oldValue, String newValue) {
                        showSelectedUser(newValue);
                    }
                });
    }

    private User changeStringToUser(String user_data) {
        String[] user_data_array = user_data.split("\n");
        String login_time = user_data_array[0];
        String username = user_data_array[1].split(" ")[1];
        String agency = null;
        if(user_data_array.length == 4){
            agency = user_data_array[3].split(" ")[1];
            Staff staff = new Staff(null,username,null,agency);
            staff.setLoginTime(login_time);
            return staff;
        }
        User user = new User(null,username,null);
        user.setLoginTime(login_time);
        return user;
    }

    private void showSelectedUser(String user_data) {
        User user = changeStringToUser(user_data);
        usernameLabel.setText(user.getUsername());
        loginTimeLabel.setText(user.toStringLoginTime());
        profileImage.setImage(new Image("file:"+user.getProfileImageFilePath()));
        if(user.getClass() == Staff.class)
            agencyLabel.setText("หน่วยงาน : " + ((Staff) user).getAgency());
        else
            agencyLabel.setText("");
    }
    private void clearSelectedUser() {
        usernameLabel.setText("");
        loginTimeLabel.setText("");
        profileImage.setImage(null);
    }
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



}
package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.Admin;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.LoginTimeFileDataSource;

import java.io.File;

public class LoginTimePageController {


    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private Label loginTimeLabel;
    @FXML private Label agencyLabel;

    @FXML private ImageView profileImage;

    @FXML private ListView<User> usersLoginListView;
    private LoginTimeFileDataSource dataSource;
    private UserList usersList;

    @FXML
    public void initialize() {
        dataSource = new LoginTimeFileDataSource("data","loginTime.csv");
        usersList = dataSource.readData();
        showUsersLoginListView();
        clearSelectedUser();
        handleSelectedListView();
    }

    private void showUsersLoginListView() {
        usersLoginListView.getItems().addAll(usersList.getAllUsers());
        usersLoginListView.refresh();
    }

    private void handleSelectedListView() {

        usersLoginListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User>
                                                observable,
                                        User oldValue, User newValue) {
                        showSelectedMemberCard(newValue);
                    }
                });
    }
    private void showSelectedMemberCard(User user) {
        nameLabel.setText(user.getName());
        usernameLabel.setText(user.getUsername());
        loginTimeLabel.setText(user.toStringLoginTime());
        profileImage.setImage(new Image("file:"+user.getProfileImageFilePath()));
        if(user.getClass() == Staff.class)
            agencyLabel.setText("หน่วยงาน : " + ((Staff) user).getAgency());
        else
            agencyLabel.setText("");
    }
    private void clearSelectedUser() {
        nameLabel.setText("");
        usernameLabel.setText("");
        loginTimeLabel.setText("");
        profileImage.setImage(null);
    }
}
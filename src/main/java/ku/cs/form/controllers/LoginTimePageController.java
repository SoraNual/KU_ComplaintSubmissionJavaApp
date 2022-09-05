package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.form.models.Admin;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.LoginTimeFileDataSource;

public class LoginTimePageController {


    @FXML private Label usernameLabel;

    @FXML private ListView<User> usersLoginListView;
    private LoginTimeFileDataSource dataSource;
    private UserList usersList;
    private Admin admin;

    @FXML
    public void initialize() {
        admin = new Admin("admin", "adminInwZa007", "123456");
        usernameLabel.setText(admin.getUsername());

        dataSource = new LoginTimeFileDataSource("data","loginTime.csv");
        usersList = dataSource.readData();
        usersList.sortByLoginTime();
        showUsersLoginListView();
    }

    private void showUsersLoginListView() {
        usersLoginListView.getItems().addAll(usersList.getAllUsers());
        usersLoginListView.refresh();
    }

}
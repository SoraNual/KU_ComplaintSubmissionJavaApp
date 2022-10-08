package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.LoginTimeFileDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.*;

public class LoginTimePageController {

    @FXML private ListView<HBox> userListView;
    @FXML private UserDataSource userDataSource;
    @FXML private UserList userList;

    @FXML
    public void initialize() {
        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
        sortUserListByLoginTime();
        showListView();
    }

    private void sortUserListByLoginTime() {
        userList.getAllUsers().sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                String s1 = o1.getLoginTime();
                String s2 = o2.getLoginTime();
                if(s1.equals("ยังไม่มีการใช้งาน")) return 1;
                if(s2.equals("ยังไม่มีการใช้งาน")) return -1;
                return -(s1.compareTo(s2));
            }
        });
    }

    private void showListView() {
        userListView.getItems().clear();
        for(User user : userList.getAllUsers()){
            HBox user_box = new HBox();
            user_box.setSpacing(50);
            user_box.setPadding(new Insets(10, 10, 10, 10));

            ImageView profile = new ImageView("file:" + user.getProfileImageFilePath());
            profile.setFitHeight(100);
            profile.setFitWidth(100);

            Label user_detail = new Label();
            String user_detail_str = "[" + user.getLoginTime() + "]\n";
            user_detail_str += "Username : " + user.getUsername() + "\n" +
                    "Name : " + user.getName() + "\n";
            if(user instanceof Staff) user_detail_str += "Agency : " + ((Staff) user).getAgency();
            user_detail.setText(user_detail_str);

            user_box.getChildren().add(profile);
            user_box.getChildren().add(user_detail);

            userListView.getItems().add(user_box);
        }
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
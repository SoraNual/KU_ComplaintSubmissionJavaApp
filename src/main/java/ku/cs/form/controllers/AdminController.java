package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class AdminController {

    @FXML private ListView<HBox> userListView;
    @FXML private UserDataSource userDataSource;
    @FXML private UserList userList;
    @FXML private AnchorPane anchorPane;
    @FXML private Rectangle profilePic;

    @FXML private Label adminGreetingLabel;
    private User admin;

    @FXML
    public void initialize() {
        admin = (User) com.github.saacsos.FXRouter.getData();
        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

        sortUserListByLoginTime();
        showListView();
        showAdminProfile();
    }

    private void sortUserListByLoginTime() {
        userList.getAllUsers().sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(o1.getLoginTime().equals("ยังไม่มีการใช้งาน")) return 1;
                if(o2.getLoginTime().equals("ยังไม่มีการใช้งาน")) return -1;
                LocalDateTime l1 = LocalDateTime.parse(o1.getLoginTime(),o1.getFormat());
                LocalDateTime l2 = LocalDateTime.parse(o2.getLoginTime(),o2.getFormat());
                return -(l1.compareTo(l2));
            }
        });
    }

    private void showListView() {
        userListView.getItems().clear();
        for(User user : userList.getAllUsers()){
            HBox user_box = new HBox();
            user_box.setSpacing(50);
            user_box.setPadding(new Insets(10, 10, 10, 10));

            Circle profile = new Circle();
            profile.setRadius(50);
            Image profile_pic = new Image("file:" + user.getProfileImageFilePath());
            profile.setFill(new ImagePattern(profile_pic));


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

    public void handleStaffRegisterMenu(ActionEvent actionEvent) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("staffRegister",admin);
        } catch (IOException e) {
            throw new IOException(e);
        }

    }

    public void showAdminProfile() {
        Image profile = new Image("file:" + admin.getProfileImageFilePath());
        profilePic.setFill(new ImagePattern(profile));
    }

    @FXML
    private void handleManageAgencyBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("manageAgency");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleUserComplaintBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("userComplaint",admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void handleThemeBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("editProfile",admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
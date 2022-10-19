package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.*;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserReportDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.HashMap;

public class ReportUserController {

    @FXML private ListView<String> bannedUsersListView;
    @FXML private ListView<String> inappropriateListView;
    @FXML private ListView<String> requestUnbannedListView;
    @FXML private AnchorPane userReportAnchorPane;
    private HashMap<String, User> userHashMap;
    private UserReportDataSource userReportDataSource;
    private HashMap<String, UserReport> userComplaintHashMap;
    private UserDataSource userDataSource;
    private UserList usersList;

    @FXML
    public void initialize() {
        User user = (User) com.github.saacsos.FXRouter.getData();
        userReportAnchorPane.getStylesheets().add("file:src/main/resources/ku/cs/styles/styles.css");
        SetTheme setTheme = new SetTheme(user.getUsername());
        setTheme.setting();
        userDataSource = new UserDataSource("data","users.csv");
        userReportDataSource = new UserReportDataSource("data","user_reports.csv");
        usersList = userDataSource.readData();
        userComplaintHashMap = userReportDataSource.readData();
        userHashMap = new HashMap<>();
        setUserHashMap();
        showBannedUsersListView();
        showInappropriateListView();
        showRequestUnbannedListView();
    }

    private void showInappropriateListView() {
        inappropriateListView.getItems().clear();
        for(User user : usersList.getAllUsers()) {
            if (userComplaintHashMap.containsKey(user.getUsername()) && ((Nisit) user).getUserStatus().equals("active")) {
                UserReport userReport = userComplaintHashMap.get(user.getUsername());
                String banned_user_data = "Username: " + user.getUsername() + "\n"
                        + "Name: " + user.getName() + "\n"
                        + "Type: " + user.getClass().getSimpleName();
                if (user.getClass().getSimpleName().equals("Staff"))
                    banned_user_data += "\nAgency: " + ((Staff) user).getAgency();
                banned_user_data += "\nประเภทความไม่เหมาะสม: " + userReport.getComplaint_category() + "\n";
                banned_user_data += "รายละเอียด: " + userReport.getComplaint_detail();
                inappropriateListView.getItems().add(banned_user_data);
            }
        }
        inappropriateListView.refresh();
    }

    private void setUserHashMap() {
        for(User user : usersList.getAllUsers())
            userHashMap.put(user.getUsername(),user);
    }

    private void showRequestUnbannedListView() {
        requestUnbannedListView.getItems().clear();
        for(User user : usersList.getAllUsers()) {
            if (user instanceof Nisit && userComplaintHashMap.containsKey(user.getUsername()) && ((Nisit) user).getUserStatus().equals("banned")) {
                UserReport userReport = userComplaintHashMap.get(user.getUsername());
                if(userReport.getRequest_permission_detail() != null){
                    String request_user_data = "Username: " + user.getUsername()
                            + "\nName: " + user.getName() + "\n"
                            + "\nType: " + user.getClass().getSimpleName();
                    if (user.getClass().getSimpleName().equals("Staff"))
                        request_user_data += "\nAgency: " + ((Staff) user).getAgency();
                    request_user_data += "\nประเภทความไม่เหมาะสม: " + userReport.getComplaint_category();
                    request_user_data += "\nรายละเอียด: " + userReport.getComplaint_detail();
                    request_user_data += "\nเหตุผลการขอคืนสิทธิ์: " + userReport.getRequest_permission_detail();
                    requestUnbannedListView.getItems().add(request_user_data);
                }
            }
        }
        requestUnbannedListView.refresh();
    }



    private void showBannedUsersListView() {
        bannedUsersListView.getItems().clear();
        for(User user : usersList.getAllUsers()){
            if(user instanceof Nisit && ((Nisit) user).getUserStatus().equals("banned") && ((Nisit) user).getUserStatus().equals("banned")){
                String banned_user_data = "Username: " + user.getUsername() + "\n"
                        + "Name: " + user.getName() + "\n"
                        + "LoginAttempt: " + ((Nisit) user).getLoginAttempt() + "\n"
                        + "Type: " + user.getClass().getSimpleName();
                if(user.getClass().getSimpleName().equals("Staff"))
                    banned_user_data += "\nAgency: " + ((Staff) user).getAgency();

                bannedUsersListView.getItems().add(banned_user_data);
            }
        }
        bannedUsersListView.refresh();
    }

    @FXML
    private void handleUnbannedBtn(ActionEvent actionEvent) {
        String[] selected_data_array = requestUnbannedListView.getSelectionModel().getSelectedItem().split("\n");
        String banned_username = selected_data_array[0].split(" ")[1];
        User target = userHashMap.get(banned_username);
        usersList.getAllUsers().remove(target);
        ((Nisit) target).setUserStatus("active");
        usersList.addUser(target);
        showBannedUsersListView();
        userComplaintHashMap.remove(banned_username);
        showRequestUnbannedListView();
        userDataSource.writeData(usersList);
        userReportDataSource.writeData(userComplaintHashMap);
    }

    @FXML
    private void handleBannedBtn(ActionEvent actionEvent) {
        String[] selected_data_array = inappropriateListView.getSelectionModel().getSelectedItem().split("\n");
        String banned_username = selected_data_array[0].split(" ")[1];
        User target = userHashMap.get(banned_username);
        usersList.getAllUsers().remove(target);
        ((Nisit) target).setUserStatus("banned");
        usersList.addUser(target);
        showInappropriateListView();
        userDataSource.writeData(usersList);
    }


    @FXML
    private void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

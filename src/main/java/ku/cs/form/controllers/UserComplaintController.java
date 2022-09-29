package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.models.UserComplaint;
import ku.cs.form.models.UserList;
import ku.cs.form.services.LoginTimeFileDataSource;
import ku.cs.form.services.UserComplaintDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class UserComplaintController {

    @FXML private ListView<String> bannedUsersListView;
    @FXML private ListView<String> inappropriateListView;
    @FXML private ListView<String> requestUnbannedListView;
    private HashMap<String, User> userHashMap;
    private UserComplaintDataSource userComplaintDataSource;
    private HashMap<String, UserComplaint> userComplaintHashMap;
    private UserDataSource userDataSource;
    private UserList usersList;

    @FXML
    public void initialize() {
        userDataSource = new UserDataSource("data","users.csv");
        userComplaintDataSource = new UserComplaintDataSource("data","user_complaint.csv");
        usersList = userDataSource.readData();
        userComplaintHashMap = userComplaintDataSource.readData();
        userHashMap = new HashMap<>();
        setUserHashMap();
        showBannedUsersListView();
        showInappropriateListView();
        showRequestUnbannedListView();
    }

    private void showInappropriateListView() {
        inappropriateListView.getItems().clear();
        for(User user : usersList.getAllUsers()) {
            if (userComplaintHashMap.containsKey(user.getUsername()) && user.getUserStatus().equals("active")) {
                UserComplaint userComplaint = userComplaintHashMap.get(user.getUsername());
                String banned_user_data = "Username: " + user.getUsername() + "\n"
                        + "Name: " + user.getName() + "\n"
                        + "Type: " + user.getClass().getSimpleName();
                if (user.getClass().getSimpleName().equals("Staff"))
                    banned_user_data += "\nAgency: " + ((Staff) user).getAgency();
                banned_user_data += "\nประเภทความไม่เหมาะสม: " + userComplaint.getComplaint_category() + "\n";
                banned_user_data += "รายละเอียด: " + userComplaint.getComplaint_detail();
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
            if (userComplaintHashMap.containsKey(user.getUsername()) && user.getUserStatus().equals("banned")) {
                UserComplaint userComplaint = userComplaintHashMap.get(user.getUsername());
                if(userComplaint.getRequest_permission_detail() != null){
                    String request_user_data = "Username: " + user.getUsername()
                            + "\nName: " + user.getName() + "\n"
                            + "\nType: " + user.getClass().getSimpleName();
                    if (user.getClass().getSimpleName().equals("Staff"))
                        request_user_data += "\nAgency: " + ((Staff) user).getAgency();
                    request_user_data += "\nประเภทความไม่เหมาะสม: " + userComplaint.getComplaint_category();
                    request_user_data += "\nรายละเอียด: " + userComplaint.getComplaint_detail();
                    request_user_data += "\nเหตุผลการขอคืนสิทธิ์: " + userComplaint.getRequest_permission_detail();
                    requestUnbannedListView.getItems().add(request_user_data);
                }
            }
        }
        requestUnbannedListView.refresh();
    }



    private void showBannedUsersListView() {
        bannedUsersListView.getItems().clear();
        for(User user : usersList.getAllUsers()){
            if(user.getUserStatus().equals("banned") && user.getUserStatus().equals("banned")){
                String banned_user_data = "Username: " + user.getUsername() + "\n"
                        + "Name: " + user.getName() + "\n"
                        + "LoginAttempt: " + user.getLoginAttempt() + "\n"
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
        target.setUserStatus("active");
        usersList.addUser(target);
        showBannedUsersListView();
        userComplaintHashMap.remove(banned_username);
        showRequestUnbannedListView();
        userDataSource.writeData(usersList);
        userComplaintDataSource.writeData(userComplaintHashMap);
    }

    @FXML
    private void handleBannedBtn(ActionEvent actionEvent) {
        String[] selected_data_array = inappropriateListView.getSelectionModel().getSelectedItem().split("\n");
        String banned_username = selected_data_array[0].split(" ")[1];
        User target = userHashMap.get(banned_username);
        usersList.getAllUsers().remove(target);
        target.setUserStatus("banned");
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

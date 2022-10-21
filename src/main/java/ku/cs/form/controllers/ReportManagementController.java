package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.form.models.*;
import ku.cs.form.services.*;

import java.io.IOException;
import java.util.HashMap;

public class ReportManagementController {

    @FXML private ListView<HBox> bannedUsersListView;
    @FXML private ListView<String> inappropriateListView;
    @FXML private ListView<String> requestUnbannedListView;
    @FXML private ListView<String> inappropriateComplaintListView;
    @FXML private AnchorPane userReportAnchorPane;

    @FXML private TextField inappropriateNameTextField;
    @FXML private TextField inappropriateUserNameTextField;
    @FXML private TextField inappropriateCategoryTextField;
    @FXML private TextArea inappropriateDetailTextArea;

    @FXML private TextField requestUnbannedUserNameTextField;
    @FXML private TextField requestUnbannedNameTextField;
    @FXML private TextField requestUnbannedCategoryTextField;
    @FXML private TextArea requestUnbannedDetailTextArea;
    @FXML private TextArea requestUnbannedRequestPermissionTextArea;

    @FXML private TextField topicTextField;
    @FXML private TextField submitTimeTextField;
    @FXML private TextField complaintReportCategoryTextField;
    @FXML private TextArea complaintReportDetailTextArea;
    @FXML private Button bannedButton;
    @FXML private Button unbannedButton;

    private HashMap<String, User> userHashMap;
    private UserReportDataSource userReportDataSource;
    private UserReportHashMap userReportHashMap;
    private ComplaintReportHashMap complaintReportHashMap;
    private ComplaintReportDataSource complaintReportDataSource;
    private UserDataSource userDataSource;
    private UserList usersList;
    private ComplaintFileDataSource complaintFileDataSource;
    private ComplaintList complaintList;
    @FXML private Button deleteButton;
    @FXML private Button rejectButton;

    @FXML
    public void initialize() {
        //setting section
        User user = (User) com.github.saacsos.FXRouter.getData();

        SetTheme setTheme = new SetTheme(user.getUsername());
        setTheme.setting();

        userDataSource = new UserDataSource("data","users.csv");
        usersList = userDataSource.readData();
        userHashMap = new HashMap<>();
        complaintFileDataSource = new ComplaintFileDataSource("data","complaints.csv");
        complaintList = complaintFileDataSource.readData();
        setUserHashMap();

        userReportDataSource = new UserReportDataSource("data","userReport.csv");
        userReportHashMap = userReportDataSource.readData();
        complaintReportDataSource = new ComplaintReportDataSource("data","complaintReport.csv");
        complaintReportHashMap = complaintReportDataSource.readData();


        showBannedUsersListView();
        showInappropriateListView();
        showRequestUnbannedListView();
        showComplaintReportListView();

        handleSelectedInappropriateListView();
        handleRequestUnbannedListView();
        handleComplaintReportListView();
    }

    private void setUserHashMap() {
        for(User user : usersList.getAllUsers())
            userHashMap.put(user.getUsername(),user);
    }
    private void showInappropriateListView() {
        inappropriateListView.getItems().clear();
        for (String k : userReportHashMap.getAllReports().keySet()) {
            Nisit nisit = (Nisit) userHashMap.get(k);
            if (userReportHashMap.getAllReports().get(k).getRequest_permission_detail() == null && nisit.getUserStatus().equals("active")) {
                inappropriateListView.getItems().add(k + "  [" + userReportHashMap.getAllReports().get(k).getComplaintCategory() + "]");
            }
        }
        inappropriateListView.refresh();
    }

    private void handleSelectedInappropriateListView() {

        inappropriateListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observable,
                                        String oldValue, String newValue) {
                        if(newValue != null) {
                            bannedButton.setDisable(false);
                            showInappropriateDetailSection(newValue.split(" ")[0]);
                        }
                    }
                });
    }

    private void showInappropriateDetailSection(String username) {
        UserReport userReport = userReportHashMap.getAllReports().get(username);
        inappropriateUserNameTextField.setText(username);
        inappropriateNameTextField.setText(userHashMap.get(username).getName());
        inappropriateCategoryTextField.setText(userReport.getComplaintCategory());
        inappropriateDetailTextArea.setText(userReport.getComplaintDetail());
    }

    private void showRequestUnbannedListView() {
        requestUnbannedListView.getItems().clear();
        for (String k : userReportHashMap.getAllReports().keySet()) {
            if (userReportHashMap.getAllReports().get(k).getRequest_permission_detail() != null) {
                requestUnbannedListView.getItems().add(k + "  [" + userReportHashMap.getAllReports().get(k).getComplaintCategory() + "]");
            }
        }
        requestUnbannedListView.refresh();
    }

    private void showRequestUnbannedDetailSection(String username) {
        UserReport userReport = userReportHashMap.getAllReports().get(username);
        requestUnbannedUserNameTextField.setText(username);
        requestUnbannedNameTextField.setText(userHashMap.get(username).getName());
        requestUnbannedCategoryTextField.setText(userReport.getComplaintCategory());
        requestUnbannedDetailTextArea.setText(userReport.getComplaintDetail());
        requestUnbannedRequestPermissionTextArea.setText(userReport.getRequest_permission_detail());
    }

    @FXML private void handleBannedBtn(ActionEvent actionEvent) {
        String username = inappropriateListView.getSelectionModel().getSelectedItem().split(" ")[0];
        ((Nisit) userHashMap.get(username)).setUserStatus("banned");
        userDataSource.writeData(usersList);
        showInappropriateListView();
        inappropriateUserNameTextField.setText("");
        inappropriateNameTextField.setText("");
        inappropriateCategoryTextField.setText("");
        inappropriateDetailTextArea.setText("");
    }

    private void handleRequestUnbannedListView() {
        requestUnbannedListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observable,
                                        String oldValue, String newValue) {
                        if(newValue != null){
                            unbannedButton.setDisable(false);
                            showRequestUnbannedDetailSection(newValue.split(" ")[0]);
                        }
                    }
                });
    }

    @FXML private void handleUnbannedBtn(ActionEvent actionEvent) {
        String username = requestUnbannedListView.getSelectionModel().getSelectedItem().split(" ")[0];
        ((Nisit) userHashMap.get(username)).setUserStatus("active");
        ((Nisit) userHashMap.get(username)).setLoginAttempt(0);
        userReportHashMap.deleteReport(username);
        userDataSource.writeData(usersList);
        userReportDataSource.writeData(userReportHashMap);
        showRequestUnbannedListView();
        showBannedUsersListView();
        requestUnbannedUserNameTextField.setText("");
        requestUnbannedNameTextField.setText("");
        requestUnbannedCategoryTextField.setText("");
        requestUnbannedDetailTextArea.setText("");
        requestUnbannedRequestPermissionTextArea.setText("");
    }



    private void showBannedUsersListView() {
        bannedUsersListView.getItems().clear();
        for(User user : usersList.getAllUsers()){
            if(user instanceof Nisit && ((Nisit) user).getUserStatus().equals("banned")){
                HBox bannedUserDetail = new HBox();

                bannedUserDetail.setSpacing(50);
                bannedUserDetail.setPadding(new Insets(10, 10, 10, 10));
                Circle profile = new Circle();
                profile.setRadius(50);
                Image profilePic = new Image("file:" + user.getProfileImageFilePath());
                profile.setFill(new ImagePattern(profilePic));

                Label  bannedUserDataLabel = new Label();
                String bannedUserData = "Username: " + user.getUsername() + "\n"
                        + "Name: " + user.getName() + "\n"
                        + "LoginAttempt: " + ((Nisit) user).getLoginAttempt();
                bannedUserDataLabel.setText(bannedUserData);

                bannedUserDetail.getChildren().add(profile);
                bannedUserDetail.getChildren().add(bannedUserDataLabel);

                bannedUsersListView.getItems().add(bannedUserDetail);
            }
        }
        bannedUsersListView.refresh();
    }

    private void showComplaintReportListView() {
        inappropriateComplaintListView.getItems().clear();
        for(String k : complaintReportHashMap.getAllReports().keySet()) {
            ComplaintReport complaintReport = complaintReportHashMap.getAllReports().get(k);
            String complaint = complaintReport.getSubmitTime() +
                    "\nชื่อเรื่อง : " + complaintReport.getTopic() +
                    "\n[" + complaintReport.getComplaintCategory() + "]";
            inappropriateComplaintListView.getItems().add(complaint);
        }
        inappropriateComplaintListView.refresh();
    }

    private void handleComplaintReportListView() {
        inappropriateComplaintListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observable,
                                        String oldValue, String newValue) {
                        if(newValue != null){
                            deleteButton.setDisable(false);
                            rejectButton.setDisable(false);
                            showComplaintReportDetailSection(complaintReportHashMap.getAllReports().get(newValue.split("\n")[0]));
                        }
                    }
                });
    }

    @FXML private void showComplaintReportDetailSection(ComplaintReport complaintReport) {
        topicTextField.setText(complaintReport.getTopic());
        submitTimeTextField.setText(complaintReport.getSubmitTime());
        complaintReportCategoryTextField.setText(complaintReport.getComplaintCategory());
        complaintReportDetailTextArea.setText(complaintReport.getComplaintDetail());
    }

    @FXML private void handleDeleteButton(ActionEvent actionEvent) {
        String submitTime = inappropriateComplaintListView.getSelectionModel().getSelectedItem().split("\n")[0];
        complaintReportHashMap.deleteReport(submitTime);
        complaintReportDataSource.writeData(complaintReportHashMap);
        for(Complaint c : complaintList.getAllComplaints()){
            if(c.getSubmitTime().equals(submitTime)){
                complaintList.getAllComplaints().remove(c);
                break;
            }
        }
        complaintFileDataSource.writeData(complaintList);
        showComplaintReportListView();
        topicTextField.setText("");
        submitTimeTextField.setText("");
        complaintReportCategoryTextField.setText("");
        complaintReportDetailTextArea.setText("");
    }

    @FXML private void handleRejectButton(ActionEvent actionEvent){
        String submitTime = inappropriateComplaintListView.getSelectionModel().getSelectedItem().split("\n")[0];
        complaintReportHashMap.deleteReport(submitTime);
        complaintReportDataSource.writeData(complaintReportHashMap);
        showComplaintReportListView();
        topicTextField.setText("");
        submitTimeTextField.setText("");
        complaintReportCategoryTextField.setText("");
        complaintReportDetailTextArea.setText("");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

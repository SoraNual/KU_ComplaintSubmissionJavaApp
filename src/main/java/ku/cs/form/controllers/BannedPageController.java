package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import ku.cs.form.models.Nisit;
import ku.cs.form.models.UserList;
import ku.cs.form.models.UserReportHashMap;
import ku.cs.form.models.User;
import ku.cs.form.services.UserDataSource;
import ku.cs.form.services.UserReportDataSource;

import java.io.IOException;

import com.github.saacsos.FXRouter;


public class BannedPageController {
    @FXML private Button cancelButton;
    @FXML private Button homeButton;
    @FXML private Button requestButton;
    @FXML private Button sendButton;
    @FXML private TextArea requestTextArea;
    private UserReportDataSource userReportDataSource;
    private UserReportHashMap userReportHashMap;
    private UserList userList;
    private UserDataSource userDataSource;
    private User user;
    @FXML public void initialize(){
        user = (User) com.github.saacsos.FXRouter.getData();
        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
        userReportDataSource =  new UserReportDataSource("data","userReport.csv");
        userReportHashMap = userReportDataSource.readData();
        setBanned();
        cancelButton.setVisible(false);
        sendButton.setVisible(false);
        requestTextArea.setVisible(false);
    }

    public void handleCancelButton(){
        requestTextArea.clear();
        requestTextArea.setVisible(false);
        sendButton.setVisible(false);
        cancelButton.setVisible(false);
        requestButton.setVisible(true);
        homeButton.setVisible(true);
    }

    public void handleHomeButton(){
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void setBanned() {
        for(User u : userList.getAllUsers()){
            if(u.getUsername().equals(user.getUsername()))
                ((Nisit) u).bannedLoginAttempt();
        }
        userDataSource.writeData(userList);
    }

    public void handleRequestButton(){
        requestButton.setVisible(false);
        homeButton.setVisible(false);

        cancelButton.setVisible(true);
        sendButton.setVisible(true);
        requestTextArea.setVisible(true);
    }

    public void handleSendButton(){
        String requestMsg = requestTextArea.getText().trim();
        if(!requestMsg.isBlank()){
            userReportHashMap.setRequest(user.getUsername(), requestMsg);
            userReportDataSource.writeData(userReportHashMap);
            showPopUp("แจ้งคำร้องขอแล้ว \nโปรดรอการตรวจสอบจากแอดมิน", "คำร้องขอของคุณถูกส่งแล้ว",null);
            try {
                FXRouter.goTo("home");
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        else showPopUp("โปรดกรอกคำร้องขอในช่องที่กำหนดให้", "ERROR",null);
    }

    public static void showPopUp(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert;
        if(titleBar != null && titleBar.equals("ERROR")) alert = new Alert(Alert.AlertType.ERROR);
        else alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}

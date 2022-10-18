package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import com.github.saacsos.FXRouter;
import ku.cs.form.models.ReportHashMap;
import ku.cs.form.models.User;
import ku.cs.form.services.UserReportDataSource;

import java.io.IOException;


public class BannedPageController {
    @FXML private Button cancelButton;
    @FXML private Button homeButton;
    @FXML private Button requestButton;
    @FXML private Button sendButton;
    @FXML private TextArea requestTextArea;
    private UserReportDataSource userReportDataSource;
    private ReportHashMap reportHashMap;
    private User user;
    @FXML public void initialize(){
        user = (User) com.github.saacsos.FXRouter.getData();


        cancelButton.setVisible(false);
        sendButton.setVisible(false);
        requestTextArea.setVisible(false);

        userReportDataSource = new UserReportDataSource("data", "user_reports.csv");
        reportHashMap = new ReportHashMap();
        reportHashMap.setReports(userReportDataSource.readData());
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
            reportHashMap.setRequest(user.getUsername(), requestMsg);
        }
    }
}

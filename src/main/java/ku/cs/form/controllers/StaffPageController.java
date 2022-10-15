package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.services.ComplaintFileDataSource;

import java.io.IOException;

public class StaffPageController {
    @FXML private ImageView staffImage;
    @FXML private Label agencyLabel;
    @FXML private ListView<Complaint> reportsListView;
    private ComplaintFileDataSource dataSource;
    private ComplaintList complaintList;

    @FXML
    public void initialize() {
        String url = getClass().getResource("/ku/cs/images/zen.jpg").toExternalForm();
        staffImage.setImage(new Image(url));

        dataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = dataSource.readData();
        showReportsListView();
    }

    @FXML
    public void showReportsListView() {
        reportsListView.getItems().addAll(complaintList.getAllComplaints());
        reportsListView.refresh();
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("staffChangePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}

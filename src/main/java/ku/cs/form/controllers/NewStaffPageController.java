package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.Staff;
import com.github.saacsos.FXRouter;
import ku.cs.form.services.ComplaintFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NewStaffPageController {
    private Stage stage;
    private Staff staff;
    @FXML
    private Label agencyLabel;

    @FXML
    private Label nameLabel;
    @FXML
    private ImageView staffImage;

    @FXML
    private ListView<Complaint> itemHolder;
    private ComplaintList complaintList;

    @FXML
    public void initialize() {
        staff = (Staff) FXRouter.getData();
        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = complaintFileDataSource.readData();

        // setText
        nameLabel.setText(staff.getName());
        agencyLabel.setText(staff.getAgency());

        showImage();
        showComplaintListView();
        handleSelectedItem();
    }

    @FXML
    public void showComplaintListView() {
        for (Complaint complaint : complaintList.getAllReports()) {
            itemHolder.getItems().add(complaint);
        }
    }

    @FXML
    public void handleSelectedItem() {
        itemHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() % 2 == 0) {
                    Complaint complaint = itemHolder.getSelectionModel().getSelectedItem();
                    if (complaint != null) {
                        try {
                            FXRouter.goTo("newReportDetail", complaint);
                        } catch (IOException e) {
                            System.out.println("ไม่สามารถไปที่หน้า ReportDetail ได้");
                        }
                    } else {
                        System.out.println("user click on empty list cell");
                    }
                }
            }
        });
    }

    @FXML
    public void handleUploadImageButton(ActionEvent actionEvent) {
        // TODO

        showImage();
    }

    @FXML
    public void showImage() {
        File imageFile = new File(staff.getProfileImageFilePath());
        Image staffImg = new Image(imageFile.toURI().toString());
        staffImage.setImage(staffImg);
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("newStaffChangePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}

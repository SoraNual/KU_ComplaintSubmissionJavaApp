package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.Staff;
import com.github.saacsos.FXRouter;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.Filterer;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class NewStaffPageController {
    @FXML private VBox leftRec;
    @FXML private AnchorPane pane;
    private Stage stage;
    @FXML private Button uploadImageButton;
    @FXML private Button sideButton;
    @FXML private Button searchButton;
    @FXML private TextField searchTextField;
    @FXML private Label agency;
    @FXML private Label agencyLabel;
    @FXML private Label nameLabel;
    @FXML private Label responsibilityLabel;
    @FXML private ImageView staffImage;

    @FXML private ListView<Complaint> itemHolder;
    private Staff staff;
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
//        setTheme();
        showComplaintListView();
        handleSelectedItem();
    }

    @FXML
    public void showComplaintListView() {
//        // filtered by Agency
//        ComplaintList filteredComplaints = complaintList.filterBy(new Filterer<Complaint>() {
//            @Override
//            public boolean filter(Complaint o) {
//                // TODO
//                return true;
//            }
//        });
//
//        for (Complaint complaint : filteredComplaints.getAllReports()) {
//            itemHolder.getItems().add(complaint);
//        }

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

//    @FXML
//    public void setTheme() {
//        SetTheme setTheme = new SetTheme(staff);
//        setTheme.setObject(itemHolder);
//        setTheme.setObject(leftRec);
//        setTheme.setObject(uploadImageButton);
//        setTheme.setObject(searchButton);
//        setTheme.setObject(searchTextField);
//        setTheme.setInvisibleBackgroundButton(sideButton);
//        setTheme.setObject(nameLabel);
//        setTheme.setObject(responsibilityLabel);
//        setTheme.setObject(agency);
//        setTheme.setObject(agencyLabel);
//        setTheme.setObject(pane);
//    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("newStaffChangePassword", staff);
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}

package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.User;
import ku.cs.form.services.ReportFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NisitPageController {

    @FXML private Label nameLabel;
    @FXML private ImageView nisitImage;
    private User user;
    @FXML private ListView<Complaint> reportsListView;
    private ReportFileDataSource dataSource;
    private ComplaintList complaintList;
    private Stage stage;
    @FXML private Rectangle rightRec;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();

        nameLabel.setText(user.getName());
        File imageFile = new File(user.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        nisitImage.setImage(userImage);
        dataSource = new ReportFileDataSource("data", "complaints.csv");
        complaintList = dataSource.readData();
        reportsListView.getItems().addAll(complaintList.getAllReports());
        rightRec.setFill(user.getRectangleColor());
    }

    public void handleUploadImageButton(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image","*jpg","*jpeg","*png"));
        fileChooser.setInitialFileName(user.getUsername()+".jpg");
        File uploadImg = fileChooser.showOpenDialog(stage);
        File newUserImg = new File("data"+File.separator+"img",user.getUsername()+".jpg");
        try{
            Files.move(uploadImg.toPath(), newUserImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File imageFile = new File(user.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        nisitImage.setImage(userImage);
    }

    @FXML
    public void handleEditProfileButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("editProfile",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("report",user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}

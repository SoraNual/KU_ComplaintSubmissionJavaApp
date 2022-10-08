package ku.cs.form.controllers;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;
import ku.cs.form.models.User;
import ku.cs.form.services.ReportFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NisitPageController {

    private User user;
    private ReportFileDataSource dataSource;
    private ReportList reportList;
    private Stage stage;
    @FXML private Rectangle rightRec;
    @FXML private ListView<Report> reportsListView;
    @FXML private AnchorPane pane;
    @FXML private Label roleLabel;
    @FXML private Label allReportLabel;
    @FXML private Label nameLabel;
    @FXML private ImageView nisitImage;
    @FXML private Button editProfileButton;
    @FXML private Button changePasswordButton;
    @FXML private Button reportButton;
    @FXML private Button uploadImageButton;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        nameLabel.setText(user.getName());
        File imageFile = new File(user.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        nisitImage.setImage(userImage);
        dataSource = new ReportFileDataSource("data", "reports.csv");
        reportList = dataSource.readData();
        theme();
        reportsListView.getItems().addAll(reportList.getAllReports());;
    }

    public void theme() {
        SetTheme setTheme = new SetTheme(user);
        setTheme.setObject(reportsListView);
        setTheme.setObject(rightRec);
        setTheme.setObject(reportButton);
        setTheme.setObject(uploadImageButton);
        setTheme.setInvisibleBackgroundButton(editProfileButton);
        setTheme.setInvisibleBackgroundButton(changePasswordButton);
        setTheme.setObject(nameLabel);
        setTheme.setObject(roleLabel);
        setTheme.setObject(allReportLabel);
        setTheme.setObject(pane);
    }

    public void handleUploadImageButton(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image","*jpg","*jpeg","*png"));
        fileChooser.setInitialFileName(user.getUsername()+".jpg");
        File uploadImg = fileChooser.showOpenDialog(stage);
        File newUserImg = new File("data"+File.separator+"img",user.getUsername()+".jpg");

        if(!(uploadImg==null)) {
            try {
                Files.copy(uploadImg.toPath(), newUserImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        File imageFile = new File(user.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        nisitImage.setImage(userImage);
    }

    public void handleEditProfileButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("editProfile",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("report",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleChangePasswordButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("changePasswordNisit",user);
        } catch (Exception e){
            System.out.println("cant go there");
            e.printStackTrace();
        }
    }

}

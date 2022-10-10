package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.User;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class NisitPageController {

    private User user;
    @FXML private ListView<Complaint> complaintsListView;
    private ComplaintFileDataSource dataSource;
    private ComplaintList complaintList;

    private Stage stage;
    @FXML private Rectangle rightRec;
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
        dataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = dataSource.readData();
        complaintsListView.getItems().addAll(complaintList.getAllReports());

        theme();
        handleSelectedItem();
    }

    public void theme() {
        SetTheme setTheme = new SetTheme(user);
        setTheme.setObject(complaintsListView);
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

    @FXML
    public void handleSelectedItem() {
        complaintsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() % 2 == 0) {
                    Complaint complaint = complaintsListView.getSelectionModel().getSelectedItem();
                    if (complaint != null) {
                        try {
                            ArrayList<Object> objects = new ArrayList<>();
                            objects.add(user);
                            objects.add(complaint);
                            com.github.saacsos.FXRouter.goTo("complaintsDetailsForNisit", objects);
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

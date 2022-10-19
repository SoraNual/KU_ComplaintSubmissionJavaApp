package ku.cs.form.controllers;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import ku.cs.form.models.*;
import com.github.saacsos.FXRouter;
import ku.cs.form.services.AgencyDataSource;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.Filterer;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NewStaffPageController {
    @FXML private VBox leftRec;
    @FXML private VBox itemHolder;
    @FXML private AnchorPane pane;
    @FXML private ScrollPane scrollPane;
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
    @FXML private AnchorPane anchorPane;
    private Staff staff;
    private ComplaintList complaintList;
    private SetTheme setTheme;

    @FXML
    public void initialize() {
        staff = (Staff) FXRouter.getData();
        setTheme = new SetTheme(staff.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = complaintFileDataSource.readData();

        itemHolder.setSpacing(10);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // disable ตัว scrollbar แนวนอน

        // setText
        nameLabel.setText(staff.getName());
        agencyLabel.setText(staff.getAgency());
        File imageFile = new File(staff.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        staffImage.setImage(userImage);

        showImage();
        showComplaintListView();
        handleSelectedCustomItem();
    }

    public ComplaintList filterByCategory(ComplaintList complains) {
        Agency agency = getStaffCategory();
        complains = complains.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                return o.getCategory().equals(agency.getCategory());
            }
        });
        return complains;
    }

    @FXML
    public void showComplaintListView() {
        ComplaintList filteredComplain = filterByCategory(complaintList);
//
        List<Complaint> complaints = filteredComplain.getAllComplaints();
        for (int i = 0; i < complaints.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ku/cs/complaint-item.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                ComplaintItemController complaintItemController = fxmlLoader.getController();
                complaintItemController.setData(complaints.get(i));

                hBox.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (newValue) {
                        hBox.setStyle("-fx-background-color: #dddddd");
                    } else {
                        hBox.setStyle("-fx-background-color: transparent");
                    }
                });

                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("mouse clicked: " + complaintItemController.getComplaint());

                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(staff);
                        objects.add(complaintItemController.getComplaint());

                        try {
                            FXRouter.goTo("newComplaintDetail", objects);
                        } catch (IOException e) {
                            System.out.println("handle มีปัญหา");
                        }
                    }
                });

                itemHolder.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Agency getStaffCategory() {
        AgencyDataSource agencyDataSource = new AgencyDataSource("data", "agency.csv");
        AgencyList agencyList = agencyDataSource.readData();

        Agency result = null;
        for (Agency agency : agencyList.getAgencies()) {
            if (agency.getName().equals(staff.getAgency())) {
                result = agency;
            }
        }
        return result;
    }

    @FXML
    public void handleSelectedCustomItem() {

    }

    public void handleUploadImageButton(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image","*jpg","*jpeg","*png"));
        fileChooser.setInitialFileName(staff.getUsername()+".jpg");
        File uploadImg = fileChooser.showOpenDialog(stage);
        File newUserImg = new File("data"+File.separator+"img",staff.getUsername()+".jpg");

        if(!(uploadImg==null)) {
            try {
                Files.copy(uploadImg.toPath(), newUserImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        staff.setProfileImage();
        File imageFile = new File(staff.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        staffImage.setImage(userImage);;
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
            FXRouter.goTo("newStaffChangePassword", staff);
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }

    @FXML
    public void handleThemeButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("editProfile", staff);
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}

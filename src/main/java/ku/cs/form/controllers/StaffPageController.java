package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import java.util.*;

public class StaffPageController {
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
    @FXML private ComboBox<String> filterComboBox;
    private Staff staff;
    private ComplaintList complaintList; // เก็บแค่ตอนอ่าน ComplaintDatasource ตอนแรก
    private ComplaintList agencyFilterComplaints; // เก็บ ComplaintList ที่ filter หน่วยงานมาแล้วจะนำไป filter/sort ต่อ
    private ComplaintList currentComplaintList; // เก็บ ComplaintList ที่ได้มาจากการใช้มา method filter/sort
    private SetTheme setTheme;

    @FXML
    public void initialize() {
        staff = (Staff) FXRouter.getData();
        setTheme = new SetTheme(staff.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = complaintFileDataSource.readData();

        agencyFilterComplaints = filterByCategory(complaintList);
        currentComplaintList = agencyFilterComplaints;

        itemHolder.setSpacing(10);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // disable ตัว scrollbar แนวนอน

        // set components
        nameLabel.setText(staff.getName());
        agencyLabel.setText(staff.getAgency());
        File imageFile = new File(staff.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        staffImage.setImage(userImage);
        filterComboBox.getItems().addAll("เวลาจากล่าสุด ไป เก่าสุด", "เวลาจากเก่าสุด ไป ล่าสุด",
                                        "คะแนนโหวตมากสุด ไป น้อยสุด", "คะแนนโหวตน้อยสุด ไป มากสุด");

        showImage();
        showComplaintListView(agencyFilterComplaints);
        handleSelectedFilterComboBox();
    }

    @FXML
    public void showComplaintListView(ComplaintList complaintList) {
        itemHolder.getChildren().clear();
        List<Complaint> complaints =  complaintList.getAllComplaints();
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
                            System.out.println("ไม่สามารถไปหน้า Complaint Detail ได้โปรดตรวจสอบ router อีกครั้ง");
                        }
                    }
                });

                itemHolder.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSearchButton(ActionEvent actionEvent) {
        currentComplaintList = filterByContain(agencyFilterComplaints);
        searchTextField.clear();
        showComplaintListView(currentComplaintList);
    }

    public void handleSelectedFilterComboBox() {
        filterComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println(oldValue + ":" + newValue);
                ComplaintList sortedComplaints = sort(currentComplaintList);

                showComplaintListView(sortedComplaints);
            }
        });
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
            FXRouter.goTo("changePassword", staff);
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

    @FXML
    public void handleLogOutButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Home ได้");
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

    public ComplaintList filterByContain(ComplaintList complaints) {
        String input = searchTextField.getText();
        System.out.println(input);
        if (input != null && !input.trim().isEmpty()) {
            complaints = complaints.filterBy(new Filterer<Complaint>() {
                @Override
                public boolean filter(Complaint o) {
                    return o.getTopic().contains(input.trim());
                }
            });
        }
        return complaints;
    }

    public ComplaintList sort(ComplaintList complaints) {
        complaints.getAllComplaints().sort(new Comparator<Complaint>() {
            @Override
            public int compare(Complaint o1, Complaint o2) {
                if (filterComboBox.getValue().equals("คะแนนโหวตมากสุด ไป น้อยสุด")) {
                    return -Integer.compare(o1.getVotePoint(), o2.getVotePoint());
                }
                if (filterComboBox.getValue().equals("คะแนนโหวตน้อยสุด ไป มากสุด")) {
                    return Integer.compare(o1.getVotePoint(), o2.getVotePoint());
                }
                if (filterComboBox.getValue().equals("เวลาจากล่าสุด ไป เก่าสุด")) {
                    return -o1.getLiteralSubmitTime().compareTo(o2.getLiteralSubmitTime());
                }
                else
                    return o1.getLiteralSubmitTime().compareTo(o2.getLiteralSubmitTime());
            }
        });
        return complaints;
    }
}

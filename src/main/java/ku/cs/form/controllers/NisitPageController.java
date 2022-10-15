package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.Comparator;

public class NisitPageController {

    private User user;
    @FXML private ListView<Complaint> complaintsListView;
    private ComplaintFileDataSource dataSource;
    private ComplaintList defaultComplaintList;

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
    @FXML private ComboBox<String> categoryFilterComboBox;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private CheckBox byMeFilterCheckBox;
    private ComplaintList filteredComplaintList;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();

        nameLabel.setText(user.getName());
        File imageFile = new File(user.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        nisitImage.setImage(userImage);
        dataSource = new ComplaintFileDataSource("data", "complaints.csv");
        defaultComplaintList = dataSource.readData();

        sortComboBox.getItems().add("คะแนนโหวตน้อยที่สุด ไป มากที่สุด");
        sortComboBox.getItems().add("คะแนนโหวตมากที่สุด ไป น้อยที่สุด");
        sortComboBox.getItems().add("ล่าสุด ไป เก่าสุด");
        sortComboBox.getItems().add("เก่าสุด ไป ล่าสุด");


        theme();

        handleSelectedComplaint();
        handleSelectedSortComboBox();
        handleByMeFilterCheckBox();
        handleSelectedCategoryFilterComboBox();
        sortComboBox.getSelectionModel().select("ล่าสุด ไป เก่าสุด");

        showComplaintListView(defaultComplaintList);

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
    private void showComplaintListView(ComplaintList complaintList){
        complaintsListView.getItems().clear();
        complaintsListView.getItems().addAll(complaintList.getAllComplaints());
        complaintsListView.refresh();
    }
    private void handleByMeFilterCheckBox(){
        byMeFilterCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){// selected
                    filteredComplaintList = defaultComplaintList;
                    filteredComplaintList = filteredComplaintList.filterBy(new Filterer<Complaint>() {
                        @Override
                        public boolean filter(Complaint o) {
                            return o.getComplainantUsername().equals(user.getUsername());
                        }
                    });
                    showComplaintListView(filteredComplaintList);
                }else{// unselected
                    showComplaintListView(defaultComplaintList);
                }
            }
        });
    }

    private void handleSelectedSortComboBox(){
        sortComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String current) {
                defaultComplaintList.getAllComplaints().sort(new Comparator<Complaint>() {
                    @Override
                    public int compare(Complaint o1, Complaint o2) {
                        if(current.contains("มากที่สุด ไป น้อยที่สุด"))
                            return -Integer.compare(o1.getVotePoint(),o2.getVotePoint());
                        else if(current.contains("น้อยที่สุด ไป มากที่สุด"))
                            return Integer.compare(o1.getVotePoint(),o2.getVotePoint());
                        else if(current.equals("ล่าสุด ไป เก่าสุด"))
                            return -o1.getLiteralSubmitTime().compareTo(o2.getLiteralSubmitTime());
                        return o1.getLiteralSubmitTime().compareTo(o2.getLiteralSubmitTime());
                    }
                });
                showComplaintListView(defaultComplaintList);

            }
        });
    }

    private void handleSelectedCategoryFilterComboBox(){
        categoryFilterComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String current) {
                filteredComplaintList = filteredComplaintList.filterBy(new Filterer<Complaint>() {
                    @Override
                    public boolean filter(Complaint o) {
                        return false;
                    }
                });
            }
        });
    }
    @FXML
    public void handleSelectedComplaint() {
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
                            e.printStackTrace();
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
        user.setProfileImage();
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

    public void handleComplainButton(ActionEvent actionEvent) {
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

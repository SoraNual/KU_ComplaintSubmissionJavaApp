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
import ku.cs.form.models.*;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.Filterer;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;

import com.github.saacsos.FXRouter;


public class NisitPageController {

    private User user;
    @FXML private ListView<Complaint> complaintsListView;
    private ComplaintFileDataSource complaintDataSource;
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
    @FXML private ComboBox<ComplaintCategory> categoryFilterComboBox;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private CheckBox byMeFilterCheckBox;
    @FXML private CheckBox pendingStatusCheckBox;
    @FXML private CheckBox processingStatusCheckBox;
    @FXML private CheckBox concludedStatusCheckBox;
    @FXML private CheckBox lessThanVotePointsCheckBox;
    @FXML private CheckBox moreThanVotePointsCheckBox;
    @FXML private CheckBox fromToVotePointsCheckBox;
    @FXML private TextField lessThanVotePointsTextField;
    @FXML private TextField moreThanVotePointsTextField;
    @FXML private TextField fromVotePointsTextField;
    @FXML private TextField toVotePointsTextField;
    private ComplaintList filteredComplaintList;
    private ComplaintCategoryDataSource categoryDataSource;
    private ComplaintCategoryList categoryList;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();

        nameLabel.setText(user.getName());
        File imageFile = new File(user.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        nisitImage.setImage(userImage);
        complaintDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        defaultComplaintList = complaintDataSource.readData();
        categoryDataSource = new ComplaintCategoryDataSource("data","complaintCategories.csv");
        categoryList = categoryDataSource.readData();
        filteredComplaintList = defaultComplaintList;

        sortComboBox.getItems().add("คะแนนโหวตน้อยที่สุด ไป มากที่สุด");
        sortComboBox.getItems().add("คะแนนโหวตมากที่สุด ไป น้อยที่สุด");
        sortComboBox.getItems().add("ล่าสุด ไป เก่าสุด");
        sortComboBox.getItems().add("เก่าสุด ไป ล่าสุด");

        ComplaintCategory all = new ComplaintCategory("ทั้งหมด",null,null,null);
        categoryFilterComboBox.getItems().add(all);
        categoryFilterComboBox.getItems().addAll(categoryList.getAllCategories());
        categoryFilterComboBox.getSelectionModel().select(all);


        handleSelectedComplaint();
        handleSelectedSortComboBox();

        sortComboBox.getSelectionModel().select("ล่าสุด ไป เก่าสุด");

        showComplaintListView(defaultComplaintList);

    }


    private void showComplaintListView(ComplaintList complaintList){
        complaintsListView.getItems().clear();
        complaintsListView.getItems().addAll(complaintList.getAllComplaints());
        complaintsListView.refresh();
    }

    private void handleSelectedSortComboBox(){

        sortComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String current) {
                filteredComplaintList = sort(filteredComplaintList);
                showComplaintListView(filteredComplaintList);
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
                            FXRouter.goTo("complaintsDetailsForNisit", objects);
                        } catch (IOException e) {
                            System.out.println("ไม่สามารถไปที่หน้า ComplaintDetail ได้");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("user click on empty list cell");
                    }
                }
            }
        });
    }
    private ComplaintList filterSubmitByMe(ComplaintList currentList){
        currentList = currentList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                if(byMeFilterCheckBox.isSelected()) return o.getComplainantUsername().equals(user.getUsername());
                return true;
            }
        });
        return currentList;
    }
    private ComplaintList filterByCategory(ComplaintList currentList){
        currentList = currentList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                if(categoryFilterComboBox.getValue() != null && !categoryFilterComboBox.getValue().getName().equals("ทั้งหมด"))
                    return categoryFilterComboBox.getValue().getName().equals(o.getCategory());
                return true;
            }
        });
        return currentList;
    }
    private ComplaintList filteredByStatus(ComplaintList currentList){
        currentList = currentList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                boolean result = true;

                if(pendingStatusCheckBox.isSelected()) {
                    result = o.getStatus().equals("รอการตรวจสอบจากเจ้าหน้าที่");
                    if(result) return true;
                }

                if(processingStatusCheckBox.isSelected()) {
                    result = o.getStatus().equals("กำลังดำเนินการ");
                    if(result) return true;
                }

                if(concludedStatusCheckBox.isSelected()) {
                    result = o.getStatus().equals("ดำเนินการเสร็จสิ้น") ;
                    if(result) return true;
                }
                return result;
            }
        });
        return currentList;
    }
    private ComplaintList filterByVotePoints(ComplaintList currentList){
        currentList = currentList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                boolean result = true;
                int moreThan, lessThan, from, to;

                if(moreThanVotePointsCheckBox.isSelected() && !moreThanVotePointsTextField.getText().isBlank()) {
                    moreThan = Integer.parseInt(moreThanVotePointsTextField.getText());
                    result = o.getVotePoint() > moreThan;
                }

                if(lessThanVotePointsCheckBox.isSelected() && !lessThanVotePointsTextField.getText().isBlank()) {
                    lessThan = Integer.parseInt(lessThanVotePointsTextField.getText().trim());
                    result = o.getVotePoint() < lessThan;
                }

                if(fromToVotePointsCheckBox.isSelected()
                        && !fromVotePointsTextField.getText().isBlank() && !toVotePointsTextField.getText().isBlank()) {
                    from = Integer.parseInt(fromVotePointsTextField.getText().trim());
                    to = Integer.parseInt(toVotePointsTextField.getText().trim());
                    result = (o.getVotePoint() >= from && o.getVotePoint() <= to);
                }

                return result;
            }
        });
        return currentList;
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

    private ComplaintList sort(ComplaintList currentList){
        currentList.getAllComplaints().sort(new Comparator<Complaint>() {
            @Override
            public int compare(Complaint o1, Complaint o2) {
                if(sortComboBox.getValue().contains("มากที่สุด ไป น้อยที่สุด"))
                    return -Integer.compare(o1.getVotePoint(),o2.getVotePoint());
                else if(sortComboBox.getValue().contains("น้อยที่สุด ไป มากที่สุด"))
                    return Integer.compare(o1.getVotePoint(),o2.getVotePoint());
                else if(sortComboBox.getValue().equals("ล่าสุด ไป เก่าสุด"))
                    return -o1.getLiteralSubmitTime().compareTo(o2.getLiteralSubmitTime());
                return o1.getLiteralSubmitTime().compareTo(o2.getLiteralSubmitTime());
            }
        });
        return currentList;
    }

    public void filter(){
        filteredComplaintList = filterByCategory(defaultComplaintList);
        filteredComplaintList = filterSubmitByMe(filteredComplaintList);
        filteredComplaintList = filterByVotePoints(filteredComplaintList);
        filteredComplaintList = filteredByStatus(filteredComplaintList);
        filteredComplaintList = sort(filteredComplaintList);
        showComplaintListView(filteredComplaintList);
    }

}

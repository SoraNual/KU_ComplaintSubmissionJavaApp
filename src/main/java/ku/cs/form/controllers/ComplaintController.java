package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.form.models.*;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ComplaintController {
    private Stage stage;
    private User user;
    private Complaint complaint;
    @FXML private TextField topicTextField;
    @FXML private TextArea basicDetailsTextArea;
    @FXML private ChoiceBox<ComplaintCategory> categoryChoiceBox;
    private ComplaintCategoryDataSource complaintCategoryDataSource;
    private ComplaintCategoryList complaintCategories;
    private ComplaintList complaintList;
    private ComplaintFileDataSource complaintFileDataSource;
    @FXML private Label warningLabel;
    @FXML private Label additionalDetailLabel;
    @FXML private Label additionalImageLabel;
    @FXML private Label uploadedImgNameLabel;
    @FXML private TextArea additionalDetailTextArea;
    @FXML private Button uploadImgButton;
    @FXML private Button backButton;
    @FXML private Button submitButton;
    private File uploadImg;
    private String time;
    private String fileName;
    private String dir;
    @FXML public void initialize() {
        complaintCategoryDataSource = new ComplaintCategoryDataSource("data","complaintCategories.csv");
        complaintCategories = complaintCategoryDataSource.readData();

        complaintFileDataSource = new ComplaintFileDataSource("data","complaints.csv");
        complaintList = complaintFileDataSource.readData();

        user = (User) com.github.saacsos.FXRouter.getData();

        categoryChoiceBox.getItems().addAll(complaintCategories.getAllCategories());
        warningLabel.setText("");
        additionalDetailLabel.setText("");
        additionalImageLabel.setText("");
        additionalDetailTextArea.setVisible(false);
        uploadImgButton.setVisible(false);
        uploadedImgNameLabel.setText("");

        handleCategoryChoiceBox();
        theme();

    }
    public void theme(){
        SetTheme setTheme = new SetTheme(user);
        setTheme.setObject(warningLabel);
        setTheme.setObject(topicTextField);
        setTheme.setObject(basicDetailsTextArea);
        setTheme.setObject(categoryChoiceBox);
        setTheme.setObject(additionalDetailTextArea);
        setTheme.setObject(uploadImgButton);
        setTheme.setObject(backButton);
        setTheme.setObject(submitButton);
    }
    private void handleCategoryChoiceBox(){
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<ComplaintCategory>() {
                    @Override
                    public void changed(ObservableValue<? extends ComplaintCategory>
                                                observable,
                                        ComplaintCategory oldValue, ComplaintCategory newValue) {
                        showSelectedCategory(newValue);
                    }
                });
    }

    private void showSelectedCategory(ComplaintCategory complaintCategory){
        additionalDetailLabel.setText(complaintCategory.getAdditionalDetailTitle());
        additionalDetailTextArea.setVisible(true);
    }
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage", user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSubmitButton(ActionEvent actionEvent) {

        String topic = topicTextField.getText().trim(),
                basicDetails = basicDetailsTextArea.getText().trim().replace("\n","NEWLINE"),
                category = categoryChoiceBox.getValue().getName(),
                additionalDetail = additionalDetailTextArea.getText().trim().replace("\n", "NEWLINE");
        if (topic.isBlank() || basicDetails.isBlank() || category == null || additionalDetail.isBlank()) {
            warningLabel.setText("โปรดกรอกให้ครบทุกช่อง");
        } else {
            if(!topicTextField.isDisabled()){
                complaint = new Complaint(topic, user.getUsername(), category);
                complaint.setBasicDetail(basicDetails);
                complaint.setAdditionalDetail(additionalDetail);
                System.out.println(complaint);
            }
            if(categoryChoiceBox.getValue().getImageNeeded()){
                additionalImageLabel.setText(categoryChoiceBox.getValue().getAdditionalImageTitle());
                uploadImgButton.setVisible(true);
                warningLabel.setText("กรุณาอัปโหลดรูปเพื่อยืนยันหลักฐาน (บังคับ)\nหลังอัปโหลดแล้วโปรดกดปุ่ม'ส่ง'อีกครั้ง");
                additionalDetailTextArea.setDisable(true);
                topicTextField.setDisable(true);
                basicDetailsTextArea.setDisable(true);
                categoryChoiceBox.setDisable(true);
                backButton.setDisable(true);
            }

            if(!categoryChoiceBox.getValue().getImageNeeded() || (categoryChoiceBox.getValue().getImageNeeded()) && !uploadedImgNameLabel.getText().isBlank()){
                try {
                    if(!uploadedImgNameLabel.getText().isBlank()) {
                        // ไฟล์จะไม่ถูกสร้างในโฟลเดอร์จนกว่าผู้ใช้จะกดส่ง
                        File newAdditionalImg = new File(dir, fileName);
                        try {
                            Files.copy(uploadImg.toPath(), newAdditionalImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    complaintList.addComplaint(complaint);
                    complaintFileDataSource.writeData(complaintList);
                    com.github.saacsos.FXRouter.goTo("nisitPage",user);
                } catch (IOException e) {
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
    }

    @FXML public void handleAddImageButton(){
        time = complaint.getSubmitTime().replace(":","-");
        fileName = time + "_" + user.getUsername() + "_" + complaint.getTopic() +".jpg";
        dir = "data" + File.separator+"img" + File.separator + "complaint";

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image","*jpg","*jpeg","*png"));
        fileChooser.setInitialFileName(fileName);
        uploadImg = fileChooser.showOpenDialog(stage);
        System.out.println(uploadImg.getName());
        System.out.println(uploadImg.getPath());
        uploadedImgNameLabel.setText(uploadImg.getName());
    }
}

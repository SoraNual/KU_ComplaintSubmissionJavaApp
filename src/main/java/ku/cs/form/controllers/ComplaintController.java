package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.form.models.*;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.IOException;

public class ComplaintController {
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
    @FXML private TextArea additionalDetailTextArea;
    @FXML private Button uploadImgButton;
    @FXML private Button backButton;
    @FXML private Button submitButton;

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

        if(complaintCategory.getImageNeeded() == true){
            additionalImageLabel.setText(complaintCategory.getAdditionalImageTitle());
            uploadImgButton.setVisible(true);
        }else{
            additionalImageLabel.setText("");
            uploadImgButton.setVisible(false);
        }
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
            complaint = new Complaint(topic, user.getUsername(), category);
            complaint.setBasicDetail(basicDetails);
            complaint.setAdditionalDetail(additionalDetail);
            System.out.println(complaint);
            complaintList.addReport(complaint);
            complaintFileDataSource.writeData(complaintList);
            try {

                com.github.saacsos.FXRouter.goTo("nisitPage");
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}

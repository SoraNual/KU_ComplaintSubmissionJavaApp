package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.form.models.*;
import ku.cs.form.services.ReportCategoryDataSource;
import ku.cs.form.services.ReportFileDataSource;

import java.io.IOException;

public class ReportController {
    private User user;
    private Complaint complaint;
    @FXML private TextField topicTextField;
    @FXML private TextArea basicDetailsTextArea;
    @FXML private ChoiceBox<ComplaintCategory> categoryChoiceBox;
    private ReportCategoryDataSource reportCategoryDataSource;
    private ComplaintCategoryList reportCategories;
    private ComplaintList complaintList;
    private ReportFileDataSource reportFileDataSource;
    @FXML private Label warningLabel;
    @FXML private Label additionalDetailLabel;
    @FXML private Label additionalImageLabel;
    @FXML private TextArea additionalDetailTextArea;
    @FXML private Button uploadImgButton;
    @FXML public void initialize() {
        reportCategoryDataSource = new ReportCategoryDataSource("data","complaintCategories.csv");
        reportCategories = reportCategoryDataSource.readData();

        reportFileDataSource = new ReportFileDataSource("data","complaints.csv");
        complaintList = reportFileDataSource.readData();

        user = (User) com.github.saacsos.FXRouter.getData();
        categoryChoiceBox.getItems().addAll(reportCategories.getAllCategories());
        warningLabel.setText("");
        additionalDetailLabel.setText("");
        additionalImageLabel.setText("");
        additionalDetailTextArea.setVisible(false);
        uploadImgButton.setVisible(false);

        handleCategoryChoiceBox();
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
                basicDetails = basicDetailsTextArea.getText().trim(),
                category = categoryChoiceBox.getValue().getName(),
                additionalDetail = additionalDetailTextArea.getText().trim();
        if (topic.isBlank() || basicDetails.isBlank() || category == null || additionalDetail.isBlank()) {
            warningLabel.setText("โปรดกรอกให้ครบทุกช่อง");
        } else {
            complaint = new Complaint(topic, user.getUsername(), basicDetails, category, additionalDetail);
            System.out.println(complaint);
            complaintList.addReport(complaint);
            reportFileDataSource.writeData(complaintList);
            try {

                com.github.saacsos.FXRouter.goTo("nisitPage");
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}

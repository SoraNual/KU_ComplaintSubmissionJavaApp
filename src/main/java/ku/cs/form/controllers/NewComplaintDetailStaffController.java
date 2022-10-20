package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.Staff;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NewComplaintDetailStaffController {
    @FXML private TextArea topicTextArea;
    @FXML private TextArea detailTextArea;
    @FXML private TextArea agencyTextArea;
    @FXML private ListView<String> responsibleListView;
    @FXML private TextArea solutionTextArea;
    @FXML private Label attachImageLabel;
    @FXML private ImageView attachImage;
    @FXML private AnchorPane anchorPane;
    private ComplaintFileDataSource complaintFileDataSource;
    private ComplaintList complaintList;
    private ArrayList<Object> objects = new ArrayList<>();
    private Staff staff;
    private Complaint complaint;
    private SetTheme setTheme;

    @FXML
    public void initialize() {
        complaintFileDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = complaintFileDataSource.readData();

        objects = (ArrayList<Object>) FXRouter.getData();
        staff = (Staff) objects.get(0);
        complaint = (Complaint) objects.get(1);

        setTheme = new SetTheme(staff.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");


        File imageFile = new File(staff.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        attachImage.setImage(null);

        topicTextArea.setText(complaint.getTopic());
        detailTextArea.setText(complaint.getBasicDetail());
        agencyTextArea.setText(complaint.getCategory());
//        attachImageLabel.setText("Set");

        if (complaint.getSolution().equals("null")) solutionTextArea.setText("");
        else solutionTextArea.setText(complaint.getSolution());

        showResponsibleListView();
    }

    @FXML
    public void handleInProgressButton(ActionEvent actionEvent) {
        String solution = solutionTextArea.getText();
        if (solution == null || solution.trim().isEmpty()) {
            showAlert("โปรดใส่ solution ก่อนกดเปลี่ยนสถานะ");
            return;
        }

        complaint.setSolution(solutionTextArea.getText().trim());
        complaintFileDataSource.updateData(complaint, solution.trim(), "กำลังดำเนินการ");

        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML public void handleDoneButton(ActionEvent actionEvent) {
        if (solutionTextArea.getText() == null || solutionTextArea.getText().trim().isEmpty()) {
            showAlert("โปรดใส่ solution ก่อน");
            return;
        }

        String solution = solutionTextArea.getText();
        System.out.println(solution);

        complaint.setSolution(solutionTextArea.getText().trim());
        complaintFileDataSource.updateData(complaint, solution.trim(), "ดำเนินการเสร็จสิ้น");

        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }

        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML
    public static void showAlert(String errMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(errMsg);
        alert.showAndWait();
    }

    @FXML
    public void showResponsibleListView() {
        responsibleListView.getItems().clear();
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.getItems().add("A");
        responsibleListView.refresh();
    }
}

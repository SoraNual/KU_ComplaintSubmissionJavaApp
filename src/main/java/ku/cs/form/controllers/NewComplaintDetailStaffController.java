package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.services.ComplaintFileDataSource;

import java.io.IOException;

public class NewComplaintDetailStaffController {
    @FXML
    private TextArea topicTextArea;
    @FXML
    private TextArea detailTextArea;
    @FXML
    private TextArea agencyTextArea;
    @FXML
    private TextArea responsibleTextArea;
    @FXML
    private TextArea solutionTextArea;
    private ComplaintFileDataSource complaintFileDataSource;
    private ComplaintList complaintList;
    private Complaint complaint;

    @FXML
    public void initialize() {
        complaintFileDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = complaintFileDataSource.readData();

        complaint = (Complaint) FXRouter.getData();

        topicTextArea.setText(complaint.getTopic());
        detailTextArea.setText(complaint.getBasicDetail());
        agencyTextArea.setText("// TODO //");
        responsibleTextArea.setText("// TODO //");

        if (complaint.getSolution().equals("null")) solutionTextArea.setText("");
        else solutionTextArea.setText(complaint.getSolution());
    }

    @FXML
    public void handleInProgressButton(ActionEvent actionEvent) {
        String solution = solutionTextArea.getText();
        if (solution == null || solution.trim().isEmpty()) {
            showAlert("โปรดใส่ solution ก่อน");
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
}

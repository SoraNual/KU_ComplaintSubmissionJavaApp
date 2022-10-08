package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import ku.cs.form.models.Report;

import java.io.IOException;

public class NewReportDetailController {
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

    @FXML
    public void initialize() {
        Report report = (Report) FXRouter.getData();

        topicTextArea.setText(report.getTopic());
        detailTextArea.setText(report.getBasicDetail());
        agencyTextArea.setText("");
        responsibleTextArea.setText("");
    }

    @FXML
    public void handleInProgressButton(ActionEvent actionEvent) {
        if (solutionTextArea.getText() == null || solutionTextArea.getText().trim().isEmpty()) {
            showAlert("please put solution first!");
            return;
        }

        String solution = solutionTextArea.getText();
        System.out.println(solution);
        // TODO

        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML public void handleDoneButton(ActionEvent actionEvent) {
        if (solutionTextArea.getText() == null || solutionTextArea.getText().trim().isEmpty()) {
            showAlert("please put solution first!");
            return;
        }

        String solution = solutionTextArea.getText();
        System.out.println(solution);
        // TODO

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

package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportDetailController {
    @FXML private TextArea topicTextArea;
    @FXML private TextArea detailTextArea;
    @FXML private TextArea agencyTextArea;
    @FXML private TextArea nameTextArea;
    @FXML private TextArea solutionTextArea;

    @FXML
    public void initialize() {
        topicTextArea.setText("-");
        detailTextArea.setText("-");
        agencyTextArea.setText("-");
        nameTextArea.setText("Loren Ipsum");
    }
    @FXML
    public void handleInProgressButton() {
        // TODO
        System.out.println("In Progress Button Clicked");
    }

    @FXML
    public void handleFinishButton() {
        // TODO
        System.out.println("Finish Button Clicked");
    }

    @FXML
    public void getSolution() {
        // TODO
    }

    @FXML
    public void handleBackButton() {
        // TODO
    }


}

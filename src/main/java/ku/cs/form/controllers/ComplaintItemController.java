package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ku.cs.form.models.Complaint;

import java.net.URL;
import java.util.ResourceBundle;

public class ComplaintItemController implements Initializable {
    @FXML Label topicLabel;
    @FXML Label voteLabel;

    @FXML
    public void setData(Complaint complaint) {
        topicLabel.setText(complaint.getTopic());
        voteLabel.setText("" + complaint.getVotePoint());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}

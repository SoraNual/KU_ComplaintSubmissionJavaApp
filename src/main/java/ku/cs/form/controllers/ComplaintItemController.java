package ku.cs.form.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ku.cs.form.models.Complaint;

import java.net.URL;
import java.util.ResourceBundle;

public class ComplaintItemController implements Initializable {
    @FXML Label topicLabel;
    @FXML Label voteLabel;
    @FXML Label dateTimeLabel;
    private ObjectProperty<EventHandler<MouseEvent>> propertyOnAction = new SimpleObjectProperty<>();
    private Complaint store;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    public void setData(Complaint complaint) {
        setComplaint(complaint);

        topicLabel.setText(complaint.getTopic());
        voteLabel.setText("" + complaint.getVotePoint());
        topicLabel.setUnderline(true);
        dateTimeLabel.setText(complaint.getSubmitTime());
    }

    public void setComplaint(Complaint complaint) {
        store = complaint;
    }

    public Complaint getComplaint() {
        return store;
    }
}

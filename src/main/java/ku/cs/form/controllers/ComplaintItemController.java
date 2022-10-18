package ku.cs.form.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import ku.cs.form.models.Complaint;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ComplaintItemController extends Region implements Initializable {
    @FXML Label topicLabel;
    @FXML Label voteLabel;
    private ObjectProperty<EventHandler<ActionEvent>> myAction = new SimpleObjectProperty<EventHandler<ActionEvent>>();

    @FXML
    public void setData(Complaint complaint) {
        topicLabel.setText(complaint.getTopic());
        voteLabel.setText("" + complaint.getVotePoint());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void click(ActionEvent actionEvent) {
        if (myAction.get() != null) {
            myAction.get().handle(actionEvent);
        }
    }

}

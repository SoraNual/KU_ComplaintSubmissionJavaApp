package ku.cs.form.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.User;
import ku.cs.form.models.UserReport;
import ku.cs.form.services.UserReportDataSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ComplaintItemController implements Initializable {
    @FXML Label topicLabel;
    @FXML Label voteLabel;
    @FXML Label dateTimeLabel;
    @FXML Circle statusCircle;
    private Complaint store;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    public void setData(Complaint complaint) {
        setComplaint(complaint);

        topicLabel.setText(complaint.getTopic());
        voteLabel.setText("" + complaint.getVotePoint());

        String[] data = complaint.getSubmitTime().trim().split(" ");
        dateTimeLabel.setText(data[0] + "\n" + data[1]);

        if (complaint.getStatus().equals("กำลังดำเนินการ")) statusCircle.setFill(Color.rgb(252, 238, 167, 1));
        else if (complaint.getStatus().equals("ดำเนินการเสร็จสิ้น")) statusCircle.setFill(Color.rgb(156, 198, 188, 1));
        else statusCircle.setFill(Color.rgb(255, 88, 88, 1));
    }

    public void setComplaint(Complaint complaint) {
        store = complaint;
    }

    public Complaint getComplaint() {
        return store;
    }
}

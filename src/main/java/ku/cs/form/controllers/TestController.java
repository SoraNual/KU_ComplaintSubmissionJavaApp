package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.form.models.Complaint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// for testing please delete this "file" if i forgor.

public class TestController implements Initializable {
    @FXML
    private VBox itemHolder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Complaint> complaints = new ArrayList<>(complaints());

        System.out.println(complaints.size());
        for (int i = 0; i < complaints.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
//            System.out.println(getClass().getResource("/ku/cs/complaint-item.fxml"));
            fxmlLoader.setLocation(getClass().getResource("/ku/cs/complaint-item.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
//                System.out.println();
//                ComplaintItemController cic = new ComplaintItemController();
//                fxmlLoader.setController(cic);

                ComplaintItemController cic = fxmlLoader.getController();
                cic.setData(complaints.get(i));

                itemHolder.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Complaint> complaints() {
        List<Complaint> complaints = new ArrayList<>();
        Complaint complaint = new Complaint();

//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("a", "b", "c", "d", "e");
//        topicLabel.setText(complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("a", "basdasd", "c", "d", "e");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("a", "b", "cadsadsa", "d", "e");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("aasdasdasd", "b", "c", "d", "e");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("a", "b", "c", "dasdasda", "e");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("a", "b", "c", "d", "easdasdsa");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("asdsadsaa", "b", "c", "d", "e");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        complaint = new Complaint("a", "b", "csadasdas", "d", "e");
//        topicLabel.setText("test" + complaint.getTopic());
//        voteLabel.setText("" + complaint.getVotePoint());
        complaints.add(complaint);

        return complaints;
    }
}

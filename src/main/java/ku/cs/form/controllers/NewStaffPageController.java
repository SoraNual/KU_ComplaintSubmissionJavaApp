package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintList;
import ku.cs.form.models.Staff;
import com.github.saacsos.FXRouter;
import ku.cs.form.services.ReportFileDataSource;

import java.io.IOException;

public class NewStaffPageController {
    @FXML
    private Label agencyLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private ListView<Complaint> itemHolder;
    private ComplaintList complaintList;

    @FXML
    public void initialize() {
        Staff staff = (Staff) FXRouter.getData();
        ReportFileDataSource reportFileDataSource = new ReportFileDataSource("data", "complaints.csv");
        complaintList = reportFileDataSource.readData();

        // setText
        nameLabel.setText(staff.getName());
        agencyLabel.setText(staff.getAgency());

        for (Complaint complaint : complaintList.getAllReports()) {
            itemHolder.getItems().add(complaint);
        }
        handleSelectedItem();

    }

    @FXML
    public void showReportListView() {
//        for (Report report : reportList.getAllReports()) {
//            itemHolder.getItems().add(report);
//        }
    }

    @FXML
    public void handleSelectedItem() {
        itemHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() % 2 == 0) {
                    Complaint complaint = itemHolder.getSelectionModel().getSelectedItem();
                    if (complaint != null) {
                        try {
                            FXRouter.goTo("newReportDetail", complaint);
                        } catch (IOException e) {
                            System.out.println("ไม่สามารถไปที่หน้า ReportDetail ได้");
                        }
                    } else {
                        System.out.println("user click on empty list cell");
                    }
                }
            }
        });
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("newStaffChangePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}

package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;
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
    private ListView<Report> itemHolder;
    private ReportList reportList;

    @FXML
    public void initialize() {
        Staff staff = (Staff) FXRouter.getData();
        ReportFileDataSource reportFileDataSource = new ReportFileDataSource("data", "reports.csv");
        reportList = reportFileDataSource.readData();

        // setText
        nameLabel.setText(staff.getName());
        agencyLabel.setText(staff.getAgency());

        showReportListView();
        handleSelectedItem();

    }

    @FXML
    public void showReportListView() {
        for (Report report : reportList.getAllReports()) {
            itemHolder.getItems().add(report);
        }
    }

    @FXML
    public void handleSelectedItem() {
        itemHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() % 2 == 0) {
                    if (!itemHolder.getItems().isEmpty()) {
                        Report report = itemHolder.getSelectionModel().getSelectedItem();
                        try {
                            FXRouter.goTo("newReportDetail", report);
                        } catch (IOException e) {
                            System.out.println("ไม่สามารถไปที่หน้า ReportDetail ได้");
                        }
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

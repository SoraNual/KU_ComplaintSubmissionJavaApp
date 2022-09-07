package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;
import ku.cs.form.services.ReportFileDataSource;

import java.io.IOException;

public class StaffPageController {
    @FXML private ImageView staffImage;
    @FXML private Label agencyLabel;
    @FXML private ListView<Report> reportsListView;
    private ReportFileDataSource dataSource;
    private ReportList reportList;

    @FXML
    public void initialize() {
        String url = getClass().getResource("/ku/cs/images/zen.jpg").toExternalForm();
        staffImage.setImage(new Image(url));

        dataSource = new ReportFileDataSource("data", "reports.csv");
        reportList = dataSource.readData();
        showReportsListView();
    }

    @FXML
    public void showReportsListView() {
        reportsListView.getItems().addAll(reportList.getAllReports());
        reportsListView.refresh();
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("staffChangePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}

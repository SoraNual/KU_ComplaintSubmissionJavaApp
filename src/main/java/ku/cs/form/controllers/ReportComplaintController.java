package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.*;
import ku.cs.form.services.ComplaintReportDataSource;

import java.io.IOException;
import java.util.ArrayList;

import com.github.saacsos.FXRouter;
import ku.cs.form.services.SetTheme;

public class ReportComplaintController {
    private User nisit;
    private ArrayList<Object> objects = new ArrayList<>();
    @FXML private TextArea detailTextArea;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Label warningLabel;
    private Complaint complaint;
    private ComplaintReportDataSource complaintReportDataSource;
    private ComplaintReportHashMap complaintReportHashMap;
    @FXML private AnchorPane anchorPane;
    private SetTheme setTheme;
    @FXML
    public void initialize(){
        objects = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        warningLabel.setText("");
        nisit = (Nisit) objects.get(0);
        complaint = (Complaint) objects.get(1);

        setTheme = new SetTheme(nisit.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

        complaintReportDataSource = new ComplaintReportDataSource("data","complaintReport.csv");
        complaintReportHashMap = complaintReportDataSource.readData();

        categoryComboBox.getItems().add("ข่าวปลอม");
        categoryComboBox.getItems().add("คำหยาบ");
        categoryComboBox.getItems().add("เนื้อหารุนแรง");
        categoryComboBox.getItems().add("แสปม");
        categoryComboBox.getItems().add("สร้างความวุ่นวาย");
        categoryComboBox.getItems().add("ขายของโดยไม่ได้รับอนุญาติ");
        categoryComboBox.getItems().add("อื่นๆ");
    }
    @FXML
    public void handleReportButton(ActionEvent actionEvent) throws IOException {
        if(categoryComboBox.getValue() == null) {
            warningLabel.setText("บอกหมวดหมู่ด้วย");
        } else if (detailTextArea.getText().trim().equals("")){
            warningLabel.setText("กรุณาใส่บางอย่างลงในรายละเอียดด้วย :<");
        }   else {
                String category = categoryComboBox.getValue();
                String detail = detailTextArea.getText().trim();
                ComplaintReport complaintReport = new ComplaintReport(complaint.getTopic(),
                        complaint.getSubmitTime(),category,detail);
            complaintReportHashMap.addReport(complaintReport);
            complaintReportDataSource.writeData(complaintReportHashMap);
            try {
                com.github.saacsos.FXRouter.goTo("complaintDetailsForNisit", objects);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("complaintDetailsForNisit", objects);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
            e.printStackTrace();
        }
    }

}

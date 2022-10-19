package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.User;
import ku.cs.form.models.UserReport;
import ku.cs.form.models.UserReportHashMap;
import ku.cs.form.services.UserReportDataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ComplaintUserController {
    private User user;
    private ArrayList<Object> objects = new ArrayList<>();
    @FXML private TextArea detailTextArea;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Label warningLabel;
    private UserReportHashMap userReportHashMap;
    private UserReportDataSource userReportDataSource;

    @FXML
    public void initialize(){
        objects = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        user = (User) objects.get(0);

        userReportDataSource = new UserReportDataSource("data","userReport.csv");
        userReportHashMap = userReportDataSource.readData();

        categoryComboBox.getItems().add("ข่าวปลอม");
        categoryComboBox.getItems().add("คำหยาบ");
        categoryComboBox.getItems().add("ชื่อไม่เหมาะสม");
        categoryComboBox.getItems().add("แสปม");
        categoryComboBox.getItems().add("สร้างความวุ่นวาย");
        categoryComboBox.getItems().add("อื่นๆ");
        warningLabel.setText("");
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) throws IOException {
        if(categoryComboBox.getValue() == null) {
            warningLabel.setText("บอกหมวดหมู่ด้วย");
        } else if (detailTextArea.getText().trim().equals("")){
            warningLabel.setText("กรุณาใส่บางอย่างลงในรายละเอียดด้วย BAKA :<");
        }   else {
            UserReport userReport = new UserReport((String) objects.get(1),
                    categoryComboBox.getValue(),
                    detailTextArea.getText().replace("\n", ""),
                    null);
            userReportHashMap.addReport(userReport);
            userReportDataSource.writeData(userReportHashMap);
            try {
                com.github.saacsos.FXRouter.goTo("nisitPage",user);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage", user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}

package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.User;
import ku.cs.form.models.UserReport;
import ku.cs.form.services.UserReportDataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class reportComplaintController {
    private User user;
    private ArrayList<Object> objects = new ArrayList<>();
    @FXML private TextArea detailTextArea;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Label warningLabel;
    private Complaint complaint;
    @FXML
    public void initialize(){
        objects = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        warningLabel.setText("");
        user = (User) objects.get(0);
        complaint = (Complaint) objects.get(1);
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
            warningLabel.setText("กรุณาใส่บางอย่างลงในรายละเอียดด้วย BAKA :<");
        }   else {
            String filePath = "data" + File.separator + "user_reports.csv";
            File file = new File(filePath);
            System.out.println("hey");
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write("เนื้อหา"+ "," +complaint.getTopic()+ "," +complaint.getSubmitTime()+ "," +categoryComboBox.getValue()+detailTextArea.getText().replace("\n","")+"\n");
            buffer.close();
            writer.close();

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

package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.*;
import ku.cs.form.services.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDetailStaffController {
    @FXML private Button inProgressButton;
    @FXML private Button doneButton;
    @FXML private TextArea topicTextArea;
    @FXML private TextArea detailTextArea;
    @FXML private TextArea additionalDetailTextArea;
    @FXML private TextArea agencyTextArea;
    @FXML private ListView<String> responsibleListView;
    @FXML private TextArea solutionTextArea;
    @FXML private Label attachImageLabel;
    @FXML private ImageView attachImage;
    @FXML private AnchorPane anchorPane;
    private ComplaintFileDataSource complaintFileDataSource;
    private ComplaintList complaintList;
    private ArrayList<Object> objects = new ArrayList<>();
    private Staff staff;
    private Complaint complaint;
    private SetTheme setTheme;

    @FXML
    public void initialize() {
        complaintFileDataSource = new ComplaintFileDataSource("data", "complaints.csv");
        complaintList = complaintFileDataSource.readData();

        objects = (ArrayList<Object>) FXRouter.getData();
        staff = (Staff) objects.get(0);
        complaint = (Complaint) objects.get(1);

        setTheme = new SetTheme(staff.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");


        File imageFile = new File(staff.getProfileImageFilePath());
        Image userImage = new Image(imageFile.toURI().toString());
        attachImage.setImage(null);

        topicTextArea.setText(complaint.getTopic());
        detailTextArea.setText(complaint.getBasicDetail());
        additionalDetailTextArea.setText(complaint.getAdditionalDetail());
        agencyTextArea.setText(getStaffAgency().getName());

        if (complaint.getSolution().equals("กำลังตรวจสอบ")) solutionTextArea.setText("");
        else solutionTextArea.setText(complaint.getSolution());

        if(complaint.getStatus().equals("ดำเนินการเสร็จสิ้น")){
            inProgressButton.setDisable(true);
            doneButton.setDisable(true);
            solutionTextArea.setEditable(false);
        }

        showResponsibleListView();
        showAttachImage();
    }

    public Agency getStaffAgency() {
        AgencyDataSource agencyDataSource = new AgencyDataSource("data", "agency.csv");
        AgencyList agencyList = agencyDataSource.readData();

        Agency result = null;
        for (Agency agency : agencyList.getAgencies()) {
            if (agency.getCategory().equals(complaint.getCategory())) {
                result = agency;
            }
        }
        return result;
    }

    @FXML
    public void handleInProgressButton(ActionEvent actionEvent) {
        String solution = solutionTextArea.getText();
        if (solution == null || solution.trim().isEmpty()) {
            showAlert("โปรดใส่ solution ก่อนกดเปลี่ยนสถานะ");
            return;
        }

        complaint.setSolution(solutionTextArea.getText().trim());
        if(!complaint.getAssignedStaff().contains(staff.getName())) complaint.addAssignedStaff(staff);
        complaintFileDataSource.updateData(complaint, solution.trim(), "กำลังดำเนินการ");
        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML public void handleDoneButton(ActionEvent actionEvent) {
        if (solutionTextArea.getText() == null || solutionTextArea.getText().trim().isEmpty()) {
            showAlert("โปรดใส่ solution ก่อน");
            return;
        }

        String solution = solutionTextArea.getText();
        System.out.println(solution);
        complaint.setSolution(solutionTextArea.getText().trim());
        if(!complaint.getAssignedStaff().contains(staff.getName())) complaint.addAssignedStaff(staff);
        complaintFileDataSource.updateData(complaint, solution.trim(), "ดำเนินการเสร็จสิ้น");

        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }

        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML
    public static void showAlert(String errMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(errMsg);
        alert.showAndWait();
    }

    @FXML
    public void showResponsibleListView() {
        responsibleListView.getItems().clear();

        ArrayList<String> assignedStaffs = new ArrayList<>();
        assignedStaffs.addAll(List.of(complaint.getAssignedStaff().split("#")));
        for(String eachStaff: assignedStaffs){
            if(eachStaff.contains(staff.getAgency())) responsibleListView.getItems().add(eachStaff);
        }
        responsibleListView.refresh();
    }

    @FXML
    public void showAttachImage() {
        ComplaintCategoryDataSource complaintCategoryDataSource = new ComplaintCategoryDataSource("data", "complaintCategories.csv");
        ComplaintCategoryList categoryList = complaintCategoryDataSource.readData();
        ComplaintCategory complaintCategory = null;

        for (ComplaintCategory category : categoryList.getAllCategories()) {
            if (category.getName().equals(complaint.getCategory())) {
                complaintCategory = category;
            }
        }
        if(complaintCategory != null && complaintCategory.getImageNeeded()){
            String filename = complaint.getSubmitTime().replace(":","-") + "_"
                    + complaint.getComplainantUsername() + "_" + complaint.getTopic() +".jpg";
            String url = "data" + File.separator + "img" + File.separator + "complaint" + File.separator
                    + filename;
            File imgFile = new File(url);
            Image img = new Image(imgFile.toURI().toString());
            attachImage.setImage(img);
        }
    }
}

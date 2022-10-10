package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ku.cs.form.models.Complaint;
import ku.cs.form.models.ComplaintCategory;
import ku.cs.form.models.ComplaintCategoryList;
import ku.cs.form.models.User;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.SetTheme;

import java.io.IOException;
import java.util.ArrayList;

public class ComplaintDetailNisitController {
    private Complaint selectedComplaint;
    private User user;
    private ArrayList<Object> objects = new ArrayList<>();
    private ComplaintCategory category;
    private ComplaintCategoryList categoryList;
    private ComplaintCategoryDataSource complaintCategoryDataSource;
    @FXML private Label topicLabel;
    @FXML private Label categoryLabel;
    @FXML private Label additionalInformationTopicLabel;
    @FXML private Label additionalImageTopicLabel;
    @FXML private Label statusLabel;
    @FXML private Label statusLabelShadow;
    @FXML private Button backButton;
    @FXML private TextArea basicDetailTextArea;
    @FXML private TextArea additionalDetailTextArea;
    @FXML private TextArea solutionTextArea;
    @FXML private ImageView additionalImg;
    @FXML public void initialize(){
        objects = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        user = (User) objects.get(0);
        selectedComplaint = (Complaint) objects.get(1);
        System.out.println(user);
        System.out.println(selectedComplaint);

        complaintCategoryDataSource = new ComplaintCategoryDataSource("data","complaintCategories.csv");
        categoryList = complaintCategoryDataSource.readData();

        for(ComplaintCategory complaintCategory: categoryList.getAllCategories()){
            if(complaintCategory.getName().equals(selectedComplaint.getCategory())){
                category = complaintCategory;
                break;
            }
        }

        topicLabel.setText(selectedComplaint.getTopic());
        categoryLabel.setText("หมวดหมู่: " + selectedComplaint.getCategory());
        basicDetailTextArea.setText(selectedComplaint.getBasicDetailWithNewLine());
        additionalInformationTopicLabel.setText(category.getAdditionalDetailTitle());
        additionalDetailTextArea.setText(selectedComplaint.getAdditionalDetailWithNewLine());
        solutionTextArea.setText(selectedComplaint.getSolution());

        if(category.getImageNeeded()){
            additionalImageTopicLabel.setText(category.getAdditionalImageTitle());
            String url = "/img/complaint/" + selectedComplaint.getTopic() + ".jpg";
            additionalImg.setImage(new Image(getClass().getResource(url).toExternalForm()));
        }else{
            additionalImageTopicLabel.setText("");
        }

        statusLabel.setText(selectedComplaint.getStatus());
        statusLabelShadow.setText(selectedComplaint.getStatus());
        if(selectedComplaint.getStatus().equals("รอการตรวจสอบจากเจ้าหน้าที่")) statusLabel.setTextFill(Color.RED);
        else if(selectedComplaint.getStatus().equals("กำลังดำเนินการ")) statusLabel.setTextFill(Color.YELLOW);
        else statusLabel.setTextFill(Color.GREEN);

        theme();
    }
    public void theme() {
        SetTheme setTheme = new SetTheme(user);
        setTheme.setObject(backButton);
        setTheme.setObject(basicDetailTextArea);
        setTheme.setObject(additionalDetailTextArea);
    }

    @FXML public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage", user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

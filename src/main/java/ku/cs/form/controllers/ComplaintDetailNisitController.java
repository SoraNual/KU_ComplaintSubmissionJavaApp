package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import ku.cs.form.models.*;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ComplaintDetailNisitController {
    @FXML private AnchorPane anchorPane;
    private Complaint selectedComplaint;
    private User nisit;
    private ArrayList<Object> objects = new ArrayList<>();
    private ComplaintCategory category;
    private ComplaintCategoryList categoryList;
    private ComplaintCategoryDataSource complaintCategoryDataSource;
    @FXML private Label topicLabel;
    @FXML private Label categoryLabel;
    @FXML private Label additionalInformationTopicLabel;
    @FXML private Label additionalImageTopicLabel;
    @FXML private Label statusLabel;
    @FXML private Label voteLabel;
    @FXML private Button backButton;
    @FXML private Button reportUserButton;
    @FXML private Button reportComplaintButton;
    @FXML private TextArea basicDetailTextArea;
    @FXML private TextArea additionalDetailTextArea;
    @FXML private TextArea solutionTextArea;
    @FXML private ImageView additionalImg;
    private SetTheme setTheme;
    @FXML public void initialize(){
        objects = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        nisit = (Nisit) objects.get(0);
        selectedComplaint = (Complaint) objects.get(1);
        System.out.println(nisit);
        System.out.println(selectedComplaint);

        setTheme = new SetTheme(nisit.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

        complaintCategoryDataSource = new ComplaintCategoryDataSource("data","complaintCategories.csv");
        categoryList = complaintCategoryDataSource.readData();

        for(ComplaintCategory complaintCategory: categoryList.getAllCategories()){
            if(complaintCategory.getName().equals(selectedComplaint.getCategory())){
                category = complaintCategory;
                break;
            }
        }

        voteLabel.setText("");
        topicLabel.setText(selectedComplaint.getTopic());
        categoryLabel.setText("หมวดหมู่: " + selectedComplaint.getCategory());
        basicDetailTextArea.setText(selectedComplaint.getBasicDetailWithNewLine());
        additionalInformationTopicLabel.setText(category.getAdditionalDetailTitle());
        additionalDetailTextArea.setText(selectedComplaint.getAdditionalDetailWithNewLine());
        solutionTextArea.setText(selectedComplaint.getSolution());

        if(category.getImageNeeded()){
            additionalImageTopicLabel.setText(category.getAdditionalImageTitle());
            String filename = selectedComplaint.getSubmitTime().replace(":","-") + "_"
                    + selectedComplaint.getComplainantUsername() + "_" + selectedComplaint.getTopic() +".jpg";
            String url = "data" + File.separator + "img" + File.separator + "complaint" + File.separator
                    + filename;
            File imgFile = new File(url);
            Image img = new Image(imgFile.toURI().toString());
            additionalImg.setImage(img);
        }else{
            additionalImageTopicLabel.setText("");
        }

        statusLabel.setText(selectedComplaint.getStatus());
        if(selectedComplaint.getStatus().equals("รอการตรวจสอบจากเจ้าหน้าที่")) statusLabel.setTextFill(Color.RED);
        else if(selectedComplaint.getStatus().equals("กำลังดำเนินการ")) statusLabel.setTextFill(Color.YELLOW);
        else statusLabel.setTextFill(Color.GREEN);


    }


    @FXML public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage", nisit);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleReportUserButton(){
        ArrayList<Object> object = new ArrayList<>();
        object.add(nisit);
        object.add(selectedComplaint);
        try {
            com.github.saacsos.FXRouter.goTo("reportUser",object);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML public void handleReportComplaintButton(){
        ArrayList<Object> object = new ArrayList<>();
        object.add(nisit);
        object.add(selectedComplaint);
        try {
            com.github.saacsos.FXRouter.goTo("reportComplaint",object);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML public void handleDownVoteButton(){
        voteLabel.setText(selectedComplaint.addNegativeVote(nisit));
        System.out.println(selectedComplaint.getPositiveVoter());
        System.out.println(selectedComplaint.getNegativeVoter());
        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data","complaints.csv");
        complaintFileDataSource.changeData(selectedComplaint);
    }
    @FXML public void handleUpVoteButton(){
        voteLabel.setText(selectedComplaint.addPositiveVote(nisit));
        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data","complaints.csv");
        complaintFileDataSource.changeData(selectedComplaint);;
    }
}

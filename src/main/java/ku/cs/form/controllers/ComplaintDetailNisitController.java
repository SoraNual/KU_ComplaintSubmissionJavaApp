package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ku.cs.form.models.*;
import ku.cs.form.services.ComplaintCategoryDataSource;
import ku.cs.form.services.ComplaintFileDataSource;
import ku.cs.form.services.SetTheme;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ComplaintDetailNisitController {

    public HBox pane;
    public Circle statusCircle;
    public Label dateTimeLabel;
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
    @FXML private Label voteLabel;
    @FXML private Button backButton;
    @FXML private Button reportUserButton;
    @FXML private Button reportComplaintButton;
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
                    + user.getUsername() + "_" + selectedComplaint.getTopic() +".jpg";
            String url = "data" + File.separator + "img" + File.separator + "complaint" + File.separator
                    + filename;
            File imgFile = new File(url);
            Image img = new Image(imgFile.toURI().toString());
            additionalImg.setImage(img);
        }else{
            additionalImageTopicLabel.setText("");
        }

        statusLabel.setText(selectedComplaint.getStatus());
        statusLabelShadow.setText(selectedComplaint.getStatus());
        if(selectedComplaint.getStatus().equals("รอการตรวจสอบจากเจ้าหน้าที่")) statusLabel.setTextFill(Color.RED);
        else if(selectedComplaint.getStatus().equals("กำลังดำเนินการ")) statusLabel.setTextFill(Color.YELLOW);
        else statusLabel.setTextFill(Color.GREEN);


    }


    @FXML public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage", user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleReportUserButton(){
        ArrayList object = new ArrayList<>();
        object.add(user);
        object.add(selectedComplaint.getComplainantUsername());
        try {
            com.github.saacsos.FXRouter.goTo("reportUser",object);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML public void handleReportComplaintButton(){
        ArrayList object = new ArrayList<>();
        object.add(user);
        object.add(selectedComplaint);
        try {
            com.github.saacsos.FXRouter.goTo("reportComplaint",object);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML public void handleDownVoteButton(){
        voteLabel.setText(selectedComplaint.addNegativeVote(user));
        System.out.println(selectedComplaint.getPositiveVoter());
        System.out.println(selectedComplaint.getNegativeVoter());
        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data","complaints.csv");
        complaintFileDataSource.changeData(selectedComplaint);
    }
    @FXML public void handleUpVoteButton(){
        voteLabel.setText(selectedComplaint.addPositiveVote(user));
        ComplaintFileDataSource complaintFileDataSource = new ComplaintFileDataSource("data","complaints.csv");
        complaintFileDataSource.changeData(selectedComplaint);;
    }
}

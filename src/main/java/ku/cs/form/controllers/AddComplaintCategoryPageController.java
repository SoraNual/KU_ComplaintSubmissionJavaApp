package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.form.models.ComplaintCategory;
import ku.cs.form.models.ComplaintCategoryList;
import ku.cs.form.services.ComplaintCategoryDataSource;

import java.io.IOException;

public class AddComplaintCategoryPageController {
    @FXML private Label additionalImageTopicLabel;
    @FXML private Label errorLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField additionalDetailTopicTextField;
    @FXML private TextField additionalImageTextField;
    @FXML private CheckBox imageNeededCheckBox;
    @FXML private ListView<ComplaintCategory> categoryListView;
    private ComplaintCategoryList categoryList;
    private ComplaintCategoryDataSource dataSource;

    @FXML private void initialize(){
        errorLabel.setText("");
        additionalImageTopicLabel.setVisible(false);
        additionalImageTextField.setVisible(false);

        dataSource = new ComplaintCategoryDataSource("data","complaintCategories.csv");
        categoryList = dataSource.readData();

        handleImageNeededCheckBox();
        showCategoryListView();
    }

    private void handleImageNeededCheckBox(){
        imageNeededCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){// selected
                    additionalImageTopicLabel.setVisible(true);
                    additionalImageTextField.setVisible(true);
                }else{// unselected
                    additionalImageTextField.clear();
                    additionalImageTopicLabel.setVisible(false);
                    additionalImageTextField.setVisible(false);
                }
            }
        });
    }

    private void showCategoryListView(){
        categoryListView.getItems().clear();
        categoryListView.getItems().addAll(categoryList.getAllCategories());
        categoryListView.refresh();
    }

    @FXML private void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML private void handleAddButton(){
        String name = nameTextField.getText().trim();
        String additionalTopic = additionalDetailTopicTextField.getText().trim();
        String additionalImageTopic = null;
        Boolean imageNeeded = imageNeededCheckBox.isSelected();
        if(imageNeeded){
            additionalImageTopic = additionalImageTextField.getText().trim();
        }
        if(checkCategoryNameDuplication(name)){
            ComplaintCategory newComplaintCategory = new ComplaintCategory(name, additionalTopic, imageNeeded, additionalImageTopic);
            categoryList.addCategory(newComplaintCategory);
            dataSource.writeData(categoryList);

            nameTextField.clear();
            additionalDetailTopicTextField.clear();
            additionalImageTextField.clear();
            imageNeededCheckBox.setSelected(false);
            showCategoryListView();
        }else {
            errorLabel.setText("หมวดหมู่ซ้ำ");
        }
    }

    private boolean checkCategoryNameDuplication(String newCategory){
        for (ComplaintCategory category: categoryList.getAllCategories()){
            if(newCategory.equals(category.getName())) return false;
        }
        return true;
    }
}

package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddComplaintCategoryPageController {
    @FXML
    Label additionalImageTopicLabel;
    @FXML
    TextField nameTextField;
    @FXML TextField additionalDetailTopicTextField;
    @FXML TextField additionalImageTextField;
    @FXML
    CheckBox imageNeededCheckBox;

    @FXML private void initialize(){
        additionalImageTopicLabel.setVisible(false);
        additionalImageTextField.setVisible(false);

        handleImageNeededCheckBox();
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

    @FXML private void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("admin");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML private void handleAddButton(){

    }
}

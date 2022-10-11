package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ku.cs.form.models.*;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserDataSource;

public class EditProfileController {


    private User user;
    @FXML private Color rectangleColor;
    @FXML private Color textColor;
    @FXML private Color backgroundColor;
    @FXML private Color borderColor;
    @FXML private Color buttonColor;
    @FXML private ColorPicker rectangleColorPicker;
    @FXML private ColorPicker textColorPicker;
    @FXML private ColorPicker backgroundColorPicker;
    @FXML private ColorPicker buttonColorPicker;
    @FXML private Rectangle rightRec;
    @FXML private Rectangle leftRec;
    @FXML private Rectangle sampleRec;
    @FXML private Rectangle sampleBackground;
    @FXML private Rectangle sampleListView;
    @FXML private Rectangle sampleButton;
    @FXML private Button confirmButton;
    @FXML private Button backButton;
    @FXML private Pane pane;
    @FXML private Label sampleLabel;
    @FXML private Label changeRectangleColorLabel;
    @FXML private Label changeTextColorLabel;
    @FXML private Label changeButtonColorLabel;
    @FXML private Label changeBackgroundColorLabel;
    private UserDataSource userDataSource;
    private UserList userList;
    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        rectangleColor = Color.web(user.getRectangleColor().toLowerCase().replace("#","0x"));
        backgroundColor = Color.web(user.getBackgroundColor().toLowerCase().replace("#","0x"));
        textColor = Color.web(user.getTextColor().toLowerCase().replace("#","0x"));
        buttonColor = Color.web(user.getButtonColor().toLowerCase().replace("#","0x"));

        setUpTheme();

        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
    }

    public void setUpTheme(){
        SetTheme theme = new SetTheme(user);

        theme.setObject(rightRec);
        theme.setObject(leftRec);
        theme.setObject(sampleRec);

        theme.setObject(changeBackgroundColorLabel);
        theme.setObject(changeRectangleColorLabel);
        theme.setObject(changeTextColorLabel);
        theme.setObject(changeButtonColorLabel);
        theme.setObject(sampleLabel);
        theme.setObject(changeButtonColorLabel);

        theme.setObject(pane);

        theme.setObject(confirmButton);
        theme.setObject(backButton);

        theme.setObject(textColorPicker);
        theme.setObject(rectangleColorPicker);
        theme.setObject(backgroundColorPicker);
        theme.setObject(buttonColorPicker);

        sampleBackground.setFill(backgroundColor);
        sampleButton.setFill(buttonColor);
        sampleListView.setFill(rectangleColor);


        sampleRec.setStroke(rectangleColor);
    }



    public void handleBackButton(ActionEvent actionEvent) {
        try{
            com.github.saacsos.FXRouter.goTo("nisitPage",user);
        } catch (Exception e){
            System.out.println("Path มีปัญหาละพ่อหนุ่ม");
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent){
        user.setRectangleColor(("#"+rectangleColor).replace("0x","").toUpperCase());
        user.setBackgroundColor(("#"+backgroundColor).replace("0x","").toUpperCase());
        user.setTextColor(("#"+textColor).replace("0x","").toUpperCase());
        user.setButtonColor(("#"+buttonColor).replace("0x","").toUpperCase());
        userDataSource.changeData(user);
        if(user instanceof Admin) {
            try {
                com.github.saacsos.FXRouter.goTo("admin", user);
            } catch (Exception e) {
                System.out.println("Path มีปัญหาละพ่อหนุ่ม");
            }
        } else if (user instanceof Staff) {
            try {
                com.github.saacsos.FXRouter.goTo("staffPage", user);
            } catch (Exception e) {
                System.out.println("Path มีปัญหาละพ่อหนุ่ม");
            }
        } else if (user instanceof Nisit) {
            try {
                com.github.saacsos.FXRouter.goTo("nisitPage", user);
            } catch (Exception e) {
                System.out.println("Path มีปัญหาละพ่อหนุ่ม");
            }
        }
    }

    public void handleRectangleColorPickerButton(ActionEvent actionEvent){
        rectangleColor = rectangleColorPicker.getValue();
        sampleRec.setFill(rectangleColor);
        sampleRec.setStroke(rectangleColor);
        sampleListView.setFill(rectangleColor);
    }

    public void handleBackgroundColorPickerButton(ActionEvent actionEvent){
        backgroundColor = backgroundColorPicker.getValue();
        sampleBackground.setFill(backgroundColor);
    }
    public void handleTextColorPickerButton(ActionEvent actionEvent){
        textColor = textColorPicker.getValue();
        sampleLabel.setTextFill(textColor);
    }


    public void handleButtonColorPickerButton(ActionEvent actionEvent){
        buttonColor = buttonColorPicker.getValue();
        sampleButton.setFill(buttonColor);
    }

}


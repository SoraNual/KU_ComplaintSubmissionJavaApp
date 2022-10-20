package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ku.cs.form.models.*;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserDataSource;

public class EditProfileController {
    private User user;
    @FXML private ComboBox<String> themeComboBox;
    @FXML private  ComboBox<String> fontSizeComboBox;
    @FXML private ComboBox<String> fontComboBox;
    @FXML private VBox customThemeSetting;
    @FXML private AnchorPane anchorPane;

    private UserDataSource userDataSource;
    private UserList userList;
    private SetTheme setTheme;
    //ColorPicker
    @FXML private ColorPicker backgroundColorPicker;
    @FXML private ColorPicker buttonColorPicker;
    @FXML private ColorPicker inputColorPicker;
    @FXML private ColorPicker listviewColorPicker;
    @FXML private ColorPicker menuBarColorPicker;
    @FXML private ColorPicker menuBarTextColorPicker;
    @FXML private ColorPicker textColorPicker;
    //Sample
    @FXML private Button sampleButton;
    @FXML private ComboBox<String> sampleComboBox;
    @FXML private ListView<String> sampleListView;
    @FXML private Rectangle sampleMenuBar;
    @FXML private Label sampleMenuBarText;
    @FXML private Label sampleText;
    @FXML private TextField sampleTextField;
    @FXML private Rectangle samplePanel;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        userDataSource = new UserDataSource("data","users.csv");
        fontSizeComboBox.getItems().addAll("Small","Medium","Large");
        fontComboBox.getItems().addAll("Kanit","Sarabun","Mitr");
        setTheme = new SetTheme(user.getUsername());
        setTheme.setting();
        addThemeComboBox();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");
        userList = userDataSource.readData();
        setUserSettingInComboBox();

    }

    private void addThemeComboBox() {
        themeComboBox.getItems().addAll("dark","default");
        if(setTheme.hasCustomTheme())  themeComboBox.getItems().addAll(user.getUsername(),"custom your theme...");
        else themeComboBox.getItems().add("make your theme...");
    }


    public void handleBackButton(ActionEvent actionEvent) {
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

    public void handleConfirmButton(ActionEvent actionEvent){

        String menuBarColor = colorToString(menuBarColorPicker.getValue());
        String menuBarText = colorToString(menuBarTextColorPicker.getValue());
        String textColor = colorToString(textColorPicker.getValue());
        String backgroundColor = colorToString(backgroundColorPicker.getValue());
        String inputColor = colorToString(inputColorPicker.getValue());
        String listviewColor = colorToString(listviewColorPicker.getValue());
        String buttonColor = colorToString(buttonColorPicker.getValue());

        String[] newTheme = {menuBarColor,
                menuBarText,
                textColor,
                backgroundColor,
                inputColor,
                listviewColor,
                buttonColor };
        setTheme.changeCustomThemeColor(newTheme);

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

    @FXML private void handleThemeComboBox(ActionEvent actionEvent){
        String theme = themeComboBox.getValue();
        if(theme.equals("custom your theme...") || theme.equals("make your theme..."))
        {
            customThemeSetting.setVisible(true);
        }
        else {
            customThemeSetting.setVisible(false);
            setTheme.changeTheme(theme);
            anchorPane.getStylesheets().clear();
            anchorPane.getStylesheets().add("file:src/main/resources/ku/cs/styles/styles.css");
        }
    }

    @FXML private void handleFontSizeComboBox(ActionEvent actionEvent){
        String size = fontSizeComboBox.getValue();
        int fontSize = 0;
        if(size.equals("Small")) fontSize = 90;
        if(size.equals("Medium")) fontSize = 100;
        if(size.equals("Large")) fontSize = 125;
        setTheme.changeTextSize(""+fontSize);
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");
    }

    @FXML private void handleFontComboBox(ActionEvent actionEvent){
        String font = fontComboBox.getValue();
        setTheme.changeFont(font);
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");
    }

    @FXML private void handleBackgroundColorPicker(ActionEvent actionEvent) { samplePanel.setFill(backgroundColorPicker.getValue());}
    @FXML private void handleButtonColorPicker(ActionEvent actionEvent) {
        Color color = buttonColorPicker.getValue();
        sampleButton.setStyle("-fx-background-color: " + colorToString(color));
    }
    @FXML private void handleInputColorPicker(ActionEvent actionEvent) {
        Color color = inputColorPicker.getValue();
        sampleTextField.setStyle("-fx-background-color: " + colorToString(color));
        sampleComboBox.setStyle("-fx-background-color: " + colorToString(color));
    }
    @FXML private void handleListviewColorPicker(ActionEvent actionEvent) {
        Color color = listviewColorPicker.getValue();
        sampleListView.setStyle("-fx-control-inner-background: " + colorToString(color));
    }
    @FXML private void handleMenuBarColorPicker(ActionEvent actionEvent) { sampleMenuBar.setFill(menuBarColorPicker.getValue()); }
    @FXML private void handleMenuBarTextColorPicker(ActionEvent actionEvent) { sampleMenuBarText.setTextFill(menuBarTextColorPicker.getValue());}
    @FXML private void handleTextColorPicker(ActionEvent actionEvent) { sampleText.setTextFill(textColorPicker.getValue());}

    private String colorToString(Color color) {
        return ("#"+color).replace("0x","").toUpperCase();
    }

    private void setUserSettingInComboBox() {
        String[] setting = setTheme.setting();
        themeComboBox.setValue(setting[0]);
        String textSize = "";
        if(setting[1].equals("90")) textSize = "Small";
        if(setting[1].equals("100")) textSize = "Medium";
        if(setting[1].equals("125")) textSize = "Large";
       fontSizeComboBox.setValue(textSize);
        fontComboBox.setValue(setting[2]);
    }

}


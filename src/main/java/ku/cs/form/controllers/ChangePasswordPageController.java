package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.Nisit;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;

public class ChangePasswordPageController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView staffRegisPic;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField oldPasswordPasswordField;
    @FXML
    private PasswordField newPasswordPasswordField;
    @FXML
    private PasswordField confirmPasswordPasswordField;
    private SetTheme setTheme;
    private User user;
    private UserDataSource userDataSource;

    @FXML
    public void initialize() {
        userDataSource = new UserDataSource("data", "users.csv");

        user = (User) FXRouter.getData();
        setTheme = new SetTheme(user.getUsername());
        setTheme.setting();
        anchorPane.getStylesheets().setAll("file:src/main/resources/ku/cs/styles/styles.css");

    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        if(user instanceof Nisit){
            try {
                FXRouter.goTo("nisitPage", user);
            } catch (IOException e) {
                System.out.println("ไม่สามารถกลับหน้า nisit Page ได้");
            }
        } else if (user instanceof Staff) {
            try {
                FXRouter.goTo("newStaff", user);
            } catch (IOException e) {
                System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
            }
        }
        else{
            try {
                FXRouter.goTo("admin", user);
            } catch (IOException e) {
                System.out.println("ไม่สามารถกลับหน้า admin Page ได้");
            }
        }

    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        String errMsg = "";
        String username = usernameTextField.getText();
        String oldPassword = oldPasswordPasswordField.getText();
        String newPassword = newPasswordPasswordField.getText();
        String confirmPassword = confirmPasswordPasswordField.getText();

        errMsg = inputCheck(username, oldPassword, newPassword, confirmPassword);
        if (!errMsg.isEmpty()) {
            showAlert(errMsg, "UNSUCCESSFUL");
            return;
        }

        User updateUser = userDataSource.usernamePasswordCheck(username, oldPassword);
        if (updateUser != null) {
            if (newPassword.equals(confirmPassword)) {
                updateUser.setPassword(newPassword);

                userDataSource.changeData(updateUser);
                showAlert("เปลี่ยนรหัสผ่านสำเร็จ","SUCCESSFUL");
                if(user instanceof Nisit){
                    try {
                        FXRouter.goTo("nisitPage", user);
                    } catch (IOException e) {
                        System.out.println("ไม่สามารถกลับหน้า nisit Page ได้");
                    }
                } else if (user instanceof Staff) {
                    try {
                        FXRouter.goTo("newStaff", user);
                    } catch (IOException e) {
                        System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
                    }
                }
                else{
                    try {
                        FXRouter.goTo("admin", user);
                    } catch (IOException e) {
                        System.out.println("ไม่สามารถกลับหน้า admin Page ได้");
                    }
                }
            } else {
                showAlert("คอนเฟิร์มรหัสผ่านไม่ตรงกับรหัสผ่านใหม่ โปรดเช็คอีกครั้ง", "UNSUCCESSFUL");
            }
        } else {
            showAlert("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง โปรดเช็คอีกครั้ง", "UNSUCCESSFUL");
        }
    }

    public String inputCheck(String username, String oldPassword, String newPassword, String confirmPassword) {
        String result = "";
        if (username.isEmpty()) result += "อย่าลืมใส่ username!\n";
        if (oldPassword.isEmpty()) result += "อย่าลืมใส่รหัสผ่าน!\n";
        if (newPassword.isEmpty()) result += "อย่าลืมใส่รหัสผ่านใหม่!\n";
        if (confirmPassword.isEmpty()) result += "อย่าลืมคอนเฟิร์มรหัสผ่านใหม่!\n";

        return result;
    }

    public void accountValidation(String username, String password) {

    }

    public static void showAlert(String message, String status) {
        Alert alert;
        if (status == "UNSUCCESSFUL") {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("เปลี่ยนรหัสผ่านไม่สำเร็จ");
        } else {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("เปลี่ยนรหัสผ่านสำเร็จ");
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


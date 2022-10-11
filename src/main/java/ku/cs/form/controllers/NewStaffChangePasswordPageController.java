package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;

public class NewStaffChangePasswordPageController {
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
    private User user;
    private UserList userList;
    private UserDataSource userDataSource;

    @FXML
    public void initialize() {
        userDataSource = new UserDataSource("data", "users.csv");
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
                try {
                    FXRouter.goTo("home");
                } catch (IOException e) {
                    System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
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
            alert.setTitle("Changed Password Unsuccessful");
        } else {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Changed Password Successful");
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


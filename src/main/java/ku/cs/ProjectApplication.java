package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        FXRouter.bind(this, stage, "Project", 1280, 720);
        configRoute();
        FXRouter.setAnimationType("fade",500);
        FXRouter.goTo("home");
    }
    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("manageAgency",packageStr+"manage-agency.fxml");
        FXRouter.when("userComplaint",packageStr+"user-complaint.fxml");
        FXRouter.when("home", packageStr+"home.fxml");
        FXRouter.when("staffRegister", packageStr+"staff-register.fxml");
        FXRouter.when("admin", packageStr+"admin.fxml");
        FXRouter.when("loginTime", packageStr+"all-user-page.fxml");
        FXRouter.when("login", packageStr+"login-page.fxml");
        FXRouter.when("creatorTeam",packageStr+"creator-team.fxml");
        FXRouter.when("register", packageStr+"register.fxml");
        FXRouter.when("staffPage", packageStr+"staff-page.fxml");
        FXRouter.when("staffChangePassword", packageStr+"staff-change-password-page.fxml");
        FXRouter.when("reportDetail", packageStr+"report-detail.fxml");
        FXRouter.when("report", packageStr+"complaint.fxml");
        FXRouter.when("nisitPage", packageStr+"nisit-page.fxml");
        FXRouter.when("editProfile",packageStr+"edit-profile.fxml");
        FXRouter.when("newStaff", packageStr+"new-staff-page.fxml");
        FXRouter.when("newStaffChangePassword", packageStr+"new-staff-change-password.fxml");
        FXRouter.when("newReportDetail", packageStr+"new-complaint-detail-staff.fxml");
        FXRouter.when("changePasswordNisit",packageStr+"nisit-change-password.fxml");
        FXRouter.when("complaintsDetailsForNisit",packageStr + "complaint-detail-nisit.fxml");
        FXRouter.when("addCategory",packageStr + "add-complaint-category.fxml");
        FXRouter.when("reportUser",packageStr+"report-user.fxml");
        FXRouter.when("reportComplaint",packageStr+"report-complaint.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}

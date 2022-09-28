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
        FXRouter.bind(this, stage, "Project", 1280, 720);
        configRoute();
        FXRouter.goTo("newReportDetail");
    }
    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr+"home.fxml");
        FXRouter.when("staffRegister", packageStr+"staff-register.fxml");
        FXRouter.when("loginTime", packageStr+"login-time-page.fxml");
        FXRouter.when("login", packageStr+"login-page.fxml");
        FXRouter.when("creatorTeam",packageStr+"creator_team.fxml");
        FXRouter.when("register", packageStr+"register.fxml");
        FXRouter.when("staffPage", packageStr+"staff-page.fxml");
        FXRouter.when("staffChangePassword", packageStr+"staff-change-password-page.fxml");
        FXRouter.when("reportDetail", packageStr+"report-detail.fxml");
        FXRouter.when("newStaff", packageStr+"new-staff-page.fxml");
        FXRouter.when("newStaffChangePassword", packageStr+"new-staff-change-password.fxml");
        FXRouter.when("newReportDetail", packageStr+"new-report-detail.fxml");



    }

    public static void main(String[] args) {
        launch();
    }
}

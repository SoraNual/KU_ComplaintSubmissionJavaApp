package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Project", 1280, 720);
        configRoute();
        FXRouter.goTo("admin");
    }
    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr+"home.fxml");
        FXRouter.when("staffRegister", packageStr+"staff-register.fxml");
        FXRouter.when("admin", packageStr+"admin.fxml");
        FXRouter.when("loginTime", packageStr+"login-time-page.fxml");
        FXRouter.when("login", packageStr+"login-page.fxml");
        FXRouter.when("creatorTeam",packageStr+"creator_team.fxml");
        FXRouter.when("register", packageStr+"register.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}

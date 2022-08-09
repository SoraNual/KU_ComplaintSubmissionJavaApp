package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CreatorTeamController {
    @FXML
    private ImageView donutImage;
    @FXML
    private ImageView joImage;
    @FXML
    private ImageView zenImage;
    @FXML
    private ImageView bezImage;

    public CreatorTeamController() {
    }

    @FXML
    public void initialize() {
        String donutImageURL = getClass().getResource("/ku/cs/images/donut.jpg").toExternalForm();
        String joImageURL = getClass().getResource("/ku/cs/images/jo.jpg").toExternalForm();
        String bezImageURL = getClass().getResource("/ku/cs/images/bez.jpg").toExternalForm();
        String zenImageURL = getClass().getResource("/ku/cs/images/zen.jpg").toExternalForm();
        donutImage.setImage(new Image(donutImageURL));
        joImage.setImage(new Image(joImageURL));
        bezImage.setImage(new Image(bezImageURL));
        zenImage.setImage(new Image(zenImageURL));
    }
    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
}

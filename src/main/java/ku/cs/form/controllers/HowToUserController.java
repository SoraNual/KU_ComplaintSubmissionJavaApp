package ku.cs.form.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class HowToUserController {
    @FXML public ImageView imgView;
    public int page;
    public String tutorial;
    @FXML
    public void initialize() {
        page = 1;
        tutorial = getClass().getResource("/ku/cs/images/KU_ (1)_page-000"+page+".jpg").toExternalForm();
        imgView.setImage(new Image(tutorial));
    }

    @FXML
    public void handlePreviousButton(){

        if(page>1 && page<=10){
            page-=1;
            tutorial = getClass().getResource("/ku/cs/images/KU_ (1)_page-000"+page+".jpg").toExternalForm();
            imgView.setImage(new Image(tutorial));
        } else if (page>10) {
            page-=1;
            tutorial = getClass().getResource("/ku/cs/images/KU_ (1)_page-00"+page+".jpg").toExternalForm();
            imgView.setImage(new Image(tutorial));
        }
    }
    @FXML
    public void handleNextButton(){

        if(page<26) {
            page+=1;
            if (page > 1 && page < 10) {
                tutorial = getClass().getResource("/ku/cs/images/KU_ (1)_page-000" + page + ".jpg").toExternalForm();
                imgView.setImage(new Image(tutorial));
            } else if (page >= 10) {
                tutorial = getClass().getResource("/ku/cs/images/KU_ (1)_page-00" + page + ".jpg").toExternalForm();
                imgView.setImage(new Image(tutorial));
            }
        }
    }
    @FXML
    public void handleBackButton() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

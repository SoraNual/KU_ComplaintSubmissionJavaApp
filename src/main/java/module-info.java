module cs.ku {
    requires javafx.controls;
    requires javafx.fxml;


    opens ku.cs to javafx.fxml;
    exports ku.cs;

    exports ku.cs.form.controllers;
    opens ku.cs.form.controllers to javafx.fxml;
}
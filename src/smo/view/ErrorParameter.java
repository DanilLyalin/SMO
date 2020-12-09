package smo.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorParameter {

    private static String error;

    public static void GetError(String err){
        error = err;
    }

    @FXML
    private Text f_errText;

    @FXML
    private Button b_OK;

    @FXML
    void initialize() {
        f_errText.setText(error);
        b_OK.setOnAction(event -> {
            Stage stage = ((Stage) b_OK.getScene().getWindow());
            stage.close();
        });
    }

}

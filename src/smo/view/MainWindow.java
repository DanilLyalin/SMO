package smo.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {

    @FXML
    private Button b_SetParameters;

    @FXML
    private Button b_AutoMode;

    @FXML
    private Button b_StepMode;

    @FXML
    private Button b_Exit;

    @FXML
    private Label l_titul;

    @FXML
    private Label l_name;

    @FXML
    private Label l_group;

    @FXML
    void initialize() {
        l_name.setTextFill(Color.web("#15062F"));
        l_group.setTextFill(Color.web("#15062F"));

        l_titul.setTextFill(Color.web("#15062F"));
        l_titul.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        l_titul.setAlignment(Pos.CENTER);
        l_titul.setMaxSize(800, 60);
        l_titul.setMinSize(800, 60);

        b_SetParameters.setMinSize(200, 40);
        b_SetParameters.setMaxSize(200, 60);
        b_SetParameters.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        b_SetParameters.setTextFill(Color.web("#15062F"));
        b_SetParameters.setAlignment(Pos.CENTER);

        b_AutoMode.setMinSize(200, 40);
        b_AutoMode.setMaxSize(200, 60);
        b_AutoMode.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        b_AutoMode.setTextFill(Color.web("#15062F"));
        b_AutoMode.setAlignment(Pos.CENTER);

        b_StepMode.setMinSize(200, 40);
        b_StepMode.setMaxSize(200, 60);
        b_StepMode.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        b_StepMode.setTextFill(Color.web("#15062F"));
        b_StepMode.setAlignment(Pos.CENTER);

        b_Exit.setMinSize(200, 40);
        b_Exit.setMaxSize(200, 60);
        b_Exit.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        b_Exit.setTextFill(Color.web("#15062F"));
        b_Exit.setAlignment(Pos.CENTER);

        b_SetParameters.setOnAction(event -> {
            Stage stage = ((Stage) b_SetParameters.getScene().getWindow());
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("resource/setParameters.fxml"));
                BackgroundImage myBI = new BackgroundImage(new Image("/smo/view/resource/img/2.jpg", 1600, 900, false, false),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT);
                BorderPane borderPane = new BorderPane(root);
                borderPane.setBackground(new Background(myBI));
                Scene scene = new Scene(borderPane);
                stage.setTitle("Settings");
                stage.setWidth(1600);
                stage.setHeight(900);
                stage.setAlwaysOnTop(true);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });


        b_AutoMode.setOnAction(event -> {
            Stage stage = ((Stage) b_AutoMode.getScene().getWindow());
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("resource/autoMode.fxml"));
                BackgroundImage myBI = new BackgroundImage(new Image("/smo/view/resource/img/3.jpg", 1600, 900, false, false),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT);
                BorderPane borderPane = new BorderPane(root);
                borderPane.setBackground(new Background(myBI));
                Scene scene = new Scene(borderPane);
                stage.setTitle("Auto mode");
                stage.setWidth(1600);
                stage.setHeight(900);
                stage.setAlwaysOnTop(true);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        b_StepMode.setOnAction(event -> {
            Stage stage = ((Stage) b_StepMode.getScene().getWindow());
            stage.close();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resource/stepMode.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Step mode");
                stage.setScene(new Scene(root1));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        b_Exit.setOnAction(event -> {
            ((Stage) b_Exit.getScene().getWindow()).close();
        });
    }

}

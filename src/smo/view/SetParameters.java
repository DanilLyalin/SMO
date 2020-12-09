package smo.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smo.model.Parameters;

import java.io.IOException;

public class SetParameters {


    @FXML
    private Button b_Cancel;

    @FXML
    private Button b_Set;

    @FXML
    private Label l_titul;

    @FXML
    private Label l_numOfSources;

    @FXML
    private Label l_numOfDevices;

    @FXML
    private Label l_sizeOfBuffer;

    @FXML
    private Label l_alpha;

    @FXML
    private Label l_beta;

    @FXML
    private Label l_lambda;

    @FXML
    private Label l_numOfReq;

    @FXML
    private Label l_result;

    @FXML
    private TextField f_numOfSources;

    @FXML
    private TextField f_numOfDevices;

    @FXML
    private TextField f_sizeOfBuffer;

    @FXML
    private TextField f_alpha;

    @FXML
    private TextField f_beta;

    @FXML
    private TextField f_lambda;

    @FXML
    private TextField f_numOfReq;


    private void callErrorWindow(String err){
        ErrorParameter.GetError("Вы ввели неверное значение в поле " + err);
        try{
            Stage stage = new Stage();
            stage.setWidth(270);
            stage.setHeight(200);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resource/errorParameter.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            BorderPane borderPane = new BorderPane(root);

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(b_Set.getScene().getWindow());
            stage.setTitle("Ошибка!");
            stage.setScene(new Scene(borderPane));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

        l_titul.setTextFill(Color.web("#15062F"));
        l_titul.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        l_titul.setAlignment(Pos.CENTER);
        l_titul.setMaxSize(800, 60);
        l_titul.setMinSize(800, 60);

        l_numOfSources.setMaxSize(300, 20);
        l_numOfSources.setMinSize(300, 20);
        l_numOfSources.setTextFill(Color.web("#15062F"));

        l_numOfDevices.setMaxSize(300, 20);
        l_numOfDevices.setMinSize(300, 20);
        l_numOfDevices.setTextFill(Color.web("#15062F"));

        l_alpha.setMaxSize(300, 20);
        l_alpha.setMinSize(300, 20);
        l_alpha.setTextFill(Color.web("#15062F"));

        l_beta.setMaxSize(300, 20);
        l_beta.setMinSize(300, 20);
        l_beta.setTextFill(Color.web("#15062F"));

        l_lambda.setMaxSize(300, 20);
        l_lambda.setMinSize(300, 20);
        l_lambda.setTextFill(Color.web("#15062F"));

        l_numOfReq.setMaxSize(300, 20);
        l_numOfReq.setMinSize(300, 20);
        l_numOfReq.setTextFill(Color.web("#15062F"));

        l_sizeOfBuffer.setMaxSize(300, 20);
        l_sizeOfBuffer.setMinSize(300, 20);
        l_sizeOfBuffer.setTextFill(Color.web("#15062F"));

        b_Set.setMinSize(200, 40);
        b_Set.setMaxSize(200, 60);
        b_Set.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        b_Set.setTextFill(Color.web("#15062F"));
        b_Set.setAlignment(Pos.CENTER);

        b_Cancel.setMinSize(200, 40);
        b_Cancel.setMaxSize(200, 60);
        b_Cancel.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
        b_Cancel.setTextFill(Color.web("#15062F"));
        b_Cancel.setAlignment(Pos.CENTER);

        f_numOfSources.setText(String.valueOf(Parameters.numberOfSource));
        f_numOfDevices.setText(String.valueOf(Parameters.numberOfDevice));
        f_sizeOfBuffer.setText(String.valueOf(Parameters.bufferMaxSize));
        f_alpha.setText(String.valueOf(Parameters.alpha));
        f_beta.setText(String.valueOf(Parameters.beta));
        f_lambda.setText(String.valueOf(Parameters.lambda));
        f_numOfReq.setText(String.valueOf(Parameters.numberOfRequest));


        b_Cancel.setOnAction(event -> {
            Stage stage = ((Stage) b_Cancel.getScene().getWindow());
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("resource/main.fxml"));
                BackgroundImage myBI = new BackgroundImage(new Image("/smo/view/resource/img/1.jpg", 1600, 900, false, false),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT);
                BorderPane borderPane = new BorderPane(root);
                borderPane.setBackground(new Background(myBI));
                Scene scene = new Scene(borderPane);
                stage.setTitle("Main window");
                stage.setWidth(1600);
                stage.setHeight(900);
                stage.setAlwaysOnTop(true);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        b_Set.setOnAction(event -> {
            try {
                Parameters.numberOfDevice = Integer.parseInt(f_numOfDevices.getText());
            }catch (Exception e){
                callErrorWindow("количество устройств.");
                return;
            }

            try {
                Parameters.numberOfSource = Integer.parseInt(f_numOfSources.getText());
            }catch (Exception e){
                callErrorWindow("количество источников.");
                return;
            }

            try {
                Parameters.numberOfRequest = Integer.parseInt(f_numOfReq.getText());
            }catch (Exception e){
                callErrorWindow("количество заявок.");
                return;
            }

            try{
                Parameters.alpha = Double.parseDouble(f_alpha.getText());
            }catch (Exception e){
                callErrorWindow("коэффициент альфа.");
                return;
            }

            try{
                Parameters.beta = Double.parseDouble(f_beta.getText());
            }catch (Exception e){
                callErrorWindow("коэффициент бета.");
                return;
            }

            try{
                Parameters.lambda = Double.parseDouble(f_lambda.getText());
            }catch (Exception e){
                callErrorWindow("коэффициент лямба.");
                return;
            }

            try{
                Parameters.bufferMaxSize = Integer.parseInt(f_sizeOfBuffer.getText());
            }catch (Exception e){

            }
            l_result.setText("Изменено");
            l_result.setTextFill(Color.web("#15062F"));
            l_result.setStyle("-fx-border-color:blue; -fx-background-color: #E8E2F2;");
            l_result.setAlignment(Pos.CENTER);
            l_result.setMaxSize(100, 30);
            l_result.setMinSize(100, 30);

        });
    }

}

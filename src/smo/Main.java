package smo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main  extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/resource/main.fxml"));

        BackgroundImage myBI= new BackgroundImage(new Image("/smo/view/resource/img/1.jpg", 1600, 900, false, false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        BorderPane borderPane = new BorderPane(root);
        borderPane.setBackground(new Background(myBI));

        Scene scene = new Scene(borderPane);

        stage.setTitle("Главное меню");
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

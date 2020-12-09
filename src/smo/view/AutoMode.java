package smo.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import smo.controller.Controller;
import smo.model.Parameters;
import smo.statistics.StatisticCalculate;
import smo.statistics.StatisticRequest;
import smo.statistics.StatisticsDevice;

import java.io.IOException;

public class AutoMode {

    private ObservableList<StatisticRequest> reqData = FXCollections.observableArrayList();
    private ObservableList<StatisticsDevice> devData = FXCollections.observableArrayList();

    @FXML
    private Button b_back;

    @FXML
    private Button b_start;

    @FXML
    private TableView<StatisticRequest> t_Sources;

    @FXML
    private TableColumn<StatisticRequest, Integer> c_Sources;

    @FXML
    private TableColumn<StatisticRequest, Integer> c_NumOfRequest;

    @FXML
    private TableColumn<StatisticRequest, Integer> c_Rejected;

    @FXML
    private TableColumn<StatisticRequest, Integer> c_Complete;

    @FXML
    private TableColumn<StatisticRequest, Double> c_ProbOfReject;

    @FXML
    private TableColumn<StatisticRequest, Double> c_AverageInSyst;

    @FXML
    private TableColumn<StatisticRequest, Double> c_AverageWaiting;

    @FXML
    private TableColumn<StatisticRequest, Double> c_AverageService;

    @FXML
    private TableColumn<StatisticRequest, Double> c_DispWaiting;

    @FXML
    private TableColumn<StatisticRequest, Double> c_DispService;

    @FXML
    private TableView<StatisticsDevice> t_Devices;

    @FXML
    private TableColumn<StatisticsDevice, Integer> c_Device;

    @FXML
    private TableColumn<StatisticsDevice, Double> c_UsingFactor;

    @FXML
    void initialize() {
        c_Sources.setCellValueFactory(new PropertyValueFactory<>("numOfSource"));
        c_NumOfRequest.setCellValueFactory(new PropertyValueFactory<>("numOfRequest"));
        c_Rejected.setCellValueFactory(new PropertyValueFactory<>("rejected"));
        c_Complete.setCellValueFactory(new PropertyValueFactory<>("complete"));
        c_ProbOfReject.setCellValueFactory(new PropertyValueFactory<>("probOfReject"));
        c_AverageInSyst.setCellValueFactory(new PropertyValueFactory<>("averageInSystem"));
        c_AverageWaiting.setCellValueFactory(new PropertyValueFactory<>("averageWaiting"));
        c_AverageService.setCellValueFactory(new PropertyValueFactory<>("averageService"));
        c_DispWaiting.setCellValueFactory(new PropertyValueFactory<>("dispWaiting"));
        c_DispService.setCellValueFactory(new PropertyValueFactory<>("dispService"));

        c_Device.setCellValueFactory(new PropertyValueFactory<>("numOfDevice"));
        c_UsingFactor.setCellValueFactory(new PropertyValueFactory<>("usingFactor"));

        b_start.setOnAction(event -> {
            try {
                for (StatisticRequest req : reqData) {
                    req.clean();
                }
                for (int i = 0; i < t_Sources.getItems().size(); i++) {
                    t_Sources.getItems().clear();
                }

                Controller controller = new Controller(Parameters.lambda, Parameters.alpha, Parameters.beta,
                        Parameters.numberOfSource, Parameters.numberOfDevice, Parameters.numberOfRequest, Parameters.bufferMaxSize);
                controller.startAuto();
                reqData = StatisticCalculate.calculateRequestStatistic();
                t_Sources.setItems(reqData);

                devData = StatisticCalculate.calculateDeviceStatistic();
                t_Devices.setItems(devData);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });


        b_back.setOnAction(event -> {
            Stage stage = ((Stage) b_back.getScene().getWindow());
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
    }
}

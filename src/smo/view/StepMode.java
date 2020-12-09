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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import smo.controller.Controller;
import smo.exception.SMOException;
import smo.model.Parameters;
import smo.model.State;
import smo.statistics.StatisticBuffer;
import smo.statistics.StatisticCalculate;
import smo.statistics.StatisticRequest;
import smo.statistics.StatisticsDevice;

import java.io.IOException;

import static smo.model.State.*;
import static smo.model.State.START;

public class StepMode {

    private ObservableList<StatisticRequest> reqData = FXCollections.observableArrayList();
    private ObservableList<StatisticsDevice> devData = FXCollections.observableArrayList();
    private ObservableList<StatisticBuffer> bufData = FXCollections.observableArrayList();

    @FXML
    private Button b_back;

    @FXML
    private Button b_step;

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
    private TableColumn<StatisticsDevice, Boolean> c_IsWorking;

    @FXML
    private TableColumn<StatisticsDevice, Double> c_UsingFactor;

    @FXML
    private TableColumn<StatisticsDevice, Double> c_TimeOfFinish;

    @FXML
    private TableColumn<StatisticsDevice, Double> c_CurrentReq;

    @FXML
    private Text text_CurrTime;

    @FXML
    private Text text_CurrEvent;


    @FXML
    private TableView<StatisticBuffer> t_Buffer;

    @FXML
    private TableColumn<StatisticBuffer, Integer> c_Queue;

    @FXML
    private TableColumn<StatisticBuffer, Integer> c_SourceNum;

    @FXML
    private TableColumn<StatisticBuffer, Double> c_TimeOfGeneration;

    private Boolean controllerReady;
    private Controller controller;
    private State state;

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
        c_IsWorking.setCellValueFactory(new PropertyValueFactory<>("isWorking"));
        c_UsingFactor.setCellValueFactory(new PropertyValueFactory<>("usingFactor"));
        c_TimeOfFinish.setCellValueFactory(new PropertyValueFactory<>("timeOfFinish"));
        c_CurrentReq.setCellValueFactory(new PropertyValueFactory<>("currentRequest"));

        c_Queue.setCellValueFactory(new PropertyValueFactory<>("numOfPlace"));
        c_SourceNum.setCellValueFactory(new PropertyValueFactory<>("numOfSource"));
        c_TimeOfGeneration.setCellValueFactory(new PropertyValueFactory<>("timeOfGeneration"));

        try {
            controller = new Controller(Parameters.lambda, Parameters.alpha, Parameters.beta,
                    Parameters.numberOfSource, Parameters.numberOfDevice, Parameters.numberOfRequest, Parameters.bufferMaxSize);
        } catch (SMOException e) {
            e.printStackTrace();
        }
        controllerReady = false;
        b_step.setOnAction(event -> {
            try {
                if(state != State.EOW) {
                    for (StatisticRequest req : reqData) {
                        req.clean();
                    }
                    for (int i = 0; i < t_Sources.getItems().size(); i++) {
                        t_Sources.getItems().clear();
                    }

                    if (!controllerReady) {
                        state = controller.startStep(false, State.START);
                        controllerReady = true;
                    }
                    text_CurrEvent.setText(nameEnum(state));

                    state = controller.startStep(true, state);
                    if(state == REQ_ALLOC){
                        text_CurrEvent.setText(nameEnum(controller.getRequestDispatcher().allocateRequest(Parameters.currentTime)));
                    }

                    reqData = StatisticCalculate.calculateRequestStatistic();
                    t_Sources.setItems(reqData);

                    devData = StatisticCalculate.calculateDeviceStatistic();
                    t_Devices.setItems(devData);

                    bufData = StatisticCalculate.calculateBufferStatistic();
                    t_Buffer.setItems(bufData);

                    text_CurrTime.setText(String.valueOf(Parameters.currentTime));


                }
            }catch (SMOException e){

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

    protected String nameEnum(State state){
        switch (state) {
            case START:
                return "Подготовка к новой заявке";
            case NEED_ALLOCATE:
                return "Появление заявки";
            case ALL_REQ_IN_DEV:
                return "Все запросы в устройствах";
            case SKIP_TIME:
                return "Ожидание события";
            case SEND:
                return "Отправка на устройство";
            case REQ_ALLOC:
                return "Заявка распределена";
            case REQ_NONALLOQ:
                return  "Заявка отклонена";
            default:
            case EOW:
                return "Конец работы";
        }
    }

}

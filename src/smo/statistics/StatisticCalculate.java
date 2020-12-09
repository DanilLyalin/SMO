package smo.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import smo.model.Buffer;
import smo.model.Device;
import smo.model.Parameters;
import smo.model.Request;

import java.util.ArrayList;

public class StatisticCalculate {

    public static ArrayList<Request> requests = new ArrayList<>();
    public static ArrayList<Device> dev = new ArrayList<>();
    public static Buffer buffer;

    public static ObservableList<StatisticRequest> calculateRequestStatistic(){
        StatisticRequest [] statistic = new StatisticRequest[Parameters.numberOfSource];
        for(int j = 0; j < Parameters.numberOfSource; j++){
            statistic[j] = new StatisticRequest(j);
        }

        for (Request request : requests){
            statistic[request.getSourceNum()].numOfRequest++;
            if(!request.isProceed()){
                statistic[request.getSourceNum()].rejected++;
            }
            else if(request.getRemoveFromDevice() > 0 && request.getAddToDevice() > 0){
                statistic[request.getSourceNum()].complete++;
                statistic[request.getSourceNum()].averageInSystem += request.getRemoveFromDevice()
                        - request.getTimeGenerated();
                if(request.getTimeAddingToBuffer() > 0) {
                    statistic[request.getSourceNum()].averageWaiting += request.getAddToDevice()
                            - request.getTimeAddingToBuffer();
                    statistic[request.getSourceNum()].dispWaiting += Math.pow(request.getAddToDevice()
                            - request.getTimeAddingToBuffer() - statistic[request.getSourceNum()].calculateAverageWaiting(), 2);
                }
                statistic[request.getSourceNum()].averageService += request.getRemoveFromDevice()
                        - request.getAddToDevice();
                statistic[request.getSourceNum()].dispService += Math.pow(request.getRemoveFromDevice()
                        - request.getAddToDevice() - statistic[request.getSourceNum()].calculateAverageService(), 2);
            }
        }
        ObservableList<StatisticRequest> reqData = FXCollections.observableArrayList();
        for (int j = 0; j < Parameters.numberOfSource; j++){
            statistic[j].probOfReject = (double) statistic[j].rejected / statistic[j].numOfRequest;
            statistic[j].averageInSystem = statistic[j].averageInSystem / statistic[j].numOfRequest;
            statistic[j].averageWaiting = statistic[j].calculateAverageWaiting();
            statistic[j].averageService = statistic[j].calculateAverageService();
            statistic[j].dispWaiting = statistic[j].dispWaiting/statistic[j].numOfRequest;
            statistic[j].dispService = statistic[j].dispService/statistic[j].numOfRequest;
            reqData.add(statistic[j]);
        }
        return reqData;
    }

    public static ObservableList<StatisticsDevice> calculateDeviceStatistic(){
        StatisticsDevice[] statistic = new StatisticsDevice[Parameters.numberOfDevice];
        for(int j = 0; j < Parameters.numberOfDevice; j++){
            statistic[j] = new StatisticsDevice(j);
        }
        ObservableList<StatisticsDevice> devData = FXCollections.observableArrayList();
        for(Device device : dev){
            statistic[device.getNum()].usingFactor = device.getTimeInWork() / Parameters.currentTime;
            devData.add(statistic[device.getNum()]);
            statistic[device.getNum()].isWorking = device.isFree() ? "Свободен" : "Занят";
            if(!device.isFree()){
                statistic[device.getNum()].timeOfFinish = device.getTimeFinish();
            }else{
                statistic[device.getNum()].timeOfFinish = null;
            }
            if( device.getCurrReq() != null){
                statistic[device.getNum()].currentRequest = device.getCurrReq().toString();
            }else{
                statistic[device.getNum()].currentRequest = "NULL";
            }

        }
        return devData;
    }

    public static ObservableList<StatisticBuffer> calculateBufferStatistic(){
        ObservableList<StatisticBuffer> bufData = FXCollections.observableArrayList();
        int count = 0;
        for(Request req : buffer.getBufferStack()){
            bufData.add(new StatisticBuffer(count++, req.getSourceNum(), req.getTimeGenerated()));
        }
        return bufData;
    }

}

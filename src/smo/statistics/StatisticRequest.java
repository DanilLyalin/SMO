package smo.statistics;

public class StatisticRequest {

    public int numOfSource;
    public int numOfRequest = 0;
    public int rejected = 0;
    public int complete = 0;
    public double probOfReject = 0;
    public double averageInSystem = 0;
    public double averageWaiting = 0;
    public double averageService = 0;
    public double dispWaiting = 0;
    public double dispService = 0;

    public StatisticRequest(final int n){
        numOfSource = n;
    }

    public int getNumOfSource() {
        return numOfSource;
    }

    public double getAverageInSystem() {
        return averageInSystem;
    }

    public double getAverageService() {
        return averageService;
    }

    public double getAverageWaiting() {
        return averageWaiting;
    }

    public double getDispService() {
        return dispService;
    }

    public double getDispWaiting() {
        return dispWaiting;
    }

    public double getProbOfReject() {
        return probOfReject;
    }

    public int getComplete() {
        return complete;
    }

    public int getNumOfRequest() {
        return numOfRequest;
    }

    public int getRejected() {
        return rejected;
    }

    public double calculateAverageWaiting(){
        return averageWaiting/numOfRequest;
    }

    public double calculateAverageService(){
        return averageService/numOfRequest;
    }

    public void clean(){
        this.numOfRequest = 0;
        this.numOfSource = 0;
        this.averageInSystem = 0;
        this.averageService = 0;
        this.averageWaiting = 0;
        this.dispService = 0;
        this.dispWaiting = 0;
        this.rejected = 0;
        this.complete = 0;
        this.probOfReject = 0;
    }

}

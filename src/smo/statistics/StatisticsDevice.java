package smo.statistics;

import smo.model.Request;

public class StatisticsDevice {

    public int numOfDevice;
    public double usingFactor;
    public String isWorking;
    public Double timeOfFinish;
    public String currentRequest;

    public StatisticsDevice(final int j){
        this.numOfDevice = j;
    }

    public double getUsingFactor() {
        return usingFactor;
    }

    public int getNumOfDevice() {
        return numOfDevice;
    }

    public String getIsWorking(){
        return isWorking;
    }

    public Double getTimeOfFinish() {
        return timeOfFinish;
    }

    public String getCurrentRequest() {
        return currentRequest;
    }

}

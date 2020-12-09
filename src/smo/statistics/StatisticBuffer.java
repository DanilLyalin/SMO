package smo.statistics;

public class StatisticBuffer {

    private int numOfPlace;
    private int numOfSource;
    private double timeOfGeneration;

    public StatisticBuffer(int numOfPlace, int numOfSource, double timeOfGeneration){
        this.numOfPlace = numOfPlace;
        this.numOfSource = numOfSource;
        this.timeOfGeneration = timeOfGeneration;
    }

    public int getNumOfSource() {
        return numOfSource;
    }

    public int getNumOfPlace() {
        return numOfPlace;
    }

    public double getTimeOfGeneration() {
        return timeOfGeneration;
    }

}

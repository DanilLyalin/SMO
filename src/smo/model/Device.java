package smo.model;

import java.util.Random;

import smo.statistics.StatisticsDevice;

public class Device {

    private Integer num;
    private Request currReq;
    private Double alpha;
    private Double beta;
    private Double timeStart;
    private Double timeFinish;

    private Double timeInWork;

    public Device(Integer num, Double alpha, Double beta) {
        this.num = num;
        this.currReq = null;
        this.alpha = alpha;
        this.beta = beta;
        this.timeStart = -1.0;
        this.timeFinish = -1.0;
        this.timeInWork = 0.;
    }

    public Boolean isFree() {
        return this.currReq == null;
    }

    public void freeRequest() {
        this.currReq = null;
        this.timeStart = -1.0;
        this.timeFinish = -1.0;
    }

    public double processRequest(Request req, Double currTime) {
        Random random = new Random();
        this.currReq = req;
        this.timeStart = currTime;
        this.timeFinish = currTime + (random.nextDouble() * (beta - alpha) + alpha);
        //this.timeFinish = time[count++];
        this.timeInWork += timeFinish - timeStart;
        return  timeFinish - timeStart;
    }

    public Double getTimeFinish() {
        return timeFinish;
    }

    public Request getCurrReq() {
        return currReq;
    }

    public Integer getNum() {
        return num;
    }

    public Double getTimeInWork() {
        return timeInWork;
    }

    @Override
    public String toString() {
        return "Device [" + num + "]";
    }


}

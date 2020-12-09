package smo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Source {

    private Integer num;
    private Double lambda;
    private Integer numberRequest;
    private RequestDispatcher requestDispatcher;


    public Source(Integer num, Double lambda, RequestDispatcher reqDisp, Integer numReq) {
        this.num = num;
        this.lambda = lambda;
        this.requestDispatcher = reqDisp;
        this.numberRequest = numReq;
    }

    public void generate() {
        List<Request> requestList = new ArrayList();
        Random random = new Random();
        double timeOfReq = 0;
        for (int i = 0; i < numberRequest; i++) {
            timeOfReq += (-1 / lambda) * Math.log(random.nextDouble());
            requestList.add(new Request(i, this.num, timeOfReq));
        }
        requestDispatcher.addRequest(requestList);
    }


}

package smo.model;

import smo.model.Buffer;
import smo.model.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestDispatcher {
    private ArrayList<Request> unallocRequests = new ArrayList();
    private Buffer buff;

    public RequestDispatcher(Buffer buff) {
        this.buff = buff;
    }

    public void addRequest(Request req){
        unallocRequests.add(req);
    }

    public void addRequest(List<Request> req){
        unallocRequests.addAll(req);
    }

    public void prepareRequests(){
        Collections.sort(unallocRequests,
                (o1, o2) -> Double.compare(o1.getTimeGenerated(),o2.getTimeGenerated()));
    }

    public ArrayList<Request> getUnallocRequests() {
        return unallocRequests;
    }

    public String showAllRequests(){
        StringBuffer sb = new StringBuffer();
        for(Request i : unallocRequests){
            sb.append(i).append("\r\n");
        }
        return sb.toString();
    }

    public Request getFirstNotAllocatedRequest(){
        try{
            return unallocRequests.get(0);
        }catch (RuntimeException e){
            return null;
        }
    }

    public State allocateRequest(final double time){
        if (buff.isFull()){
            Request req =  buff.removeOldest();
            req.setProceed(false);
            return State.REQ_NONALLOQ;
        }
        Request req = unallocRequests.remove(0);
        req.setProceed(true);
        req.setTimeAddingToBuffer(time);
        buff.pushRequest(req);
        return State.REQ_ALLOC;
    }

}

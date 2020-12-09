package smo.model;

import java.util.Stack;

public class Buffer {

    final private Stack<Request> bufferStack;
    final private Integer maxBufferSize;

    public Buffer(final Integer bufferSize) {
        this.maxBufferSize = bufferSize;
        bufferStack = new Stack<Request>();
    }

    public int size(){
        return bufferStack.size();
    }

    public Stack<Request> getBufferStack(){
        return bufferStack;
    }

    public void pushRequest(final Request req) {
        bufferStack.push(req);
    }

    public Request popRequest() {
        return bufferStack.pop();
    }

    public Request removeOldest() {
        return bufferStack.remove(0);
    }

    public Boolean isFull() {
        return bufferStack.size() >= maxBufferSize;
    }

    public Boolean isEmpty(){
        return bufferStack.size() == 0 ? true : false;
    }

    public String showAllRequests() {
        StringBuffer sb = new StringBuffer();
        for (Request i : bufferStack) {
            sb.append(i).append("\r\n");
        }
        return sb.toString();
    }
}

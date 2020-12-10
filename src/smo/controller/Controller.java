package smo.controller;

import smo.exception.SMOException;
import smo.model.*;
import smo.statistics.StatisticCalculate;

import java.util.ArrayList;
import java.util.Arrays;

import static smo.model.State.*;

public class Controller {

    final private Double lamda;
    final private Double alpha;
    final private Double beta;

    final private Integer numSource;
    final private Integer numDevice;
    final private Integer numRequest;
    final private Integer maxSizeBuffer;

    private Source[] sources;
    private Device[] devices;

    private Buffer buffer;
    private BufferDispatcher bufferDispatcher;
    private RequestDispatcher requestDispatcher;

    private Double timeOfLast = 0.;

    private Double currentTime = 0.;
    private Request currentStepReq;
    private Integer countUnallocatedReq = 0;

    private State tempState;

    private boolean debug = false;

    private ArrayList<Request> saveUnallocatedReq;


    public Controller(final Double lamda, final Double alpha, final Double beta, final Integer numSource,
                      final Integer numDevice, final Integer numRequest, final Integer maxSizeBuffer)
            throws SMOException {
        this.lamda = lamda;
        this.alpha = alpha;
        this.beta = beta;

        this.numDevice = numDevice;
        this.numSource = numSource;
        this.numRequest = numRequest;
        this.maxSizeBuffer = maxSizeBuffer;

        checkArgs();
        init();
    }

    public RequestDispatcher getRequestDispatcher() {
        return requestDispatcher;
    }

    protected void prepare(final boolean stepMode) {
        for (int i = 0; i < numSource; i++) {
            sources[i].generate();                                  //все в неупор массив
        }

        requestDispatcher.prepareRequests();                        //сортируются
        StatisticCalculate.requests.clear();
        StatisticCalculate.dev.clear();
        StatisticCalculate.dev.addAll(Arrays.asList(devices));
        //StatisticCalculate.requests.addAll(requestDispatcher.getUnallocRequests());

        StatisticCalculate.buffer = this.buffer;
        if (!stepMode) {
            StatisticCalculate.requests.addAll(requestDispatcher.getUnallocRequests());
        } else {
            saveUnallocatedReq = new ArrayList<>();
            saveUnallocatedReq.addAll(requestDispatcher.getUnallocRequests());
        }
        if (debug) System.out.println("All requests:");
        if (debug) System.out.println(requestDispatcher.showAllRequests());
    }

    protected State step(final State state, final boolean modeStep) throws SMOException {
        if (modeStep) {
            StatisticCalculate.requests.clear();
            for (int i = 0; i < countUnallocatedReq; i++) {
                StatisticCalculate.requests.add(saveUnallocatedReq.get(i));
            }
        }
        if (debug) System.out.println();
        if (debug) System.out.println("__________________________________________________");
        if (debug) System.out.println("current time = " + currentTime);
        Parameters.currentTime = currentTime;
        switch (state) {
            case START:

                currentStepReq = requestDispatcher.getFirstNotAllocatedRequest();
                if (currentStepReq != null && currentStepReq.getTimeGenerated() >= currentTime) {
                    countUnallocatedReq++;
                    return NEED_ALLOCATE;
                } else if (currentStepReq == null && buffer.isEmpty()) {
                    return ALL_REQ_IN_DEV;
                } else {
                    return SKIP_TIME;
                }
            case NEED_ALLOCATE:
                currentTime = currentStepReq.getTimeGenerated();
                Parameters.currentTime = currentTime;
                checkAllDevice(currentTime);
                sendToDevice(currentTime);
                if (debug) System.out.println("Buffer at time = " + currentTime);
                return REQ_ALLOC;

            case REQ_ALLOC:
                if (!modeStep) {
                    tempState = requestDispatcher.allocateRequest(currentTime);
                }
                return SEND;

            case ALL_REQ_IN_DEV:
                for (int i = 0; i < numDevice; i++) {
                    if (!devices[i].isFree()) {
                        if (debug)
                            System.out.println("[" + devices[i] + "] end of request [" + devices[i].getCurrReq() +
                                    "] at " + devices[i].getTimeFinish());
                        devices[i].freeRequest();
                    }
                }
                return EOW;

            case SKIP_TIME:
                double temp = Double.MAX_VALUE;
                for (int i = 0; i < devices.length; i++) {
                    if (temp > devices[i].getTimeFinish()) {
                        temp = devices[i].getTimeFinish();
                    }
                }
                currentTime = temp + Parameters.epsilon;
                Parameters.currentTime = currentTime;
            case SEND:
                checkAllDevice(currentTime);
                sendToDevice(currentTime);
                return START;

            default:
            case EOW:
                throw new SMOException("invalid state");

        }
    }

    public Double getTimeOfLast() {
        return timeOfLast;
    }

    public void startAuto() throws SMOException {
        prepare(false);
        State state = START;
        while (state != EOW) {
            state = step(state, false);
        }

        if (debug) System.out.println(timeOfLast);
        Parameters.timeOfLast = timeOfLast;
    }

    public State startStep(final boolean modeWork, State state) throws SMOException {
        if (!modeWork) {
            prepare(true);
        } else {
            return step(state, true);
        }
        return START;
    }

    protected void checkAllDevice(final Double currentTime) {
        for (int i = 0; i < numDevice; i++) {
            if (devices[i].getTimeFinish() < currentTime && devices[i].getTimeFinish() != -1) {
                if (debug)
                    System.out.println("[" + devices[i] + "] end of request [" + devices[i].getCurrReq() + "] at " + devices[i].getTimeFinish());
                devices[i].freeRequest();
            }
        }
    }

    protected boolean sendToDevice(final Double currentTime) {
        Integer numOfFreeDevice = bufferDispatcher.getIdFreeDevice();  //находит номер свободного устройства
        boolean result = false;
        while (numOfFreeDevice != -1 && !buffer.isEmpty()) {
            Request currentRequest = buffer.popRequest();
            devices[numOfFreeDevice].processRequest(currentRequest, currentTime);
            if (timeOfLast < devices[numOfFreeDevice].getTimeFinish()) {
                timeOfLast = devices[numOfFreeDevice].getTimeFinish();
            }
            if (debug)
                System.out.println(currentRequest + " send to " + devices[numOfFreeDevice] + ", will be complete at "
                        + devices[numOfFreeDevice].getTimeFinish());
            currentRequest.setNumDev(numOfFreeDevice);
            currentRequest.setAddToDevice(currentTime);
            currentRequest.setRemoveFromDevice(devices[numOfFreeDevice].getTimeFinish());
            currentRequest.setProceed(true);
            numOfFreeDevice = bufferDispatcher.getIdFreeDevice();
            result = true;
        }
        return result;
    }

    protected void init() {
        this.buffer = new Buffer(maxSizeBuffer);
        this.requestDispatcher = new RequestDispatcher(buffer);

        this.sources = new Source[numSource];
        this.devices = new Device[numDevice];

        if (numSource <= numRequest) {
            for (int i = 0; i < numSource; i++) {
                if (i != numSource - 1) {
                    sources[i] = new Source(i, lamda, requestDispatcher, numRequest / numSource);
                } else {
                    sources[i] = new Source(i, lamda, requestDispatcher, numRequest -
                            i * numRequest / numSource);
                }
            }
        } else {
            for (int i = 0; i < numRequest; i++) {
                sources[i] = new Source(i, lamda, requestDispatcher, 1);
            }
            for (int i = numRequest; i < numSource; i++) {
                sources[i] = new Source(i, lamda, requestDispatcher, 0);
            }
        }
        for (int i = 0; i < numDevice; i++) {
            devices[i] = new Device(i, alpha, beta);
        }

        this.bufferDispatcher = new BufferDispatcher(devices, buffer);
    }

    protected void checkArgs() throws SMOException {
        if (lamda <= 0)
            throw new SMOException("lambda should be a positive double");
        if (alpha <= 0)
            throw new SMOException("alpha should be a positive double");
        if (beta <= 0)
            throw new SMOException("beta should be a positive double");
        if (numSource <= 0)
            throw new SMOException("Number of sources should be a natural number");
        if (numDevice <= 0)
            throw new SMOException("Number of devices should be a natural number");
        if (numRequest <= 0)
            throw new SMOException("Number of requests should be a natural number");
        if (maxSizeBuffer <= 0)
            throw new SMOException("Max size of buffer should be a natural number");
    }

}

package smo.model;

public class Request {
    final private Integer num;
    final private Integer sourceNum;
    final private double timeGenerated;

    private double addToDevice = -1;
    private double removeFromDevice = -1;
    private double timeAddingToBuffer = -1;
    private boolean isProceed = false;
    private Integer numDev = -1;

    public Request(final Integer num, final Integer sourceNum, final double timeGenerated) {
        this.num = num;
        this.sourceNum = sourceNum;
        this.timeGenerated = timeGenerated;
    }

    public double getTimeGenerated() {
        return timeGenerated;
    }

    public double getAddToDevice(){
        return this.addToDevice;
    }

    public double getRemoveFromDevice(){
        return this.removeFromDevice;
    }

    public boolean isProceed() {
        return isProceed;
    }

    public void setAddToDevice(double addToDevice) {
        this.addToDevice = addToDevice;
    }

    public void setProceed(boolean proceed) {
        isProceed = proceed;
    }

    public void setRemoveFromDevice(double removeFromDevice) {
        this.removeFromDevice = removeFromDevice;
    }

    public Integer getNumDev() {
        return numDev;
    }

    public void setNumDev(Integer numDev) {
        this.numDev = numDev;
    }

    public Integer getSourceNum() {
        return sourceNum;
    }

    @Override
    public String toString() {
        return "Request " + num +
                "/" + sourceNum +
                ", generated at " + timeGenerated;
    }

    public double getTimeAddingToBuffer() {
        return timeAddingToBuffer;
    }

    public void setTimeAddingToBuffer(double timeAddingToBuffer) {
        this.timeAddingToBuffer = timeAddingToBuffer;
    }
}

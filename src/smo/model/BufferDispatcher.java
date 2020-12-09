package smo.model;

public class BufferDispatcher {

    final private Device[] devs;
    final private Buffer buff;

    public BufferDispatcher(final Device[] devs, final Buffer buff) {
        this.devs = devs;
        this.buff = buff;
    }

    public int getIdFreeDevice() {
        for (int i = 0; i < devs.length; i++) {
            if (devs[i].isFree()) {
                return i;
            }
        }
        return -1;
    }



}

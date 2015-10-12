package hm.rest;

public interface ResponseContentProvider {
    long getLength();

    boolean hasNextBlock();

    byte[] getNextBlock();
}

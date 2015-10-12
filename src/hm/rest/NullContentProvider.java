package hm.rest;

class NullContentProvider implements ResponseContentProvider {
    public long getLength() {
        return 0;
    }

    public boolean hasNextBlock() {
        return false;
    }

    public byte[] getNextBlock() {
        return new byte[0];
    }
}
package hm.rest;

abstract class SimpleContentProvider implements ResponseContentProvider {
    private byte[] content;
    private boolean hasProvided;

    public long getLength() {
        return getContent().length;
    }

    public boolean hasNextBlock() {
        return !hasProvided;
    }

    public byte[] getNextBlock() {
        hasProvided = true;
        return getContent();
    }

    private byte[] getContent() {
        if (content == null)
            content = makeContent();
        return content;
    }

    protected abstract byte[] makeContent();
}
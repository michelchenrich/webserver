package hm.rest;

class FixedContentProvider extends SimpleContentProvider {
    private final byte[] content;

    public FixedContentProvider(byte[] content) {
        this.content = content;
    }

    protected byte[] makeContent() {
        return content;
    }
}
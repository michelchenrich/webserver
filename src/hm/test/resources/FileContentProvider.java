package hm.test.resources;

import hm.rest.ResponseContentProvider;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class FileContentProvider implements ResponseContentProvider {
    private Charset charset = StandardCharsets.ISO_8859_1;
    private Reader reader;
    private long length;
    private long totalCharactersRead;
    private int blockSize;

    public FileContentProvider(File file) throws FileNotFoundException {
        length = file.length();
        reader = new InputStreamReader(new FileInputStream(file), charset);
        defineBlockSize(file);
    }

    private void defineBlockSize(File file) {
        blockSize = 8192;
        if (file.length() < blockSize) blockSize = (int) file.length();
    }

    public long getLength() {
        return length;
    }

    public boolean hasNextBlock() {
        return totalCharactersRead < length;
    }

    public byte[] getNextBlock() {
        try {
            return getFileBlock();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private byte[] getFileBlock() throws IOException {
        char[] block = new char[blockSize];
        int charactersRead = reader.read(block);
        totalCharactersRead += charactersRead;
        if (charactersRead > 0) return charset.encode(CharBuffer.wrap(block, 0, charactersRead)).array();
        else return new byte[0];
    }
}
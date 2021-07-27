package com.pankov.roadtoseniour.iobuf;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {

    private static final int INITIAL_CAPACITY = 7;

    private InputStream inputStream;
    private byte[] buffer;
    private int index;
    private int count;

    public BufferedInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.buffer = new byte[INITIAL_CAPACITY];
    }

    public BufferedInputStream(InputStream inputStream, int capacity) {
        this.inputStream = inputStream;
        this.buffer = new byte[capacity];
    }

    @Override
    public int read() throws IOException {
        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }

        if (count == -1 ) {
            return -1;
        }

        return buffer[index++];
    }

    /*@Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }*/

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (count == -1 ) {
            return -1;
        }
        int availableInBuffer = count - index;

        if (availableInBuffer < len) {
            return inputStream.read(b);
        } else {
            System.arraycopy(b, off, buffer, index, len);
            index += len;
            return len;
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}

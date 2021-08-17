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

        if (count == -1) {
            return -1;
        }

        return buffer[index++];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {

        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }

        if (count == -1) {
            return -1;
        }

        int availableInBuffer = count - index;

        if (availableInBuffer < len) {
            System.arraycopy(buffer, index, b, off, availableInBuffer);
            count = inputStream.read(buffer);

            if (count == -1) {
                return availableInBuffer;
            }

            index = 0;
            int length = len - availableInBuffer;
            System.arraycopy(buffer, index, b, off + availableInBuffer, length);
            index += length;
            return len;
        } else {
            int length = Math.min(availableInBuffer, len);
            System.arraycopy(buffer, index, b, off, length);
            index += length;
            return length;
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}

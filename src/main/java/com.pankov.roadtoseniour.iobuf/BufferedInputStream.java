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
        fillBuffer();

        if (count == -1) {
            return -1;
        }

        return buffer[index++];
    }

    @Override
    public int read(byte[] array) throws IOException {
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int offset, int arrayLength) throws IOException {

        if (arrayLength + offset > array.length) {
            throw new ArrayIndexOutOfBoundsException("Count of elements exceed array length");
        }

        if (arrayLength > buffer.length && index == count) {
            return inputStream.read(array);
        }

        fillBuffer();

        if (count == -1) {
            return -1;
        }

        int availableInBuffer = count - index;

        if (availableInBuffer < arrayLength) {
            System.arraycopy(buffer, index, array, offset, availableInBuffer);
            index = count;
            return availableInBuffer;
        } else {
            int length = Math.min(availableInBuffer, arrayLength);
            System.arraycopy(buffer, index, array, offset, length);
            index += length;
            return length;
        }
    }

    private void fillBuffer() throws IOException {
        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}

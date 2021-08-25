package com.pankov.roadtoseniour.iobuf;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {

    private static final int INITIAL_CAPACITY = 8192;
    private byte[] buffer;
    private OutputStream outputStream;
    private int index;

    public BufferedOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.buffer = new byte[INITIAL_CAPACITY];
    }

    public BufferedOutputStream(OutputStream outputStream, int capacity) {
        this.outputStream = outputStream;
        this.buffer = new byte[capacity];
    }

    @Override
    public void write(int b) throws IOException {
        if (index == buffer.length) {
            flushBuff();
        }
        buffer[index++] = (byte) b;
    }

    @Override
    public void write(byte[] array) throws IOException {
        write(array, 0, array.length);
    }

    @Override
    public void write(byte[] array, int offset, int countToWrite) throws IOException {

        if (buffer.length <= countToWrite) {
            flushBuff();
            outputStream.write(array, offset, countToWrite);
            return;
        }

        int availableInBuffer = buffer.length - index;

        if (availableInBuffer < countToWrite) {
            flushBuff();
        }

        System.arraycopy(array, offset, buffer, index, countToWrite);
        index += countToWrite;
    }

    @Override
    public void flush() throws IOException {
        flushBuff();
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        flush();
        outputStream.close();
    }

    private void flushBuff() throws IOException {
        outputStream.write(buffer, 0, index);
        index = 0;
    }
}

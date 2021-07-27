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

    private void flushBuff() throws IOException {
        outputStream.write(buffer, 0, index);
        index = 0;
    }
    @Override
    public void flush() throws IOException {
        flushBuff();
        outputStream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        if (index == buffer.length) {
            flushBuff();
        }
        buffer[index++] = (byte) b;
    }

    /*@Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        int availableInBuffer = buffer.length - index;
        if (availableInBuffer < len) {
            flush();
            outputStream.write(b, off, len);
        } else {
            System.arraycopy(b, off, buffer, index, len);
        }
    }*/

    @Override
    public void close() throws IOException {
        flush();
        index = 0;
        outputStream.close();
    }
}

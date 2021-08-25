package com.pankov.roadtoseniour.iobuf;

import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {

    private static final int INITIAL_CAPACITY = 64;
    private int index;
    private byte[] buffer;

    public ByteArrayOutputStream() {
        this.buffer = new byte[INITIAL_CAPACITY];
    }

    public ByteArrayOutputStream(int capacity) {
        this.buffer = new byte[capacity];
    }

    @Override
    public void write(int b) {
        if (index == buffer.length - 1) {
            ensureCapacity();
        }
        buffer[index++] = (byte) b;
    }

    @Override
    public void write(byte[] b, int off, int len) {
        if (buffer.length - index < len) {
            ensureCapacity(len);
        }
        System.arraycopy(b, off, buffer, index, len);
        index += len;
    }

    @Override
    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    private void ensureCapacity() {
        byte[] newBuffer = new byte[(int) (buffer.length * 1.5 + 1)];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        buffer = newBuffer;
    }

    private void ensureCapacity(int addCapacity) {
        byte[] newBuffer = new byte[(int) (buffer.length + addCapacity)];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        buffer = newBuffer;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, index);
    }

    @Override
    public String toString() {
        return new String(buffer, 0, index);
    }

    public int size() {
        return index;
    }
}

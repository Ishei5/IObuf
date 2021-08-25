package com.pankov.roadtoseniour.iobuf;

import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {

    private byte[] buffer;
    private int index;
    private int count;

    public ByteArrayInputStream(byte[] buffer) {
        this.buffer = buffer;
        this.index = 0;
        this.count = buffer.length;
    }

    @Override
    public int read() {
        if (index == count) {
            return -1;
        }
        return buffer[index++];
    }

    @Override
    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) {

        if (len + off > b.length) {
            throw new IndexOutOfBoundsException();
        }

        int countOfNotReaded = count - index;

        if (countOfNotReaded <= 0) {
            return -1;
        }
        if (countOfNotReaded < len) {
            len = countOfNotReaded;
        }

        System.arraycopy(buffer, index, b, off, len);
        index += len;

        return len;
    }

    public int available() {
        return count - index;
    }
}

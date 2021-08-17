package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class BufferedInputStreamTest {

    private String testString = "Hello InputStream";

    @Test
    public void testRead() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testString.getBytes());
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS);

        StringBuilder stringBuilder = new StringBuilder();
        int value;
        while ((value = inputStream.read()) != -1) {
            stringBuilder.append((char) value);
        }

        assertEquals(testString, stringBuilder.toString());
    }

    @Test
    public void testReadArray() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testString.getBytes());
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 11);

        StringBuilder stringBuilder = new StringBuilder();
        int value;
        byte[] buffer = new byte[5];

        while ((value = inputStream.read(buffer)) != -1) {
            stringBuilder.append(new String(buffer, 0, value));
        }

        assertEquals(testString, stringBuilder.toString());
        assertEquals(3, byteArrayIS.getCountOfInvokeRead());
    }

    @Test
    public void testReadFromEmptyStream() throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream("".getBytes()));
        assertEquals(-1, inputStream.read());
    }
}


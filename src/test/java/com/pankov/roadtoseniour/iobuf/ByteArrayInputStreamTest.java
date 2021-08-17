package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
//import java.io.ByteArrayInputStream;

public class ByteArrayInputStreamTest {

    private String testString = "ByteArrayInputStream";
    private ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(testString.getBytes());

    @Test
    public void testRead() {
        StringBuilder stringBuilder = new StringBuilder();

        int value;
        while ((value = byteArrayInputStream.read()) != -1) {
            stringBuilder.append((char) value);
        }

        assertEquals(testString, stringBuilder.toString());
    }

    @Test
    public void testReadByOneByte() {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream
                = new ByteArrayInputStream(content.getBytes());
        assertEquals('H', (char) byteArrayInputStream.read());
        assertEquals('e', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('o', (char) byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    public void testReadArray() {
        byte[] buff = new byte[15];

        int count = byteArrayInputStream.read(buff);
        assertEquals(15, count);
        assertEquals(new String(buff), testString.substring(0, 15));

        count = byteArrayInputStream.read(buff);
        assertEquals(5, count);
        assertEquals(new String(buff, 0, count), testString.substring(15));

        assertEquals(-1, byteArrayInputStream.read(buff));
    }

    @Test
    public void testReadWithOffset() {
        byte[] buff = new byte[10];
        int count = byteArrayInputStream.read(buff, 2, 4);
        assertEquals(4, count);
    }
}

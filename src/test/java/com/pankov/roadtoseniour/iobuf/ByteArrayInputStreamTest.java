package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
//import java.io.ByteArrayInputStream;

public class ByteArrayInputStreamTest {

    private String testString = "ByteArrayInputStream";
    private ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(testString.getBytes());

    @Test
    public void testRead() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        
        int value;
        while( (value = byteArrayInputStream.read()) != -1 ) {
            stringBuilder.append((char) value);
        }
        
        assertEquals(testString, stringBuilder.toString());
    }

    @Test
    public void testReadArray() throws IOException {
        byte[] buff = new byte[15];

        int count = byteArrayInputStream.read(buff);
        assertEquals(15, count);
        assertEquals(new String(buff), testString.substring(0,15));

        count = byteArrayInputStream.read(buff);
        assertEquals(5, count);

        assertEquals(-1, byteArrayInputStream.read(buff));
    }

    @Test
    public void testReadWithOffset() throws IOException {
        byte[] buff = new byte[10];
        int count = byteArrayInputStream.read(buff, 2, 4);
        assertEquals(4, count);
    }
}

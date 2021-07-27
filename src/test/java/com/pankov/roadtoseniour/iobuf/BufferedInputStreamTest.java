package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamTest {

    @Test
    public void testRead() throws IOException {
        String testString = "Hello InputStream";
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testString.getBytes());
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS);

        StringBuilder stringBuilder = new StringBuilder();
        int value;
        while ((value = inputStream.read()) != -1){
            stringBuilder.append((char) value);
        }

        assertEquals(testString, stringBuilder.toString());
    }

    @Test
    public void testReadArray() throws IOException {
        String testString = "Hello InputStream";
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testString.getBytes());

        }
}

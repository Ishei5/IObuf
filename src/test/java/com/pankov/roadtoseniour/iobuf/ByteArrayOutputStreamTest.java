package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ByteArrayOutputStreamTest {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(9);
    String testSting = "Byte Output";

    @Test
    public void testWrite() {
        byteArrayOutputStream.write(65);
        assertEquals(1, byteArrayOutputStream.size());
        assertEquals(65, byteArrayOutputStream.toByteArray()[0]);
    }

    @Test
    public void testWriteWithOffset() {
        byteArrayOutputStream.write(testSting.getBytes(), 5, 6);
        assertEquals(6, byteArrayOutputStream.size());
        assertEquals(testSting.substring(5), new String(byteArrayOutputStream.toByteArray()));
    }

    @Test
    public void testWriteArrayAndToString() {
        byteArrayOutputStream.write(testSting.getBytes());
        assertEquals(11, byteArrayOutputStream.size());
        assertEquals(testSting, byteArrayOutputStream.toString());
    }

    @Test
    public void testToByteArray() {
        byte[] expectedByteArray = testSting.getBytes();

        byteArrayOutputStream.write(expectedByteArray);

        assertArrayEquals(expectedByteArray, byteArrayOutputStream.toByteArray());
    }
}

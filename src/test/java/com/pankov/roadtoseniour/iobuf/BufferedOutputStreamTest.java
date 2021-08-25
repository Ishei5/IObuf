package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class BufferedOutputStreamTest {

    @Test
    public void testWriteOneByte() throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS);

        outputStream.write(74);
        outputStream.flush();

        byte[] array = byteArrayOS.toByteArray();
        assertEquals(1, array.length);
        assertEquals(74, array[0]);
    }

    @Test
    @DisplayName("Test flush not filled buffer when write the array which exceed available bytes in buffer " +
            "OS should contains two byte")
    public void testBufferFlush() throws IOException{
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 4);

        outputStream.write(5);
        outputStream.write(6);
        outputStream.write(new byte[]{2, 3, 4});

        assertArrayEquals(new byte[] {5, 6}, byteArrayOS.toByteArray());
    }
    @Test
    @DisplayName("Write two arrays and flush after second OS should contains 5 bytes")
    public void testWriteArray() throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 3);

        outputStream.write(new byte[]{1, 2, 3});
        outputStream.write(new byte[]{4, 5});
        outputStream.flush();
        byte[] array = byteArrayOS.toByteArray();

        assertEquals(5, array.length);
        for (int i = 0; i < 5; i++) {
            assertEquals(i + 1, array[i]);
        }
    }

    @Test
    @DisplayName("Write array to not empty buffer that exceed its length")
    public void testWriteArrayToNotEmptyBufferTharExceedItsLength() throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 5);

        outputStream.write(new byte[] {1, 2});
        outputStream.write(new byte[] {3, 4, 5, 6, 7, 8});

        byte[] expectedArray = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(expectedArray, byteArrayOS.toByteArray());
    }

    @Test
    @DisplayName("Write array when count of available bytes in buffer less then array length")
    public void testWriteArrayWhenCountOfAvailableBytesInBufferLessThenArrayLength() throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 5);

        outputStream.write(new byte[] {1, 2, 3});
        outputStream.write(new byte[] {4, 5, 6});
        outputStream.flush();

        byte[] expectedArray = new byte[] {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expectedArray, byteArrayOS.toByteArray());
    }

    @Test
    @DisplayName("Write array which length equals to buffer length should write directly to OS")
    public void testWriteArrayLengthEqualsBuffer() throws IOException{
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 5);

        outputStream.write(new byte[] {1, 2, 3, 4, 5});

        byte[] expectedArray = new byte[] {1, 2, 3, 4, 5};

        assertArrayEquals(expectedArray, byteArrayOS.toByteArray());
    }

    @Test
    @DisplayName("Use try with resources")
    public void testWriteAndCloseWithTryWithResources() throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();

        try (BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 5)) {
            outputStream.write(1);
            outputStream.write(new byte[] {2, 3});
        }

        byte[] expectedArray = new byte[] {1, 2, 3};

        assertArrayEquals(expectedArray, byteArrayOS.toByteArray());
    }

    @Test
    @DisplayName("Write array starting at offset")
    public void testWriteOffset() throws IOException{
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOS, 5);

        outputStream.write(new byte[] {1, 2, 3, 4, 5, 6, 7}, 2, 4);
        outputStream.flush();

        byte[] expectedArray = new byte[] {3, 4, 5, 6};

        assertArrayEquals(expectedArray, byteArrayOS.toByteArray());
    }
}

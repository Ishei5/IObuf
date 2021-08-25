package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferedInputStreamTest {

    private byte[] testArray = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    @DisplayName("Read by one byte")
    public void testReadByOneByte() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS);

        int value;
        List<Byte> list = new ArrayList<>();

        while ((value = inputStream.read()) != -1) {
            list.add((byte) value);
        }

        Byte[] expectedArray = new Byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Byte[] actualArray = list.toArray(new Byte[0]);

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    @DisplayName("Read all bytes by array which length less buffer")
    public void testReadByArray() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 4);

        int count;
        List<Byte> list = new ArrayList<>();
        byte[] array = new byte[3];

        while ((count = inputStream.read(array)) != -1) {
            for (int i = 0; i < count; i++) {
                list.add(array[i]);
            }
        }

        Byte[] expectedArray = new Byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Byte[] actualArray = list.toArray(new Byte[0]);

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    @DisplayName("Test read array with offset")
    public void testReadArray() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 5);

        byte[] array = new byte[5];
        int count = inputStream.read(array, 1, 3);
        assertEquals(3, count);
        assertArrayEquals(array, new byte[]{0, 1, 2, 3, 0});

        int count1 = inputStream.read(array, 2, 2);
        assertEquals(2, count1);
        assertArrayEquals(array, new byte[]{0, 1, 4, 5, 0});
    }

    @Test
    public void testReadFromEmptyStream() throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream("".getBytes()));
        assertEquals(-1, inputStream.read());
    }

    @Test
    @DisplayName("Read array which length is greater then buffer length")
    public void testReadArrayWhichLengthIsGreaterThenBuffer() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 3);

        byte[] array = new byte[5];
        int count = inputStream.read(array);

        byte[] expectedArray = new byte[]{1, 2, 3, 4, 5};

        assertEquals(5, count);
        assertArrayEquals(expectedArray, array);
    }

    @Test
    @DisplayName("Read array which length greater then available bytes in buffer should read all unread bytes from buffer")
    public void testReadArrayWhichLengthGreaterThenAvailableByteInBuffer() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 4);

        byte[] array1 = new byte[2];
        int count1 = inputStream.read(array1);

        assertEquals(2, count1);
        assertArrayEquals(new byte[]{1, 2}, array1);

        byte[] array2 = new byte[3];
        int count2 = inputStream.read(array2);

        assertEquals(3, count2);
        assertArrayEquals(new byte[]{3, 4, 5}, array2);
    }

    @Test
    @DisplayName("Read all bytes by array which length greater buffer")
    public void testReadByArrayWhichLengthGreaterThenBuffer() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 3);

        int count;
        List<Byte> list = new ArrayList<>();
        byte[] array = new byte[4];

        while ((count = inputStream.read(array)) != -1) {
            for (int i = 0; i < count; i++) {
                list.add(array[i]);
            }
        }

        Byte[] expectedArray = new Byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Byte[] actualArray = list.toArray(new Byte[0]);

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    @DisplayName("Test read by arrays of diff length")
    public void testReadByArraysOfDiffLength() throws IOException {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(
                new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17});
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 3);

        byte[] array1 = new byte[4];
        int count1 = inputStream.read(array1);
        assertEquals(4, count1);
        assertArrayEquals(new byte[]{1, 2, 3, 4}, array1);

        byte[] array2 = new byte[2];
        int count2 = inputStream.read(array2);
        assertEquals(2, count2);
        assertArrayEquals(new byte[]{5, 6}, array2);

        byte[] array3 = new byte[4];
        int count3 = inputStream.read(array3);
        assertEquals(4, count3);
        assertArrayEquals(new byte[]{7, 8, 9, 10}, array3);

        byte[] array4 = new byte[3];
        int count4 = inputStream.read(array4);
        assertEquals(3, count4);
        assertArrayEquals(new byte[]{11, 12, 13}, array4);

        byte[] array5 = new byte[2];
        int count5 = inputStream.read(array5);
        assertEquals(2, count5);
        assertArrayEquals(new byte[]{14, 15}, array5);

        int count6 = inputStream.read(array4);
        assertEquals(2, count6);
        assertArrayEquals(new byte[]{16, 17, 13}, array4);

        assertEquals(-1, inputStream.read(array2));
    }

    @Test
    public void testReadArrayWithOffsetShouldThrowException() throws IOException{
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(testArray);
            BufferedInputStream inputStream = new BufferedInputStream(byteArrayIS, 3);

            byte[] array = new byte[4];
            int count = inputStream.read(array, 1, 4);
        });
    }
}


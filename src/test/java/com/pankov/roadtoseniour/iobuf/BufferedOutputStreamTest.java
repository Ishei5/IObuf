package com.pankov.roadtoseniour.iobuf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import java.io.ByteArrayOutputStream;
//import java.io.BufferedOutputStream;
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

        assertEquals(2, byteArrayOS.getCountOfInvokeWrite());
    }
}

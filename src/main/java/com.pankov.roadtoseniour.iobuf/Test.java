package com.pankov.roadtoseniour.iobuf;

import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(
                new ByteArrayInputStream(new byte[]{1,2,3,4,5,6,7}), 4);

        byte[] array = new byte[2];
        System.out.println(inputStream.read(array));
        System.out.println(Arrays.toString(array));

        byte[] array2 = new byte[5];
        int count = inputStream.read(array2);
        System.out.println(count);
        System.out.println(Arrays.toString(array2));


    }
}

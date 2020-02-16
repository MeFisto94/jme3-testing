package com.github.MeFisto94.jme3_testing.harness;

import org.junit.jupiter.api.Assertions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.File;

public class ImageComparator {

    public static double compare(byte[] A, byte[] B) {
        double difference = 0d;
        for (int i = 0; i < A.length; i++) {
            // We need to expand the bytes, because otherwise a 0xFF ends up being -1 and thus the difference to 0 is only 1
            int a_exp = (A[i] & 0xFF);
            int b_exp = (B[i] & 0xFF);
            difference += Math.abs(a_exp - b_exp);
        }

        // Normalize the difference on a per-pixel base and convert the byte offset to [0..1]
        return 1d - (difference / (A.length * 255d));
    }

    public static void assertPixelPerfect(byte[] A, byte[] B) {
        Assertions.assertEquals(A.length, B.length, "Buffer Sizes not even matching");
        for (int i = 0; i < A.length; i++) {
            // We need to expand the bytes, because otherwise a 0xFF ends up being -1 and thus the difference to 0 is only 1
            int a_exp = (A[i] & 0xFF);
            int b_exp = (B[i] & 0xFF);
            Assertions.assertEquals(a_exp, b_exp, "Byte " + i + " doesn't match");
        }
    }

    public static void assertSimilarityOver(byte[] A, byte[] B, double threshold) {
        Assertions.assertEquals(A.length, B.length, "Buffer Sizes not even matching");
        double sim = compare(A, B);
        System.out.println(sim);
        Assertions.assertTrue(sim >= threshold, String.format("Image similarity of %f is below the threshold %f", sim, threshold));
    }

    /**
     * <b>Warning</b>: This method guarantees to return a buffer with {@link BufferedImage#TYPE_3BYTE_BGR} and otherwise Asserts
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] loadReferenceImage(File file) throws Exception {
        BufferedImage referenceImage;
        referenceImage = ImageIO.read(file);
        Assertions.assertEquals(BufferedImage.TYPE_3BYTE_BGR, referenceImage.getType());

        DataBuffer data = referenceImage.getData().getDataBuffer();
        if (data instanceof DataBufferByte) {
            return  ((DataBufferByte) data).getData();
        } else {
            // Maybe no RGBA8 but RGBA16 (ushort), etc. In general we assume the format of the file blindly.
            throw new IllegalArgumentException("The loaded image was no DataBufferByte, I don't know what this means, though.");
        }
    }
}

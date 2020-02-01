package com.github.MeFisto94.jme3_testing.harness;

import org.junit.jupiter.api.Assertions;

public class TestingUtils {
    public static void assertNoException(LegacyTestApplication lta) {
        Throwable t = lta.getAtomicThrowable().get();

        if (t != null) {
            Assertions.assertDoesNotThrow(() -> {throw t;});
        }
    }

    public static void assertNoException(SimpleTestApplication sta) {
        Throwable t = sta.getAtomicThrowable().get();

        if (t != null) {
            Assertions.assertDoesNotThrow(() -> {throw t;});
        }
    }
}

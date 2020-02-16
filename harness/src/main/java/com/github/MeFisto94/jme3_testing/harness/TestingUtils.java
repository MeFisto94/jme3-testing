package com.github.MeFisto94.jme3_testing.harness;

import org.junit.jupiter.api.Assertions;

import java.util.Optional;

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

    public static Optional<Throwable> getThrowable(LegacyTestApplication lta) {
        return Optional.ofNullable(lta.getAtomicThrowable().get());
    }

    public static Optional<Throwable> getThrowable(SimpleTestApplication sta) {
        return Optional.ofNullable(sta.getAtomicThrowable().get());
    }
}

package com.github.MeFisto94.jme3_testing.tests;

import com.github.MeFisto94.jme3_testing.harness.LegacyTestApplication;
import com.github.MeFisto94.jme3_testing.harness.SimpleTestApplication;
import com.github.MeFisto94.jme3_testing.harness.TestingUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

/**
 * This tests ensure that the testing harness works correctly, so yes, we are testing the tester.
 *
 * @author MeFisto94
 */
public class TestTestHarness {
    @Test
    void testExceptionDispatcher() {
        LegacyTestApplication lta = new LegacyTestApplication();
        lta.handleError("FOO=BAR", new IllegalArgumentException());
        Assertions.assertThrows(AssertionFailedError.class, () -> TestingUtils.assertNoException(lta));
        Assertions.assertThrows(IllegalArgumentException.class, () -> { throw TestingUtils.getThrowable(lta).get(); });
    }

    @Test
    void testProvocateNPE() {
        LegacyTestApplication lta = new LegacyTestApplication();
        lta.enqueue(() -> { String s = null; s.toString(); });
        Assertions.assertThrows(NullPointerException.class, lta::update);
    }

    @Test
    void testExceptionDispatcherSimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        sta.handleError("FOO=BAR", new IllegalArgumentException());
        Assertions.assertThrows(AssertionFailedError.class, () -> TestingUtils.assertNoException(sta));
        Assertions.assertThrows(IllegalArgumentException.class, () -> { throw TestingUtils.getThrowable(sta).get(); });
    }

    @Test
    void testProvocateNPESimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        sta.enqueue(() -> { String s = null; s.toString(); });
        Assertions.assertThrows(NullPointerException.class, sta::update);
    }

}
package com.github.MeFisto94.jme3_testing.tests;

import com.github.MeFisto94.jme3_testing.harness.LegacyTestApplication;
import com.github.MeFisto94.jme3_testing.harness.SimpleTestApplication;
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
        Assertions.assertThrows(AssertionFailedError.class, () -> lta.handleError("FOO=BAR", new IllegalArgumentException()), "FOO=BAR");
    }

    @Test
    void testStopFalse() {
        LegacyTestApplication lta = new LegacyTestApplication();
        Assertions.assertThrows(IllegalArgumentException.class, lta::stop,
                "Always call stop(true), because otherwise the next test might fail to initialize");
    }

    @Test
    void testProvocateNPE() {
        LegacyTestApplication lta = new LegacyTestApplication();
        lta.start(true);
        lta.enqueue(() -> { String s = null; s.toString(); });
        Assertions.assertThrows(NullPointerException.class, lta::update);
        //Assertions.assertTimeout(Duration.ofSeconds(10), );
    }

    @Test
    void testExceptionDispatcherSimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        Assertions.assertThrows(AssertionFailedError.class, () -> sta.handleError("FOO=BAR", new IllegalArgumentException()), "FOO=BAR");
    }

    @Test
    void testStopFalseSimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        Assertions.assertThrows(IllegalArgumentException.class, sta::stop,
                "Always call stop(true), because otherwise the next test might fail to initialize");
    }

    @Test
    void testProvocateNPESimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        sta.start(true);
        sta.enqueue(() -> { String s = null; s.toString(); });
        Assertions.assertThrows(NullPointerException.class, sta::update);
        //Assertions.assertTimeout(Duration.ofSeconds(10), );
    }

}
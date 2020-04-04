package com.github.MeFisto94.jme3_testing.tests.lwjgl3;

import com.github.MeFisto94.jme3_testing.tests.lwjgl.TestContextCreation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * This tests ensure that jme at least boots up for a few frames and closes again.
 *
 * @author MeFisto94
 */
public class TestContextCreationLWJGL3 extends TestContextCreation {

    /**
     * Disabled because LWJGL3 does not support returning from start.
     */
    @Override
    @Test
    @Disabled
    protected void testLegacyWaitFor() {
        super.testLegacyWaitFor();
    }

    /**
     * Disabled because LWJGL3 does not support returning from start.
     */
    @Override
    @Test
    @Disabled
    protected void testSimpleWaitFor() {
        super.testSimpleWaitFor();
    }

    /**
     * Disabled because LWJGL3 does not support returning from start.
     */
    @Override
    @Test
    @Disabled
    protected void testStopWaitForLegacy() {
        super.testStopWaitForLegacy();
    }

    /**
     * Disabled because LWJGL3 does not support returning from start.
     */
    @Override
    @Test
    @Disabled
    protected void testStopWaitForSimple() {
        super.testStopWaitForSimple();
    }

    @Override
    @Test
    protected void test5FramesLegacy() {
        super.test5FramesLegacy();
    }

    @Override
    @Test
    protected void test5FramesSimple() {
        super.test5FramesSimple();
    }

    @Test
    protected void testSimpleBlueCube10s() {
        super.testSimpleBlueCube10s(false, false);
    }

    @Test
    protected void testSimpleBlueCube10sDebug() {
        super.testSimpleBlueCube10s(true, false);
    }

    @Test
    protected void testSimpleBlueCube10sStats() {
        super.testSimpleBlueCube10s(false, true);
    }

    @Test
    protected void testSimpleBlueCube10sDebugStats() {
        super.testSimpleBlueCube10s(true, true);
    }

    @Override
    @Test
    protected void testDoubleStopHangup() {
        super.testDoubleStopHangup();
    }
}
package com.github.MeFisto94.jme3_testing.tests.lwjgl2;

import com.github.MeFisto94.jme3_testing.tests.lwjgl.TestContextCreation;
import com.jme3.system.lwjgl.LwjglAbstractDisplay;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This tests ensure that jme at least boots up for a few frames and closes again.
 *
 * @author MeFisto94
 */
public class TestContextCreationLWJGL2 extends TestContextCreation {
    private static final Logger logger = Logger.getLogger(LwjglAbstractDisplay.class.getName());

    static {
        logger.setLevel(Level.ALL);
    }

    @Override
    @Test
    protected void testLegacyWaitFor() {
        super.testLegacyWaitFor();
    }

    @Override
    @Test
    protected void testSimpleWaitFor() {
        super.testSimpleWaitFor();
    }

    @Override
    @Test
    protected void testStopWaitForLegacy() {
        super.testStopWaitForLegacy();
    }

    @Override
    @Test
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

    @Override
    @Test
    protected void testSimpleBlueCube10s() {
        super.testSimpleBlueCube10s();
    }

    /**
     * Currently disabled on lwjgl2 as this is definitely a bug.
     */
    @Override
    @Test
    @Disabled
    protected void testDoubleStopHangup() {
        super.testDoubleStopHangup();
    }
}
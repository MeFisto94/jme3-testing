package com.github.MeFisto94.jme3_testing.tests.lwjgl3;

import com.github.MeFisto94.jme3_testing.tests.lwjgl.TestOpenGLVersions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

/**
 * This class is a bit uncommon: The Test contains the debug parameter, so that the base implementation can set
 * GraphicsDebug or not. <br />
 */
public class TestOpenGLVersionsLWJGL3Debug extends TestOpenGLVersions {

    @Test
    protected void testOpenGL20() {
        super.testOpenGL20(true);
    }

    /**
     * Disabled until setting specific compat GL versions is available (3.0 is no valid core version)
     */
    @Test
    @Disabled
    protected void testOpenGL30() {
        super.testOpenGL30(true);
    }


    /**
     * Disabled until setting specific compat GL versions is available (3.1 is no valid core version)
     */
    @Test
    @Disabled
    protected void testOpenGL31() {
        super.testOpenGL31(true);
    }

    @Test
    protected void testOpenGL32() {
        super.testOpenGL32(true);
    }

    @Test
    protected void testOpenGL33() {
        super.testOpenGL33(true);
    }

    @Test
    @DisabledIfSystemProperty(named = "mesa-llvm-ci", matches= "true")
    protected void testOpenGL40() {
        super.testOpenGL40(true);
    }

    @Test
    @DisabledIfSystemProperty(named = "mesa-llvm-ci", matches= "true")
    protected void testOpenGL41() {
        super.testOpenGL41(true);
    }

    @Test
    @DisabledIfSystemProperty(named = "mesa-llvm-ci", matches= "true")
    protected void testOpenGL42() {
        super.testOpenGL42(true);
    }

    @Test
    @DisabledIfSystemProperty(named = "mesa-llvm-ci", matches= "true")
    protected void testOpenGL43() {
        super.testOpenGL43(true);
    }

    @Test
    @DisabledIfSystemProperty(named = "mesa-llvm-ci", matches= "true")
    protected void testOpenGL44() {
        super.testOpenGL44(true);
    }

    @Test
    @DisabledIfSystemProperty(named = "mesa-llvm-ci", matches= "true")
    protected void testOpenGL45() {
        super.testOpenGL45(true);
    }
}

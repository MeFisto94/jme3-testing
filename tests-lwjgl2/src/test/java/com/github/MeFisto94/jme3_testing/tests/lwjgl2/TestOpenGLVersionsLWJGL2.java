package com.github.MeFisto94.jme3_testing.tests.lwjgl2;

import com.github.MeFisto94.jme3_testing.tests.lwjgl.TestOpenGLVersions;
import org.junit.jupiter.api.Test;

/**
 * This class is a bit uncommon: The Test contains the debug parameter, so that the base implementation can set
 * GraphicsDebug or not. <br />
 */
public class TestOpenGLVersionsLWJGL2 extends TestOpenGLVersions {

    @Test
    protected void testOpenGL20() {
        super.testOpenGL20(false);
    }

    @Test
    protected void testOpenGL30() {
        super.testOpenGL30(false);
    }

    @Test
    protected void testOpenGL31() {
        super.testOpenGL31(false);
    }

    @Test
    protected void testOpenGL32() {
        super.testOpenGL32(false);
    }

    @Test
    protected void testOpenGL33() {
        super.testOpenGL33(false);
    }

    @Test
    protected void testOpenGL40() {
        super.testOpenGL40(false);
    }

    @Test
    protected void testOpenGL41() {
        super.testOpenGL41(false);
    }

    @Test
    protected void testOpenGL42() {
        super.testOpenGL42(false);
    }

    @Test
    protected void testOpenGL43() {
        super.testOpenGL43(false);
    }

    @Test
    protected void testOpenGL44() {
        super.testOpenGL44(false);
    }

    @Test
    protected void testOpenGL45() {
        super.testOpenGL45(false);
    }
}

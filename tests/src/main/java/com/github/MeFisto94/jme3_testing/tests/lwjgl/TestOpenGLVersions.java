package com.github.MeFisto94.jme3_testing.tests.lwjgl;

import com.github.MeFisto94.jme3_testing.harness.LegacyTestApplication;
import com.github.MeFisto94.jme3_testing.harness.SimpleTestApplication;
import com.github.MeFisto94.jme3_testing.harness.TestingUtils;
import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

/**
 * This tests ensure that jme starts with different openGL Versions.<br />
 * This tests are run by both the lwjgl2 and lwjgl3 module and as such this is kind of an "abstract class" or
 * "interface with implementation".<br />
 * Actually the sub-modules extend this class, override each method, call super() and add the @Test annotation.<br />
 * <br />
 * Do note that a TestFactory would be good here, but then we can't enforce subclasses for lwjgl2 and 3.<br />
 * One issue these tests have at least on LWJGL3 is that they require focus or else start() doesn't seem to return.
 *
 * @author MeFisto94
 */
public abstract class TestOpenGLVersions {
    Application app;

    @AfterEach
    protected void stop() {
        if (app != null) {
            // If an Exception fired, ensure disposal.
            if (app instanceof LegacyTestApplication) {
                if (((LegacyTestApplication) app).getStopped().get()) {
                    app = null;
                    return;
                } else if (((SimpleTestApplication) app).getStopped().get()) {
                    app = null;
                    return;
                }
            }

            app.stop(true);
            app = null;
        }
    }

    /**
     * Tests whether openGL 2.0 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL20(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 2.0 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL2);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true)); // lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 3.0 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL30(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 3.0 Test");
        // @TODO: Core Profiles start at 3.2, so that needs implementing first.
        set.setRenderer(AppSettings.LWJGL_OPENGL30);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 3.1 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL31(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 3.1 Test");
        set.putBoolean("GraphicsDebug", debug);
        // @TODO: Wait for PR to be merged. Also: Core Profiles start at 3.2, so that needs implementing first
        set.setRenderer(AppSettings.LWJGL_OPENGL30);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 3.2 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL32(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 3.2 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL32);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 3.3 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL33(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 3.3 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL33);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 4.0 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL40(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 4.0 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL40);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 4.1 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL41(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 4.1 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL41);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 4.2 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL42(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 4.2 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL42);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 4.3 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL43(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 4.3 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL43);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 4.4 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL44(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 4.4 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL44);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }

    /**
     * Tests whether openGL 4.5 is working
     *
     * @param debug Whether to run in GraphicsDebug or not
     */
    protected void testOpenGL45(boolean debug) {
        LegacyTestApplication lta = new LegacyTestApplication();
        AppSettings set = new AppSettings(true);
        set.setTitle("OpenGL 4.5 Test");
        set.setRenderer(AppSettings.LWJGL_OPENGL45);
        set.putBoolean("GraphicsDebug", debug);
        lta.setSettings(set);
        app = lta;
        lta.enqueue(() -> lta.stopAfter(3L)); // lwjgl3, has to be enqueued, so the app is inited.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), lta.waitForStop()); // lwjgl2
        TestingUtils.assertNoException(lta);
    }
}

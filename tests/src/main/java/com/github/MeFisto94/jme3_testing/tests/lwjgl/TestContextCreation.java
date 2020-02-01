package com.github.MeFisto94.jme3_testing.tests.lwjgl;

import com.github.MeFisto94.jme3_testing.harness.LegacyTestApplication;
import com.github.MeFisto94.jme3_testing.harness.SimpleTestApplication;
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
 * This tests ensure that jme at least boots up for a few frames and closes again.<br />
 * This tests are run by both the lwjgl2 and lwjgl3 module and as such this is kind of an "abstract class" or
 * "interface with implementation".<br />
 * Actually the sub-modules extend this class, override each method, call super() and add the @Test annotation.<br />
 * <br />
 * Do note that at the time of writing LWJGL3 has the limitation of not returning from a start() call until the
 * Application stops, thus the tests are written a bit oddly where start() is the last statement instead of what one
 * might expect to wait until the application has fully started and then do the testing.
 *
 * @author MeFisto94
 */
public abstract class TestContextCreation {
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

            app.stop(false);
            app = null;
        }
    }

    /**
     * This test should ensure that starting an application with waitFor = true blocks until the context is inited,
     * but not any longer (no deadlock) and does the same for stop.
     */
    protected void testLegacyWaitFor() {
        LegacyTestApplication lta = new LegacyTestApplication();
        app = lta;
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> lta.stop(true));
    }

    /**
     * @see #testLegacyWaitFor()
     */
    protected void testSimpleWaitFor() {
        SimpleTestApplication sta = new SimpleTestApplication();
        app = sta;
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> sta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5L), () -> sta.stop(true));
    }

    /**
     * This test is temporary as waitFor is not working for start(), so we'll see if it works for stop at least.
     */
    protected void testStopWaitForLegacy() {
        LegacyTestApplication lta = new LegacyTestApplication();
        app = lta;
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(3L), () -> lta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), () -> lta.stop(true));
    }

    /**
     * @see #testStopWaitForLegacy()
     */
    protected void testStopWaitForSimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        app = sta;
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(3L), () -> sta.start(true));
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), () -> sta.stop(true));
    }

    /**
     * There have been cases where a duplicated stop led to an endless hangup
     */
    protected void testDoubleStopHangup() {
        SimpleTestApplication sta = new SimpleTestApplication();
        sta.enqueue(() -> { sta.stop(true); sta.stop(true); });
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), () -> sta.start(true)); // lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), sta.waitForStop()); // lwjgl2
    }

    protected void test5FramesLegacy() {
        LegacyTestApplication lta = new LegacyTestApplication();
        lta.enqueue(() -> lta.stopAfter(5L)); // As said, has to be done that way because start can be blocking
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), () -> lta.start(true)); // for lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), lta.waitForStop()); // for lwjgl2
    }

    protected void test5FramesSimple() {
        SimpleTestApplication sta = new SimpleTestApplication();
        sta.enqueue(() -> sta.stopAfter(5L)); // As said, has to be done that way because start can be blocking
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), () -> sta.start(true)); // for lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10L), sta.waitForStop()); // for lwjgl2
    }

    protected void testSimpleBlueCube10s() {
        SimpleTestApplication sta = new SimpleTestApplication();
        AppSettings set = new AppSettings(true);
        set.setFrameRate(30); // Otherwise we can't control how fast the blue will pop up and down.
        set.setTitle("Blue Cube Functional Test");
        sta.setSettings(set);

        app = sta; // Never know.

        sta.enqueue(() -> {
            Box b = new Box(1, 1, 1); // create cube shape
            Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
            Material mat = new Material(sta.getAssetManager(),
                    "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
            mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
            geom.setMaterial(mat);                   // set the cube's material
            sta.getRootNode().attachChild(geom);              // make the cube appear in the scene
        });

        sta.stopAfter(150L); // stop after 150 frames aka 5 seconds.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(20L), () -> sta.start(true)); // for lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(20L), sta.waitForStop()); // for lwjgl2
    }
}
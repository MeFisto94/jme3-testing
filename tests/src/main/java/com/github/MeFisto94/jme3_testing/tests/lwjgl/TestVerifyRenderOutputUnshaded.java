package com.github.MeFisto94.jme3_testing.tests.lwjgl;

import com.github.MeFisto94.jme3_testing.harness.*;
import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.net.URL;
import java.time.Duration;

/**
 * Tests that compare the render output with previously gathered samples.<br />
 * Here we specifically test Unshaded.j3md.
 *
 * @author MeFisto94
 */
public class TestVerifyRenderOutputUnshaded {
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

    protected void testSimpleBlueCube() {
        // Specifically leave out all AppStates, so the user doesn't accidentially move the mouse etc.
        SimpleTestApplication sta = new SimpleTestApplication(new AppState[]{});
        AppSettings set = new AppSettings(true);
        set.setFrameRate(30); // timebase for the timeouts and comparison speed.
        set.setTitle("Blue Cube Functional Test");
        sta.setSettings(set);

        app = sta; // Never know.

        try {
            File imgFile = new File(getClass().getResource("/samples/blue_cube_640x480.png").toURI());
            byte[] refImage = ImageComparator.loadReferenceImage(imgFile);
            sta.enqueue(() -> {
                Box b = new Box(1, 1, 1); // create cube shape
                Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
                Material mat = new Material(sta.getAssetManager(),
                        "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
                mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
                geom.setMaterial(mat);                   // set the cube's material
                sta.getRootNode().attachChild(geom);              // make the cube appear in the scene

                //sta.getViewPort().addProcessor(new FrameGrabProcessor(bytes -> ImageComparator.assertSimilarityOver(bytes, refImage, 0.998d)));
                sta.getViewPort().addProcessor(new FrameGrabProcessor(bytes -> ImageComparator.assertPixelPerfect(bytes, refImage)));
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            Assertions.assertDoesNotThrow(() -> ex);
        }

        //
        /*try (FileOutputStream fos = new FileOutputStream(new File("./dump/" + UUID.randomUUID() + ".png"))) {
            JmeSystem.writeImageFile(fos, "png", buf, vp.getCamera().getWidth(), vp.getCamera().getHeight());
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        sta.stopAfter(150L); // stop after 150 frames aka 5 seconds.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(20L), () -> sta.start(true)); // for lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(20L), sta.waitForStop()); // for lwjgl2
        TestingUtils.assertNoException(sta);
    }
}

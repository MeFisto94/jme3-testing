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

public class TestFullscreen {
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

    protected void testFullscreenCube() {
        SimpleTestApplication sta = new SimpleTestApplication();
        AppSettings set = new AppSettings(true);
        set.setFrameRate(30); // Otherwise we can't control how fast the blue will pop up and down.
        set.setTitle("Blue Cube Functional Test");
        set.setFullscreen(true);
        set.setWidth(1920);
        set.setHeight(1080);
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

package com.github.MeFisto94.jme3_testing.tests.lwjgl;

import com.github.MeFisto94.jme3_testing.harness.*;
import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.light.LightProbe;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

/**
 * Tests that compare the render output with previously gathered samples.<br />
 * Here we specifically test PBRLighting.j3md.
 *
 * @author MeFisto94
 */
public class TestVerifyRenderOutputPBR {
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

    private void baseTestPBRBox(ColorRGBA color, float metallic, float roughness, String reference, double precision) {
        // Specifically leave out all AppStates, so the user doesn't accidentally move the mouse etc.
        SimpleTestApplication sta = new SimpleTestApplication(new AppState[]{});
        AppSettings set = new AppSettings(true);
        set.setFrameRate(30); // timebase for the timeouts and comparison speed.
        set.setTitle(String.format("Red Cube PBR Test: Metallic = %f, Roughness = %f", metallic, roughness));
        sta.setSettings(set);

        app = sta; // Never know.

        try (InputStream is = TestVerifyRenderOutputPBR.class.getResourceAsStream(reference)) {
            byte[] refImage = ImageComparator.loadReferenceImage(is);
            sta.enqueue(() -> {
                LightProbe probe = (LightProbe)sta.getAssetManager().loadModel("Scenes/defaultProbe.j3o").getLocalLightList().get(0);

                Box b = new Box(2, 2, 2); // create cube shape
                Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
                Material mat = new Material(sta.getAssetManager(),
                        "Common/MatDefs/Light/PBRLighting.j3md");  // create a simple material
                mat.setColor("BaseColor", color);   // set color of material to blue
                mat.setFloat("Roughness", roughness);
                mat.setFloat("Metallic", metallic);
                geom.setMaterial(mat);                   // set the cube's material
                sta.getRootNode().attachChild(geom);              // make the cube appear in the scene
                geom.addLight(probe);

                sta.getViewPort().addProcessor(new FrameGrabProcessor(bytes -> {
                    if (Double.isNaN(precision)) {
                        ImageComparator.assertPixelPerfect(bytes, refImage);
                    } else {
                        ImageComparator.assertSimilarityOver(bytes, refImage, precision);
                    }
                }));
                /*sta.getViewPort().addProcessor(new FrameGrabProcessor(bytes -> {
                    try {
                        ImageComparator.saveAsReferenceImage("./dump/" + UUID.randomUUID() + ".png", bytes,
                                app.getCamera().getWidth(), app.getCamera().getHeight());
                    } catch (Exception e) {
                        Assertions.fail(e);
                    }
                }, Image.Format.ABGR8));*/
            });

        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }

        sta.stopAfter(60L); // stop after 60 frames aka 2 seconds.
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(20L), () -> sta.start(true)); // for lwjgl3
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(20L), sta.waitForStop()); // for lwjgl2
        TestingUtils.assertNoException(sta);
    }

    protected void testRedCube1() {
        baseTestPBRBox(ColorRGBA.Red, 0f, 0f, "/samples/red_cube_2m_pbr_met_0_rough_0_640x480.png", 0.994d);
    }
    protected void testRedCube2() {
        baseTestPBRBox(ColorRGBA.Red, 0f, 1f, "/samples/red_cube_2m_pbr_met_0_rough_1_640x480.png", Double.NaN);
    }
    protected void testRedCube3() {
        baseTestPBRBox(ColorRGBA.Red, 1f, 0f, "/samples/red_cube_2m_pbr_met_1_rough_0_640x480.png", 0.965d);
    }
    protected void testRedCube4() {
        baseTestPBRBox(ColorRGBA.Red, 1f, 1f, "/samples/red_cube_2m_pbr_met_1_rough_1_640x480.png", Double.NaN);
    }
}

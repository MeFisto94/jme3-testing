package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.system.JmeSystem;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.util.BufferUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class FrameGrabProcessor implements SceneProcessor {
    RenderManager manager;
    ViewPort vp;
    boolean initialized = false;
    ByteBuffer buf;

    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        manager = rm;
        this.vp = vp;
        initialized = true;

        // Trigger initialization of buf
        reshape(vp, vp.getCamera().getWidth(), vp.getCamera().getHeight());
    }

    @Override
    public void reshape(ViewPort vp, int w, int h) {
        buf = BufferUtils.createByteBuffer(w * h * 4);
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void preFrame(float tpf) {

    }

    @Override
    public void postQueue(RenderQueue rq) {
    }

    @Override
    public void postFrame(FrameBuffer out) {
        manager.getRenderer().readFrameBufferWithFormat(out, buf, Image.Format.RGBA8);
        try (FileOutputStream fos = new FileOutputStream(new File("./dump/" + UUID.randomUUID() + ".png"))) {
            JmeSystem.writeImageFile(fos, "png", buf, vp.getCamera().getWidth(), vp.getCamera().getHeight());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void cleanup() {
        manager = null;
        vp = null;
        initialized = false;
    }

    @Override
    public void setProfiler(AppProfiler profiler) {

    }
}

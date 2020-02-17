package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.util.BufferUtils;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Consumer;

public class FrameGrabProcessor implements SceneProcessor {
    RenderManager manager;
    ViewPort vp;
    boolean initialized = false;
    ByteBuffer buf;
    int len;
    Consumer<byte[]> callback;
    Optional<Image.Format> format;

    public FrameGrabProcessor(Consumer<byte[]> callback) {
        this.callback = callback;
        this.format = Optional.empty();
    }

    public FrameGrabProcessor(Consumer<byte[]> callback, Image.Format format) {
        this.callback = callback;
        this.format = Optional.of(format);
    }

    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        manager = rm;
        this.vp = vp;
        initialized = true;

        // Trigger initialization of buf
        reshape(vp, vp.getCamera().getWidth(), vp.getCamera().getHeight());
    }

    protected int getComponents() {
        return format.map(value -> value.getBitsPerPixel() / 8).orElse(3);
    }

    @Override
    public void reshape(ViewPort vp, int w, int h) {
        len = w * h * getComponents();
        buf = BufferUtils.createByteBuffer(len);
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
        // When the glClearColor stays constant we don't really need alpha, which complicates image writing and loading
        // Also PNG is {@link BufferedImage#TYPE_3BYTE_BGR}, so we need to adopt
        manager.getRenderer().readFrameBufferWithFormat(out, buf, format.orElse(Image.Format.BGR8));
        byte[] a = new byte[buf.limit()];
        buf.get(a);
        buf.rewind();
        callback.accept(a);
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

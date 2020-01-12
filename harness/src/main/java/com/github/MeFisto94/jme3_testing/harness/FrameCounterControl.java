package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.app.Application;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

import java.util.concurrent.atomic.AtomicLong;

public class FrameCounterControl extends AbstractControl {
    protected long stopFrames = -1L;
    protected Application app;

    protected AtomicLong frames = new AtomicLong();

    public FrameCounterControl(Application app, long stopFrames) {
        this.app = app;
        this.stopFrames = stopFrames;
    }

    public FrameCounterControl() {
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (stopFrames == -1L || app == null) {
            frames.incrementAndGet();
        } else {
            if (frames.incrementAndGet() > stopFrames) {
                app.stop(true);
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public long getFrames() {
        return frames.get();
    }
}

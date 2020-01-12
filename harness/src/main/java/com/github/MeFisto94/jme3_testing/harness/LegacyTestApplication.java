package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.app.LegacyApplication;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.atomic.AtomicBoolean;

public class LegacyTestApplication extends LegacyApplication {
    protected Node rootNode;

    // State variables to monitor the application
    protected AtomicBoolean fullyLoaded = new AtomicBoolean();
    protected AtomicBoolean stopped = new AtomicBoolean();

    @Override
    public void start(JmeContext.Type contextType, boolean waitFor) {
        Thread.setDefaultUncaughtExceptionHandler(
                (t, e) -> handleError("[" + t.getName() + "]: " + e.getLocalizedMessage(), e)
        );

        super.start(contextType, waitFor);
    }

    @Override
    public void stop(boolean waitFor) {
        if (!waitFor) {
            throw new IllegalArgumentException("Always call stop(true), because otherwise the next test might fail to initialize");
        }

        super.stop(true);
        stopped.set(true);
    }

    @Override
    public void handleError(String errMsg, Throwable t) {
        // @TODO: Could the test succeed, because the blocking start() is finished and then Assertions is called when the test is already done?
        try {
            //stop(true); //@TODO: I think the tests will already stop the application in the after.
        } catch (Exception ex) {
            System.err.println("Exception during Application#stop, swallowed in handleError as this is most likely a NullPointerException because context is null.");
            ex.printStackTrace();
        }
        Assertions.fail(errMsg, t);
    }

    @Override
    public void update() {
        super.update();
        fullyLoaded.set(true);
        if (rootNode != null) {
            rootNode.updateLogicalState(timer.getTimePerFrame());
        }
    }

    /**
     * Since LegacyApplication doesn't have a concept of rootNodes (SimpleApplication has), we'll add this in our
     * harness, when we need more low-level tests but still want to render something (minus settings, FPS Counter, ...)
     * @param node the node to set
     */
    public void setRootNode(Node node) {
        Assertions.assertNull(rootNode); // Don't set twice.
        rootNode = node;
        getViewPort().attachScene(node);
    }

    public AtomicBoolean getFullyLoaded() {
        return fullyLoaded;
    }

    /**
     * For a few tests we just want to know whether the application survives for 10 frames without crashing.<br />
     * We'll track that with a control and block this Runnable for that time.<br />
     * <b>Note: </b> The Frame Counter is only evaluated every 50ms, so it's more a "disposeAfterAtLeast"<br />
     *
     * @param frames How many frames to count
     * @return The runnable
     */
    public Executable disposeAfterAndWait(long frames) {
        return () -> {
            Node n = new Node("Frame-Counter");
            FrameCounterControl fcc = new FrameCounterControl();
            n.addControl(fcc);
            enqueue(() -> setRootNode(n));

            while (fcc.getFrames() < frames) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }

    public void stopAfter(long frames) {
        if (rootNode == null) {
            setRootNode(new Node("StopAfterNode"));
        }
        enqueue(() -> rootNode.addControl(new FrameCounterControl(this, frames)));
    }

    /**
     * Use this to pass it to the preemptiveTimeout, so that you can ensure the applications timely termination.<br />
     * @return An Executable which will block until the application has terminated.
     */
    public Executable waitForStop() {
        return () -> {
            while (!this.stopped.get()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}
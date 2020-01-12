package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the wrapper for the SimpleApplication, and as such the easiest harness to test Applications.<br />
 * Do note that it should ultimatively be transitioned to {@link LegacyTestApplication}, however since jme code used to
 * be very un-modular, we are used to seeing a rootNode and a guiNode and everything set up like this.<br />
 * Thus until the LegacyTestApplication (LTA) is fully working, we'll resort to this simple testing. Try to avoid it at
 * any cost, if possible.<br />
 *
 * @author MeFisto94
 */
public class SimpleTestApplication extends SimpleApplication {
    //List<Runnable> runAfterInit = new ArrayList<>();
    AtomicBoolean fullyLoaded = new AtomicBoolean();
    AtomicBoolean stopped = new AtomicBoolean();

    public SimpleTestApplication(AppState... initialStates) {
        super(initialStates);
        setPauseOnLostFocus(false);
    }

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

        stopped.set(true);
        super.stop(true);
    }

    public AtomicBoolean getFullyLoaded() {
        return fullyLoaded;
    }

    @Override
    public void simpleInitApp() {
        fullyLoaded.set(true);
    }

    @Override
    public void handleError(String errMsg, Throwable t) {
        // @TODO: Could the test succeed, because the blocking start() is finished and then Assertions is called when the test is already done?
        try {
            stop(true);
        } catch (Exception ex) {
            System.err.println("Exception during Application#stop, swallowed in handleError as this is most likely a NullPointerException because context is null.");
            ex.printStackTrace();
        }
        Assertions.fail(errMsg, t);
    }

    /**
     * For a few tests we just want to know whether the application survives for 10 frames without crashing.<br />
     * We'll track that with a control and block this Runnable for that time.<br />
     * <b>Note: </b> The Frame Counter is only evaluated every 50ms, so it's more a "disposeAfterAtLeast"<br />
     * 
     * @param frames How many frames to count
     * @return The runnable
     */
    public Executable disposeAfter(long frames) {
        return () -> {
            FrameCounterControl fcc = new FrameCounterControl();
            enqueue(() -> rootNode.addControl(fcc));

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
        enqueue(() -> rootNode.addControl(new FrameCounterControl(this, frames)));
    }

    public Executable stopAfterAndWait(long frames) {
        return () -> {
            FrameCounterControl fcc = new FrameCounterControl(this, frames);
            enqueue(() -> rootNode.addControl(fcc));

            while (fcc.getFrames() < frames) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
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
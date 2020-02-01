package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
    AtomicReference<Throwable> atomicThrowable = new AtomicReference<>();

    /**
     * This method has a special signature, because otherwise it is ambigous between STA(AppState...) and STA(), when
     * no AppStates are passed.
     * @param first
     * @param initialStates
     */
    public SimpleTestApplication(AppState first, AppState... initialStates) {
        super(initialStates);
        getStateManager().attach(first);
        setPauseOnLostFocus(false);
    }

    public SimpleTestApplication() {
        super(); // don't use this(new StateX()); because that can get out of sync
        setPauseOnLostFocus(false);
    }

    @Override
    public void start(JmeContext.Type contextType, boolean waitFor) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
                if (e instanceof AssertionFailedError) {
                    e.printStackTrace();
                    throw (AssertionFailedError)e;
                } else {
                    handleError("[" + t.getName() + "]: " + e.getLocalizedMessage(), e);
                }
            }
        );

        super.start(contextType, waitFor);
    }

    @Override
    public void stop(boolean waitFor) {
        super.stop(waitFor);
        stopped.set(true);
    }

    public AtomicBoolean getFullyLoaded() {
        return fullyLoaded;
    }

    public AtomicBoolean getStopped() {
        return stopped;
    }

    public AtomicReference<Throwable> getAtomicThrowable() {
        return atomicThrowable;
    }

    @Override
    public void simpleInitApp() {
        fullyLoaded.set(true);
    }

    @Override
    public void handleError(String errMsg, Throwable t) {
        /* in case of lwjgl2, start() isn't blocking and as such the exception isn't delivered, that is why we stop
         * the application (to not run into the timeout) and hope the tester checks atomicThrowable to not have
         * a false positive!
         */
        t.printStackTrace(); // Printing never hurts
        atomicThrowable.compareAndSet(null, t); // Don't overwrite an existing exception
        stop(false);
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
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}
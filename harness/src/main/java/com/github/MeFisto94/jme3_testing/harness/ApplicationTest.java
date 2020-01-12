package com.github.MeFisto94.jme3_testing.harness;

import com.jme3.app.Application;
import com.jme3.app.LostFocusBehavior;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Listener;
import com.jme3.input.InputManager;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.system.Timer;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * This class should provide a unit-test friendly implementation of Application and will become relevant when LegacyApplication
 * is removed. Currently it doesn't do anything.
 *
 */
public class ApplicationTest implements Application {
    @Override
    public LostFocusBehavior getLostFocusBehavior() {
        return null;
    }

    @Override
    public void setLostFocusBehavior(LostFocusBehavior lostFocusBehavior) {

    }

    @Override
    public boolean isPauseOnLostFocus() {
        return false;
    }

    @Override
    public void setPauseOnLostFocus(boolean pauseOnLostFocus) {

    }

    @Override
    public void setSettings(AppSettings settings) {

    }

    @Override
    public void setTimer(Timer timer) {

    }

    @Override
    public Timer getTimer() {
        return null;
    }

    @Override
    public AssetManager getAssetManager() {
        return null;
    }

    @Override
    public InputManager getInputManager() {
        return null;
    }

    @Override
    public AppStateManager getStateManager() {
        return null;
    }

    @Override
    public RenderManager getRenderManager() {
        return null;
    }

    @Override
    public Renderer getRenderer() {
        return null;
    }

    @Override
    public AudioRenderer getAudioRenderer() {
        return null;
    }

    @Override
    public Listener getListener() {
        return null;
    }

    @Override
    public JmeContext getContext() {
        return null;
    }

    @Override
    public Camera getCamera() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void start(boolean waitFor) {

    }

    @Override
    public void setAppProfiler(AppProfiler prof) {

    }

    @Override
    public AppProfiler getAppProfiler() {
        return null;
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void stop(boolean waitFor) {

    }

    @Override
    public <V> Future<V> enqueue(Callable<V> callable) {
        return null;
    }

    @Override
    public void enqueue(Runnable runnable) {

    }

    @Override
    public ViewPort getGuiViewPort() {
        return null;
    }

    @Override
    public ViewPort getViewPort() {
        return null;
    }
}
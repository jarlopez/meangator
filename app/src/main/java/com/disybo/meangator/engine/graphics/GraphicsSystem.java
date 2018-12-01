package com.disybo.meangator.engine.graphics;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.disybo.meangator.engine.Driver;
import com.disybo.meangator.engine.input.InputSystem;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GraphicsSystem implements Renderer {

    private static final String TAG = GraphicsSystem.class.getName();

    private static final int CLIENT_GLES_VERSION = 2;

    private Driver driver;
    private GLSurfaceView view;

    private TimeData time;

    private int width;
    private int height;

    public GraphicsSystem(Driver driver) {
        Log.v(TAG, "Initializing graphics");
        this.driver = driver;
        time = new TimeData();
        view = createView();
        driver.getActivity().setContentView(view);
        driver.setInput(new InputSystem(view));
    }

    private GLSurfaceView createView() {
        GLSurfaceView glView = new GLSurfaceView(driver.getContext());
        glView.setEGLContextClientVersion(CLIENT_GLES_VERSION);
        glView.setRenderer(this);
        return glView;
    }

    public float getDeltaTime() {
        return time.delta;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        driver.getApp().onGraphicsReady();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        Log.v(TAG, "Resizing rest of application");
        this.width = width;
        this.height = height;
        driver.getApp().onResize(width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        time.update();
        driver.getApp().onRender();
        driver.getInput().processEvents();
    }

    public void onPause() {
        if (view != null) view.onPause();
    }

    public void onResume() {
        if (view != null) view.onResume();
    }

    class TimeData {
        float delta = 0.0f;
        long previous = System.nanoTime();
        long current = System.nanoTime();

        void update() {
            current = System.nanoTime();
            delta = (current - previous) / 1000000000.0f;
            previous = current;
        }
    }
}

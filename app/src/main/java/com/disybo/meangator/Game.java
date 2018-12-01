package com.disybo.meangator;

import android.opengl.GLES20;
import android.util.Log;

import com.disybo.meangator.engine.Application;
import com.disybo.meangator.engine.Driver;
import com.disybo.meangator.engine.graphics.PerspectiveCamera;

public class Game implements Application {

    public static final String TAG = Game.class.getName();

    private Driver driver;

    private PerspectiveCamera camera;

    public Game(Driver driver) {
        this.driver = driver;
        camera = new PerspectiveCamera(0, 0);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onRender() {
        GLES20.glClearColor(0.0f, 0.0f, 0.1f, 1.0f);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        camera.update();
    }

    @Override
    public void onResize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void onGraphicsReady() {
        Log.v(TAG, "Graphics reported ready!");

    }
}

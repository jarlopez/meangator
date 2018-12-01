package com.disybo.meangator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.disybo.meangator.engine.Application;
import com.disybo.meangator.engine.Driver;
import com.disybo.meangator.engine.graphics.GraphicsSystem;
import com.disybo.meangator.engine.input.InputSystem;

public class MainDriver extends Activity implements Driver {

    private static final String TAG = MainDriver.class.getName();

    private InputSystem input;
    private GraphicsSystem graphics;
    private Application app;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        Log.v(TAG, "Running main engine driver");

        graphics = new GraphicsSystem(this);
        app = new Game(this); // TODO come up with better way to set this
        app.onCreate();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public InputSystem getInput() {
        return input;
    }

    @Override
    public GraphicsSystem getGraphics() {
        return graphics;
    }

    @Override
    public Application getApp() {
        return app;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setInput(InputSystem inputSystem) {
        input = inputSystem;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

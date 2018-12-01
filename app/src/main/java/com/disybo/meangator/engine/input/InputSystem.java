package com.disybo.meangator.engine.input;

import android.opengl.GLSurfaceView;
import android.util.Log;

public class InputSystem {

    private static final String TAG = InputSystem.class.getName();

    private GLSurfaceView view;

    public InputSystem(GLSurfaceView view) {
        Log.v(TAG, "Initializing input");
        this.view = view;
    }

    public void processEvents() {
        // TODO
    }
}

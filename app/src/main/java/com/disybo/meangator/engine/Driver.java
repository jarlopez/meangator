package com.disybo.meangator.engine;


import android.app.Activity;
import android.content.Context;

import com.disybo.meangator.engine.graphics.GraphicsSystem;
import com.disybo.meangator.engine.input.InputSystem;

public interface Driver {
    Context getContext();

    InputSystem getInput();

    void setInput(InputSystem inputSystem);

    GraphicsSystem getGraphics();

    Application getApp();

    Activity getActivity();
}
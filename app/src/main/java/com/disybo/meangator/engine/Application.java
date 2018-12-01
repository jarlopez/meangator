package com.disybo.meangator.engine;

public interface Application {
    void onCreate();

    void onRender();

    void onResize(int width, int height);

    void onGraphicsReady();
}

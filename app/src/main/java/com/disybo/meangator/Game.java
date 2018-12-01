package com.disybo.meangator;

import android.opengl.GLES20;
import android.util.Log;

import com.disybo.meangator.engine.Application;
import com.disybo.meangator.engine.Driver;
import com.disybo.meangator.engine.graphics.PerspectiveCamera;
import com.disybo.meangator.engine.graphics.ShaderProgram;
import com.disybo.meangator.engine.graphics.Sprite;
import com.disybo.meangator.engine.graphics.Texture;
import com.disybo.meangator.engine.graphics.shaders.Defaults;

public class Game implements Application {

    public static final String TAG = Game.class.getName();

    private Driver driver;

    private PerspectiveCamera camera;

    // demo objects
    Sprite sprite;
    ShaderProgram shader;

    public Game(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "Creating camera");
        camera = new PerspectiveCamera(0, 0);
    }

    @Override
    public void onRender() {
        GLES20.glClearColor(0.0f, 0.0f, 0.1f, 1.0f);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        camera.update();
        sprite.onDraw(shader, camera.getCombined());
    }

    @Override
    public void onResize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void onGraphicsReady() {
        Log.v(TAG, "Graphics reported ready!");
        try {
            shader = new ShaderProgram(
                    Defaults.DEFAULT_TEX_VERTEX_SHADER,
                    Defaults.DEFAULT_TEX_FRAGMENT_SHADER,
                    new Defaults.Attribute[]{
                            Defaults.Attribute.Position,
                            Defaults.Attribute.Color,
                            Defaults.Attribute.TextureCoordinate
                    }
            );
            shader.use();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Texture tex = Texture.load(driver.getContext(), R.drawable.bumpy_bricks_public_domain);
        sprite = new Sprite(tex);
    }
}

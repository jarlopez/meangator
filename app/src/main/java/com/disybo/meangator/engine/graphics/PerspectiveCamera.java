package com.disybo.meangator.engine.graphics;

import android.opengl.Matrix;

import com.disybo.meangator.engine.math.Mat4;
import com.disybo.meangator.engine.math.Vec3;

public class PerspectiveCamera implements Camera {
    private static final String TAG = PerspectiveCamera.class.getName();

    private static final Vec3 AXIS_X = new Vec3(1, 0, 0);
    private static final Vec3 AXIS_Y = new Vec3(0, 1, 0);
    private static final Vec3 AXIS_Z = new Vec3(0, 0, 1);
    private final float near = 1.0f;
    private final float far = 1000f;
    protected int width;
    protected int height;
    protected Mat4 viewMat = new Mat4();
    protected Mat4 projectionMat = new Mat4();
    protected Mat4 combinedMat = new Mat4();
    private Vec3 eye;
    private Vec3 forward;
    private Vec3 up;

    public PerspectiveCamera(int width, int height) {
        this(width, height, new Vec3(0.0f, 0.0f, -3), AXIS_Z, AXIS_Y);
    }

    public PerspectiveCamera(int width, int height, Vec3 eye, Vec3 look, Vec3 up) {
        this.width = width;
        this.height = height;
        this.eye = eye;
        this.forward = look;
        this.up = up;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void update() {
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;

        Matrix.setLookAtM(viewMat.rep, 0,
                eye.x, eye.y, eye.z,
                eye.x + forward.x, eye.y + forward.y, eye.z + forward.z,
                up.x, up.y, up.z);
        Matrix.frustumM(projectionMat.rep, 0, left, right, bottom, top, near, far);
        Matrix.multiplyMM(combinedMat.rep, 0, projectionMat.rep, 0, viewMat.rep, 0);
    }

    public Mat4 getView() {
        return viewMat;
    }

    public Mat4 getProjection() {
        return projectionMat;
    }

    public Mat4 getCombined() {
        return combinedMat;
    }

    @Override
    public void move(Vec3 dir, float speed) {
        eye.add(dir.mul(speed));

    }

    @Override
    public void lookAt(Vec3 target) {
        eye.update(target);
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

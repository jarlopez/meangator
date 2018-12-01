package com.disybo.meangator.engine.math;

import android.opengl.Matrix;
import android.support.annotation.NonNull;

public class Vec3 {
    private final Mat4 rotMat = new Mat4();
    public float x;
    public float y;
    public float z;

    public Vec3() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 update(Vec3 other) {
        return update(other.x, other.y, other.z);
    }

    public Vec3 update(float nx, float ny, float nz) {
        x = nx;
        y = ny;
        z = nz;
        return this;
    }

    public float length() {
        double sum = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
        double sqrt = Math.sqrt(sum);
        return (float) sqrt;
    }

    public Vec3 add(Vec3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vec3 sub(Vec3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vec3 mul(float a) {
        x *= a;
        y *= a;
        z *= a;
        return this;
    }

    public Vec3 mul(Mat4 mat) {
        float[] m = mat.rep;
        float nx = x * m[0] + y * m[4] + z * m[8] + m[12];
        float ny = x * m[1] + y * m[5] + z * m[9] + m[13];
        float nz = x * m[2] + y * m[6] + z * m[10] + m[14];
        return update(nx, ny, nz);
    }

    public Vec3 rotate(Vec3 axis, float angle) {
        Matrix.setIdentityM(rotMat.rep, 0);
        Matrix.rotateM(rotMat.rep, 0, angle, axis.x, axis.y, axis.z);
        return mul(rotMat);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Vec3.class.getName());
        sb.append("(");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(", ");
        sb.append(z);
        sb.append(")");
        return sb.toString();
    }
}

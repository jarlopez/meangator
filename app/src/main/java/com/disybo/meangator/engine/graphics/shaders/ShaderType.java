package com.disybo.meangator.engine.graphics.shaders;

import android.opengl.GLES20;

public enum ShaderType {
    Vertex(GLES20.GL_VERTEX_SHADER),
    Fragment(GLES20.GL_FRAGMENT_SHADER);

    private int type;

    ShaderType(int type) {
        this.type = type;
    }

    public int asInt() {
        return type;
    }
}

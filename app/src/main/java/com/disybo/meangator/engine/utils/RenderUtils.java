package com.disybo.meangator.engine.utils;

import android.opengl.GLES20;

import com.disybo.meangator.engine.graphics.shaders.ShaderType;

public class RenderUtils {

    public static final int SHADER_ERR_CODE = 0;

    public static int loadShader(ShaderType type, String code) {
        int handle = GLES20.glCreateShader(type.asInt());
        if (handle != 0) {
            GLES20.glShaderSource(handle, code);
            GLES20.glCompileShader(handle);

            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            if (compileStatus[0] == 0) {
                // TODO report error
                GLES20.glDeleteShader(handle);
                handle = SHADER_ERR_CODE;
            }
        }
        return handle;
    }
}

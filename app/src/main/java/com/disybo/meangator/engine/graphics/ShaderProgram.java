package com.disybo.meangator.engine.graphics;

import android.opengl.GLES20;
import android.util.Log;

import com.disybo.meangator.engine.graphics.shaders.Defaults;
import com.disybo.meangator.engine.graphics.shaders.ShaderType;
import com.disybo.meangator.engine.utils.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {

    private static final String TAG = ShaderProgram.class.getName();

    private boolean compiled = false;

    private int[] linkStatus = new int[1];
    private int programHandle;
    private int vertexShaderHandle;
    private int fragmentShaderHandle;

    private Map<String, Integer> uniforms = new HashMap<>(5);
    private Map<String, Integer> attributes = new HashMap<>(5);


    public ShaderProgram() throws Exception {
        this(Defaults.DEFAULT_VERTEX_SHADER, Defaults.DEFAULT_FRAGMENT_SHADER, null);
    }

    public ShaderProgram(String vertexCode,
                         String fragmentCode,
                         Defaults.Attribute[] attribLocations
    ) throws Exception {
        Log.d(TAG, "vertexcode: \n" + vertexCode);
        Log.d(TAG, "shaderCode: \n" + fragmentCode);
        // TODO error handling on shader source code
        vertexShaderHandle = RenderUtils.loadShader(ShaderType.Vertex, vertexCode);
        fragmentShaderHandle = RenderUtils.loadShader(ShaderType.Fragment, fragmentCode);
        if (vertexShaderHandle == RenderUtils.SHADER_ERR_CODE || fragmentShaderHandle == RenderUtils.SHADER_ERR_CODE) {
            Log.e(TAG, "Error compiling vertex or fragment shader.");
            throw new Exception("Error compiling vertex or fragment shader.");
        }
        programHandle = GLES20.glCreateProgram();

        GLES20.glAttachShader(programHandle, vertexShaderHandle);
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);

        if (attribLocations != null) {
            for (Defaults.Attribute attrib : attribLocations) {
                if (attrib != null) {
                    GLES20.glBindAttribLocation(programHandle, attrib.index(), attrib.repr());
                    attributes.put(attrib.repr(), attrib.index());
                }
            }
        }

        linkProgram();
        loadStatus(GLES20.GL_LINK_STATUS);

        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programHandle);
            programHandle = 0;
        } else {
            compiled = true;
        }
    }

    public void use() {
        GLES20.glUseProgram(programHandle);
    }

    public void linkProgram() {
        GLES20.glLinkProgram(programHandle);
    }

    public void loadStatus(int param) {
        linkStatus[0] = 0;
        GLES20.glGetProgramiv(programHandle, param, linkStatus, 0);
    }

    public int getProgramHandle() {
        return programHandle;
    }

    public int getAttribute(Defaults.Attribute target) {
        return getAttribute(target.repr());
    }

    public int getAttribute(String name) {
        if (attributes.containsKey(name)) {
            return attributes.get(name);
        } else {
            return -1;
        }
    }

    public int getUniform(Defaults.Uniform target) {
        return getUniform(target.repr());
    }

    public int getUniform(String name) {
        if (uniforms.containsKey(name)) {
            return uniforms.get(name);
        } else {
            int handle = GLES20.glGetUniformLocation(programHandle, name);
            uniforms.put(name, handle);
            return handle;
        }
    }

}

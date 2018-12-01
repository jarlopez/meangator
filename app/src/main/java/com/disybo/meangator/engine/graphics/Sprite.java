package com.disybo.meangator.engine.graphics;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.disybo.meangator.engine.graphics.shaders.Defaults;
import com.disybo.meangator.engine.math.Mat4;
import com.disybo.meangator.engine.math.Vec3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Sprite {
    private Texture texture;

    private FloatBuffer vertices;
    private FloatBuffer coordinates;

    private Mat4 transformationMat = new Mat4();
    private Mat4 mvpMat = new Mat4();

    public Sprite(Texture texture) {
        this(texture, new Vec3(0, 0, 0));
    }

    public Sprite(Texture texture, Vec3 origin) {
        this.texture = texture;
        Matrix.setIdentityM(transformationMat.rep, 0);
        Matrix.translateM(transformationMat.rep, 0, origin.x, origin.y, origin.z);
        initBuffers();
    }

    public void onDraw(final ShaderProgram program, final Mat4 viewProjectionMatrix) {
        onDraw(program, viewProjectionMatrix, transformationMat);
    }

    public void onDraw(final ShaderProgram program, final Mat4 viewProjectionMatrix, final Mat4 transformation) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.handle);
        GLES20.glUniform1i(program.getUniform(Defaults.Uniform.Texture), 0);

        vertices.position(0);
        GLES20.glVertexAttribPointer(
                Defaults.Attribute.Position.index(),
                3,
                GLES20.GL_FLOAT,
                false,
                7 * 4,
                vertices
        );
        GLES20.glEnableVertexAttribArray(Defaults.Attribute.Position.index());

        vertices.position(3);
        GLES20.glVertexAttribPointer(
                Defaults.Attribute.Color.index(),
                4,
                GLES20.GL_FLOAT,
                false,
                7 * 4,
                vertices
        );
        GLES20.glEnableVertexAttribArray(Defaults.Attribute.Color.index());

        coordinates.position(0);
        GLES20.glVertexAttribPointer(
                Defaults.Attribute.TextureCoordinate.index(),
                2,
                GLES20.GL_FLOAT,
                false,
                0,
                coordinates
        );
        GLES20.glEnableVertexAttribArray(Defaults.Attribute.TextureCoordinate.index());

        Matrix.multiplyMM(mvpMat.rep, 0, viewProjectionMatrix.rep, 0, transformation.rep, 0);

        GLES20.glUniformMatrix4fv(program.getUniform(Defaults.Uniform.MVP), 1, false, mvpMat.rep, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }

    private void initBuffers() {
        vertices = ByteBuffer
                .allocateDirect(Demo.vertexData.length * Demo.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertices.put(Demo.vertexData);
        vertices.position(0);

        coordinates = ByteBuffer
                .allocateDirect(Demo.coordData.length * Demo.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        coordinates.put(Demo.coordData);
        coordinates.position(0);
    }

}


class Demo {

    public static final int BYTES_PER_FLOAT = 4;
    public static final float[] vertexData = {
            // X, Y, Z,
            // R, G, B, A
            // Triangle 1
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,

            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,

            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,

            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
    };
    static final float[] coordData = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };
    static final float[] gridCoordData = {
            0.0f, 0.0f,
            0.0f, 64.f,
            64.f, 0.0f,
            0.0f, 64.f,
            64.f, 64.f,
            64.f, 0.0f,
    };
    private static final float SIZE = 0.3f;
    private static final float ZERO = 0.0f;
    private static final float ONE = 1.0f;
}
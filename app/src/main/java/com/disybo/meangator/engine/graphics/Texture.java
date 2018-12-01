package com.disybo.meangator.engine.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture {
    public int handle;

    private int resourceId;

    private Texture(int handle, int resourceId) {
        this.handle = handle;
        this.resourceId = resourceId;
    }

    public static Texture load(final Context ctx, final int resourceId) {
        final int[] handles = new int[1];
        GLES20.glGenTextures(1, handles, 0);
        if (handles[0] == 0) {
            throw new RuntimeException("Error generating texture name.");
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), resourceId, options);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handles[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        Texture texture = new Texture(handles[0], resourceId);
        return texture;
    }
}

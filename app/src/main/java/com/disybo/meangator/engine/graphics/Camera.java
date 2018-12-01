package com.disybo.meangator.engine.graphics;

import com.disybo.meangator.engine.math.Vec3;

public interface Camera {
    void move(Vec3 dir, float speed);

    void lookAt(Vec3 target);

    void resize(int width, int height);
}

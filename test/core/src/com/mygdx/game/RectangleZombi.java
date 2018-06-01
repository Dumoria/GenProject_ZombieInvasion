package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.awt.*;

public class RectangleZombi extends Rectangle {
    public float dx = 1;

    public float dy = 1;


    public RectangleZombi() {
        super(MathUtils.random(0, Gdx.graphics.getHeight() - 64), MathUtils.random(0, Gdx.graphics.getWidth() - 64), 64, 64);

    }

    public void move() {
        if (this.x < 0)
            this.dx = -1;
        if (this.x > 640 - 64)
            this.dx = -1;
        if (this.y < 0)
            this.dy = -1;
        if (this.y > 480 - 64)
            this.dy = -1;
        }

}

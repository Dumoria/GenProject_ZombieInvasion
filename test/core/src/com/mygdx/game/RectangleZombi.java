package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.awt.*;

public class RectangleZombi extends Rectangle {
    public float dx = MathUtils.random(0, 2) - 1;

    public float dy = MathUtils.random(0, 2) - 1;


    public RectangleZombi() {
        super(MathUtils.random(0, 640- 30), MathUtils.random(0, 480 - 30), 30, 30);
        while (dx == 0 && dy == 0) {
            dx = MathUtils.random(0, 2) - 1;
            dy = MathUtils.random(0, 2) - 1;
            ;
        }

    }

    public void move() {
        if (this.x < 0)
            this.dx *= -1;
        if (this.x > 640 -30){
            this.dx *= -1;

        }
        if (this.y < 0)
            this.dy *= -1;
        if (this.y > 480 -30){
            this.dy *= -1;
        }
    }

}

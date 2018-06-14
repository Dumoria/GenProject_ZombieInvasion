package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

import java.awt.*;

public class RecrangleBullet extends Rectangle {

    public float dx ;

    public float dy ;

    public RecrangleBullet(int x, int y){
        super(x,y,33,33);
    }

    public RecrangleBullet() {
    }
}

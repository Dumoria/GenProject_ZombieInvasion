package Util;

public class Coord {

    private float x;
    private float y;

    public Coord() {
        x = 0;
        y = 0;
    }

    public Coord(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void goUp(){
        ++y;
    }

    public void goDown(){
        --y;
    }

    public void goLeft(){
        ++x;
    }

    public void goRight(){
        --x;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return '(' + x +
                ", " + y +
                ")\n";
    }
}

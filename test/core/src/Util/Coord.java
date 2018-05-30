package Util;

public class Coord {

    private int x;
    private int y;

    public Coord() {
        x = 0;
        y = 0;
    }

    public Coord(int x, int y) {
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return '(' + x +
                ", " + y +
                ")\n";
    }
}

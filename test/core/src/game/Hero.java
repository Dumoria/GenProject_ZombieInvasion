package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Hero {

    private int x;
    private int y;
    private int prcHealth;
    private int prcArmor;
    private int nbBullets;
    private int nbChargors;
    private int nbCash;

    private final int FULL_CHARGOR = 15;


    private Rectangle hero; //check if circle
    private Texture herosImage;


    public Hero() {
        herosImage = new Texture("LogicScreen.jpg");

        x = 0;
        y = 0;
        prcHealth = 100;
        prcArmor = 0;
        nbBullets = FULL_CHARGOR;
        nbChargors = 1;
        nbCash = 0;

    }

    public void shoot(){
        if(nbBullets > 0)
            --nbBullets;

    }

    public void recharge(){
        if(nbChargors > 0){
            nbBullets = FULL_CHARGOR;
            --nbChargors;
        }

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

    public int getPrcHealth() {
        return prcHealth;
    }

    public int getPrcArmor() {
        return prcArmor;
    }

    public int getNbBullets() {
        return nbBullets;
    }

    public int getNbChargors() {
        return nbChargors;
    }

    public int getNbCash() {
        return nbCash;
    }

    public Rectangle getHero() {
        return hero;
    }

    public Texture getHerosImage() {
        return herosImage;
    }
}

package game;

import Util.Coord;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Hero {

    private static final int FULL_CHARGOR = 15;

    private Coord coord;
    private int prcHealth;
    private int prcArmor;
    private int nbBullets;
    private int nbChargors;
    private int nbCash;

    private Rectangle hero;
    private Texture herosImage;

    public Hero() {

        herosImage = new Texture("core/src/resources/mercenary1.png");
        hero = new Rectangle();
        coord = new Coord();
        prcHealth = 100;
        prcArmor = 0;
        nbBullets = FULL_CHARGOR;
        nbChargors = 1;
        nbCash = 0;

        hero.x = 800 / 2 - 32 / 2; // center the bucket horizontally
        hero.y = 16; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        hero.width = 32;
        hero.height = 32;

    }

    public void setHero(Rectangle hero) {
        this.hero = hero;
    }

    public void setHerosImage(Texture herosImage) {
        this.herosImage = herosImage;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
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

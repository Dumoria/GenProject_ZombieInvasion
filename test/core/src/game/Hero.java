package game;

import Util.Coord;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Hero {

    private static final int FULL_CHARGOR = 15;

    private int id;
    private Coord coord;
    private int prcHealth;
    private int prcArmor;
    private int nbBullets;
    private int nbChargors;
    private int nbCash;

    private Rectangle hero;
    private Texture herosImage;

    public Hero() {
        herosImage = new Texture("core/src/resources/mercenary1_0.png");

        coord = new Coord();
        prcHealth = 100;
        prcArmor = 0;
        nbBullets = FULL_CHARGOR;
        nbChargors = 1;
        nbCash = 0;

    }

    public int getId() {
        return id;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdFromClient(int id) {
        this.id = id;
    }

    public void setCoord(Coord coord) {
        this.id = (id * 10) + 1;
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

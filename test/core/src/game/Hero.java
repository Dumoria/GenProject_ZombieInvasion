package game;

import Util.Coord;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Hero extends Rectangle{

    private static final int FULL_CHARGOR = 15;

    public float dx,dy;
    private int prcHealth;
    private int prcArmor;
    private int nbBullets;
    private int nbChargors;
    private int nbCash;
    private Texture herosImage;

    public Hero() {
        super(800 / 2 - 32 / 2,16,32,32);
        herosImage = new Texture("core/src/resources/mercenary1.png");
        prcHealth = 100;
        prcArmor = 0;
        nbBullets = FULL_CHARGOR;
        nbChargors = 1;
        nbCash = 0;
        dx=0;
        dy=-1;

    }

    public void setHerosImage(Texture herosImage) {
        this.herosImage = herosImage;
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
    public Texture getHerosImage() {
        return herosImage;
    }
}

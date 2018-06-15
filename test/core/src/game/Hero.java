package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Hero extends Rectangle{

    private static final int FULL_CHARGOR = 30;

    public float dx,dy;
    private int prcHealth;
    private int prcArmor;
    private int nbBullets;
    private int nbChargors;
    private int nbCash;
    private Texture herosImage;
    private Random random;

    public Hero() {
        super(800 / 2 - 32 / 2,16,32,32);
        random = new Random();
        herosImage = new Texture("core/src/resources/mercenary1.png");
        prcHealth = 100;
        prcArmor = 0;
        nbBullets = FULL_CHARGOR;
        nbChargors = 1;
        nbCash = 0;
        dx=0;
        dy=-1;

    }

    public void getObject(){
        int chance = random.nextInt() % 3;
        switch(chance){
            case 0:
                nbCash += random.nextInt() % 100;
                break;
            case 1:
                nbChargors++;
                break;
            case 2:
                prcArmor += random.nextInt() % 100;
                if(prcArmor > 100) prcArmor = 100;
                break;
            default:
                break;
        }
    }

    public void setHerosImage(Texture herosImage) {
        this.herosImage = herosImage;
    }

    public void getDamage(int dmg){
        if(prcArmor > 0){
            prcArmor -= dmg;
            if(prcArmor < 0) {
                prcHealth += prcArmor;
                prcArmor = 0;
            }
        }else{
            prcHealth -= dmg;
        }
    }
    public void getShot(){
        getDamage(15);
    }

    public void getEat(){
        getDamage(1);
    }

    public void shoot(){
        if(nbBullets > 0)
            --nbBullets;
    }

    public boolean canShoot(){
        return nbBullets != 0;
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

package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bonus extends Rectangle{

    private static int nextId;
    private int id;
    private Texture bonusImage;
    public float dx ;
    public float dy ;

    public Bonus(float x, float y) {
        super(x,y,20,20);
        id = nextId++;
        bonusImage = new Texture("core/src/resources/toolkit.png");
    }

    public Texture getBonusImage() {
        return bonusImage;
    }
}
//Create interface for bonus, etc..
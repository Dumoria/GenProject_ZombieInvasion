package game;

import Util.Coord;





import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Enemy {
    Vector2 position;
    Texture enemyTexture;
    Rectangle bounds;
    Player player;

    public Enemy(Vector2 position, Player player){
        enemyTexture = new Texture(Gdx.files.internal("enemy.png"));
        this.position = position;
        bounds = new Rectangle(position.x, position.y, 25,25);
        this.player = player;
    }

    public void update(){
        bounds = new Rectangle(position.x, position.y, 25,25);
        if(player.getPosition().x > position.x){
            position.x++;
        }else{
            position.x--;
        }
        if(player.getPosition().y > position.y){
            position.y++;
        }else{
            position.y--;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Texture getEnemyTexture() {
        return enemyTexture;
    }

    public void setEnemyTexture(Texture enemyTexture) {
        this.enemyTexture = enemyTexture;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}

/*

import Util.Coord;

public class Ennemy {

    private static int nextId;

    private int id;
    private int nbShots;
    private Coord coord;

    public Ennemy() {
        id = (nextId++) * 10 + 2;
        nbShots = 0;
        coord = new Coord();
    }

    public Ennemy(int nbShots, Coord coord) {
        id = (nextId++) * 10 + 2;
        this.nbShots = nbShots;
        this.coord = coord;
    }

    public int getNbShots() {
        return nbShots;
    }

    public int getShot(){
        return ++nbShots;
    }

    public void setNbShots(int nbShots) {
        this.nbShots = nbShots;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public void updatePosition() {

    }
}
*/
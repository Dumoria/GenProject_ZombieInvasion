package screens;

import ClientServer.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import game.Hero;

import java.awt.*;
import java.util.Iterator;

public class GameScreen implements Screen {
    public static Texture backgroundTexture;
    private Client client;

    SpriteBatch batch=new SpriteBatch();
    Game game;
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;

    Hero hero;

    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    public GameScreen(Game game, Client client) {

        //this.hero =  BOUGER les mem, fct et autre de client a gamescreen. timer présent dans cette classe,
        //aavec reference au client on serialise le hero de cette classe, etc...
        this.game = game;
        this.client = client;
        client.startGame();


        backgroundTexture = new Texture("core/src/resources/grass.jpg");

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture("core/src/resources/mercenary1.png");
        bucketImage = new Texture("core/src/resources/mercenary1.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.

        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        batch.draw(backgroundTexture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            //bucket.x = touchPos.x - 64 / 2;
        }

        //Gérer après coup qu'on ne recharge pas inutilement l'image
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketImage = new Texture("core/src/resources/mercenary2.png");
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketImage = new Texture("core/src/resources/mercenary3.png");
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bucketImage = new Texture("core/src/resources/mercenary1.png");
            bucket.y -= 200 * Gdx.graphics.getDeltaTime();
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bucketImage = new Texture("core/src/resources/mercenary4.png");
            bucket.y += 200 * Gdx.graphics.getDeltaTime();
        }

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
      /*  Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }*/
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
       // rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
    }

}

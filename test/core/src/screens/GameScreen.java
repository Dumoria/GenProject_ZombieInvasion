package screens;

import ClientServer.Client;
import ClientServer.Json.BonusJson;
import ClientServer.Json.ClientJson;
import ClientServer.Json.JoueurJson;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.RecrangleBullet;
import com.mygdx.game.RectangleZombi;
import com.mygdx.game.Zombi_Invasion;
import game.Hero;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen implements Screen {
    public static Texture backgroundTexture;
    private Client client;

    SpriteBatch batch = new SpriteBatch();
    Zombi_Invasion game;
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;




    //----------------Data game members-------------------
    Hero hero;
    private LinkedList<JoueurJson> teamMate;
    private LinkedList<BonusJson> bonuses;
    private Timer timer;
    //Rectangle bucket;


    Texture shot;
    Array<RecrangleBullet> bullets;
    Array<RectangleZombi> zombis;
    long lastDropTime;
    private Music music_level1;
    int dropsGathered;

    public GameScreen(Zombi_Invasion game, Client client) {

        this.game = game;
        this.hero = new Hero();
        this.timer = new Timer();
        this.client = client;
        teamMate = new LinkedList<>();
        bonuses = new LinkedList<>();
        client.writeServer("Begin");
        startGame();

        shot = new Texture("core/src/resources/bomb_3.gif");

        music_level1 = Gdx.audio.newMusic(Gdx.files.internal("core/src/resources/Towards The End.mp3"));
        music_level1.setLooping(true);
        music_level1.play();
        backgroundTexture = new Texture("core/src/resources/grass.jpg");

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture("core/src/resources/mercenary1.png");
        bucketImage = new Texture("core/src/resources/zombi1.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        // create the zombis array and spawn the first raindrop
        zombis = new Array<RectangleZombi>();
        bullets=new Array<RecrangleBullet>();
        for (int i = 0; i < 7; ++i)
            spawnZombi();

    }

    private void spawnZombi() {
        RectangleZombi Zombi = new RectangleZombi();
        zombis.add(Zombi);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {

        batch.begin();

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        // begin a new batch and draw the bucket and
        // all drops
        batch.draw(hero.getHerosImage(), hero.getHero().x, hero.getHero().y, hero.getHero().width, hero.getHero().height);
        //batch.draw(shot, shotBucket.x, shotBucket.y, shotBucket.width, shotBucket.height);



        // begin a new batch and draw the bucket and
        // all drops
        //batch.draw(bucketImage, hero.getHero().x, hero.getHero().y, hero.getHero().width, hero.getHero().height);

        for (JoueurJson joueurJson : teamMate) {
            batch.draw(hero.getHerosImage(), joueurJson.getCoord().getX(), joueurJson.getCoord().getY(), hero.getHero().width, hero.getHero().height);
        }

        for (RectangleZombi raindrop : zombis) {
            batch.draw(bucketImage, raindrop.x, raindrop.y);
        }
        for (Rectangle bullet : bullets) {
            batch.draw(shot, bullet.x, bullet.y);
        }
        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            //bucket.x = touchPos.x - 64 / 2;
        }

        //Gérer après coup qu'on ne recharge pas inutilement l'image
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary2.png"));
            hero.getHero().x -= 200 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary3.png"));
            hero.getHero().x += 200 * Gdx.graphics.getDeltaTime();


        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary1.png"));
            hero.getHero().y -= 200 * Gdx.graphics.getDeltaTime();


        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary4.png"));
            hero.getHero().y += 200 * Gdx.graphics.getDeltaTime();


        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            RecrangleBullet shotBucket = new RecrangleBullet();
            shotBucket.dx=1;
            shotBucket.dy=1;
            shotBucket.width = 32;
            shotBucket.height = 32;
            bullets.add(shotBucket);
            batch.draw(shot, hero.getHero().x, hero.getHero().y, shotBucket.width, shotBucket.height);

        }

        // make sure the bucket stays within the screen bounds
        if (hero.getHero().x < 0)
            hero.getHero().x = 0;
        if (hero.getHero().x > 640 - 32)
            hero.getHero().x = 640 - 32;
        if (hero.getHero().y < 0)
            hero.getHero().y = 0;
        if (hero.getHero().y > 480 - 32)
            hero.getHero().y = 480 - 32;
        Iterator<RecrangleBullet> iterbullets = bullets.iterator();
        while (iterbullets.hasNext()) {
            RecrangleBullet bullet = iterbullets.next();
            bullet.y -= bullet.dy * 100 * Gdx.graphics.getDeltaTime();
            bullet.x -= bullet.dx * 100 * Gdx.graphics.getDeltaTime();
        }

        // move the zombis, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<RectangleZombi> iter = zombis.iterator();
        while (iter.hasNext()) {
            RectangleZombi raindrop = iter.next();
            raindrop.y -= raindrop.dy * 100 * Gdx.graphics.getDeltaTime();
            raindrop.x -= raindrop.dx * 100 * Gdx.graphics.getDeltaTime();
            //enlever ca pour qu'il aura pas le screen you lose

            /*
            if (pos_zomb_hero(hero.getHero().x, hero.getHero().y, raindrop.x, raindrop.y)) {

                float x = hero.getHero().x - raindrop.x;
                float y = hero.getHero().y - raindrop.y;
                double res = Math.sqrt(x * x + y * y);
                raindrop.x = (int) (hero.getHero().x-res);
                raindrop.y = (int) (hero.getHero().y - res);
            }*/



         /*   if (raindrop.contains(hero.getHero().x,hero.getHero().y)){
                game.setScreen(new LoseScreen(game));
                dispose();
            }*/

                raindrop.move();

        }

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    private boolean pos_zomb_hero(float xz, float yz, int xh, int yh) {
        float x = xz - xh;
        float y = yz - yh;
        double res = Math.sqrt(x * x + y * y);
        return res <= 100;
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
        hero.getHerosImage().dispose();
    }

    public void startGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                String str = client.getGson().toJson(new JoueurJson(client.getIdClient(), hero.getHero().x, hero.getHero().y));
                System.out.println(str);
                ClientJson str2 = client.getGson().fromJson(str, ClientJson.class);
                System.out.println(str2);

                client.writeServer(str);

                //if(!teamMate.isEmpty()) {
                try {
                    //attention, rempli en continu toujours plus de joueur pour l'instant
                    JoueurJson joueurJson = client.getGson().fromJson(client.readServer(), JoueurJson.class);
                    teamMate.add(joueurJson); //a terme utiliser fct pour reconnaitre parquet
                    System.out.println(joueurJson.getCoord().getX() + " " + joueurJson.getCoord().getY());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // }

            }
        }, 0, 3);
    }


}

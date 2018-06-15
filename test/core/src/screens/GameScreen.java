package screens;

import ClientServer.Client;
import ClientServer.Json.JoueurJson;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.RecrangleBullet;
import com.mygdx.game.RectangleZombi;
import com.mygdx.game.Zombi_Invasion;
import game.Bonus;
import game.Hero;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;

public class GameScreen implements Screen {
    public static Texture backgroundTexture;
    private Client client;

    SpriteBatch batch = new SpriteBatch();
    Zombi_Invasion game;
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;
    Random random = new Random();


    //----------------Data game members-------------------
    Hero hero;
    private LinkedList<JoueurJson> teamMate;
    private LinkedList<Bonus> bonuses;
    private Timer timer;
    //Rectangle bucket;


    Texture shot,zombiShot;
    Array<RecrangleBullet> bullets;

    Array<RecrangleBullet> bulletsZombi;
    Array<RectangleZombi> zombis;
    long lastDropTime;
    private Music music_level1;
    int dropsGathered;
    long lastShot = System.currentTimeMillis();

    BitmapFont fontCash = new BitmapFont();
    Texture cash = new Texture("core/src/resources/cash.png");
    BitmapFont fontHealth = new BitmapFont();
    Texture healt = new Texture("core/src/resources/health.png");
    BitmapFont fontArmor = new BitmapFont();
    Texture armor = new Texture("core/src/resources/armor.png");
    BitmapFont fontBullets = new BitmapFont();
    Texture bulletsCount = new Texture("core/src/resources/bullet.png");
    BitmapFont fontChargor = new BitmapFont();
    Texture chargor = new Texture("core/src/resources/charger.png");

    public GameScreen(Zombi_Invasion game, Client client) throws IOException {

        lastDropTime = TimeUtils.nanoTime();
        this.game = game;
        this.hero = new Hero();
        this.timer = new Timer();
        this.client = client;
        teamMate = new LinkedList<>();
        bonuses = new LinkedList<>();

        //startGame();
        zombiShot=new Texture("core/src/resources/bomb_3.gif");
        shot = new Texture("core/src/resources/bomb.jpg");

        music_level1 = Gdx.audio.newMusic(Gdx.files.internal("core/src/resources/Towards The End.mp3"));
        music_level1.setLooping(true);
        music_level1.play();
        backgroundTexture = new Texture("core/src/resources/grass.jpg");

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture("core/src/resources/mercenary1.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        // create the zombis array and spawn the first raindrop
        zombis = new Array<RectangleZombi>();
        bulletsZombi = new Array<RecrangleBullet>();
        bullets = new Array<RecrangleBullet>();
        for (int i = 0; i < 7; ++i)
            spawnZombi();

    }

    private void spawnZombi() {
        RectangleZombi Zombi = new RectangleZombi();
        zombis.add(Zombi);
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
        batch.draw(hero.getHerosImage(), hero.x, hero.y, hero.width, hero.height);
        //batch.draw(shot, shotBucket.x, shotBucket.y, shotBucket.width, shotBucket.height);


        // begin a new batch and draw the bucket and
        // all drops
        //batch.draw(bucketImage, hero.getHero().x, hero.getHero().y, hero.getHero().width, hero.getHero().height);

        for (RectangleZombi zombi : zombis) {
            if(zombi.nbRebound>3){
                zombi.nbRebound=0;
            RecrangleBullet shotBucket = new RecrangleBullet((int)zombi.x, (int)zombi.y);
            shotBucket.dx = zombi.dx;
            shotBucket.dy = zombi.dy;
            shotBucket.width = 32;
            shotBucket.height = 32;
            bulletsZombi.add(shotBucket);
            batch.draw(zombiShot, shotBucket.x, shotBucket.y, shotBucket.width, shotBucket.height);}
        }

        for (Bonus bonus : bonuses) {
            batch.draw(bonus.getBonusImage(), bonus.x, bonus.y, bonus.width, bonus.height);
        }

        for (JoueurJson joueurJson : teamMate) {
            batch.draw(hero.getHerosImage(), joueurJson.getCoord().getX(), joueurJson.getCoord().getY(), hero.width, hero.height);
        }

        for (RectangleZombi zombi : zombis) {
            batch.draw(zombi.zombiImage, zombi.x, zombi.y);
        }
        for (Rectangle bullet : bullets) {
            batch.draw(shot, bullet.x, bullet.y);
        }
        for (Rectangle bullet : bulletsZombi) {
            batch.draw(zombiShot, bullet.x, bullet.y);
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
            hero.x -= 200 * Gdx.graphics.getDeltaTime();
            hero.dx = 1;
            hero.dy = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary3.png"));
            hero.x += 200 * Gdx.graphics.getDeltaTime();
            hero.dx = -1;
            hero.dy = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary1.png"));
            hero.y -= 200 * Gdx.graphics.getDeltaTime();
            hero.dx = 0;
            hero.dy = 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            hero.setHerosImage(new Texture("core/src/resources/mercenary4.png"));
            hero.y += 200 * Gdx.graphics.getDeltaTime();
            hero.dx = 0;
            hero.dy = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && System.currentTimeMillis() - lastShot > 100 && hero.canShoot()) {
            RecrangleBullet shotBucket = new RecrangleBullet((int) hero.x, (int) hero.y);
            shotBucket.dx = hero.dx;
            shotBucket.dy = hero.dy;
            shotBucket.width = 32;
            shotBucket.height = 32;
            bullets.add(shotBucket);
            hero.shoot();
            lastShot = System.currentTimeMillis();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                hero.recharge();
        }
        // make sure the bucket stays within the screen bounds
        if (hero.x < 0)
            hero.x = 0;
        if (hero.x > 640 - 32)
            hero.x = 640 - 32;
        if (hero.y < 0)
            hero.y = 0;
        if (hero.y > 480 - 32)
            hero.y = 480 - 32;
        Iterator<RecrangleBullet> iterbullets = bullets.iterator();
        while (iterbullets.hasNext()) {
            RecrangleBullet bullet = iterbullets.next();
            bullet.y -= bullet.dy * 200 * Gdx.graphics.getDeltaTime();
            bullet.x -= bullet.dx * 200 * Gdx.graphics.getDeltaTime();
            if (bullet.x < 0 || bullet.x > 640 || bullet.y < 0 || bullet.y > 480) {
                iterbullets.remove();
                break;
            }
            for(RectangleZombi zombi : zombis){
                if(zombi.overlaps(bullet)) {
                    zombi.getShot();
                    iterbullets.remove();
                    break;
                }
            }
        }
        Iterator<RecrangleBullet> iterbulletsZombi = bulletsZombi.iterator();
        while (iterbulletsZombi.hasNext()) {
            RecrangleBullet bullet = iterbulletsZombi.next();
            bullet.y -= bullet.dy * 300 * Gdx.graphics.getDeltaTime();
            bullet.x -= bullet.dx * 300 * Gdx.graphics.getDeltaTime();
            if (bullet.x < 0 || bullet.x > 640 || bullet.y < 0 || bullet.y > 480) {
                iterbulletsZombi.remove();
                break;
            }
            if(hero.overlaps(bullet)){
                iterbulletsZombi.remove();
                hero.getShot();
                break;
            }
        }

        Iterator<Bonus> iterBonus = bonuses.iterator();
        while (iterBonus.hasNext()) {
            Bonus bonus = iterBonus.next();
            if(bonus.overlaps(hero)){
                iterBonus.remove();
                hero.getObject();
                break;
            }
        }

        // move the zombis, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<RectangleZombi> iter = zombis.iterator();
        while (iter.hasNext()) {
            RectangleZombi zombi = iter.next();
            zombi.y -= zombi.dy * 100 * Gdx.graphics.getDeltaTime();
            zombi.x -= zombi.dx * 100 * Gdx.graphics.getDeltaTime();
            zombi.move();
                //enlever ca pour qu'il aura pas le screen you lose

            if(zombi.overlaps(hero)){
                hero.getEat();
            }

            if(zombi.getNbShot() == 3) {
                if(random.nextInt() % 3 == 0)
                    bonuses.add(new Bonus(zombi.x, zombi.y));
                iter.remove();
                break;
            }
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



            }

            if(hero.getPrcHealth() <= 0){
                game.setScreen(new LoseScreen(game));
                dispose();
            }

            fontHealth.draw(batch, Integer.toString(hero.getPrcHealth()), 10, 30);
            batch.draw(healt, 6, 32, 32, 32);
            fontArmor.draw(batch, Integer.toString(hero.getPrcArmor()), 59, 30);
            batch.draw(armor, 50, 32, 32, 32);
            fontBullets.draw(batch, Integer.toString(hero.getNbBullets()), 96, 30);
            batch.draw(bulletsCount, 85, 32, 32, 32);
            fontChargor.draw(batch, Integer.toString(hero.getNbChargors()), 132, 30);
            batch.draw(chargor, 130, 32, 32, 32);
            fontCash.draw(batch, Integer.toString(hero.getNbCash()), 178, 30);
            batch.draw(cash, 170, 32, 32, 32);

            batch.end();

        }

        @Override
        public void resize ( int width, int height){
        }

        private boolean pos_zomb_hero ( float xz, float yz, int xh, int yh){
            float x = xz - xh;
            float y = yz - yh;
            double res = Math.sqrt(x * x + y * y);
            return res <= 100;
        }

        @Override
        public void show () {
            // start the playback of the background music
            // when the screen is shown
            // rainMusic.play();
        }

        @Override
        public void hide () {
        }

        @Override
        public void pause () {
        }

        @Override
        public void resume () {
        }

    /*public void startGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                String str = client.getGson().toJson(new JoueurJson(client.getIdClient(), hero.x, hero.y));
                System.out.println(str);
                ClientJson str2 = client.getGson().fromJson(str, ClientJson.class);
                System.out.println(str2);

                client.writeServer(str);

                try {
                    //interbloquage.
                    /*
                    On se connecte au premier client, envois ses coords puis se bloque en lecture
                    Serveur recoit est en fait rien
                    Deuxieme arrive et fait la meme. Nop
                    Serveur devrait transmettre au premier et relancer le mecanisme

                    PAR CONTRE, serveur va tenter de lire du premier client et va bloquer sur cette instruction



                    JoueurJson joueurJson = client.getGson().fromJson(client.readServer(), JoueurJson.class);
                    if(!teamMate.contains(joueurJson))
                        teamMate.add(joueurJson);
                    System.out.println(joueurJson.getCoord().getX() + " " + joueurJson.getCoord().getY());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3);
    }*/

        @Override
        public void dispose () {
            dropImage.dispose();
            hero.getHerosImage().dispose();
        }



    }

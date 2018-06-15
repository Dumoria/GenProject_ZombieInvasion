package screens;

import ClientServer.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Zombi_Invasion;

import java.io.IOException;

public class LoadingScreen implements Screen {
    String affich;
    BitmapFont loading = new BitmapFont();
    public static Texture backgroundTexture;
    private Client client;

    OrthographicCamera camera;
    private Music music_level1;
    SpriteBatch batch = new SpriteBatch();
    Zombi_Invasion game;

    public LoadingScreen(Zombi_Invasion game, Client client) {
       this.game=game;
       this.client=client;
        music_level1 = Gdx.audio.newMusic(Gdx.files.internal("core/src/resources/Towards The End.mp3"));
        music_level1.setLooping(true);
        music_level1.play();
        affich = "Loading";
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundTexture = new Texture("core/src/resources/grass.jpg");


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.begin();

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loading.draw(batch, affich, 300, 300);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

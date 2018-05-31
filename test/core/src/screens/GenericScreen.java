package screens;

import ClientServer.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public abstract class GenericScreen implements Screen {

    protected Game game;
    protected Stage stage;
    protected Skin skin;
    protected SpriteBatch batch;
    protected Texture img;
    protected Client client;

    public GenericScreen(Game game, Client client) {
        this.client = client;
        batch = new SpriteBatch();
        img = new Texture("core/src/resources/LoginScreen.jpg");

        this.game = game;
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

    }

    public GenericScreen(Game game, String pathImage) {
        batch = new SpriteBatch();
        img = new Texture(pathImage);

        this.game = game;
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
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
        batch.dispose();
        img.dispose();
    }
}

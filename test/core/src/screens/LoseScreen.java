package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;


    import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Zombi_Invasion;

public class LoseScreen   implements Screen {

        final Zombi_Invasion game;
    public BitmapFont font;

        OrthographicCamera camera;

        public LoseScreen(final Zombi_Invasion game) {
            this.game = game;
            font =new BitmapFont();
            font.getData().setScale(2,2);
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);

        }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        font.draw(game.batch, "You Lose!!! ", 100, 150);
        font.draw(game.batch, "Fuck Off", 100, 100);
        game.batch.end();

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
font.dispose();
    }
}

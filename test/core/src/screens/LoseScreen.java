package screens;

import ClientServer.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Zombi_Invasion;

import java.io.IOException;

public class LoseScreen implements Screen {
    private TextButton start;
    final Zombi_Invasion game;
    public BitmapFont font;
   final Client client;
    Stage stage;
    Skin skin;

    OrthographicCamera camera;

    public LoseScreen(final Zombi_Invasion game,final Client client) {
        this.client=client;
        this.game = game;
        font = new BitmapFont();

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font.getData().setScale(2, 2);
        camera = new OrthographicCamera();
        stage = new Stage();
        camera.setToOrtho(false, 800, 480);
        start =new TextButton("Recommencer",skin);
        start.setPosition(300,150);
        start.setSize(300,40);
        start.addListener(new

                                  ChangeListener() {
                                      @Override
                                      public void changed (ChangeListener.ChangeEvent event, Actor actor){


                                              game.setScreen(new StartScreen(game, client));


                                      }
                                  });
        stage.addActor(start);
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

        game.batch.end();
        game.batch.begin();

        font.draw(game.batch, "You Lose!!! ", 100, 150);
        stage.draw();
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

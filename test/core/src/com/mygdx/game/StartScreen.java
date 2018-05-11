package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.IOException;

public class StartScreen implements Screen {

    private Game game;
    private Stage stage;

    private TextButton start;
    private TextButton commands;
    private TextButton highScors;

    SpriteBatch batch;
    Texture img;

    public StartScreen(Game game) { //prob role aussi pour admin

        batch = new SpriteBatch();
        img = new Texture("core/src/resources/LoginScreen.jpg");

        this.game = game;
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        //-----------Start button-----------
        start = new TextButton("Start", skin);
        start.setPosition(300,150);
        start.setSize(300, 40);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        stage.addActor(start);

        //-----------Commands button-----------
        commands = new TextButton("Commands", skin);
        commands.setPosition(300,200);
        commands.setSize(300, 40);
        commands.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        stage.addActor(commands);

        //-----------highScors button-----------
        highScors = new TextButton("High scores", skin);
        highScors.setPosition(300,250);
        highScors.setSize(300, 40);
        highScors.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        stage.addActor(highScors);
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

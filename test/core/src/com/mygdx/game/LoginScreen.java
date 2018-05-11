package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.IOException;


public class LoginScreen implements Screen {

    private Game game;
    private Stage stage;

    private TextButton connect;
    private TextField username;
    private TextField password;

    private boolean logged;

    SpriteBatch batch;
    Texture img;

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public LoginScreen(final Game game, final Client client) {
        batch = new SpriteBatch();
        img = new Texture("core/src/resources/LoginScreen.jpg");

        this.game = game;
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));


        connect = new TextButton("Connect", skin);
        connect.setPosition(300,150);
        connect.setSize(300, 40);
        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    logged = client.loginUser(username.getText(), password.getText());
                    if(logged){
                        System.out.println("logged");
                        game.setScreen(new StartScreen(game));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.addActor(connect);

        username = new TextField("Username", skin);
        username.setPosition(300,250);
        username.setSize(300, 40);
        username.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                username.setText("");
            }
        });
        stage.addActor(username);

        password = new TextField("Password", skin);
        password.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                password.setText("");
            }
        });
        password.setPosition(300,200);
        password.setSize(300, 40);
        stage.addActor(password);
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

    public boolean isLogged() {
        return logged;
    }


}

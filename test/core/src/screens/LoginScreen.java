package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ClientServer.Client;
import javafx.scene.text.Font;

import javax.swing.*;
import java.io.IOException;


public class LoginScreen extends GenericScreen {

    private TextButton connect;
    private TextButton createPlayer;
    private TextField username;
    private TextField password;
    private Label titre;
    private Label userNameLogin;
    private Label mdpLogin;

    private boolean logged;


    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public LoginScreen(final Game game, final Client client) {
        super(game);
        titre = new Label("Zombie Invasion", skin);
        titre.setPosition(200, 380);
        titre.setFontScale(2);
        stage.addActor(titre);

        userNameLogin = new Label("userName", skin);
        userNameLogin.setPosition(40, 255);
        stage.addActor(userNameLogin);

        mdpLogin = new Label("password", skin);
        mdpLogin.setPosition(40, 205);
        stage.addActor(mdpLogin);
        connect = new TextButton("Connect", skin);
        connect.setPosition(120, 150);
        connect.setSize(120, 40);
        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    logged = client.loginUser(username.getText(), password.getText());
                    if (logged) {
                        System.out.println("logged");
                        game.setScreen(new StartScreen(game));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.addActor(connect);
        createPlayer = new TextButton("create player", skin);
        createPlayer.setPosition(370, 200);
        createPlayer.setSize(170, 70);
        createPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new CreatePlayer(game));
            }
        });

        stage.addActor(createPlayer);
        username = new TextField("Username", skin);
        username.setPosition(120, 250);
        username.setSize(120, 40);
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
        password.setPosition(120, 200);
        password.setSize(120, 40);
        stage.addActor(password);
    }


    public boolean isLogged() {
        return logged;
    }


}

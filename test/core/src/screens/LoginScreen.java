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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ClientServer.Client;

import java.io.IOException;


public class LoginScreen extends GenericScreen {

    private TextButton connect;
    private TextField username;
    private TextField password;
    private TextButton newUser;

    private boolean logged;


    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public LoginScreen(final Game game, final Client client) {

        super(game);

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

        // --------------------------------------

        newUser = new TextButton("New User", skin);
        newUser.setPosition(50,20);
        newUser.setSize(90, 30);
        newUser.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //save the command in a new file (commandsNew)
                game.setScreen(new CreatePlayer(game, client));
            }
        });

        stage.addActor(newUser);
    }


    public boolean isLogged() {
        return logged;
    }


}

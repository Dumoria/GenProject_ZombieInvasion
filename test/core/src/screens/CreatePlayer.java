package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.IOException;

public class CreatePlayer extends GenericScreen {
    private TextButton connect;
    private TextField username;
    private TextField password;
    private Label titre;
    private Label userNameLogin;
    private Label mdpLogin;
    public CreatePlayer(Game game) {
        super(game);
        titre = new Label("Zombie Invasion", skin);
        titre.setPosition(200, 380);
        titre.setFontScale(2);
        stage.addActor(titre);

        userNameLogin = new Label("userName", skin);
        userNameLogin.setPosition(140, 255);
        stage.addActor(userNameLogin);

        mdpLogin = new Label("password", skin);
        mdpLogin.setPosition(140, 205);
        stage.addActor(mdpLogin);
        connect = new TextButton("Create", skin);
        connect.setPosition(220, 150);
        connect.setSize(180, 40);
        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        stage.addActor(connect);

        username = new TextField("Username", skin);
        username.setPosition(220, 250);
        username.setSize(180, 40);
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
        password.setPosition(220, 200);
        password.setSize(180, 40);
        stage.addActor(password);
    }
}

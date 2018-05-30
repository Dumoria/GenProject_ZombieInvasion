package screens;

import ClientServer.Client.Client;
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
    private TextButton create;
    private TextButton previous;
    private TextField username;
    private TextField password;
    private Label titre;
    private Label userNameLogin;
    private Label mdpLogin;
    public CreatePlayer(final Game game, final Client client) {
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
        create = new TextButton("Create", skin);
        create.setPosition(220, 150);
        create.setSize(180, 40);
        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                   boolean logged = client.createUser(username.getText(), password.getText());
                    if(logged){
                        System.out.println("logged");
                        game.setScreen(new StartScreen(game));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.addActor(create);

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

        //-----------Save button-----------
        previous = new TextButton("previous", skin);
        previous.setPosition(500,60);
        previous.setSize(90, 30);
        previous.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //save the command in a new file (commandsNew)
                game.setScreen(new LoginScreen(game,client));
            }
        });

        stage.addActor(previous);
    }
}

package screens;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class StartScreen extends GenericScreen {


    private TextButton start;
    private TextButton commands;
    private TextButton highScors;


    public StartScreen(final Game game) { //prob role aussi pour admin

        super(game);

        //-----------Start button-----------
        start = new TextButton("Start", skin);
        start.setPosition(300,150);
        start.setSize(300, 40);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(new LoadingScreen(game));
                game.setScreen(new GameScreen(game));
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
                game.setScreen(new CommandsScreen(game));
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
                game.setScreen(new HighScoresScreen(game));
            }
        });

        stage.addActor(highScors);
    }

}

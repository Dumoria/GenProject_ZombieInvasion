package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class CommandsScreen extends GenericScreen {

    private TextButton save;
    private TextButton quit;
    private TextButton reinit;

    public CommandsScreen(final Game game) {
        super(game);

        //-----------save button-----------
        save = new TextButton("Start", skin);
        save.setPosition(50,20);
        save.setSize(90, 30);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //save the command in a new file
            }
        });

        stage.addActor(save);

        //-----------Commands button-----------
        quit = new TextButton("Commands", skin);
        quit.setPosition(500,60);
        quit.setSize(90, 30);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StartScreen(game));
            }
        });

        stage.addActor(quit);

        //-----------reinit button-----------
        reinit = new TextButton("High scores", skin);
        reinit.setPosition(500,20);
        reinit.setSize(90, 30);
        reinit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //reload config init
            }
        });

        stage.addActor(reinit);

    }

}

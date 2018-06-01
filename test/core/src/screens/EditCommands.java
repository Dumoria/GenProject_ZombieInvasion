package screens;

import ClientServer.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Zombi_Invasion;

public class EditCommands extends CommandsScreen
{


    public EditCommands(final Zombi_Invasion game, Client client) {
        super(game, client);

        super.init();
    }

}

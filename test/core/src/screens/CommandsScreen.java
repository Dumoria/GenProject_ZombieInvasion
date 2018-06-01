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

public class CommandsScreen extends GenericScreen {

    private TextButton save;
    private TextButton quit;
    private TextButton reinit;
    private TextButton forward;
    private TextButton backward;
    private TextButton left;
    private TextButton right;

    private TextField editforward;
    private TextField editbackward;
    private TextField editleft;
    private TextField editright;

    private TextButton listCommand;
    private TextButton editCommand;
    private String forwardChar;
    private String backwardChar;
    private String leftChar;
    private String rightChar;


    public String getForwardChar() {
        return forwardChar;
    }

    public String getBackwardChar() {
        return backwardChar;
    }

    public String getLeftChar() {
        return leftChar;
    }

    public String getRightChar() {
        return rightChar;
    }

    public void setForwardChar(String forwardChar) {
        this.forwardChar = forwardChar;
    }

    public void setBackwardChar(String backwardChar) {
        this.backwardChar = backwardChar;
    }

    public void setLeftChar(String leftChar) {
        this.leftChar = leftChar;
    }

    public void setRightChar(String rightChar) {
        this.rightChar = rightChar;
    }

    public CommandsScreen(final Zombi_Invasion game, final Client client) {
        super(game, client);

        init();
        //----------- Command List-----------
        listCommand = new TextButton(" Commands ", skin);
        listCommand.setPosition(65,300);
        listCommand.setSize(150, 25);
        stage.addActor(listCommand);

        //-----------left button-----------

        left = new TextButton("left", skin);
        left.setPosition(65,150);
        left.setSize(90, 30);
        stage.addActor(left);

        editleft = new TextField(getLeftChar(), skin);
        editleft.setPosition(160,150);
        editleft.setSize(60, 30);
        stage.addActor(editleft);

        //-----------right button-----------

        right = new TextButton("right", skin);
        right.setPosition(65,183);
        right.setSize(90, 30);
        right.getText();
        stage.addActor(right);

        editright = new TextField(getLeftChar(), skin);
        editright.setPosition(160,183);
        editright.setSize(60, 30);
        stage.addActor(editright);


        //-----------forward button-----------
        forward = new TextButton("forward", skin);
        forward.setPosition(65,215);
        forward.setSize(90, 30);
        forward.getText();
        stage.addActor(forward);

        editforward = new TextField( forwardChar, skin);
        editforward.setPosition(160,215);
        editforward.setSize(60, 30);
        stage.addActor(editforward);

        //-----------backward button-----------
        backward = new TextButton("backward", skin);
        backward.setPosition(65,245);
        backward.setSize(90, 30);
        backward.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                //
            }
        });
        backward.getText();
        stage.addActor(backward);

        //-----------forward edit button-----------
        editbackward = new TextField(backwardChar.toString(), skin);
        editbackward.setPosition(160,245);
        editbackward.setSize(60, 30);


        stage.addActor(editbackward);

        //----------- Edit Command -----------
        editCommand = new TextButton(" Edit Commands ", skin);
        editCommand.setPosition(50,50);
        editCommand.setSize(130, 32);
        editCommand.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new EditCommands(game, client)
                {});
            }
        });
        stage.addActor(editCommand);

        //-----------save button-----------
        save = new TextButton("Save", skin);
        save.setPosition(50,20);
        save.setSize(90, 30);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StartScreen(game, client));
            }
        });

        stage.addActor(save);

        //-----------Save button-----------
        quit = new TextButton("Quit", skin);
        quit.setPosition(500,60);
        quit.setSize(90, 30);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //save the command in a new file (commandsNew)
                game.setScreen(new StartScreen(game, client));
            }
        });

        stage.addActor(quit);

        //-----------reinit button-----------
        reinit = new TextButton("Reinit", skin);
        reinit.setPosition(500,20);
        reinit.setSize(90, 30);
        reinit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //reload  default commandsOrigin
                init();
            }
        });

        stage.addActor(reinit);



    }
    public void editCommand()
    {
        left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                left.setText("");
                //setForwardChar( left.getText());
            }
        });

    }

    public void init(){
        setForwardChar("w");
        setBackwardChar("s");
        setLeftChar("a");
        setRightChar("d");
    }


}
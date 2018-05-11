package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.io.IOException;

public class Zombi_Invasion extends Game {
	//SpriteBatch batch;
	//Texture img;

	@Override
	public void create () {
		Client client = new Client();

		try {
			client.connect("localhost", 2323);
		}catch(IOException e){
			System.out.println("Can't reach server");
		}

		this.setScreen(new LoginScreen(this, client));

		//batch = new SpriteBatch();
		//img = new Texture("core/assets/badlogic.jpg");
	}

	@Override
	public void render () {
		super.render();
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}

	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();
	}
}

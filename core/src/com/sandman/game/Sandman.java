package com.sandman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sandman.game.Scene.MainMenuScreen;


//Classe qui va géré les différentes scènes du jeu
public class Sandman extends Game{
    
    public SpriteBatch batch;
	public BitmapFont font;
	
	//Taille de notre �cran
	public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 200;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}

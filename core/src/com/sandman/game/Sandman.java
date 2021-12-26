package com.sandman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sandman.game.Scene.LevelJardin;


//Classe qui va géré les différentes scènes du jeu
public class Sandman extends Game{
    
    public SpriteBatch batch;
	public BitmapFont font;
	
	//Taille de notre Ecran
	public static final int V_WIDTH = 1600;
    public static final int V_HEIGHT = 900;
    
    //Pixels par metre
    public static final float PPM = 16;

	public void create() {
		batch = new SpriteBatch();
		//font = new BitmapFont();
		this.setScreen(new LevelJardin(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}

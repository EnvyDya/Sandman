package com.sandman.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sandman.game.Sandman;
import com.sandman.game.Scene.Level;

public class Hud extends Sprite{
	
	private Level level;
	
	public Hud(Level level) {
		super(new TextureRegion(new Texture("images/clock.png")));
		this.level = level;
		setBounds((level.getCamera().position.x), (level.getCamera().position.y), 64/Sandman.PPM, 64/Sandman.PPM);

	}
	
	public void update() {
 	    setPosition((level.getCamera().position.x) + (level.getCamera().viewportWidth/2) - 64/Sandman.PPM ,(level.getCamera().position.y) - (level.getCamera().viewportHeight/2));
	}
}

package com.sandman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.sandman.game.Sandman;

public class Water extends InteractiveTileObject{
	private Texture texture;
	private Rectangle bounds;
	
	public Water(World world, Rectangle bounds) {
		super(world, bounds);
		this.bounds = bounds;
		fixture.setUserData(this);
		texture = new Texture(Gdx.files.internal("iceFilter.png"));
	}
	
	/**
	 * Fonction de transformation de l'eau en glace
	 */
	public void onClick() {
		gel = !gel;
	}
	
	
	//TODO : Refaire le draw avec méthode perso²
	public void draw(SpriteBatch batch) {
		if(gel)
			batch.draw(texture, bounds.x/Sandman.PPM, bounds.y/Sandman.PPM, bounds.width/Sandman.PPM, bounds.height/Sandman.PPM);
	}
	
}